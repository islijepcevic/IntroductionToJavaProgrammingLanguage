package hr.fer.zemris.java.tecaj_11.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

public class UDPPosluzitelj {
	
	
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.err.println("Ocekivao sam jedan agrument: port.");
			System.exit(-1);
		}
		
		int serverPort = Integer.parseInt(args[0]);
		
		DatagramSocket socket = new DatagramSocket( serverPort );
		System.out.println("Posluzitelj slusa na adresi: " + socket.getLocalSocketAddress());
		
		while (true) {
			byte[] buf = new byte[1024];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			
			socket.receive(packet); // posluzitelj se ovdje zablokira (dretva main)
			
			System.out.println("Imam upit od klijenta: " + packet.getSocketAddress() );
			
			String upit = new String(
				packet.getData(), 
				packet.getOffset(),
				packet.getLength(),
				StandardCharsets.UTF_8
			);
			
			String odgovor = generirajOdgovor( upit );
			
			byte[] oktetiOdgovora = odgovor.getBytes(StandardCharsets.UTF_8);
			
			DatagramPacket paketOdgovor = new DatagramPacket( oktetiOdgovora, oktetiOdgovora.length);
			
			paketOdgovor.setAddress( packet.getAddress() );
			paketOdgovor.setPort( packet.getPort() );
			
			socket.send(paketOdgovor);
		}
	}

	private static String generirajOdgovor(String upit) {
		
		try {
			
			String[] elems = upit.split(" ");
			double a = Double.parseDouble(elems[0]);
			double b = Double.parseDouble(elems[2]);
			if (elems[1].equals("+")) {
				return Double.toString(a+b);
			} else if (elems[1].equals("-")) {
				return Double.toString(a-b);
			}
		} catch (Exception ex) {
		}
		
		return "ERR";
	}

}
