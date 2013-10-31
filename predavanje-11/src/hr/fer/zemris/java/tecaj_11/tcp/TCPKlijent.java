package hr.fer.zemris.java.tecaj_11.tcp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class TCPKlijent {

	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.err.println("Ocekivao sam dva argumenta: ip-adresu i port");
			System.exit(-1);
		}
		
		InetAddress adresa = InetAddress.getByName(args[0]);
		int port = Integer.parseInt(args[1]);
		
		Socket socket = new Socket( adresa, port );
		
		
		Scanner tipkovnica = new Scanner(System.in);
		Scanner odgovori = new Scanner(socket.getInputStream(), "UTF-8");
		
		while (true) {
			
			String upit = tipkovnica.nextLine();
			
			socket.getOutputStream().write(upit.getBytes(StandardCharsets.UTF_8));
			socket.getOutputStream().write((byte)10); // '\n'
			socket.getOutputStream().flush();
			
			String odgovorPosluzitelja = null;
			
			try {
				odgovorPosluzitelja = odgovori.nextLine();
			} catch (NoSuchElementException ex) {
				break;
			}
			
			System.out.println("Dobio sam odgovor: " + odgovorPosluzitelja);
			
		}
		
		socket.close();
	}
	
}
