package shared.server;

import shared.board.Board;
import shared.board.Mark;
import shared.board.Player;
import shared.exceptions.CoordinatesNotFoundException;

public class NetworkPlayer extends Player {
	private ClientHandler client;

	public NetworkPlayer(String name, Mark mark, ClientHandler client) {
		super(name, mark);
		this.client = client;
	}

	@Override
	public int[] determineMove(Board board) throws CoordinatesNotFoundException {
		client.sendPlayerTurn();
		return null;
	}

}
