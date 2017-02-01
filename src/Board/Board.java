package Board;

import java.util.Arrays;

/**
 * This is the game board for a Qubic game.
 * 
 * @author beitske
 *
 */
public class Board {
	// -----Fields---------------------------------------------------
	private Mark[][][] fields;

	public static final int DIM = 4;
	private static final String[] RASTER = { "___|___|___", "---+---+---", "   |   |   ", "---+---+---",
			"   |   |   " };
	private static final String LINE = RASTER[1];
	private static final String SPACE = "     ";

	// -----Constructor----------------------------------------------

	/**
	 * Creates a new board filled with beautiful emptyness.
	 * 
	 */
	public Board() {
		fields = new Mark[DIM][DIM][DIM];
		Arrays.fill(fields, Mark.EMPTY);
	}

	// -----Methods--------------------------------------------------

	/**
	 * Checks if either player or bot aren't cheating by entering an invalid
	 * field.
	 * 
	 * @param row
	 *            number of the row (or X-coordinate)
	 * @param col
	 *            number of the column (or Y-coordinate)
	 * @param lay
	 *            number of the layer (or Z-coordinate)
	 * @return true if 0 <= row && row < DIM && 0 <= col && col < DIM && 0 <=
	 *         lay && lay < DIM
	 */
	// @ ensures \result == (0 <= row && row < DIM && 0 <= col && col < DIM && 0
	// <= lay && lay < DIM);
	/* @pure */
	public boolean isField(int row, int col, int lay) {
		return (0 <= row && row < DIM && 0 <= col && col < DIM && 0 <= lay && lay < DIM);
	}

	/**
	 * Return the content on field referred to by the parameters.
	 * 
	 * @param row
	 *            number of the row
	 * @param col
	 *            number of the column
	 * @param lay
	 *            number of the layer
	 * @return Mark on the field
	 */
	public Mark getField(int row, int col, int lay) {
		if (isField(row, col, lay)) {
			Mark res = fields[row][col][lay];
			return res;
		} else {
			return null;
		}
	}

	public void setField(Mark m) {
		// to do
	}

	/**
	 * Returns a string representation of the current layer. Also conveniently
	 * shows a number representation of the layer. Oh wait, it's a TUI, so we
	 * don't. It could get very messy with all the numbers.
	 * 
	 * @return The current layer represented in a String.
	 */
	public String toStringLayer() {
		String s = "";
		return s;
	}

}
