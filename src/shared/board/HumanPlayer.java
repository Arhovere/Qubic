package shared.board;

import java.util.Scanner;

import shared.exceptions.CoordinatesNotFoundException;

/**
 * Implements a, big surprise, human player.
 * 
 * @author beitske
 *
 */
public class HumanPlayer extends Player {

	// -----Constructor----------------------------------------------

	/**
	 * Creates a new human player object. Totally not a robot.
	 * 
	 * @param name
	 * 				Thy has been given a String name
	 * @param mark
	 * 				Thy has been given a mark
	 */
	/*@
	requires name != null;
	requires mark == Mark.XX || mark == Mark.OO;
	ensures this.getName() == name;
	ensures this.getMark() == mark;
	 */
	public HumanPlayer(String name, Mark mark) {
		super(name, mark);
	}

	// -----Methods--------------------------------------------------

	/* (non-Javadoc)
	 * @see shared.board.Player#determineMove(shared.board.Board)
	 */
	/*@
	requires board != null;
	ensures board.isField(\result[0],\result[1],\result[2]) && board.isEmptyField(\result[0],\result[1],\result[2]);
	 */
	@Override
	public int[] determineMove(Board board) throws CoordinatesNotFoundException {
		String prompt = "> " + getName() + " (" + getMark().toString() + ")" + ", what is your choice?";
		int[] choice = readInt(prompt);
		while (!board.belowIsNotEmpty(choice[0], choice[1], choice[2])) {
			System.err.println(
					"ERROR: field " + choice[0] + ", " + choice[1] + ", " + choice[2] + " is no valid choice.");
			choice = readInt(prompt);
		}
		return choice;
	}

	/**
	 * Reads the input from the player.
	 * 
	 * @param prompt
	 * 				String with guidance for the player.
	 * @return int array with three values
	 */
	private int[] readInt(String prompt) {
		int[] value = new int[3];
		boolean intRead = false;
		Scanner line = new Scanner(System.in);
		do {
			System.out.print(prompt);
			try (Scanner scannerLine = new Scanner(line.nextLine());) {
				int i = 0;
				while (scannerLine.hasNextInt() && i < 3) {
					if (i == 2) {
						intRead = true;
					}
					value[i] = scannerLine.nextInt();
					System.out.println(value[i]);
					i++;
				}
			}
		} while (!intRead);
		return value;
	}

}
