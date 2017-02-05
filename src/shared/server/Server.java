package shared.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	// -----Fields---------------------------------------------------
	private static final String INFO = "Info needed: " + Server.class.getName() + "<port>";
	public static int id = 0;

	// -----Main-----------------------------------------------------
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
		// It waits until it has two clients and only then starts a game
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
		id++ ;
		return id;
	}

}
