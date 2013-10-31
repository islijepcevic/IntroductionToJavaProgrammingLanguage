package hr.fer.zemris.java.tecaj_9;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class Prozor4 extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public Prozor4() {
		setLocation( 20, 20 );
		setSize( 500, 200 );
		setTitle("Moj prvi prozor");
		setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
		
		initGUI();
	}
	
	private void initGUI() {
		final ModelListeParnihBrojeva model = new ModelListeParnihBrojeva( 2 );
		JList<Integer> lista1 = new JList<Integer>(model);
		JList<Integer> lista2 = new JList<Integer>(model);
		JPanel panel = new JPanel(new GridLayout(1,2));
		panel.add( new JScrollPane( lista1 ) );
		panel.add( new JScrollPane( lista2 ) );
		JButton gumb = new JButton("stisni me");
		PrikazBrojaElemenata prikaz = new PrikazBrojaElemenata( model );
		
		getContentPane().setLayout( new BorderLayout() );
		getContentPane().add( panel, BorderLayout.CENTER );
		getContentPane().add( gumb, BorderLayout.PAGE_END );
		getContentPane().add( prikaz, BorderLayout.PAGE_START );
		
		gumb.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.povecajN();
			}
		});
	}
	
	public static void main( String[] args ) {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				new Prozor4().setVisible(true);
			}
		});
	}
	
	private static class ModelListeParnihBrojeva implements ListModel<Integer> {
		private int n;
		private List<ListDataListener> slusaci = new ArrayList<ListDataListener>();

		public ModelListeParnihBrojeva(int n) {
			super();
			this.n = n;
		}
		
		@Override
		public int getSize() {
			System.out.println("Netko me pita za velicinu.");
			return n;
		}
		
		@Override
		public Integer getElementAt( int index ) {
			System.out.println("Netko me pita za element " + index);
			return Integer.valueOf( index * 2 );
		}
		
		@Override
		public void addListDataListener(ListDataListener l) {
			System.out.println("Netko se je registrirao " + l);
			if (!slusaci.contains(l)) {
				slusaci.add(l);
			}
			
		}
		
		@Override
		public void removeListDataListener(ListDataListener l) {
			if (slusaci.contains(l)) {
				slusaci.remove( l );
			}
		}
		
		public void povecajN() {
			n++;
			
			ListDataEvent dogadaj = new ListDataEvent(
					this,
					ListDataEvent.INTERVAL_ADDED, 
					n-1, // inkluzivni index
					n-1	 // inkluzivni index
			);
			
			// krivi nacin obavjestavanja:
//			for (ListDataListener l : slusaci) { // opasno - lista se moze mijenjati dok se iterira
//				l.intervalAdded( dogadaj );
//			}
			
			ListDataListener[] polje = slusaci.toArray( new ListDataListener[0] );
			for (ListDataListener l : polje) {
				l.intervalAdded( dogadaj );
			}
		}
	}
	
	private static class PrikazBrojaElemenata extends JLabel implements ListDataListener {
		
		private static final long serialVersionUID = 1L;

		public PrikazBrojaElemenata( ListModel<Integer> model ) {
			azurirajPrikaz( model );
			model.addListDataListener( this );
		}

		@SuppressWarnings("unchecked")
		@Override
		public void intervalAdded(ListDataEvent e) {
			azurirajPrikaz( (ListModel<Integer>)e.getSource() );
		}

		@SuppressWarnings("unchecked")
		@Override
		public void intervalRemoved(ListDataEvent e) {
			azurirajPrikaz( (ListModel<Integer>)e.getSource() );
		}

		@SuppressWarnings("unchecked")
		@Override
		public void contentsChanged(ListDataEvent e) {
			azurirajPrikaz( (ListModel<Integer>)e.getSource() );
		}
		
		public void azurirajPrikaz( ListModel<Integer> model ) {
			setText("Broj elemenata je: " + model.getSize() );
		}
		
	}

}
