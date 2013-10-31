package hr.fer.zemris.java.tecaj_8.primjer1;

import java.util.LinkedList;
import java.util.List;

public class MyBlockingQueue {

	private List<Runnable> poslovi = new LinkedList<Runnable>();
	private int kapacitet;
	
	
	public MyBlockingQueue(int kapacitet) {
		super();
		this.kapacitet = kapacitet;
	}
	
	
	/**
	 * trenutni primjerak MyBlockingQueue -a je sinkronizacijski mutex
	 * @param posao
	 */
	public synchronized void ubaciPosao(Runnable posao) {
		while (this.poslovi.size() >= this.kapacitet) {
			while (true) {
				try {
					this.wait();
					break;
				} catch (InterruptedException ignorable) {
				}
			}
		}
		poslovi.add( posao );
		this.notifyAll();
	}
	
	public synchronized Runnable izvadiPosao() {
		while (this.poslovi.isEmpty()) {
			while (true) {
				try {
					this.wait();
					break;
				} catch (InterruptedException ignorable) {
				}
			}
		}
		
		Runnable posao = poslovi.remove(0);
		this.notifyAll();
		return posao;
	}
	
	
}
