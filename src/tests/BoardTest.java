package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import board.Board;
import board.Mark;
import exceptions.CoordinatesNotFoundException;

public class BoardTest {
	private Board board;

	@Before
	public void setUp() throws Exception {
		board = new Board();
	}

	@Test
	public void testBoard() {
		assertFalse(board.isFull());
	}

	@Test
	public void testDeepCopy() throws CoordinatesNotFoundException {
		board.setField(0, 0, 0, Mark.OO);
		Board versie2 = board.deepCopy();
		for (int i = 0; i < Board.DIM; i++) {
			for (int j = 0; j < Board.DIM; j++) {
				for (int k = 0; k < Board.DIM; k++) {
					assertEquals(board.getField(i, j, k), versie2.getField(i, j, k));
				}
			}
		}
	}

	@Test
	public void testIsField() {
		assertTrue(board.isField(2, 3, 0));
		assertFalse(board.isField(Board.DIM + 1, 0, 0));
	}

	@Test
	public void testGetField() throws CoordinatesNotFoundException {
		assertEquals(board.getField(0, 0, 0), Mark.EMPTY);
		board.setField(0, 0, 0, Mark.XX);
		assertEquals(board.getField(0, 0, 0), Mark.XX);
	}

	@Test
	public void testIsEmptyField() throws CoordinatesNotFoundException {
		assertTrue(board.isEmptyField(0, 0, 0));
		board.setField(0, 0, 0, Mark.XX);
		assertFalse(board.isEmptyField(0, 0, 0));
	}

	@Test
	public void testIsFull() throws CoordinatesNotFoundException {
		assertFalse(board.isFull());
		for (int kwik = 0; kwik < Board.DIM; kwik++) {
			for (int kwek = 0; kwek < Board.DIM; kwek++) {
				for (int kwak = 0; kwak < Board.DIM; kwak++) {
					board.setField(kwik, kwek, kwak, Mark.OO);
				}
			}
		}
		assertTrue(board.isFull());
	}

	@Test
	public void testGameOver() throws CoordinatesNotFoundException {
		assertFalse(board.gameOver());
		board.setField(0, 0, 0, Mark.OO);
		board.setField(0, 0, 1, Mark.OO);
		board.setField(0, 0, 2, Mark.OO);
		board.setField(0, 0, 3, Mark.OO);
		assertTrue(board.gameOver());
	}

	@Test
	public void testBelowIsNotEmpty() throws CoordinatesNotFoundException {
		assertTrue(board.belowIsNotEmpty(0, 0, 0));
		assertFalse(board.belowIsNotEmpty(0, 0, 1));
		board.setField(0, 0, 0, Mark.XX);
		assertTrue(board.belowIsNotEmpty(0, 0, 1));
	}

	@Test
	public void testSetField() throws CoordinatesNotFoundException {
		assertEquals(board.getField(0, 0, 0), Mark.EMPTY);
		board.setField(0, 0, 0, Mark.XX);
		assertEquals(board.getField(0, 0, 0), Mark.XX);
	}

	@Test
	public void testHasRow() throws CoordinatesNotFoundException {
		assertFalse(board.hasRow(Mark.OO));
		board.setField(0, 0, 0, Mark.OO);
		board.setField(0, 1, 0, Mark.OO);
		board.setField(0, 2, 0, Mark.OO);
		board.setField(0, 3, 0, Mark.OO);
		assertTrue(board.hasRow(Mark.OO));
	}

	@Test
	public void testHasColumn() throws CoordinatesNotFoundException {
		assertFalse(board.hasColumn(Mark.OO));
		board.setField(0, 0, 0, Mark.OO);
		board.setField(1, 0, 0, Mark.OO);
		board.setField(2, 0, 0, Mark.OO);
		board.setField(3, 0, 0, Mark.OO);
		assertTrue(board.hasColumn(Mark.OO));
	}

	@Test
	public void testHasTower() throws CoordinatesNotFoundException {
		assertFalse(board.hasTower(Mark.OO));
		board.setField(0, 0, 0, Mark.OO);
		board.setField(0, 0, 1, Mark.OO);
		board.setField(0, 0, 2, Mark.OO);
		board.setField(0, 0, 3, Mark.OO);
		assertTrue(board.hasTower(Mark.OO));
	}

	@Test
	public void testHasDiagonal() throws CoordinatesNotFoundException {
		assertFalse(board.hasDiagonal(Mark.OO));
		board.setField(0, 0, 0, Mark.OO);
		board.setField(1, 1, 0, Mark.OO);
		board.setField(2, 2, 0, Mark.OO);
		board.setField(3, 3, 0, Mark.OO);
		assertTrue(board.hasDiagonal(Mark.OO));
	}

	@Test
	public void testIsWinner() throws CoordinatesNotFoundException {
		assertFalse(board.isWinner(Mark.OO));
		board.setField(0, 0, 0, Mark.OO);
		board.setField(1, 1, 0, Mark.OO);
		board.setField(2, 2, 0, Mark.OO);
		board.setField(3, 3, 0, Mark.OO);
		assertTrue(board.isWinner(Mark.OO));
		assertFalse(board.isWinner(Mark.XX));
	}

	@Test
	public void testHasWinner() throws CoordinatesNotFoundException {
		assertFalse(board.hasWinner());
		board.setField(0, 0, 0, Mark.OO);
		board.setField(1, 1, 0, Mark.OO);
		board.setField(2, 2, 0, Mark.OO);
		board.setField(3, 3, 0, Mark.OO);
		assertTrue(board.hasWinner());
	}

	@Test
	public void testReset() throws CoordinatesNotFoundException {
		assertEquals(board.getField(0, 0, 0), Mark.EMPTY);
		board.setField(0, 0, 0, Mark.XX);
		assertEquals(board.getField(0, 0, 0), Mark.XX);
		board.reset();
		assertEquals(board.getField(0, 0, 0), Mark.EMPTY);
	}
}
