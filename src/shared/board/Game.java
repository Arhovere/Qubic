package shared.board;

import java.util.Scanner;

import shared.exceptions.CoordinatesNotFoundException;
import shared.exceptions.InvalidArgumentException;

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

	/**
	 * Builds a new game! Players are limited to 2.
	 * Basically creates a new battlefield and two new knights.
	 * 
	 * @param knight1
	 * 				Noble knight no 1
	 * @param knight2
	 * 				Noble knight no 2
	 */
	//@requires knight1 != null && knight2 != null;
	public Game(Player knight1, Player knight2) {
		board = new Board();
		players = new Player[NO_PLAYERS];
		players[0] = knight1;
		players[1] = knight2;
	}

	// -----Methods--------------------------------------------------

	/**
	 * Starts your epic battle.
	 * Gives you the option to play another time, if you still feel like with this TUI.
	 * 
	 */
	public void start() {
		boolean another = true;
		while (another) {
			reset();
			play();
			another = readBoolean("\nWant to play another time? (Yes/No)?", "Yes", "No");
		}
	}

	/**
	 * Wipes the battlefield and cleans up the bodies.
	 * 
	 */
	private void reset() {
		current = 0;
		board.reset();
	}

	/**
	 * Lets the players take turns until the battle is done.
	 * First the battlefield is shown before any knight has done anything.
	 */
	private void play() {
		board.toString();
		while (!board.gameOver()) {
			update();
			try {
				players[current].makeMove(board);
			} catch (CoordinatesNotFoundException e) {
				//This is really unlikely to happen, but, you know, just in case...
				System.out.println("Coordinates out of range.");
			}
			current = ++current % NO_PLAYERS;
		}
		printResult();
	}

	/**
	 * Shows the current situation on the battlefield.
	 * 
	 */
	private void update() {
		System.out.println("\nCurrent situation on the field: \n\n" + board.toString() + "\n");
	}

	/**
	 * Determines the answer of the player.
	 * 
	 * @param prompt
	 * 				A string with the description of the quest.
	 * @param yes
	 * 				Aye!
	 * @param no
	 * 				Nay!
	 * @return String with the answer of the player.
	 */
	private boolean readBoolean(String prompt, String yes, String no) {
		String answer;
		Scanner in = new Scanner(System.in);
		do {
			System.out.print(prompt);
			answer = in.nextLine();
		} while (!answer.equalsIgnoreCase(yes) && !answer.equalsIgnoreCase(no));
		in.close();
		return answer.equalsIgnoreCase(yes);
	}

	/**
	 * Tells the world (also known as compiler) about the outcome
	 * of the epic battle! Might be an (epic) draw though.
	 * 
	 */
	//@requires this.board.gameOver();
	private void printResult() {
		if (board.hasWinner()) {
			Player winner;
			try {
				winner = board.isWinner(players[0].getMark()) ? players[0] : players[1];
				System.out.println("Speler " + winner.getName() + " (" + winner.getMark().toString() + ") has won!");
			} catch (InvalidArgumentException e) {
				System.out.println("You used Mark.EMPTY, you cheater ;-)");
			}
		} else {
			System.out.println("Draw. There is no winner!");
		}
	}

}
