//*************************************************************************************************
package age.event;
//*************************************************************************************************

/**************************************************************************************************
 * Enumeration of Event Types to register to
 */
public enum Type {

	/**********************************************************************************************
	 * Literal representing "No Event" 
	 */
	NONE,
	
	KEY_PRESSED,
	KEY_RELEASED,
	KEY_TYPED,

	POINTER_ENTERED,
	POINTER_EXITED,
	POINTER_PRESSED,
	POINTER_RELEASED,
	POINTER_CLICKED,
	POINTER_MOVED,

	SURFACE_RESIZED,
	SURFACE_CLOSE_REQUEST,

	TASK_COMMAND,
	
}
//*************************************************************************************************
