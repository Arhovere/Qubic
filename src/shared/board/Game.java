package shared.board;

public class Game {
	// -----Fields---------------------------------------------------
	/**
	 * The min, max and only number of players possible.
	 * Why? There are only two marks. Yes, there is a mark called mark.EMPTY.
	 * But that would be winning by doing nothing, so that isn't allowed.
	 * I am sincerely sorry.
	 * 
	 */
	public static final int NO_PLAYERS = 2;

	private Board board;
	private Player[] players;
	private int current;

	// -----Constructor----------------------------------------------

	public Game(Player pl1, Player pl2) {
		board = new Board();
		players = new Player[NO_PLAYERS];
		players[0] = pl1;
		players[1] = pl2;
	}

	// -----Methods--------------------------------------------------

	public void start() {
		//to do
	}

	private void reset() {
		//to do
	}

	private void play() {
		//to do
	}

	private void update() {
		//to do
	}

	private boolean readBoolean(String prompt, String yes, String no) {
		return false;
	}
	
	private void printResult() {
		//to do
	}

}
