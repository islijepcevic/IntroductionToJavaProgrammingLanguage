package hr.fer.zemris.java.tecaj_1;

import java.util.Scanner;

public class Vjezba {
	
	/**
	 * funkcija provjerava je li broj prim (ne radi jos)
	 * @param broj broj koji se provjerava
	 * @return boolean vrijednost
	 */
	public static boolean jeLiPrim( int broj ) {
		
		for ( int i = 2; i <= broj/2; i++) {
			if (broj % i == 0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * funkcija koja vraca prim broj pod zadanim rednim brojem
	 * @param indeks zadani redni broj
	 * @return prim broj
	 */
	public static long dohvatiPrimBroj( int indeks ) {
		
		int brojPrimova = 1;
		int trenutni = 2;
		
		while( brojPrimova < indeks) { 
			while( !jeLiPrim(trenutni)) {
				trenutni++;
			}
			brojPrimova++;
			trenutni++;
		}
		
		return trenutni - 1;
		
	}
	
	/**
	 * funkcija koja vraca fibonaccijev broj pod zadanim rednim brojem
	 * @param indeks zadani redni broj
	 * @return broj iz fibonaccijevog slijeda
	 */
	public static long dohvatiFibBroj( int indeks ) {
		
		if (indeks == 1) {
			return 0;
		}
		
		if (indeks == 2) {
			return 1;
		}
		
		return dohvatiFibBroj( indeks - 1 ) + dohvatiFibBroj( indeks - 2 );
	}
	
	/**
	 * funkcija koja ucitava int brojeve
	 * @param sc inicijalizirani scanner
	 * @param min 
	 * @return
	 */
	public static int ucitaj( Scanner sc, int min ) {
		
		int ucitano = -1;
		
		while ( ucitano < min ) {
			ucitano = sc.nextInt();
			
			if (ucitano < min) {
				System.out.println("Ne valja, ponovi!");
			}
		}
		
		return ucitano;
		
	}
	
	public static void main( String[] args ) {
		
		Scanner sc = new Scanner( System.in );
		
		System.out.println("Ucitaj donju granicu:");
		int donja = ucitaj( sc, 0 );
		System.out.println("Ucitaj gornju granicu:");
		int gornja = ucitaj( sc, donja );
		
		for ( int i = donja; i <= gornja; i++) {
			System.out.println(
					i + " " + dohvatiPrimBroj(i) + " " + dohvatiFibBroj(i)
			);
		}
	}
	
	
}
