package hr.fer.zemris.java.tecaj_9;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Prozor2 extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public Prozor2() {
		setLocation( 20, 20 );
		setSize( 500, 200 );
		setTitle("Moj prvi prozor");
		setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
		
		initGUI();
	}
	
	private void initGUI() {
		
		getContentPane().setLayout( null );
		
		JLabel labela = new JLabel("Ovo je poruka");
		JButton gumb = new JButton("Stisni me");
		
		labela.setBounds( 20, 20, 200, 15 ); // koordinate relativne u odnosu na prozor
		
		gumb.setLocation( 20, 40 );
		gumb.setSize( 200, 20 );
		
		getContentPane().add( labela);
		getContentPane().add( gumb );
	}
	
	public static void main( String[] args ) {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				new Prozor2().setVisible(true);
			}
		});
	}

}
