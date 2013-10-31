package hr.fer.zemris.java.tecaj_6;
import java.io.*;
public class Lister {
	
	private final static String RAZMAK = "    ";
	
	public static void main( String[] args ) {
		
		File parametar = new File( args[0] );
		long velicina = listaj( parametar, 0 );
		
		System.out.println("Ukupna velicina svih datoteka iznosi: " + velicina);
	}
	
	public static long listaj( File trenutni, int razina ) {
		
		if (trenutni == null || !(trenutni.exists())) {
			return 0L;
		}
		
		String prefix = "";
		for (int i = 0; i < razina; i++) {
			prefix += RAZMAK;
		}
		
		if (razina == 0) {
			System.out.println(trenutni.getAbsolutePath());
		} else {
			System.out.println(prefix + trenutni.getName());
		}
		
		long velicina = 0;
		
		if (trenutni.isDirectory()) {
			for (File dublji : trenutni.listFiles()) {
				velicina += listaj( dublji, razina + 1 );
			}
			return velicina;
		}
		
		if (trenutni.isFile()) {
			velicina = trenutni.length();
		}
		
		return velicina;
	}

}
