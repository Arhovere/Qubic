package shared.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

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
	private static int id = 1;

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
		while (in.hasNextLine()) {
			String line = in.nextLine();
			String[] cmd = line.split(" ");
			sendMessage(ServerMessages.ASSIGNID.getMessage() + " " + id);
			id++;
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
}
