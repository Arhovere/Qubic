package board;

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
	 * Creates a new board filled with beautiful emptiness.
	 * 
	 */
	public Board() {
		fields = new Mark[DIM][DIM][DIM];
		Arrays.fill(fields, Mark.EMPTY);
	}

	// -----Methods--------------------------------------------------

	/**
	 * Creates a new Board with an exact copy of the contents of the current board.
	 * 
	 * @return new Board newBoard with a copy of the current boards contents
	 * @throws CoordinatesNotFoundException if field is out of range
	 */
	/*
	 *@ensures \result != this; ensures \result.getField(r,c,l) == this.getField(r,c,l);
	 */
	/*pure */
	public Board deepCopy() throws CoordinatesNotFoundException {
		Board newBoard = new Board();
		for (int l = 0; l < DIM; l++) {
			for (int r = 0; r < DIM; r++) {
				for (int c = 0; c < DIM; c++) {
					newBoard.setField(r, c, l, getField(r, c, l));
				}
			}
		}
		return newBoard;
	}

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
	//@ensures \result == (0 <= row && row < DIM && 0 <= col && col < DIM && 0 <= lay && lay < DIM);
	/*@pure */
	public boolean isField(int row, int col, int lay) {
		return 0 <= row && row < DIM && 0 <= col && col < DIM && 0 <= lay && lay < DIM;
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
	//@requires this.isField(row,col,lay);
	//@ensures \result == Mark.EMPTY || \result == Mark.XX || \result == Mark.OO;
	/*@pure */
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
	//@requires this.isField(row,col,lay);
	//@ensures \result == (this.getField(row,col,lay) == Mark.EMPTY);
	/*@pure */
	public boolean isEmptyField(int row, int col, int lay) throws CoordinatesNotFoundException {
		return getField(row, col, lay) == Mark.EMPTY;
	}

	/**
	 * Checks if all fields on the board are filled with either mark XX or OO
	 * 
	 * @return true if all fields are not filled with Mark.EMPTY
	 */
	/*@pure */
	public boolean isFull() {
		for (int l = 0; l < DIM; l++) {
			for (int r = 0; r < DIM; r++) {
				for (int c = 0; c < DIM; c++) {
					try {
						if (getField(r, c, l) == Mark.EMPTY) {
							return false;
						}
					} catch (CoordinatesNotFoundException e) {
						// This should logically never happen, but just to be sure.
						e.printStackTrace();
					}
				}
			}
		}
		return true;
	}

	/**
	 * Determines if the end of the game is present.
	 * 
	 * @return true if the game has a winner or when there is a draw (isFull)
	 */
	/*@pure */
	public boolean gameOver() {
		return hasWinner() || isFull();
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
	//@requires this.isField(row,col,lay);
	//@ensures \result == (this.getField(row,col,(lay-1)) != Mark.EMPTY);
	/*@pure */
	public boolean belowIsNotEmpty(int row, int col, int lay) throws CoordinatesNotFoundException {
		if (lay == 0) {
			return true;
		} else if (!isEmptyField(row, col, lay - 1)) {
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
	//@requires this.isField(row,col,lay);
	//@ensures this.getField(row,col,lay) == m;
	public void setField(int row, int col, int lay, Mark m) throws CoordinatesNotFoundException {
		if (isEmptyField(row, col, lay) && belowIsNotEmpty(row, col, lay)) {
			fields[row][col][lay] = m;
		}
	}

	/**
	 * Checks if the Board has a row with given Mark
	 * 
	 * @param m
	 *            the mark
	 * @return true if there is a row controlled by m
	 * @throws CoordinatesNotFoundException
	 *             if field is out of range
	 */
	/*@pure */
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

	/**
	 * Checks if the Board has a column with given Mark m
	 * 
	 * @param m
	 *            the mark
	 * @return true if there is a column controlled by m
	 * @throws CoordinatesNotFoundException
	 *             if field is out of range
	 */
	/*@pure */
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

	/**
	 * Checks if the board has a vertical row with Mark m
	 * 
	 * @param m
	 *            the mark
	 * @return true if a vertical row is controlled by m
	 * @throws CoordinatesNotFoundException
	 *             if field is out of range
	 */
	/*@pure */
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

	/**
	 * Checks if the Board has a diagonal with row Mark m
	 * 
	 * @param m
	 * 			the mark
	 * @return true if the a diagonal row is controlled by m
	 * @throws CoordinatesNotFoundException if field is out of range
	 */
	/*@pure */
	public boolean hasDiagonal(Mark m) throws CoordinatesNotFoundException {
		for (int c = 0; c < DIM; c++) {
			boolean a = true;
			boolean b = true;
			boolean x = true;
			boolean y = true;
			boolean o = true;
			boolean r = true;
			// Every Mark beneath checks a diagonal line:
			// From left to right and the other way around
			// This is done in three dimensions, so every diagonal line is checked
			// as the counter l goes up
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
				r = f6 == m && r;
			}
			if (a || b || x || y || o || r) {
				return true;
			}
		}
		boolean a = true;
		boolean b = true;
		boolean c = true;
		boolean d = true;
		for (int i = 0; i < DIM; i++) {
			Mark f = getField(i, i, i);
			a = f == m && a;
			Mark f2 = getField(DIM - 1 - i, i, i);
			b = f2 == m && b;
			Mark f3 = getField(i, DIM - 1 - i, i);
			c = f3 == m && c;
			Mark f4 = getField(DIM - 1 - i, DIM - 1 - i, i);
			d = f4 == m && d;
		}
		if (a || b || c || d) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if there is a winner with the given Mark m
	 * 
	 * @param m
	 * 			the mark
	 * @return true if m returns true at hasRow, hasColumn, hasDiagonal or hasTower.
	 */
	//@requires m != Mark.EMPTY;
	/*@pure */
	public boolean isWinner(Mark m) {
		try {
			return hasRow(m) || hasColumn(m) || hasDiagonal(m) || hasTower(m);
		} catch (CoordinatesNotFoundException e) {
			// This should logically never happen but just in case
			System.out.println("Coordinates not found or out of range.");
		}
		return false;
	}

	/**
	 * Checks if there is a winner on the board
	 * 
	 * @return true if Mark.OO or Mark.XX isWinner
	 */
	//@ensures \result == isWinner(Mark.OO) || isWinner(Mark.XX);
	/*@pure */
	public boolean hasWinner() {
		return isWinner(Mark.OO) || isWinner(Mark.XX);
	}

	/**
	 * Resets the board by filling the fields with Mark.EMPTY.
	 */
	//@ensures (\forall int i,j,k; 0 <= i && i < DIM && 0 <= j && j < DIM && 0 <= k && k < DIM; this.getField(i,j,k) == Mark.EMPTY);
	public void reset() {
		Arrays.fill(fields, Mark.EMPTY);
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
