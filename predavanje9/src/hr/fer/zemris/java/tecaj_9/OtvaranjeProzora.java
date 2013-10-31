package hr.fer.zemris.java.tecaj_9;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class OtvaranjeProzora {

	// los nacin!!!
	// ovaj posao ne smije raditi dretva main, vec dretva za to! (EDT)
	public static void mainold(String[] args) {
		
		izdampajDretve("Stanje prije stvaranja prozora:");
		
		JFrame frame = new JFrame();
		frame.setLocation( 20, 20 ); // relative to desktop, top left coordinates
		frame.setSize( 500, 200 );
		
		// verzija 2:
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // radi kako "zelimo", oslobada resurse i gasi prozor
		// odabir EXIT_ON_CLOSE - poziva system.exit(0) i terminira proces trenutno
		
		frame.setVisible(true);	// tek od ovdje se prozor moze vidjeti
		
		// (default slucaj - HIDE_ON_CLOSE)
		// sada kad se zatvori prozor, proces i dalje radi
		// prozor samo postane "skriven"
		
		izdampajDretve("Stanje nakon stvaranja prozora:");
	}
	
	public static void izdampajDretve( String poruka ) {
		System.out.println( poruka );
		System.out.println( "===============================================" );
		
		ThreadGroup tg = Thread.currentThread().getThreadGroup();
		while (tg.getParent() != null) {
			tg = tg.getParent();
		}
		tg.list();
	}
	
	public static void main(String[] args) {
		
		izdampajDretve("Stvaranje prozora radi: " + Thread.currentThread().getName());
		
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame();
				frame.setLocation( 20, 20 ); // relative to desktop, top left coordinates
				frame.setSize( 500, 200 );
				
				// verzija 2:
				frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // radi kako "zelimo", oslobada resurse i gasi prozor
				// odabir EXIT_ON_CLOSE - poziva system.exit(0) i terminira proces trenutno
				
				frame.setVisible(true);	// tek od ovdje se prozor moze vidjeti
			}
		});
		
		
		// (default slucaj - HIDE_ON_CLOSE)
		// sada kad se zatvori prozor, proces i dalje radi
		// prozor samo postane "skriven"
		
		izdampajDretve("Stanje nakon stvaranja prozora:");
	}
}
