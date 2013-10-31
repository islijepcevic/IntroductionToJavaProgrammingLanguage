package hr.fer.zemris.java.tecaj_8.fraktali;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import hr.fer.zemris.java.tecaj_06.fractals.FractalViewer;
import hr.fer.zemris.java.tecaj_06.fractals.IFractalProducer;
import hr.fer.zemris.java.tecaj_06.fractals.IFractalResultObserver;
import hr.fer.zemris.java.tecaj_8.fraktali.MandelbrotParalelno3.MandelbrotJob;

/**
 * 
 * @author is45342
 *
 */
public class MandelbrotParalelno4 {

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
			ForkJoinPool pool = new ForkJoinPool();
			
			pool.invoke(
				new MandelbrotJob(
					reMin, reMax, imMin, imMax, width, height, m, data, 0, height- 1
				)
			);
			
			pool.shutdown();
			// kraj racunanja
			
			gui.acceptResult(data, (short)m, requestNumber);
			
		}
	}

	public static class MandelbrotJob implements RecursiveAction {
		
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
		final static int treshold = 16;
		
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
		protected void compute() {
			if (yMax - yMin + 1 < treshold) {
				computeDirect();
				return;
			}
			
			// mozda granice nisu dobre
			invokeAll( reMin, reMax, imMin,
					imMax,  width, height,data, yMin,
					(yMax - yMin)/2 - 1,  m);
			invokeAll(reMin, reMax, imMin,
					imMax,  width, height, data, (yMax - yMin)/2,
					yMax - 1, m);
		}
		
		public Void computeDirect() {
			System.out.println("racunam od " + yMin + " do " yMax);
			
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
