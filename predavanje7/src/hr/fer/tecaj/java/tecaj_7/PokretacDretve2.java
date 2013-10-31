package hr.fer.tecaj.java.tecaj_7;

public class PokretacDretve2 {
	
	public static void main(String[] args) {
		PosaoDretve p = new PosaoDretve();
		new Thread(p).start();
		new Thread(p).start();
		new Thread(p).start();
		new Thread(p).start();
		new Thread(p).start();
	}

}
