package Board;

import java.util.Arrays;

import Exceptions.CoordinatesNotFoundException;

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
	// private static final String[] RASTER = { "___|___|___", "---+---+---", "
	// | | ", "---+---+---",
	// " | | " };
	// private static final String LINE = RASTER[1];
	// private static final String SPACE = " ";

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
	 * @throws CoordinatesNotFoundException
	 *             if field out of range
	 */
	// @ requires this.isField(row,col,lay);
	// @ ensures \result == Mark.EMPTY || \result == Mark.XX || \result ==
	// Mark.OO;
	/* @pure */
	public Mark getField(int row, int col, int lay) throws CoordinatesNotFoundException {
		if (isField(row, col, lay)) {
			Mark res = fields[row][col][lay];
			return res;
		} else {
			throw new CoordinatesNotFoundException("Coordinate(s) of the board are out of range.");
		}
	}

	/**
	 * Checks if the content of the field referred to is empty.
	 * 
	 * @param row
	 *            no of the row
	 * @param col
	 *            no of the column
	 * @param lay
	 *            no of the layer
	 * @return true if getField(row, col, lay) == Mark.EMPTY
	 * @throws CoordinatesNotFoundException
	 *             if field out of range
	 */
	// @ requires this.isField(row,col,lay);
	// @ ensures \result == (this.getField(row,col,lay) == Mark.EMPTY);
	/* @pure */
	public boolean isEmptyField(int row, int col, int lay) throws CoordinatesNotFoundException {
		return (getField(row, col, lay) == Mark.EMPTY);
	}

	/**
	 * Checks if the same spot on the layer below is empty, if there is one.
	 * 
	 * @param row
	 *            no of the row
	 * @param col
	 *            no of the column
	 * @param lay
	 *            no of the layer
	 * @return true if lay == 0 || !(isEmptyField(row, col, (lay - 1)))
	 * @throws CoordinatesNotFoundException
	 *             if field is out of range
	 */
	// @ requires this.isField(row,col,lay);
	// @ ensures \result (this.getField(row,col,(lay-1)) != Mark.EMPTY);
	/* @pure */
	public boolean belowIsNotEmpty(int row, int col, int lay) throws CoordinatesNotFoundException {
		if (lay == 0) {
			return true;
		} else if (!(isEmptyField(row, col, (lay - 1)))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Sets the mark on the field referred to by the parameters to the given
	 * Mark.
	 * 
	 * @param row
	 *            no of the row
	 * @param col
	 *            no of the column
	 * @param lay
	 *            no of the layer
	 * @param m
	 *            the Mark to conquer this field with
	 * @throws CoordinatesNotFoundException
	 *             if field is out of range
	 */
	// @ requires this.isField(row,col,lay);
	// @ ensures this.getField == m;
	public void setField(int row, int col, int lay, Mark m) throws CoordinatesNotFoundException {
		if (isEmptyField(row, col, lay) && belowIsNotEmpty(row, col, lay)) {
			fields[row][col][lay] = m;
		}
	}

	public boolean hasRow(Mark m) throws CoordinatesNotFoundException {
		for (int l = 0; l < DIM; l++) {
			for (int r = 0; r < DIM; r++) {
				boolean res = true;
				for (int c = 0; c < DIM; c++) {
					if (!(getField(r, c, l) == m)) {
						res = false;
						break;
					}
				}
				if (res) {
					return res;
				}
			}
		}
		return false;
	}

	public boolean hasColumn(Mark m) throws CoordinatesNotFoundException {
		for (int l = 0; l < DIM; l++) {
			for (int c = 0; c < DIM; c++) {
				boolean res = true;
				for (int r = 0; r < DIM; r++) {
					if (!(getField(r, c, l) == m)) {
						res = false;
						break;
					}
				}
				if (res) {
					return res;
				}
			}
		}
		return false;
	}

	public boolean hasTower(Mark m) throws CoordinatesNotFoundException {
		for (int r = 0; r < DIM; r++) {
			for (int c = 0; c < DIM; c++) {
				boolean res = true;
				for (int l = 0; l < DIM; l++) {
					if (!(getField(r, c, l) == m)) {
						res = false;
						break;
					}
				}
				if (res) {
					return res;
				}
			}
		}
		return false;
	}

	public boolean hasDiagonal(Mark m) throws CoordinatesNotFoundException {
		for (int c = 0; c < DIM; c++) {
			boolean a = true;
			boolean b = true;
			boolean x = true;
			boolean y = true;
			boolean o = true;
			boolean n = true;
			for (int l = 0; l < DIM; l++) {
				Mark f = getField(l, c, l);
				a = f == m && a;
				Mark f2 = getField(l, c, DIM - 1 - l);
				b = f2 == m && b;
				Mark f3 = getField(c, l, l);
				x = f3 == m && x;
				Mark f4 = getField(c, l, DIM - 1 - l);
				y = f4 == m && y;
				Mark f5 = getField(l, l, c);
				o = f5 == m && o;
				Mark f6 = getField(l, DIM - l - 1, c);
				n = f6 == m && n;
			}
			if (a || b || x || y || o || n) {
				return true;
			}
		}
		return false;
	}

	public boolean isWinner(Mark m) {
		try {
			return (hasRow(m) || hasColumn(m) || hasDiagonal(m) || hasTower(m));
		} catch (CoordinatesNotFoundException e) {
			System.out.println("Coordinates not found or out of range.");
		}
		return false;
	}

	public boolean hasWinner() {
		return (isWinner(Mark.OO) || isWinner(Mark.XX));
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
