//*************************************************************************************************
package ode.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

//*************************************************************************************************

//*************************************************************************************************
public class Layouts {

	//=============================================================================================
	private Map<Widget, String> map = new HashMap<>(); 
	//=============================================================================================

	//=============================================================================================
	public void put(Widget widget, String layout) {
		map.put(widget, layout);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void update() {
		for (Entry<Widget, String> entry : map.entrySet()) {
			Widget widget = entry.getKey();
			String layout = entry.getValue();
			if (widget.match(Flag.DIRTY)) {
				layout(widget, layout);
			}
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void layout(Widget widget, String layout) {
		if (layout.equals("window")) layoutWindow(widget);
	}
	//=============================================================================================

	//=============================================================================================
	private void layoutWindow(Widget widget) {
		Widget titleBar = widget.children().get(0);
		Widget resizeButton = widget.children().get(1);
		Widget closeButton = widget.children().get(2);
		Widget clientBox = widget.children().get(3);
		float w = widget.dimension().x;
		float h = widget.dimension().y;
		titleBar.dimension(w-60, 20);
		resizeButton.position(w-50, 5);
		closeButton.position(w-25, 5);
		clientBox.dimension(w-10, h-35);
	}
	//=============================================================================================
	
}
//*************************************************************************************************
