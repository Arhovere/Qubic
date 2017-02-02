package shared.exceptions;

public class CoordinatesNotFoundException extends Exception {

	/**
	 * Extends exceptions. Throws an exception when the coordinates
	 * are out of range.
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Calls string from super class exception.
	 * 
	 * @param string
	 * 				String string with the error message.
	 */
	public CoordinatesNotFoundException(String string) {
		super(string);
	}

	public CoordinatesNotFoundException() {

	}

}
