package hr.fer.zemris.java.tecaj_6;

import java.io.IOError;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Demo2 {
	
	public static void main(String[] args) throws IOException {
		Path datoteka = Paths.get("C:/", "tmp", "javaPrimjeri", "a.txt");
		Path datoteka2 = Paths.get("C:/tmp/javaPrimjeri/a.txt");
		
		long velicina = Files.size(datoteka2);
		//Files.is...?
	}

}
