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

	private void print(String[] cmd) {
		System.out.println(cmd);
	}

	// -----Methods--------------------------------------------------
	@Override
	public void run() {
		id = Server.getID();
		while (in.hasNextLine()) {
			String line = in.nextLine();
			String[] cmd = line.split(" ");
			sendMessage(ServerMessages.ASSIGNID.getMessage() + " " + id);
		}
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
	
	public void sendPlayerTurn() {
		sendMessage(ServerMessages.TURNOFPLAYER.getMessage() + " " + id);
	}
	
	public void notifyMove() {
		sendMessage(ServerMessages.NOTIFYMOVE.getMessage() + " " + move[0] + " " + move[1]);
	}
	
	public int[] getMove() {
		return move;
	}
	
	public boolean isUpdated() {
		return updated;
	}
	
	public void setUpdated(boolean update) {
		updated = update;
	}
}
