//*************************************************************************************************
package age.gui;
//*************************************************************************************************

import age.input.InputEvent;

/**************************************************************************************************
 * Extension class of the normal input event to bind the event to a specific widget it was
 * triggered in 
 */
public class GUIEvent extends InputEvent {

	/**********************************************************************************************
	 * The source widget from which the input event is seen to be fired 
	 */
	private Widget source;

	/**********************************************************************************************
	 * Constructor 
	 * @param source the source widget firing the event
	 * @param event the input event considered to be fired by the source 
	 */
	private GUIEvent(Widget source, InputEvent event) {
		this.source = source;
		set(event);
	}

	/**********************************************************************************************
	 * the source widget property getter
	 * @return the source widget
	 */
	public Widget source() {
		return this.source;
	}

}
