package shared.server;

import shared.board.Game;
import shared.board.Mark;

public class ServerGame extends Game {
	
	private ClientHandler pl1;
	private ClientHandler pl2;

	public ServerGame(ClientHandler player1, ClientHandler player2) {
		super(new NetworkPlayer(player1.getPlayerName(), Mark.OO, player1), new NetworkPlayer(player2.getPlayerName(), Mark.XX, player2));
		pl1 = player1;
		pl2 = player2;
	}

}
