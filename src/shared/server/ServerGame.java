package shared.server;

import shared.board.Game;
import shared.board.Mark;

/**
 * Class to create a ServerGame. Not finished unfortunately.
 * It is based on two clienthandlers that are passed on to create two 
 * networkPlayers. They are assigned a Mark.
 * 
 * @author beitske
 *
 */
public class ServerGame extends Game {
	
	private ClientHandler pl1;
	private ClientHandler pl2;

	/**
	 * Creates new game.
	 * 
	 * @param player1
	 * 				NetworkPlayer 1
	 * @param player2
	 * 				NetworkPlayer 2
	 */
	public ServerGame(ClientHandler player1, ClientHandler player2) {
		super(new NetworkPlayer(player1.getPlayerName(), Mark.OO, player1), new NetworkPlayer(player2.getPlayerName(), Mark.XX, player2));
		pl1 = player1;
		pl2 = player2;
	}

}
