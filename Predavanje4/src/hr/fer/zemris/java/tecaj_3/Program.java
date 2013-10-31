package hr.fer.zemris.java.tecaj_3;

import hr.fer.zemris.java.tecaj_3.prikaz.PrikaznikSlike;
import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

public class Program {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Slika slika = new Slika(300, 300);
		Pravokutnik p1 = new Pravokutnik(10, 10, 280, 60);
		Pravokutnik p2 = new Pravokutnik(10, 150, 280, 100);
		
		p1.popuniLik(slika);
		p2.popuniLik(slika);
		
		PrikaznikSlike.prikaziSliku(slika);
	}

}