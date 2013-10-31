package hr.fer.zemris.java.tecaj_8.fraktali;

import hr.fer.zemris.java.tecaj_06.fractals.FractalViewer;
import hr.fer.zemris.java.tecaj_06.fractals.IFractalProducer;
import hr.fer.zemris.java.tecaj_06.fractals.IFractalResultObserver;

public class MandelbrotParalelno1 {
	
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
			Thread[] radnici = new Thread[2];
			radnici[0] = new Thread(new MandelbrotJob(reMin, reMax, imMin, imMax, width, height, data, 0, height/2 - 1, m));
			radnici[0] = new Thread(new MandelbrotJob(reMin, reMax, imMin, imMax, width, height, data, height/2, height - 1, m));
			
			for(int i = 0; i < radnici.length; i++) {
				radnici[i].start();
			}
			
			for (int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						radnici[i].join();
						break;
					} catch(InterruptedException ignorable){}
				}
			}
			// kraj racunanja
			
			gui.acceptResult(data, (short)m, requestNumber);
			
		}
	}
	
	public static class MandelbrotJob implements Runnable {
		
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
		public void run() {
			
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
			
		}
	}

}
