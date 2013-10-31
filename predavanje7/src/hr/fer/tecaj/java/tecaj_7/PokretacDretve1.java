package hr.fer.tecaj.java.tecaj_7;

public class PokretacDretve1 {

	public static void main(String[] args) {
		Thread t = new Dretva1();
		t.start();
		try {
			Thread.sleep(100);
		} catch (Exception ignorable) {
		}
		new Dretva1().start();
		new Dretva1().start();
		new Dretva1().start();
		new Dretva1().start();
		new Dretva1().start();
		new Dretva1().start();
		try {
			Thread.sleep(100);
		} catch (Exception ignorable) {
		}
	}
}
