package hr.fer.zemris.java.tecaj_11.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UDPKlijent {

	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.err.println("Ocekivao sam dva argumenta: ip-adresu i port");
			System.exit(-1);
		}
		
		InetAddress adresa = InetAddress.getByName(args[0]);
		int port = Integer.parseInt(args[1]);
		
		// port postavljen na 0 (wildcard) - OS ce dati random slobodni port
		DatagramSocket socket = new DatagramSocket();
		
		Scanner sc = new Scanner(System.in);
		
		while (true) {
			String redak = sc.nextLine();
			if (redak.equals("gotovo")) {
				break;
			}
			
			byte[] upit = redak.getBytes(StandardCharsets.UTF_8);
			
			DatagramPacket upitPaket = new DatagramPacket(upit, upit.length);
			
			upitPaket.setAddress(adresa);
			upitPaket.setPort(port);
			
			while (true) {
				socket.send(upitPaket);
				
				byte[] buf = new byte[1024];
				
				DatagramPacket odgovorPaket = new DatagramPacket(buf, buf.length);
				
				socket.setSoTimeout(5000); //milisecond; ovo je moglo i izvan petlji jer ostaje stalno kad se postavi
				try {
					socket.receive(odgovorPaket);
				} catch (SocketTimeoutException ex) {
					continue;
				}
				
				System.out.println("Rezultat je: " +
					new String(odgovorPaket.getData(),
						odgovorPaket.getOffset(),
						odgovorPaket.getLength(),
						StandardCharsets.UTF_8
					)
				);
				break;
			}
		}
		
		socket.close();
	}
	
}
