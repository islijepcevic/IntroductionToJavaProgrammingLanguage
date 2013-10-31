package hr.fer.tecaj.java.tecaj_7;

import java.util.concurrent.atomic.AtomicLong;

public class PokretanjeDretve {
	
	/*
	 *  ovo volatile znaci:
	 *  osigurava citanje pisanje varijable u/iz memorije a ne registra
	 *  (korisno za paralelnost)
	 *  moze i iz cache-a, ali to je ok jer se oni hardverski sinkroniziraju
	 *  nuzno se citaju/pisu svi bajtovi varijable bez nekog prekida 
	 */
	private volatile static long brojac;
	
	private static AtomicLong abrojac;
	
	public static void main(String[] args) {
		
		brojac = 0;
		abrojac = new AtomicLong(0);
		
		Object lokot = new Object();
		
		PosalDretve pd = new PosalDretve(lokot);
		
		
//		Thread t = new Thread(pd);
//		t.start();
//		
//		while (true) {
//			try {
//				t.join();
//				break;
//			} catch( InterruptedException e) {
//				continue;
//			}
//		}

		Thread[] dretve = new Thread[5];
		
		for (int i = 0; i<dretve.length; i++) {
			dretve[i] = new Thread(pd); 
		}
		for (int i = 0; i < dretve.length; i++) {
			dretve[i].start();
		}
		for (int i = 0; i < dretve.length; i++) {
			while (true) {
				try {
					dretve[i].join();
					break;
				} catch( InterruptedException e) {
					continue;
				}
			}
		}
		
		synchronized (lokot) {
			System.out.println("vrijednost brojaca je: " + brojac);
		}
		System.out.println("vrijednost atomic brojaca je: " + abrojac.get());
		
	}
	
	/*
	 * kao synchronized(OvajRazred.class) {} oko cijelog tijela metode
	 */
	public static synchronized void run() {
		
	}

	private static class PosalDretve implements Runnable {
		
		private Object lokot;
		
		public PosalDretve(Object lokot) {
			this.lokot = lokot;
		}
		
		@Override
		public void run() {
			for (int i = 0; i < 10000000; i++) {
				/*
				 * da bi ovo bio kriticni odsjecak, sve dretve se moraju sinkronizirati nad istim
				 * mutexom, sto moze biti bilo koji objekt
				 * u ovom slucaju to moze biti i this jer su sve dretve inicijalizirane sa istom
				 * instancom PosalDretve
				 */
				/*
				 * synchronized blok je cak i jaci od volatile, pa mi zato ne treba volatile za 
				 * varijable koje se tu mijenjaju
				 * sada kako bi se u mainu "sigurno" rijesilo citanje varijable brojac, treba
				 * i tamo dodati synchronized sa istim mutexom!
				 */
				synchronized(lokot) {
					brojac = brojac + 1;
				}
				
				while (true) {
					long stara = abrojac.get();
					long nova = stara + 1;
					//ako je stara jos uvijek stara, azuriraj na novu
					//vraca boolean ako je uspio azurirati
					if (abrojac.compareAndSet(stara, nova)) {
						break;
					}
				}
				
			}
		}
		
		/*
		 * zamjena za synchronized(this) koji stiti cijelu metodu:
		 */
		public synchronized void metoda() {
			
		}
	}
}
