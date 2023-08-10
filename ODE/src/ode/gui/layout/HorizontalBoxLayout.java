//*************************************************************************************************
package ode.gui.layout;
//*************************************************************************************************

import ode.gui.Widget;

//*************************************************************************************************
public class HorizontalBoxLayout extends Layout {

	//=============================================================================================
	public void apply(Widget widget) {
		float xpos = 0f;
		float ypos = 0f;
		float width = widget.getWidth() / widget.getChildCount();
		float height = widget.getHeight();
		for (Widget child : widget.getChildren()) {
			child.setLocation(xpos, ypos);
			child.setSize(width, height);
			xpos += child.getWidth();
		}
	}
	//=============================================================================================

}
//*************************************************************************************************
