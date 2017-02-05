package shared.board;

/**
 * This is the main method to create a new game.
 * 
 * @author beitske
 *
 */
public class Qubic {
	
	/**
	 * Time to play! Creates a new game.
	 * 
	 * @param args
	 * 				Player names
	 */
	public static void main(String[] args) {
		Player player = new HumanPlayer(args[0], Mark.XX);
		Player player2 = new HumanPlayer(args[1], Mark.OO);
		Game game = new Game(player, player2);
		game.start();
	}

}
