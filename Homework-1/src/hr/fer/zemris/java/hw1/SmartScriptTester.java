package hr.fer.zemris.java.hw1;

import hr.fer.zemris.java.custom.scripting.parser.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.tokens.*;

/**
 * test class for SmartScriptParser
 * @author ivan
 *
 */
public class SmartScriptTester {

    /**
     * entry point
     * @param args ignored
     */
    public static void main( String[] args ) {

        String docBody1 = "Lala " +
        "[$ FOR Lala 1.9 2.0 5$]" +
        "\r\nTsk" +
        "[$= No \"goog\\\"\" 13 + @wa_ga$]" +
        "\\" +
        "[$END$]" +
        "[$FOR ni -1.0 2 $]" +
        "[$END$]" +
        "[$END$]";

        String docBody2 = "moj mali \t text\r\\je razdvojen\"navodnikom";
        String docBody3 = "Tekst \\[$\n\\\\[$= i $]";

        String docBody4 = "Tekst [$= @a32_t \"ja\\\\o\\t\" -3 2.5 $]";

        String docBody5 = "[$FOR i 99 a k$] [$FOR f 99 z 7$] [$END$][$END$]";

        // TESKI PRIMJER
        String docBody6 = "my\\aText\n\r\\[$ FOR abc 0.01 -1 @a_2 $]text\n [$FOR i @abc @a $][$=var * var \"My \\\\ str\\cing \\n text\" @var_ 12 -12.2 + -$][$ FOR abc 0 -1.0$][$=\"\\\\\"$][$END$]some[$=\"String\" a b @c $]Text[$END $]";

        // greska for loop variable:
        String docBody7 = "[$FOR 1 @abc ab a $]someText[$END$]";

        // greska for loop previse varijabli:
        String docBody8 = "[$FOR a @abc ab a b $]someText[$END$]";

        // greska razmak unutar oznake za tag:
        String docBody9 = "mojTekst[$ FOR i 1 2 $][ $ END $]";

        // greska previse endova:
        String docBodyA = "tekstString[$=\"test\" + - @car ab 0.12 -1 $][$ FOR i 0 1 $]test[$END$] [$ END$]";

        // greska premalo endova:
        String docBodyB = "moj\r[$ FOR i 0 1 $]test[$END$][$ FOR abc 0.12 -11 -0.1 $][$= \"\\n\\\\\" $]";

        // OSNOVNI PRIMJER
        String docBody = "This is sample text.\r\n[$ FOR i 1 10 1 $]\r\n This is [$= i $]-th time this message is generated.\r\n[$END$]\r\n[$FOR i 0 10 2 $]\r\n sin([$=i$]^2) = [$= i i * @sin \"0.000\" @decfmt $]\r\n[$END$]";

        SmartScriptParser parser = null;

        try {
            parser = new SmartScriptParser(docBody);
        } catch(SmartScriptParserException e) {
        	System.out.println(e.getMessage());
            System.out.println("Unable to parse document!");
            System.exit(-1);
        } catch(Exception e) {
            System.out.println("If this line ever executes, you deserve cookies!");
            System.exit(-1);
        }

        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = createOriginalDocumentBody((Node)document);

        // should write something like original
        // content of docBody
        System.out.println(originalDocumentBody); 
    }

    /**
     * original document recreation
     * @param document document node
     * @return string document
     */
    private static String createOriginalDocumentBody( Node document ) {
        String original = "";
        for (int i = 0; i < document.numberOfChildren(); i++) {
            Node child = document.getChild( i );

            if (child instanceof TextNode) {
                original += ((TextNode) child).getText();

            } else if (child instanceof ForLoopNode) {
                original += recreateForLoop( (ForLoopNode) child );

            } else if (child instanceof EchoNode) {
                original += recreateEcho( (EchoNode) child );

            } else {
                original += "<" + child.getClass() + ">";
            }
        }

        return original;
    }

    /**
     * for loop node recreation
     * @param loop for loop node
     * @return part of string document
     */
    private static String recreateForLoop( ForLoopNode loop ) {
        String original = "[$ FOR ";

        //tokens
        original += loop.getVariable().asText() + " ";
        original += loop.getStartExpression().asText() + " ";
        original += loop.getEndExpression().asText() + " ";
        original += loop.getStepExpression().asText() + " ";

        original += "$]";

        //for loop body
        original += createOriginalDocumentBody( loop );

        original += "[$ END $]";
        return original;
    }

    /**
     * echo node recreation
     * @param echo echo node
     * @return part of string document
     */
    private static String recreateEcho( EchoNode echo ) {
        String original = "[$= ";

        for (int i = 0; i < echo.getTokens().length; i++) {
            Token oneToken = echo.getTokens()[i];
            if (oneToken instanceof TokenFunction) {
                original += "@";
            }
            original += oneToken.asText() + " ";
        }
        
        original += "$]";
        return original;
    }

}
