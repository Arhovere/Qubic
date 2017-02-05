package shared.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Creates a server that creates ClientHandlers for every socket opened. 
 * Can be seen as Enschede, and the ClientHandler can be defined as the 
 * Hengelosestraat here.
 * (Metaphor 2/4. Client < Previous] - [Next > ServerHandler )
 * 
 * @author beitske
 *
 */
public class Server {
	// -----Fields---------------------------------------------------
	/**
	 * Public static id is meant to create unique id's for every client connecting.
	 */
	private static final String INFO = "Info needed: " + Server.class.getName() + "<port>";
	public static int id = 0;

	// -----Main-----------------------------------------------------
	/**
	 * Checks all arguments passed to the main and sets up a serversocket. 
	 * With serversocket creates a ClientHandler for every connection.
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println(INFO);
			System.exit(0);
		}

		int port = 0;

		// Checks args[1] - The port (if it's valid or not)
		try {
			port = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			System.out.println(INFO);
			System.err.println("Error: port " + args[0] + " is not an integer");
			System.exit(0);
		}

		// Create new Server
		Server server = new Server();
		server.start(port);

	}

	private void start(int port) {
		ServerSocket servsock = null;

		// Open a new Server Socket
		try {
			servsock = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("Could not create a serversocket on port " + port);
			System.exit(0);
		}

		ClientHandler waiting = null;

		// Now the real stuff's starting.
		// It waits until it has two clients and only then starts a servergame
		try {
			while (true) {
				Socket sock = servsock.accept();
				ClientHandler client = new ClientHandler(sock, this);
				client.start();
				if (waiting == null) {
					waiting = client;
				} else {
					ServerGame game = new ServerGame(waiting, client);
				}
			}
		} catch (IOException e) {
			System.err.println("Resquiem in pace.");
			System.exit(-1);
		}
	}

	public static int getID() {
		id++;
		return id;
	}

}
