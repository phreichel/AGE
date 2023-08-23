//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

//*************************************************************************************************
public class LayoutData {

	//=============================================================================================
	private static int inc = 0;
	//=============================================================================================

	//=============================================================================================
	public static final int START = inc++;
	public static final int CENTER = inc++;
	public static final int END = inc++;
	//=============================================================================================
	
	//=============================================================================================
	public float padding_top = 5f;
	public float padding_bottom = 5f;
	public float padding_left = 5f;
	public float padding_right = 5f;
	public float spacing   = 5f;
	public int   alignment = CENTER;
	//=============================================================================================

	//=============================================================================================
	public Action action = this::perform;
	//=============================================================================================

	//=============================================================================================
	private void perform(Widget widget) {
		LayoutData data = widget.gui.layoutMap.get(widget);
		float offset = data.padding_top;
		float maxwidth = 0f;
		for (Widget child : widget.children) {
			if (!child.flags.get(Widget.DISPLAYED)) continue;
			child.x = data.padding_left;
			child.y = offset;
			offset += child.height;
			offset += spacing;
			maxwidth = Math.max(maxwidth, child.width);
		}
		if ((alignment == CENTER) || (alignment == END)) {
			for (Widget child : widget.children) {
				float xalign = maxwidth - child.width;
				if (alignment == CENTER) xalign *= .5f;
				child.x += xalign;
			}
		}
		widget.width = data.padding_left + maxwidth + data.padding_right;
		widget.height = offset - spacing + padding_bottom;
	}
	//=============================================================================================
	
}
//*************************************************************************************************
