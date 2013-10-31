package hr.fer.zemris.java.tecaj_11.tcp;

import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TCPPosluzitelj {
	
	
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.err.println("Ocekivao sam jedan agrument: port.");
			System.exit(-1);
		}
		
		int serverPort = Integer.parseInt(args[0]);
		
		
		ServerSocket ssocket = new ServerSocket(serverPort);
		
		while (true) {
			Socket csocket = ssocket.accept();
			
			new Thread(new ObradaKlijenta(csocket)).start();
		}
	}
	
	
	private static class ObradaKlijenta implements Runnable {
		
		private Socket csocket;
		
		public ObradaKlijenta(Socket csocket) {
			this.csocket = csocket;
		}
		
		@Override
		public void run() {
			System.out.println("Komuniciram s: " + csocket.getRemoteSocketAddress());
			try {
				Scanner sc = new Scanner(csocket.getInputStream(), "UTF-8");
				while (true) {
					String redak = sc.nextLine();
					if (redak.equals("quit")) {
						break;
					}
					
					String odgovor = generirajOdgovor(redak);
					odgovor = odgovor + "\n";
					csocket.getOutputStream().write(
							odgovor.getBytes(StandardCharsets.UTF_8)
					);
					csocket.getOutputStream().flush();
				}
			} catch (Exception e) {
			}
			
			System.out.println("Gotova komunikacija s: " +csocket.getRemoteSocketAddress());
			 
			
			try { csocket.close(); } catch (Exception ignorable) {
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
	
	
}
