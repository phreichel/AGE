//*************************************************************************************************
package age.gui;
//*************************************************************************************************

import age.event.Event;

/**************************************************************************************************
 * 
 */
public class GUIEvent extends Event {

	/**********************************************************************************************
	 * 
	 */
	private Widget source;

	/**********************************************************************************************
	 * 
	 * @param source
	 * @param event
	 */
	private GUIEvent(Widget source, Event event) {
		this.source = source;
		set(event);
	}

	/**********************************************************************************************
	 * 
	 */
	public Widget source() {
		return this.source;
	}

}
