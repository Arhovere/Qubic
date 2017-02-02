package board;

import exceptions.CoordinatesNotFoundException;

/**
 * @author beitske
 *
 */
public abstract class Player {
	// -----Fields---------------------------------------------------
	private String name;
	private Mark mark;

	// -----Constructor----------------------------------------------
	/**
	 * The Almighty Constructor creates a new player.
	 * 
	 * @param name
	 * 				String name of player
	 * @param mark
	 * 				Mark belonging to player
	 */
	/*@
	requires name != null;
	requires mark == Mark.XX || mark== Mark.OO;
	ensures this.getName() == name;
	ensures this.getMark() == mark;
	*/
	public Player(String name, Mark mark) {
		this.name = name;
		this.mark = mark;
	}

	// -----Methods--------------------------------------------------

	/**
	 * Returns the name of the player.
	 * 
	 * @return String name of player
	 */
	/*@pure */
	public String getName() {
		return name;
	}

	/**
	 * Returns the mark of the player.
	 * 
	 * @return Mark mark of player
	 */
	/*@pure */
	public Mark getMark() {
		return mark;
	}

	/**
	 * Prepares the battleplan for the board.
	 * 
	 * @param board
	 * 				the current game board
	 * @return int array with the coordinates for your battlemove
	 */
	/*@
	requires board != null & !board.isFull();
	ensures board.isField(\result[0], \result[1], \result[2]) 
	& board.isEmptyField(\result[0], \result[1], \result[2]);
	
	*/
	/*@pure */
	public abstract int[] determineMove(Board board);

	/**
	 * Moves your armies across the board according to your battleplan.
	 * 
	 * @param board
	 * 				the current game board
	 * @throws CoordinatesNotFoundException if field is out of range
	 */
	/*@ensures board.getField(determineMove(board)[0], determineMove(board)[1], 
	 determineMove(board)[2]) == getMark();
	 */
	public void makeMove(Board board) throws CoordinatesNotFoundException {
		int[] move = determineMove(board);
		board.setField(move[0], move[1], move[2], getMark());
	}

}
