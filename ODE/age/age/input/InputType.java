/*
 * Commit: 363b75a18db4122bb21de7cf1091c0bd6434b79b
 * Date: 2023-09-23 18:26:28+02:00
 * Author: Philip Reichel
 * Comment: Finished Switch to new Event Handling
Added Pointer Wheel Input
 *
 * Commit: 4ba52177b03c84982e184472f4e51a9157e9a67f
 * Date: 2023-09-21 20:15:06+02:00
 * Author: Philip Reichel
 * Comment: Cleaning all up
 *
 * Commit: dd56eccbb9b27eab45e556efde8d3bb7d0f4ce97
 * Date: 2023-09-20 17:16:57+02:00
 * Author: pre7618
 * Comment: Renamings
 *
 */

//*************************************************************************************************
package age.input;
//*************************************************************************************************

//*************************************************************************************************
public enum InputType {

	//=============================================================================================
	NONE,
	KEY_PRESSED,
	KEY_RELEASED,
	KEY_TYPED,
	POINTER_ENTERED,
	POINTER_EXITED,
	POINTER_PRESSED,
	POINTER_RELEASED,
	POINTER_CLICKED,
	POINTER_WHEEL,
	POINTER_MOVED,
	SURFACE_RESIZED,
	SURFACE_CLOSE_REQUEST,
	TASK_COMMAND,
	//=============================================================================================
	
}
//*************************************************************************************************
