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
	public ServerHandler(Socket sock) throws IOException {
		this.sock = sock;
		out = new BufferedWriter(new OutputStreamWriter(this.sock.getOutputStream()));
	}

	// -----Methods--------------------------------------------------

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
	
	public boolean isUpdated() {
		return updated;
	}
	
	public void setUpdated(boolean update) {
		updated = update;
	}
	
	public int[] getMove() {
		return move;
	}
	
	public void makeMove() {
		sendMessage(ClientMessages.MAKEMOVE.getMessage() + " " + move[0] + " " + move[1]);
	}
	
	public void sendMessage(String string) {
		try {
			out.write(string);
			out.newLine();
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
