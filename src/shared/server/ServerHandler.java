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
 * ServerHandler is set up by a Client. 
 * Like Hengelo, the Enschedesestraat is part of it although it will 
 * eventually lead to Enschede. This is comparable to the Client: 
 * It will set up a connection that via the ClientHandler sends info to 
 * the Server.
 * (Metaphor 3/4. ServerHandler < Previous] - [Next > ClientHandler )
 * 
 * @author beitske
 *
 */
public class ServerHandler implements Runnable {
	// -----Fields---------------------------------------------------
	public static final String EXIT = "exit";

	protected Socket sock;
	protected BufferedReader in;
	protected BufferedWriter out;
	
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
	
	public Lock lock = new ReentrantLock();
	
	private boolean updated = false;
	private int[] move;

	// -----Constructor----------------------------------------------
	/**
	 * Creates new ServerHandler by the given socket
	 * 
	 * @param sock
	 * 				the socket passed on by the client
	 * @throws IOException if the socket is incorrect
	 */
	public ServerHandler(Socket sock) throws IOException {
		this.sock = sock;
		out = new BufferedWriter(new OutputStreamWriter(this.sock.getOutputStream()));
	}

	// -----Methods--------------------------------------------------

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			Scanner s = new Scanner(in);
			while (s.hasNextLine()) {
				System.out.println(s.nextLine());
			}
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Handles the terminal input.
	 * Basically reads out the ServerMessages that have been passed on by the Client. 
	 * Also handles threading by the use of Locks. This means two threads won't be 
	 * able to update at the same time, which would cause serious errors.
	 */
	public void handleTerminalInput() {
		Scanner userInput = new Scanner(System.in);
		while (userInput.hasNextLine()) {
			String input = userInput.nextLine();
			String[] cmd = input.split(" ");
			switch (ServerMessages.valueOf(cmd[0])) {
				case ASSIGNID:
					id = Integer.parseInt(cmd[1]);
					break;
				case ERROR:
					break;
				case NOTIFYEND:
					break;
				case NOTIFYMESSAGE:
					break;
				case NOTIFYMOVE:
					lock.lock();
					move = new int[]{Integer.parseInt(cmd[2]), Integer.parseInt(cmd[3])};
					updated = true;
					lock.unlock();
					break;
				case ROOMCREATED:
					break;
				case SENDLEADERBOARD:
					break;
				case SENDLISTROOMS:
					break;
				case SERVERCAPABILITIES:
					players = Integer.parseInt(cmd[1]);
					name = cmd[2];
					if (cmd[3].equals("0")) {
						support = false;
					} else {
						support = true;
					}
					maxX = Integer.parseInt(cmd[4]);
					maxY = Integer.parseInt(cmd[5]);
					maxZ = Integer.parseInt(cmd[6]);
					winl = Integer.parseInt(cmd[7]);
					if (cmd[8].equals("0")) {
						chat = false;
					} else {
						chat = true;
					}
					if (cmd[9].equals("0")) {
						autoR = false;
					} else {
						autoR = true;
					}
					break;
				case STARTGAME:
					
					break;
				case TURNOFPLAYER:
					if (Integer.parseInt(cmd[1])==id) {
						
					}
					break;
				default:
					break;
			}
		}
	}

	/**
	 * Shuts down the connection.
	 */
	public void shutDown() {
		try {
			in.close();
			out.close();
			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	/**
	 * Returns the state of updated.
	 * 
	 * @return boolean updated
	 */
	public boolean isUpdated() {
		return updated;
	}
	
	/**
	 * Sets updated to a new value
	 * 
	 * @param update
	 * 				boolean true or false
	 */
	public void setUpdated(boolean update) {
		updated = update;
	}
	
	/**
	 * Returns the move
	 * 
	 * @return int array with coordinates
	 */
	public int[] getMove() {
		return move;
	}
	
	/**
	 * Sends message to the ClientHandler with a move
	 */
	public void makeMove() {
		sendMessage(ClientMessages.MAKEMOVE.getMessage() + " " + move[0] + " " + move[1]);
	}
	
	/**
	 * Method to send messages to the ClientHandler
	 * 
	 * @param string
	 * 				String that should be send to the ClientHandler
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
	
	public String getName() {
		return name;
	}
}
