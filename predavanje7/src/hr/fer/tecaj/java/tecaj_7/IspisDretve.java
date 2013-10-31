package hr.fer.tecaj.java.tecaj_7;

public class IspisDretve {
	
	public static void main(String[] args) {
		
		Thread t = Thread.currentThread();
		
		System.out.println("Moje ime je: " + t.getName());
		//10 je max prioritet, 1 je min
		System.out.println("Moj prioritet je: " + t.getPriority());
		System.out.println("Jesam li ziva: " + t.isDaemon());
		System.out.println("U kojoj sam ja grupi: " + t.getThreadGroup().getName());
		System.out.println();
		
		ThreadGroup tg = t.getThreadGroup();
		while (tg.getParent() != null) {
			tg = tg.getParent();
		}
		
		// sada mi je tg vrsni threadgroup
		tg.list();
		
		// reference handler - ima max prioritet, tamo je garbage collector
		// finalizer zove finalizator metode, a objekte mu daje garb. coll.
	}

}
