package shared.server;

import shared.board.Board;
import shared.board.Mark;
import shared.board.Player;
import shared.exceptions.CoordinatesNotFoundException;

/**
 * Implements a ServerPlayer that extends Player.
 * 
 * @author beitske
 *
 */
public class ServerPlayer extends Player {
	private ServerHandler player;

	/**
	 * Creates a new ServerPlayer.
	 * 
	 * @param name
	 * 				String name of the player
	 * @param mark
	 * 				Mark mark of player
	 * @param player
	 * 				ServerHandler for player
	 */
	public ServerPlayer(String name, Mark mark, ServerHandler player) {
		super(name, mark);
		this.player = player;
	}

	/* (non-Javadoc)
	 * @see shared.board.Player#determineMove(shared.board.Board)
	 */
	@Override
	public int[] determineMove(Board board) throws CoordinatesNotFoundException {
		int[] move;
		while (true) {
			player.lock.lock();
			if (player.isUpdated()) {
				move = player.getMove();
				player.setUpdated(false);
				player.lock.unlock();
				break;
			}
			player.lock.unlock();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}
		}
		return move;
	}

}
