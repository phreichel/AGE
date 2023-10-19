/*
 * Commit: 01f6edb422e32c691886b08481b74af734d2419d
 * Date: 2023-09-25 00:22:26+02:00
 * Author: Philip Reichel
 * Comment: Added Mesh
 *
 */

//*************************************************************************************************
package age.gui;
//*************************************************************************************************

//*************************************************************************************************
public enum WFlag {

	//=============================================================================================
	// Render Types
	//=============================================================================================
	BOX,
	FRAME,
	BUTTON,
	CANVAS,
	TITLE,
	MULTILINE,
	SCROLLBAR,
	HANDLE,
	//=============================================================================================

	//=============================================================================================
	// Behaviour Types
	//=============================================================================================
	CONTEXT_MENU,
	DRAG_HANDLE,
	RESIZE_HANDLE,
	CLOSE_HANDLE,
	SCROLL_START,
	SCROLL_END,
	SCROLLBAR_SLIDER,
	SCROLLBAR_HANDLE,
	POINTER_SCROLL,
	COMMAND_HANDLE,

	//=============================================================================================
	// Layout Types
	//=============================================================================================
	VSTACK,
	
	//=============================================================================================
	// Transient States
	//=============================================================================================
	HIDDEN,
	HOVERED,
	CLEAN,
	//=============================================================================================

}
//*************************************************************************************************
