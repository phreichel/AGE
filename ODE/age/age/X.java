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
public class X extends RuntimeException {

	/**********************************************************************************************
	 * Default serial version number.
	 */
	private static final long serialVersionUID = 1L;

	/**********************************************************************************************
	 * Default Constructor
	 */
	public X() {
		super();
	}

	/**********************************************************************************************
	 * Constructor
	 * @param cause an Exception typically to be re-thrown.
	 */
	public X(Throwable cause) {
		super(cause);
	}

	/**********************************************************************************************
	 * Constructor
	 * @param message A text message that is printed on the stack trace in case the Exception is thrown on application level.
	 */
	public X(String message) {
		super(message);
	}

	/**********************************************************************************************
	 * Constructor 
	 * @param message A text message that is printed on the stack trace in case the Exception is thrown on application level.
	 * @param cause an Exception typically to be re-thrown.
	 */
	public X(String message, Throwable cause) {
		super(message, cause);
	}

}
//*************************************************************************************************
