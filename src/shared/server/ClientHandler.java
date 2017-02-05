package shared.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Last but not least, ClientHandler. ClientHandler is set up by the Server.
 * ClientHandler is the Hengelosestraat to Server Enschede. Also here, 
 * it sets up a connection to eventually communicate with the Client(s).
 * (Metaphor 4/4. ServerHandler < Previous] )
 * 
 * @author beitske
 *
 */
/**
 * @author beitske
 *
 */
public class ClientHandler extends Thread {
	// -----Fields---------------------------------------------------
	protected Socket sock;
	protected Server server;
	private Scanner in;
	private BufferedWriter out;

	private int players;
	private String name;
	private boolean support;
	private int maxX;
	private int maxY;
	private int maxZ;
	private int winl;
	private boolean chat;
	private boolean autoR;
	private int id;
	
	private boolean updated = false;
	private int[] move;
	
	public Lock lock = new ReentrantLock();

	// -----Constructor----------------------------------------------
	/**
	 * Creates a ClientHandler based on the given socket and server.
	 * 
	 * @param socket
	 * 				socket passed on by the server
	 * @param server
	 * 				the server that creates the clienthandler
	 */
	public ClientHandler(Socket socket, Server server) {
		sock = socket;
		this.server = server;
		try {
			in = new Scanner(new BufferedReader(new InputStreamReader(socket.getInputStream())));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			System.err.println("Something, somewhere, went terribly wrong. Someone deleted the internet.");
		}

	}

	/**
	 * Prints inserted line.
	 * 
	 * @param cmd
	 * 				String array to be printed
	 */
	private void print(String[] cmd) {
		System.out.println(cmd);
	}

	// -----Methods--------------------------------------------------
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		id = Server.getID();
		while (in.hasNextLine()) {
			String line = in.nextLine();
			String[] cmd = line.split(" ");
			sendMessage(ServerMessages.ASSIGNID.getMessage() + " " + id);
		}
	}

	/**
	 * Method to send messages to the ServerHandler
	 * 
	 * @param string
	 * 				String that should be send
	 */
	public void sendMessage(String string) {
		try {
			out.write(string);
			out.newLine();
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to send the turn of the player (and his ID) to the serverhandler
	 */
	public void sendPlayerTurn() {
		sendMessage(ServerMessages.TURNOFPLAYER.getMessage() + " " + id);
	}
	
	/**
	 * Method to notify the serverhandler about the move of the player
	 */
	public void notifyMove() {
		sendMessage(ServerMessages.NOTIFYMOVE.getMessage() + " " + move[0] + " " + move[1]);
	}
	
	/**
	 * Returns the move
	 * 
	 * @return int array with the move coordinates
	 */
	public int[] getMove() {
		return move;
	}
	
	/**
	 * Boolean to check if the player has been updated
	 * 
	 * @return boolean updated
	 */
	public boolean isUpdated() {
		return updated;
	}
	
	/**
	 * Sets updated to another value
	 * 
	 * @param update
	 * 				Boolean true or false
	 */
	public void setUpdated(boolean update) {
		updated = update;
	}
	
	/**
	 * Returns the name of the player
	 * 
	 * @return string name
	 */
	public String getPlayerName() {
		return name;
	}
}
