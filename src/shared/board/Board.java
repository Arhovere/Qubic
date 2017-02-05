package shared.board;

import shared.exceptions.CoordinatesNotFoundException;
import shared.exceptions.InvalidArgumentException;

/**
 * This is the game board for a Qubic game.
 * 
 * @author beitske
 *
 */
public class Board {
	// -----Fields---------------------------------------------------
	/**
	 * A 3 dimensional array filled with marks, mostly emptiness at the beginning.
	 * You could say fields is a little dead on the inside.
	 * 
	 */
	private Mark[][][] fields;

	public static final int DIM = 4;

	// -----Constructor----------------------------------------------

	/**
	 * Creates a new board filled with beautiful emptiness.
	 * 
	 */
	public Board() {
		fields = new Mark[DIM][DIM][DIM];
		for (int kwik = 0; kwik < DIM; kwik++) {
			for (int kwek = 0; kwek < DIM; kwek++) {
				for (int kwak = 0; kwak < DIM; kwak++) {
					fields[kwik][kwek][kwak] = Mark.EMPTY;
				}
			}
		}
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
		for (int layer = 0; layer < DIM; layer++) {
			for (int row = 0; row < DIM; row++) {
				for (int column = 0; column < DIM; column++) {
					newBoard.setField(row, column, layer, getField(row, column, layer));
				}
			}
		}
		return newBoard;
	}

	/**
	 * Checks if either player or bot aren't cheating by entering an invalid
	 * field. We're watching you.
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
		for (int layer = 0; layer < DIM; layer++) {
			for (int row = 0; row < DIM; row++) {
				for (int column = 0; column < DIM; column++) {
					try {
						if (getField(row, column, layer) == Mark.EMPTY) {
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
		}
		return !isEmptyField(row, col, lay - 1);
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
		for (int lay = 0; lay < DIM; lay++) {
			for (int row = 0; row < DIM; row++) {
				boolean res = true;
				for (int col = 0; col < DIM; col++) {
					if (!(getField(row, col, lay) == m)) {
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
	 * Checks if the board has a vertical row, a so called tower*, with Mark m
	 * *Disclaimer: No princess present.
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
		for (int i = 0; i < DIM; i++) {
			boolean a = true;
			boolean b = true;
			boolean x = true;
			boolean y = true;
			boolean v = true;
			boolean w = true;
			// Every Mark beneath checks a diagonal line:
			// From left to right and the other way around
			// This is done in three dimensions, so every diagonal line is checked
			// as the counter l goes up
			for (int j = 0; j < DIM; j++) {
				Mark f = getField(j, i, j);
				a = f == m && a;
				Mark f2 = getField(j, i, DIM - 1 - j);
				b = f2 == m && b;
				Mark f3 = getField(i, j, j);
				x = f3 == m && x;
				Mark f4 = getField(i, j, DIM - 1 - j);
				y = f4 == m && y;
				Mark f5 = getField(j, j, i);
				v = f5 == m && v;
				Mark f6 = getField(j, DIM - j - 1, i);
				w = f6 == m && w;
			}
			if (a || b || x || y || v || w) {
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
	 * @throws InvalidArgumentException if the mark is Mark.EMPTY
	 */
	//@requires m != Mark.EMPTY;
	/*@pure */
	public boolean isWinner(Mark m) throws InvalidArgumentException {
		if (m == Mark.EMPTY) {
			throw new InvalidArgumentException("Argument Mark.EMPTY is invalid.");
		}
		try {
			return hasRow(m) || hasColumn(m) || hasDiagonal(m) || hasTower(m);
		} catch (CoordinatesNotFoundException e) {
			// This should logically never happen but just in case
			System.out.println("Coordinates not found or out of range.");
		}
		return false;
	}

	/**
	 * Checks if there is a winner on the board (although we're all winners)
	 * 
	 * @return true if Mark.OO or Mark.XX isWinner
	 */
	//@ensures \result == isWinner(Mark.OO) || isWinner(Mark.XX);
	/*@pure */
	public boolean hasWinner() {
		try {
			return isWinner(Mark.OO) || isWinner(Mark.XX);
		} catch (InvalidArgumentException e) {
			return false;
		}
	}

	/**
	 * In case of 3 am networking problems a function that checks 
	 * what is the valid layer belonging to a given row and column.
	 * 
	 * @param row
	 * @param column
	 * @return int with the valid layers
	 * @throws CoordinatesNotFoundException if there is no valid field for this combination
	 */
	public int findLayer(int row, int column) throws CoordinatesNotFoundException {
		for (int i = DIM - 1; i >= 0; i--) {
			if (belowIsNotEmpty(row, column, i) && isEmptyField(row, column, i)) {
				return i;
			}
		}
		throw new CoordinatesNotFoundException("Coordinates are not valid.");
	}

	/**
	 * Resets the board by filling the fields with Mark.EMPTY.
	 * All current fields on the board will return to dust.
	 */
	//@ensures (\forall int i,j,k; 0 <= i && i < DIM && 0 <= j && j < DIM && 0 <= k && k < DIM; this.getField(i,j,k) == Mark.EMPTY);
	public void reset() {
		for (int kwik = 0; kwik < DIM; kwik++) {
			for (int kwek = 0; kwek < DIM; kwek++) {
				for (int kwak = 0; kwak < DIM; kwak++) {
					fields[kwik][kwek][kwak] = Mark.EMPTY;
				}
			}
		}
	}

	/**
	 * Returns a string representation of the current layer. Also conveniently
	 * shows a number representation of the layer. Oh wait, it's a TUI, so we
	 * don't. It could get very messy with all the numbers.
	 * I kindly refer to the other toString() method for a more
	 * complete approach to toStringness.
	 * 
	 * @return The current layer represented in a String.
	 */
	/*@pure */
	public String toStringLayer(int layer) {
		String header = "Layer: " + layer + "\n";

		String board = "";
		for (int r = 0; r < DIM; r++) {
			for (int c = 0; c < DIM; c++) {
				try {
					board += getField(r, c, layer).toString() + " ";
				} catch (CoordinatesNotFoundException e) {
					// This will never happen, as it is already checked before
					e.printStackTrace();
				}
			}
			board += "\n";
		}

		return header + board + "\n";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	/*@pure */
	public String toString() {
		String header = "Board: \n";
		String board = "";
		for (int i = DIM - 1; i >= 0; i--) {
			board += toStringLayer(i) + "\n";
		}

		return header + board;
	}

}
