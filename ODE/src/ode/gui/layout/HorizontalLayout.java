//*************************************************************************************************
package ode.gui.layout;
//*************************************************************************************************

import ode.gui.Widget;

//*************************************************************************************************
public class HorizontalLayout extends Layout {

	//=============================================================================================
	public void apply(Widget widget) {
		float xpos = 0f;
		float ypos = 0f;
		float height = widget.getHeight();
		for (Widget child : widget.getChildren()) {
			child.setLocation(xpos, ypos);
			child.setSize(child.getWidth(), height);
			xpos += child.getWidth();
		}
		widget.setSize(xpos, height);		
	}
	//=============================================================================================

}
//*************************************************************************************************
