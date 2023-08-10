//*************************************************************************************************
package ode.gui.layout;
//*************************************************************************************************

import ode.gui.Widget;

//*************************************************************************************************
public class VerticalLayout extends Layout {

	//=============================================================================================
	public void apply(Widget widget) {
		float xpos = 0f;
		float ypos = 0f;
		float width = widget.getWidth();
		for (Widget child : widget.getChildren()) {
			float height = child.getHeight();
			child.setLocation(xpos, ypos);
			child.setSize(width, height);
			ypos += height;
		}
		widget.setSize(width, ypos);
	}
	//=============================================================================================

}
//*************************************************************************************************
