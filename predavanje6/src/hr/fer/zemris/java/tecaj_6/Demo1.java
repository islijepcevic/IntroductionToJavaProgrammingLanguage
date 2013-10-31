package hr.fer.zemris.java.tecaj_6;

public class Demo1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		double s1 = suma( 1.0, 5.0, -17.0, 2.31 );
		double s2 = suma( 2.0, -5.0 );
		double s3 = suma( -2.0, 5.0, 12.0 );
		// od ovoga smo se osigurali parametrom broj1
		//double s4 = suma();

                double arg1 = 1.0;
                double arg2 = 3.0;
                final int operacija = nesto();
                final Operacija op= nestoOp();
                
                switch (op) {
                case DIJELJENJE:
                	//
                	break;
                //...
                default:
                	//
                }

                double rezultat = obaviOperaciju( operacija, arg1, arg2 );

                //ali radi i sljedece (NOVA KATASTROFA)
                double rezultat2 = obaviOperaciju( GeometrijskiLik.KRUZNICA, -5.14, 2.73 );

                System.out.println("Rezultat = " + rezultat);

	}
	
	public static double suma( double broj1, double ... podatci ) {
		//podatci su ovdje obicno polje doubleova
		// ove tri tockice su samo sintaksna pokrata
		//int velicina = podatci.length;
		
		double s = broj1;
		for (double d: podatci) {
			s += d;
		}
		return s;
	}

	//STARA NEVALJALA METODA NESTO
//        public static int nesto() {
//            // staro rijesenje uz katastrofu
//            return 0;
//        }

	
        public static int nesto() {
            return OperacijeX.ZBRAJANJE;
        }

        public static Operacija nestoOp() {
            return Operacija.ZBRAJANJE;
        }

        public static double obaviOperaciju( int operacija, double arg1, double arg2 ) {

                double rezultat = 0.0;

                //ono prestaje biti prevodivo sa dodavanjem final u OperacijeX
                //OperacijeX.ZBRAJANJE++;

                //if (operacija == 1) {
                if (operacija == OperacijeX.ZBRAJANJE) {
                //if (operacija == Operacija.ZBRAJANJE) {
                    rezultat = arg1 + arg2;
                //} else if (operacija == -7) {
                } else if (operacija == OperacijeX.ODUZIMANJE) {
                //} else if (operacija == Operacija.ODUZIMANJE) {
                    rezultat = arg1 - arg2;
                //} else if (operacija == 4) {
                } else if (operacija== OperacijeX.ODUZIMANJE) {
                //} else if (operacija== Operacija.ODUZIMANJE) {
                    rezultat = arg1 / arg2;
                //} else if (operacija == 2) {
                } else if (operacija == OperacijeX.ODUZIMANJE) {
                //} else if (operacija == Operacija.ODUZIMANJE) {
                    rezultat = arg1 * arg2;
                } else {
                    throw new UnsupportedOperationException("Operacija " + operacija + " nije podrzana!");
                }

            return rezultat;
        }

}
