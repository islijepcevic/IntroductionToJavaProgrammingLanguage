package hr.fer.zemris.java.tecaj_8.primjer1;

public class Glavni {

	public static void main(String[] args) {
		
		MyBlockingQueue queue = new MyBlockingQueue(2);
		
		for (int i = 0; i < 5; i++) {
			new Thread(new Potrosac(queue), "Potrosac_"+i).start();
		}
		
		for (int i = 0; i < 4; i++) {
			new Thread(new Proizvodac(i*3, queue), "Proizvodac_"+i).start();
		}
	}
	
	
	public static class Potrosac implements Runnable {
		
		private MyBlockingQueue queue;
		
		
		public Potrosac(MyBlockingQueue queue) {
			super();
			this.queue = queue;
		}
		
		@Override
		public void run() {
			while (true) {
				Runnable posao = queue.izvadiPosao();
				posao.run();
			}
		}
	}
	
	
	public static class Proizvodac implements Runnable {
		
		private int pocetniBrojPosla;
		private MyBlockingQueue queue;
		
		public Proizvodac(int pocetniBrojPosla, MyBlockingQueue queue) {
			super();
			this.pocetniBrojPosla = pocetniBrojPosla;
			this.queue = queue;
		}

		@Override
		public void run() {
			for (int i = 0; i < 3; i++) {
				Runnable posao = new Posao(pocetniBrojPosla + i);
				queue.ubaciPosao(posao);
			}
		}
	}
	
	public static class Posao implements Runnable {
		private int brojPosla;
		
		public Posao( int brojPosla) {
			super();
			this.brojPosla = brojPosla;
		}
		
		@Override
		public void run() {
			
			System.out.println("Dretva "+Thread.currentThread().getName() + " izvodi posao " + this.brojPosla);
			
			try {
				Thread.sleep(2000);
				
			} catch (InterruptedException ignorable) {
				
			}
			
		}
	}
	
}
