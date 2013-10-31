package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.*;
import hr.fer.zemris.java.custom.scripting.tokens.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;

/**
 * parser for smart scripts
 * @author ivan
 *
 */
public class SmartScriptParser {

    /**
     * states for the parsing
     */
    private enum State {
        TEXT, FOR, ECHO
    }

    private DocumentNode document;
    private String textBody;
    private int position;
    private State state;

    /**
     * constructor for the parser, calls the parsing immediately
     * @param body text to be parsed
     */
    public SmartScriptParser( String body ) {
        textBody = body;
        document = new DocumentNode();
        position = 0;
        parseBody();
    }

    /**
     * getter for document
     * @return document
     */
    public DocumentNode getDocumentNode() {
        return document;
    }

    /**
     * main parsing method
     */
    private void parseBody() throws SmartScriptParserException {
        // init stack and push document
        ObjectStack stack = new ObjectStack();
        stack.push( document );

        Node top;

        while (position < textBody.length()) {
            decipherState();
            switch (state) {
                case TEXT:
                    TextNode textNode = parseText();
                    top = (Node) stack.pop();
                    top.addChildNode( textNode );
                    stack.push( top );
                    break;

                case FOR:
                    try {
                        ForLoopNode forLoopNode = parseNonEmptyTag();
                        top = (Node) stack.pop();
                        top.addChildNode( forLoopNode );
                        stack.push( top );
                        stack.push( forLoopNode );

                    } catch (EndLoopException e) {
                        stack.pop();
                        if (stack.isEmpty()) {
                            throw new SmartScriptParserException (
                                    "Too much END tags"
                                    );
                        }
                    }
                    break;

                case ECHO:
                    EchoNode echoNode = parseEchoNode();
                    top = (Node) stack.pop();
                    top.addChildNode( echoNode );
                    stack.push( top );
                    break;

                default:
                    throw new SmartScriptParserException(
                            "Error state while parsing"
                            );
                    // end switch
            }
            // end while
        }
            // end try
        /*} catch (StringIndexOutOfBoundsException e) {
            throw new SmartScriptParserException(
                    "Document isn't well structured, encountered end before " +
                    "parsing has finished"
                    );
        }*/

        // remove document from stack
        stack.pop();
        if (!stack.isEmpty()) {
            throw new SmartScriptParserException(
                    "Document isn't well structured, possibly not every loop was ended"
                    );
        }

    }

    /**
     * used to decide which node is about to be parsed next, based on current
     * position
     * the language specification allows this
     * @return new State enum
     */
    private void decipherState() throws SmartScriptParserException {

        state = State.TEXT;

        if (textBody.charAt( position ) == '[') {
            position++;
            // check for [$
            if (textBody.charAt( position ) != '$') {
                throw new SmartScriptParserException(
                        "Found [, but tag needs to be opened with [$"
                        );
            }

            position++;
            if (textBody.charAt( position ) == '=') {
                state = State.ECHO;
                position++;
            } else {
                state = State.FOR;
            }
        }
    }

    /**
     * parses the next text chunk from textBody
     * @return text node with found text
     */
    private TextNode parseText() {

        boolean backSlashed = false;
        // position of character after the ending of found text
        int end = position;

        String text = "";

        // move until opened square bracket is found (that is not backslashed)
        while (textBody.charAt( end ) != '[' || backSlashed) {
            if (backSlashed) {
                backSlashed = false;

                // escaping '\\' -> '\'
                if ( textBody.charAt( end ) == '\\' ) {
                    text += textBody.substring( position, end );
                    position = end + 1;
                    // end will increase anyway in the end of loop
                }
            } else if (textBody.charAt( end ) == '\\') {
                backSlashed = true;
            }

            end++;
            if (passedBodyLength( end )) {
                break;
            }
        }

        text += textBody.substring( position, end );

        TextNode textNode = new TextNode( text );
        position = end;
        return textNode;
    }

    /**
     * parses the next non empty tag from textBody
     * @return for loop node with needed tokens, or throws EmptyStackException
     *          if tag is empty
     */
    private ForLoopNode parseNonEmptyTag() {

        position = ignoreWhite( position );
        String tagName = textBody.substring( position, position + 3 );
        position += 3;
        switch (tagName) {
            case "FOR":
                return parseForLoopNode();
            case "END":
                parseEndTag();
            default:
            throw new SmartScriptParserException(
                "wrong tag name"
            );
        // switch end
        }
    }

    /**
     * parses the next echo tag from textBody
     * @return echo node with needed tokens
     */
    private EchoNode parseEchoNode() throws SmartScriptParserException {
        ArrayBackedIndexedCollection myTokens = new 
            ArrayBackedIndexedCollection(5);

        while (textBody.charAt( position ) != '$') {
        	position = ignoreWhite( position );
            if (passedBodyLength(position)) {
                break;
            }

            myTokens.add( recogniseToken() );
            position = ignoreWhite( position );

            if (passedBodyLength( position )) {
                throw new SmartScriptParserException(
                    "reached end inside echo node"
                );
            }
        }

        checkTagEnding();

        Token[] tokens = new Token[myTokens.size()];
        for (int i = 0; i < myTokens.size(); i++) {
            tokens[i] = (Token) myTokens.get(i);
        }

        return new EchoNode( tokens );
    }

    /**
     * parses the for loop tag, after the FOR keyword
     * @return for loop node
     */
    private ForLoopNode parseForLoopNode() throws SmartScriptParserException {

        position = ignoreWhite( position );
        TokenVariable variable;
        try {
            variable = (TokenVariable) recogniseToken();
        } catch (ClassCastException e) {
            throw new SmartScriptParserException(
                    "FOR loop: in the place of variable was something else"
                    );
        }

        position = ignoreWhite( position );
        Token startExpression = recogniseToken();

        position = ignoreWhite( position );
        Token endExpression = recogniseToken();

        position = ignoreWhite( position );
        ForLoopNode forLoopNode;

        if (passedBodyLength( position )) {
            throw new SmartScriptParserException(
                "script should not end in the middle of the for loop tag"
            );
        }

        if ( textBody.charAt( position ) != '$' ) {
            Token stepExpression = recogniseToken();
            position = ignoreWhite( position );

            forLoopNode = new ForLoopNode( variable, startExpression,
                    endExpression, stepExpression );
        } else {
            forLoopNode = new ForLoopNode( variable, startExpression,
                    endExpression );
        }

        checkTagEnding();
        return forLoopNode;
    }


    /**
     * makes sure end tag is parsed properly
     */
    private void parseEndTag() throws SmartScriptParserException,
                                         EndLoopException {
        position = ignoreWhite( position );
        position++;
        if (passedBodyLength( position )) {
            throw new SmartScriptParserException(
                "reached end before the end of the end tag"
            );
        }
        position++;
        throw new EndLoopException();
    }

    /**
     * recognises and returns token
     * @return recognised token
     */
    private Token recogniseToken() throws SmartScriptParserException {

        if (passedBodyLength( position ) || passedBodyLength( position + 1 )) {
            throw new SmartScriptParserException(
                "reached end while parsing a token"
            );
        }

        char first = textBody.charAt( position );
        char second = textBody.charAt( position + 1 );
        Token token;

        if ( Character.isLetter( first ) ) {
            token = new TokenVariable( getIdentifier() );

        } else if (Character.isDigit( first ) || first == '.' ||
                (first == '-'  && (!isWhiteSpace( second ) && second != '$'))) {
            token = parseConstant();

        } else if (first == '"') {
        	position++;
            token = parseString();

        } else if ( first == '@') {
            position++;
            token = new TokenFunction( getIdentifier() );

        } else if (first == '-' || first == '/' || first == '+' || first == '*')
            {
            token = parseOperator();

        } else {
            throw new SmartScriptParserException(
                "can't recognise a token"
            );
        }

        return token;
    }

    /**
     * parses identifier name (variable or function name)
     * @return name of identifier
     */
    private String getIdentifier() throws SmartScriptParserException {
        
        int end = position;
        char endChar = textBody.charAt( end );

        while (!isWhiteSpace( endChar ) && endChar != '$') {

            if ( !Character.isLetterOrDigit( endChar ) && endChar != '_' ) {
                throw new SmartScriptParserException(
                    "Wrong identifier name at character: \"" + endChar + "\""
                );
            }

            end++;
            if (passedBodyLength( end )) {
                throw new SmartScriptParserException(
                    "reached end while parsing a token variable or function"
                );
            }
            endChar = textBody.charAt( end );
        }

        String identifier = textBody.substring( position, end );
        position = end;

        return identifier;
    }

    /**
     * parses number constant
     * @return double or integer token constant
     */
    private Token parseConstant() throws SmartScriptParserException {

        int end = position;
        boolean isDouble = false;
        boolean foundDot = false;
        boolean foundE = false;
        boolean hasDigit = false;
        
        char endChar = textBody.charAt( end );
        char charBefore;
        while (!isWhiteSpace( endChar ) && endChar != '$') {

            if (!Character.isDigit( endChar ) && endChar != '-' &&
                 endChar != '.' && endChar != 'e') {
                throw new SmartScriptParserException(
                    "Wrong constant"
                );
            }

            if (endChar == '.') {
                if (foundDot || foundE) {
                    throw new SmartScriptParserException(
                        "floating point in wrong position"
                    );
                }
                isDouble = true;
                foundDot = true;

            } else if (endChar == 'e' || endChar == 'E') {
                if (foundE) {
                    throw new SmartScriptParserException(
                        "exponential operator in wrong position"
                    );
                }
                foundE = true;

            } else if (Character.isDigit( endChar )) {
                hasDigit = true;

            } else if (endChar == '-') {
                charBefore = textBody.charAt( end - 1 );
                if (end != position && charBefore != 'e' && charBefore != 'E') {
                    throw new SmartScriptParserException(
                        "- sign in a wrong place in constant"
                    );
                }
            } else {
                throw new SmartScriptParserException(
                    "unexpected character in a constant"
                );
            }

            end++;
            if (passedBodyLength( end )) {
                throw new SmartScriptParserException(
                    "reached end while parsing a token constant"
                );
            }
            endChar = textBody.charAt( end );
        }

        if (!hasDigit) {
            throw new SmartScriptParserException(
                "no digit in a constant"
            );
        }

        String textValue = textBody.substring( position, end );
        position = end;
               
        Token token;
        if (isDouble) {
            double value = Double.parseDouble( textValue );
            token = new TokenConstantDouble( value );
        } else {
            int value = Integer.parseInt( textValue );
            token = new TokenConstantInteger( value );
        }

        return token;
    }

    /**
     * parses string token with correct rules,
     * before call, position should be on starting "
     * @return string token
     */
    private TokenString parseString() throws SmartScriptParserException {
        int end = position;
        boolean backSlashed = false;

        String parsedString = "";
        char endChar = textBody.charAt( end );

        while (endChar != '"') {

            if (backSlashed) {
            	backSlashed = false;
                parsedString += textBody.substring( position, end - 1 );

                switch (endChar) {
                    case '\\':
                        parsedString += "\\";
                        break;
                    case '"':
                        parsedString += "\"";
                        break;
                    case 'n':
                        parsedString += "\n";
                        break;
                    case 'r':
                        parsedString += "\r";
                        break;
                    case 't':
                        parsedString += "\t";
                        break;
                    default:
                        parsedString += "\\" + endChar;
                        break;
                // end switch
                }
                position = end + 1;
                // end will automatically increase

            // not backSlashed case
            } else if (endChar == '\\') {
                backSlashed = true;
            }

            end++;
            if (passedBodyLength( end )) {
                throw new SmartScriptParserException(
                    "reached an end while parsing token string"
                );
            }
            endChar = textBody.charAt( end );
        // end while
        }

        parsedString += textBody.substring( position, end );
        position = end + 1;

        return new TokenString( parsedString );
    }

    /**
     * parses operator
     * @return operator token
     */
    private TokenOperator parseOperator() {

        String operator = textBody.substring( position, position + 1 );
        position++;

        TokenOperator tokenOperator = new TokenOperator( operator );
        return tokenOperator;
    }

    /**
     * gets the next non white-space character in textBody, starting from index
     * @param index index of character from which searching begins
     * @return index of first non white-space character, can be the same as
     *      param index
     */
    private int ignoreWhite( int index ) {
        if (passedBodyLength( index )) {
            return index;
        }

        char end = textBody.charAt( index );
        while (isWhiteSpace( end )) {
            index++;
            if (passedBodyLength( index )) {
                break;
            }
            end = textBody.charAt( index );

            if (passedBodyLength( end )) {
                // not needed to throw exception here,
                // it is always checked after this function
                break;
            }
        }
        return index;
    }
    
    /**
     * checks if character is whitespace
     * @param character character to check
     * @return true if whitespace
     */
    private boolean isWhiteSpace( char character ) {
        if (character == ' ' || character == '\t' || character == '\n' ||
            character == '\r') {
            return true;
        }
        return false;
    }

    /**
     * checks if tag ended propely (to be called when position should be at $)
     */
    private void checkTagEnding() throws SmartScriptParserException {
        position++;
        if (passedBodyLength(position) || textBody.charAt( position ) != ']' ) {
            throw new SmartScriptParserException(
                "Tag should end with $]"
            );
        }
        position++;
        // no need to call passedBodyLength,
        // now first to check position will be the condition in main parsing loop
    }

    /**
     * checks if index is past the length of textBody string
     * @param index index to check
     * @return true if index is after the end of textBody
     */
    private boolean passedBodyLength( int index ) {
        if (index >= textBody.length()) {
            return true;
        }
        return false;
    }

}
