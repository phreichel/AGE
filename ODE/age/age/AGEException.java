//*************************************************************************************************
package age;
//*************************************************************************************************

/**************************************************************************************************
 * Root Exception for AGE.
 * The AGEException is derived from Runtime Exception
 * which means that no throws declarations have to be
 * added to surrounding methods.
 * 
 * Exceptions of local try/catch blocks commonly are
 * re-thrown by that Exception in order to make throws
 * statements unnecessary.  
 */
public class AGEException extends RuntimeException {

	//=============================================================================================
	/**
	 * Default serial version number.
	 */
	private static final long serialVersionUID = 1L;
	//=============================================================================================

	//=============================================================================================
	/**
	 * Default Constructor
	 */
	public AGEException() {
		super();
	}
	//=============================================================================================

	//=============================================================================================
	/**
	 * Constructor
	 * @param cause an Exception typically to be re-thrown.
	 */
	public AGEException(Throwable cause) {
		super(cause);
	}
	//=============================================================================================

	//=============================================================================================
	/**
	 * Constructor
	 * @param message A text message that is printed on the stack trace in case the Exception is thrown on application level.
	 */
	public AGEException(String message) {
		super(message);
	}
	//=============================================================================================

	//=============================================================================================
	/**
	 * Constructor 
	 * @param message A text message that is printed on the stack trace in case the Exception is thrown on application level.
	 * @param cause an Exception typically to be re-thrown.
	 */
	public AGEException(String message, Throwable cause) {
		super(message, cause);
	}
	//=============================================================================================

}
//*************************************************************************************************
