package hr.fer.zemris.java.tecaj_8.fraktali;

import hr.fer.zemris.java.tecaj_06.fractals.FractalViewer;
import hr.fer.zemris.java.tecaj_06.fractals.IFractalProducer;
import hr.fer.zemris.java.tecaj_06.fractals.IFractalResultObserver;
import hr.fer.zemris.java.tecaj_8.fraktali.MandelbrotParalelno2.GeneratorFraktala;
import hr.fer.zemris.java.tecaj_8.fraktali.MandelbrotParalelno2.MandelbrotJob;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;


public class MandelbrotParalelno3 {

	public static void main(String[] args) {
		FractalViewer.show( new GeneratorFraktala() );
	}
	
	public static class GeneratorFraktala implements IFractalProducer {
		
		
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNumber, IFractalResultObserver gui) {
			
			int m = 16*16*16;
			short[] data = new short[width*height];

			// ovdje sada racunamo -- pocetak
			int brojTraka = 16;
			int brojYaPoTraci = height/brojTraka;
			
			ExecutorService pool = Executors.newFixedThreadPool( Runtime.getRuntime().availableProcessors() );
			List<Future<Void>> rezultati = new ArrayList<Future<Void>>();
			
			for (int i = 0; i < brojTraka; i++) {
				int yMin = i * brojYaPoTraci;
				int yMax = (i+1) * brojYaPoTraci - 1;
				if (i==brojTraka-1) {
					// da pokupi ostatke cjelobrojnog dijeljenja
					yMax = height - 1;
				}
				
				MandelbrotJob job = new MandelbrotJob(reMin, reMax, imMin, imMax, width, height, data, yMin, yMax, m);
				rezultati.add( pool.submit( job ) );
						
			}
			
			for (Future<Void> rezultat : rezultati) {
				try {
					rezultat.get();
				// u javi 7
				//} catch (InterruptedException | ExecutionException ignorable) {}
				} catch (Exception ignorable){}
			}
			pool.shutdown();
			
			// kraj racunanja
			
			gui.acceptResult(data, (short)m, requestNumber);
			
		}
	}
	
	public static class MandelbrotJob implements Callable<Void> {
		
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int m;
		short[] data;
		int yMin;
		int yMax;
		
		public MandelbrotJob(double reMin, double reMax, double imMin,
				double imMax, int width, int height, short[] data, int yMin,
				int yMax, int m) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.data = data;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
		}
		
		@Override
		public Void call() {
			
			int offset = yMin*width;
			for (int y = yMin; y < yMax; y++) {
				for (int x = 0; x < width; x++) {
					// fiksirani pixel - (x,y) - pristupamo mu preko offset
					
					double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
					double cim = (height - 1.0 - y) / (height  - 1.0) * (imMax - imMin) + imMin;
					
					double znre = 0.0;
					double znim = 0.0;
					int iters = 0;
					//racunamo s kvadratom modula, da netrebamo korjenovat
					double modulkv = 0;
					do {
						double zn1re = znre*znre - znim*znim + cre;
						double zn1im = 2*znre*znim + cim;
						modulkv = zn1re*zn1re + zn1im*zn1im;
						
						iters++;
						znre = zn1re;
						znim = zn1im;
					} while (iters < m && modulkv < 4);
					
					data[offset] = (short)iters;
					offset++;
				}
			}
		
			return null;
		}
	}
}
