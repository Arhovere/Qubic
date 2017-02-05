package shared.server;

import shared.board.Board;
import shared.board.Mark;
import shared.board.Player;
import shared.exceptions.CoordinatesNotFoundException;

public class ServerPlayer extends Player {
	private ServerHandler player;

	public ServerPlayer(String name, Mark mark, ServerHandler player) {
		super(name, mark);
		this.player = player;
	}

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
