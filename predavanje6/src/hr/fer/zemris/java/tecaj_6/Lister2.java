package hr.fer.zemris.java.tecaj_6;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class Lister2 {
	
	public static void main(String[] args) throws IOException {
		Path dir = Paths.get("D:/Dropbox/FER_predmeti");
		
		Files.walkFileTree(dir, new Radnik() );
		/*
		 * ako nam trebaju informacije nakon obilaska, imat varijablu Radnika
		 * koja ce cuvati te podatke
		 */
	}
	
	static class Radnik implements FileVisitor<Path> {
		
		private long ukupnaVelicina;
		private int brojTxtDatoteka;
		private int dubina;

		@Override
		public FileVisitResult preVisitDirectory(Path dir,
				BasicFileAttributes attrs) throws IOException {
			
			if (dubina == 0) {
				System.out.println( dir.toFile().getAbsolutePath() );
			} else {
				// dva spacea po dubini
				System.out.print( String.format("%" + (dubina*2) + "s", "") );
				System.out.println( dir.toFile().getName() );
			}
			dubina++;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
				throws IOException {
			
			String fileName = file.toFile().getName();
			ukupnaVelicina += Files.size( file );
			
			if (fileName.endsWith(".txt")) {
				brojTxtDatoteka++;
			}
			
			System.out.print( String.format("%" + (dubina*2) + "s", "") );
			System.out.println( file.toFile().getName() );
			
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc)
				throws IOException {
			
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc)
				throws IOException {
			
			dubina--;
			if (dubina == 0) {
				System.out.println("Ukupna velicina datoteka je: " + ukupnaVelicina);
				System.out.println("Boje txt datoteka je: " + brojTxtDatoteka);
			}
			
			return FileVisitResult.CONTINUE;
		}
		
	}

}
