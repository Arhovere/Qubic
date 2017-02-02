package shared.exceptions;

public class InvalidArgumentException extends Exception {
	
	/**
	 * Extends exception. Throws an exceptions when a wrong argument
	 * is passed to the method.
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Calls to the string from super.
	 * 
	 * @param string
	 * 				String string with the error message.
	 */
	public InvalidArgumentException(String string) {
		super(string);
	}
	
	public InvalidArgumentException() {
		
	}

}
