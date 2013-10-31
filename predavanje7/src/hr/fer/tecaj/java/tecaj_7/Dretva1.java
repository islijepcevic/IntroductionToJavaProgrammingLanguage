package hr.fer.tecaj.java.tecaj_7;

/**
 * prvi nacin
 * @author is45342
 *
 */
public class Dretva1 extends Thread {

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
