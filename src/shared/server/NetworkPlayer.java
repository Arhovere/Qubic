package shared.server;

import shared.board.Board;
import shared.board.Mark;
import shared.board.Player;
import shared.exceptions.CoordinatesNotFoundException;

/**
 * Creates a new networkplayer that extends player.
 * 
 * @author beitske
 *
 */
public class NetworkPlayer extends Player {
	private ClientHandler client;

	/**
	 * Creates new networkplayer.
	 * 
	 * @param name
	 * 				String name player
	 * @param mark
	 * 				Mark mark for the player
	 * @param client
	 * 				ClientHandler belonging to client behind player.
	 */
	public NetworkPlayer(String name, Mark mark, ClientHandler client) {
		super(name, mark);
		this.client = client;
	}

	/* (non-Javadoc)
	 * @see shared.board.Player#determineMove(shared.board.Board)
	 */
	@Override
	public int[] determineMove(Board board) throws CoordinatesNotFoundException {
		client.sendPlayerTurn();
		return null;
	}

}
