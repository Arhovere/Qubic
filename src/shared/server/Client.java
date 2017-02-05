package shared.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Creates a client that calls to the ServerHandler. 
 * This sounds completely confusing and it is completely confusing. 
 * Thus, I'm going to make this a metaphor*: A Client is like Hengelo. 
 * Hengelo has the Enschedesestraat, which can be seen as the ServerHandler. 
 * To be continued!
 * (Metaphor 1/4. [Next> Server ) 
 * 
 * *Credits to Nander© for the idea of the metaphor
 * 
 * @author beitske
 *
 */
public class Client {
	// -----Fields---------------------------------------------------
	/**
	 * String INFO that permanently whines about the usage of addresses and ports.
	 */
	private final static String INFO = "Info needed: <address> <port>";

	// -----Main-----------------------------------------------------
	/**
	 * The Main that checks the arguments passed to the ServerHandler. 
	 * Also sets up a socket for the ServerHandler and starts the thread.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println(INFO);
			System.exit(0);
		}

		InetAddress address = null;
		int port = 0;
		Socket sock = null;

		// Checks args[0] - The IP-address
		try {
			address = InetAddress.getByName(args[0]);
		} catch (UnknownHostException e) {
			System.out.println(INFO);
			System.err.println("Error: Host " + args[0] + " unknown.");
			System.exit(0);
		}

		// Checks args[1] - The Port
		try {
			port = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			System.out.println(INFO);
			System.err.println("Error: Port " + args[1] + " is not an integer.");
		}
		
		// Open a new socket to the server
		try {
			sock = new Socket(address, port);
		} catch (IOException e) {
			System.out.println("Could not create socket on address " 
					+ address + " and port " + port);
			System.exit(0);
		}
		
		// Creates ServerHandler and starts the two-communication
		try {
			ServerHandler client = new ServerHandler(sock);
			Thread network = new Thread(client);
			network.start();
			client.handleTerminalInput();
			client.shutDown();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
