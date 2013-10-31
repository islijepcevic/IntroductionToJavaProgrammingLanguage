package hr.fer.zemris.java.tecaj_3;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

public class Pravokutnik extends GeometrijskiLik {
	
	private int topLeftX;
	private int topLeftY;
	private int height;
	private int width;
	
	public Pravokutnik(int topLeftX, int topLeftY, int width, int height) {
		super();
		this.topLeftX = topLeftX;
		this.topLeftY = topLeftY;
		this.height = height;
		this.width = width;
	}
	
	/**
	 * polimorfizam radi i bez anotacije Override
	 * Override osigurava da je metoda sigurno bude override necega prije
	 * ako se slucajno zada krivo ime funkcije, i istovremeno override,
	 * kompajler ce se buniti
	 * 
	 * u slucaju da treba overrideate apstraktnu metodu, onda ce se compiler
	 * ipak buniti
	 */
	@Override
	public boolean sadrziTocku(int x, int y) {
		if (x < topLeftX) return false;
		if (y < topLeftY) return false;
		if (x >= topLeftX + width) return false;
		if (y >= topLeftY + height) return false;
		
		return true;
	}
	
	@Override
	public void popuniLik(Slika slika) {
		int minX = this.topLeftX;
		int minY = this.topLeftY;
		int maxX = this.topLeftX + this.width - 1;
		int maxY = this.topLeftY + this.height - 1;
		
		for (int y = minY; y <= maxY; y++) {
			for (int x = minX; x <= maxX; x++) {
				slika.upaliTocku(x, y);
			}
		}
	}

	public int getTopLeftX() {
		return topLeftX;
	}

	public int getTopLeftY() {
		return topLeftY;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

}
