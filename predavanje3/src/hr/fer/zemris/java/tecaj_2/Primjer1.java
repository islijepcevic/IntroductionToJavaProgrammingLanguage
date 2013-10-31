package hr.fer.zemris.java.tecaj_2;

public class Primjer1 {

	public static void main(String[] args) {
		GeometrijskiLik g = null;
		
		g = new GeometrijskiLik("prvi lik");
		
		// to se kompajlira u nesto ekvivalentno:
		// GeometrijskiLik.getIme( g );
		g.getIme();
		// kaze Marko da je ovaj poziv gore "notorna laz"
		
		GeometrijskiLik g1 = new GeometrijskiLik("Kvadrat");
		GeometrijskiLik g2 = new GeometrijskiLik("Pravokutnik");
		
		System.out.println("Ime od g1 je: " + g1.getIme());
		System.out.println("Ime od g2 je: " + g2.getIme());
		
	}
	
	/*
	* PRIMJER S PLOCE
	*/
	
	void ispisi( GeometrijskiLik l) {
		System.out.println( l.getOpseg());
	}
	
	public static void primjer() {
		//ovdje nam compiler ne pomaze
		
		GeometrijskiLik l1 = new Linija( 0, 0, 10, 10);
		Pravokutnik l2 = new Pravokutnik( 10, 10, 20, 30 );
		GeometrijskiLik l3 = new GeometrijskiLik("nepoznat");
		
		ispisi( l3 ); // legalan poziv		
		ispisi( l2 ); // legalan poziv
		ispisi( l1 ); // legalan poziv
		
		// no sto ce se ispisat?
		
		// mogucnosti:
		// 0
		// 100 / 0 -> 100, dinamicki polimorfizam
		// 0 - nema sto drugo (linija nije redefinirala getOpseg)
		
	}
	
	//PRAVA PRISTUPA:
	// private - samo klasa koja posjeduje property
	// public - svi
	// protected - klasa koja posjeduje property, izvedene klase i svi u 
	// 				istom paketu
	// <bez oznake> - svi u istom paketu
	
	//POLIMORFIZAM:
	// 1) dinamicki polimorfizam - redefiniranje metoda (ovo gore)
	// 2) metode koje se zovu identicno, ali primaju razliciti broj parametara
	// (npr. konstruktori)
	
	/*
	 *KRUZNICE I ELIPSE 
	 */
	class Elipsa {
		int cx;
		int cy;
		int rx;
		int ry;
		//+ svi setteri / getteri
	}
	class Kruznica extends Elipsa {}
	
	//opasni poziv:
	K.setRx( 8 );
	
	// moguci popravak:
	// sa setRx moramo promijeniti rx i ry
	// ali ipak krhko!!!
	
	//UNIT TEST:
	test1( Elipsa e) {
		e.setRx(10);
		e.setRy(20);
		assert Math.abs( e.setOpseg() - /*izracun pomocu rucno izracunatog opsega*/ )
				<= 1e-7;
	}
	
	// PRAVI ODGOVOR:
	// nasljedivanje je dobro u ovom slucaju ako je roditelj IMMUTABLE
	
	//moze se napraviti npr ElipticalLikeShape extends GeometrijskiLik
	// Elipsa extends ElipticalLikeShape
	// Kruznica extends ElipticalLikeShape
	
	
	//THROWABLE:
	//Error - oporavak nemoguc ili nevjerojatan
	//Exceptioni - regularne iznimke koje se inace obraduju
	
	// checked / unchecked iznimke
	// RuntimeException - unchecked - nitko (compiler) se ne buni ako ih ne obradimo
	// ostale su checked iznimke, moraju se handleati!
	
}
