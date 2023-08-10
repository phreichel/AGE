//*************************************************************************************************
package ode.gui.layout;
//*************************************************************************************************

import ode.gui.Widget;

//*************************************************************************************************
public class VerticalBoxLayout extends Layout {

	//=============================================================================================
	public void apply(Widget widget) {
		float xpos = 0f;
		float ypos = 0f;
		float width = widget.getWidth();
		float height = widget.getHeight() / widget.getChildCount();
		for (Widget child : widget.getChildren()) {
			child.setLocation(xpos, ypos);
			child.setSize(width, height);
			ypos += child.getHeight();
		}
	}
	//=============================================================================================

}
//*************************************************************************************************
