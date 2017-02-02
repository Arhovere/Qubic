package shared.board;

/**
 * Represents a mark in the Qubic game. There are three possible values:
 * Mark.EMPTY, Mark.XX and Mark.OO.
 * 
 * @author beitske
 *
 */
public enum Mark {

	EMPTY, XX, OO;

	
    /*@ensures this == Mark.XX ==> \result == Mark.OO;
    @ensures this == Mark.OO ==> \result == Mark.XX;
    @ensures this == Mark.EMPTY ==> \result == Mark.EMPTY;
  */
	/**
	 * Returns the opposite Mark, or empty.
	 * 
	 * @return The other Mark, that is, obviously, not EMPTY. Otherwise EMPTY.
	 */
	public Mark reverse() {
		if (this == XX) {
			return OO;
		} else if (this == OO) {
			return XX;
		} else {
			return EMPTY;
		}
	}

}
