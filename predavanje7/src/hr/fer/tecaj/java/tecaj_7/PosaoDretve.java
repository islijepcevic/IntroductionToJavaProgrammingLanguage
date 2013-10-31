package hr.fer.tecaj.java.tecaj_7;

/**
 * drugi nacin
 * primjerak ovog razreda nam sluzi kao "adresa metode" koja se predaje dretvi u C-u
 * @author is45342
 *
 */
public class PosaoDretve implements Runnable {

	@Override
	public void run() {
		Thread t = Thread.currentThread();
		System.out.println("pozdravi iz dretve koja se zove: " + t.getName());
		System.out.println();
		
		ThreadGroup tg = t.getThreadGroup();
		while (tg.getParent() != null) {
			tg = tg.getParent();
		}
		tg.list();
	}
}
