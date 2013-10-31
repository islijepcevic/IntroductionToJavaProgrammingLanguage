package hr.fer.zemris.java.tecaj_9;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Prozor3 extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public Prozor3() {
		setLocation( 20, 20 );
		setSize( 500, 200 );
		setTitle("Moj prvi prozor");
		setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
		
		initGUI();
		
		pack(); // maksimalno postivanje preferiranih sirina/visina
	}
	
	private void initGUI() {
		
		getContentPane().setLayout( new BorderLayout() );
		
		// sa ovim final compiler ima dozvolu da implicitnim konstruktorom preda
		// varijablu anonimnom razredu malo ispod
		final JLabel labela = new JLabel("Ovo je poruka");
		//JButton gumb = new JButton("Stisni me");
		
		JPanel panel = new JPanel();
		panel.setLayout( new GridLayout(1, 3) );
		
		getContentPane().add( labela, BorderLayout.PAGE_START );
		getContentPane().add( panel, BorderLayout.CENTER );
		
		JButton gumb1 = new JButton("Stisni me1");
		JButton gumb2 = new JButton("Stisni me2");
		JButton gumb3 = new JButton("Stisni me3");
		
		panel.add( gumb1 );
		panel.add( gumb2 );
		panel.add( gumb3 );
		
		gumb3.addActionListener( new ActionListener() {
			int brojac;

			@Override
			public void actionPerformed(ActionEvent e) {
				brojac++;
				labela.setText( "Ovaj je gumb bio pritisnut " + brojac + " puta.");
				
			}
			
		});
		
		addWindowListener( new WindowListener() {
			
			private DretvaRadnik dretva;

			@Override
			public void windowOpened(WindowEvent e) {
				if (dretva == null) {
					dretva = new DretvaRadnik( labela );
					dretva.start();
				}
			}

			@Override
			public void windowClosing(WindowEvent e) {
			}

			@Override
			public void windowClosed(WindowEvent e) {
				if (dretva != null) {
					dretva.stopMe = true;
					dretva = null;
				}
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}
			
		});
	}
	
	public static void main( String[] args ) {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				new Prozor3().setVisible(true);
			}
		});
	}
	
	public static class DretvaRadnik extends Thread {
		private JLabel labela;
		private volatile boolean stopMe;
		private String[] poruke = new String[] {
			"Ovo je prva poruka",
			"Skoro smo gotovi s tecajem za danas",
			"Ali ipak imamo jos jednu stvar za odradit..."
		};
		private int index = 0;
		
		public DretvaRadnik( JLabel labela ) {
			this.labela = labela;
		}
		
		@Override
		public void run() {
			while (!stopMe) {
				// ispisi poruku
				SwingUtilities.invokeLater( new Posao(poruke[index], labela));
				index  = (index + 1) % poruke.length;
				
				// spavaj
				try {
					Thread.sleep( 2000 );
				} catch (InterruptedException ignorable) {}
			}
		}
		
		public static class Posao implements Runnable {
			private String poruka;
			private JLabel labela;
			
			public Posao( String poruka, JLabel labela ) {
				this.poruka = poruka;
				this.labela = labela;
			}

			@Override
			public void run() {
				labela.setText( poruka );
				
			}
		}
	}

}
