package hr.fer.zemris.java.tecaj_10.notepad;

import hr.fer.zemris.java.tecaj_10.local.LocalizationProvider;
import hr.fer.zemris.java.tecaj_10.local.swing.LocalizableAction;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class JNotepad extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JTextArea editor;
	private Path openFilePath;

	public JNotepad() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(0, 0);
		setSize(600, 600);
		
		initGUI();
	}
	
	private void initGUI() {
		getContentPane().setLayout(new BorderLayout());
		
		editor = new JTextArea();
		getContentPane().add( new JScrollPane(editor), BorderLayout.CENTER );
		
		createActions();
		createMenus();
		createToolbar();
	}
	
	private void createActions() {
		openFileAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openFileAction.putValue(
			Action.ACCELERATOR_KEY,
			KeyStroke.getKeyStroke("control O")					
		);
		
		saveFileAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveFileAction.putValue(
			Action.ACCELERATOR_KEY,
			KeyStroke.getKeyStroke("control S")		
		);
		
		deleteSelectedTextPartAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
		deleteSelectedTextPartAction.putValue(
			Action.ACCELERATOR_KEY,
			KeyStroke.getKeyStroke("control F2")		
		);
		
		toggleCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		toggleCaseAction.putValue(
			Action.ACCELERATOR_KEY,
			KeyStroke.getKeyStroke("control F3")		
		);
	}

	private void createMenus() {
		JMenuBar menubar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		menubar.add( fileMenu );
		
		fileMenu.add(new JMenuItem(openFileAction));
		fileMenu.add(new JMenuItem(saveFileAction));
		
		JMenu editMenu = new JMenu("Edit");
		menubar.add( editMenu );
		
		editMenu.add( new JMenuItem(deleteSelectedTextPartAction) );
		editMenu.add( new JMenuItem(toggleCaseAction) );
		
		setJMenuBar(menubar);
	}

	private void createToolbar() {
		JToolBar toolbar = new JToolBar("Alati");
		toolbar.add(new JButton(openFileAction));
		toolbar.add(new JButton(saveFileAction));
		toolbar.addSeparator();
		toolbar.add(new JButton(deleteSelectedTextPartAction));
		toolbar.add(new JButton(toggleCaseAction));
		
		getContentPane().add(toolbar, BorderLayout.PAGE_START);
	}
	
	
	private Action openFileAction = new LocalizableAction(LocalizationProvider.getInstance(), "OpenKey") {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("otvaranje datoteke");
			
			if (jfc.showOpenDialog(JNotepad.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			
			File fileName = jfc.getSelectedFile();
			Path filePath = fileName.toPath();
			
			if (!Files.isReadable(filePath)) {
				JOptionPane.showConfirmDialog(
						JNotepad.this,
						"Datoteka " + fileName.getAbsolutePath() + " ne psotoji",
						"Nastupila je pogreska",
						JOptionPane.ERROR_MESSAGE
				);
				return;
			}
			
			byte[] sadrzaj = null;
			try {
				sadrzaj = Files.readAllBytes(filePath);
				
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(
						JNotepad.this,
						"Datoteka " + fileName.getAbsolutePath() + "ne psotoji",
						"Nastupila je pogreska",
						JOptionPane.ERROR_MESSAGE
				);
				return;
			}
			
			openFilePath = filePath;
			String text = new String( sadrzaj, StandardCharsets.UTF_8);
			editor.setText(text);
		}		
		
	};
	
	private Action saveFileAction = new LocalizableAction(LocalizationProvider.getInstance(), "SaveKey") {
				
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (openFilePath == null) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Pohrana datoteke");
				if (jfc.showSaveDialog(JNotepad.this) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showConfirmDialog(
							JNotepad.this,
							"Odustali ste od snimanja datoteke. Nista nije pohranjeno",
							"Upozorenje",
							JOptionPane.WARNING_MESSAGE
					);
					return;
				}
				openFilePath = jfc.getSelectedFile().toPath();
			}
			
			byte[] sadrzaj = editor.getText().getBytes( StandardCharsets.UTF_8 );
			try {
				Files.write(openFilePath, sadrzaj);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(
						JNotepad.this,
						"Datotceku " + openFilePath.toFile().getAbsolutePath() + " nije moguce spremiti",
						"Nastupila je pogreska",
						JOptionPane.ERROR_MESSAGE
				);
				return;
			}
			JOptionPane.showMessageDialog(
					JNotepad.this,
					"Datotceka " + openFilePath.toFile().getAbsolutePath() + " je pohranjena",
					"Informacija",
					JOptionPane.INFORMATION_MESSAGE
			);
		}
	};
	
	private Action deleteSelectedTextPartAction = new LocalizableAction(LocalizationProvider.getInstance(), "RemSelPrt") {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = editor.getDocument();
			int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
			
			if (len == 0) {
				return;
			}
			
			int offs = Math.min( editor.getCaret().getDot(), editor.getCaret().getMark());
			try {
				doc.remove( offs, len );
			} catch (BadLocationException e1) {
				// Ovo se ne smije dogodit - ak se desi, treba debuggirat program
				e1.printStackTrace();
				return;
			}
			editor.getCaret().setDot( offs );
		}
	};
	
	private Action toggleCaseAction = new LocalizableAction(LocalizationProvider.getInstance(), "TglCase") {
		
		private static final long serialVersionUID = 1L;
		
		boolean jezik = false;

		@Override
		public void actionPerformed(ActionEvent e) {
			
			jezik = !jezik;
			LocalizationProvider.getInstance().setLanguage(jezik?"en":"hr");
			
			Document doc = editor.getDocument();
			int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
			int offset = 0;
			
			if (len == 0) {
				// nista nije selektirano
				len = doc.getLength();
			} else {
				offset = Math.min( editor.getCaret().getDot(), editor.getCaret().getMark());
			}
			
			try {
				String text = doc.getText(offset, len);
				text = toggleCase(text);
				doc.remove( offset, len );
				doc.insertString(offset, text, null);
			} catch (BadLocationException e1) {
				// Ovo se ne smije dogodit - ak se desi, treba debuggirat program
				e1.printStackTrace();
				return;
			}
			editor.getCaret().setDot( offset );
		}

		private String toggleCase(String text) {
			char[] data = text.toCharArray();
			for (int i = 0; i < data.length; i++) {
				char c = data[i];
				if (Character.isUpperCase(c)) {
					data[i] = Character.toLowerCase(c);
				} else if (Character.isLowerCase(c)) {
					data[i] = Character.toUpperCase(c);
				}
			}
			return new String(data);
		}
	};
	
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater( new Runnable() {
			
			@Override
			public void run() {
			
				new JNotepad().setVisible( true );
			}
		});
	}

}
