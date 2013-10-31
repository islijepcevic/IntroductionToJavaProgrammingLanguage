package hr.fer.zemris.java.tecaj_8.fraktali;

import hr.fer.zemris.java.tecaj_06.fractals.FractalViewer;
import hr.fer.zemris.java.tecaj_06.fractals.IFractalProducer;
import hr.fer.zemris.java.tecaj_06.fractals.IFractalResultObserver;

public class MandelbrotSlijedno {
	
	public static void main(String[] args) {
		
		FractalViewer.show( new GeneratorFraktala() );
	}
	
	
	public static class GeneratorFraktala implements IFractalProducer {
		
		
		@Override
		public void produce(double remin, double remax, double immin, double immax,
				int width, int height, long requestNumber, IFractalResultObserver gui) {
			
			int m = 16*16*16;
			short[] data = new short[width*height];
			
			// ovdje sada racunamo -- pocetak
			int offset = 0;
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					// fiksirani pixel - (x,y) - pristupamo mu preko offset
					
					double cre = x / (width - 1.0) * (remax - remin) + remin;
					double cim = (height - 1.0 - y) / (height  - 1.0) * (immax - immin) + immin;
					
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
			
			// kraj racunanja
			
			gui.acceptResult(data, (short)m, requestNumber);
			
		}
	}

}
