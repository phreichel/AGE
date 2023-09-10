//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

//*************************************************************************************************
public class Layouts {

	//=============================================================================================
	private final Map<Widget, String> map = new HashMap<>();
	private final Map<String, Action> layouts = new HashMap<>();
	//=============================================================================================

	//=============================================================================================
	public Layouts() {
		add("window", this::layoutWindow);
		add("vertical", this::layoutVertical);
		add("horizontal", this::layoutHorizontal);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void put(Widget widget, String layout) {
		map.put(widget, layout);
	}
	//=============================================================================================

	//=============================================================================================
	public void add(String layout, Action action) {
		layouts.put(layout, action);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void update() {
		for (Entry<Widget, String> entry : map.entrySet()) {
			Widget widget = entry.getKey();
			String layout = entry.getValue();
			if (widget.match(Flag.DIRTY)) {
				layout(widget, layout);
				widget.clear(Flag.DIRTY);
			}
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void layout(Widget widget, String layout) {
		Action action = layouts.get(layout);
		action.perform(widget);
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

	//=============================================================================================
	private void layoutVertical(Widget widget) {
		List<Widget> list = widget.children();
		float gap = 3f;
		float dx  = 0f;
		float dy  = gap;
		for (Widget child : list) {
			if (child.match(Flag.DISPLAYED)) {
				dx = Math.max(dx, child.dimension().x);			
				child.position().x = gap;
				child.position().y = dy;
				dy += child.dimension().y;
				dy += gap;
			}
		}
		for (Widget child : list) {
			child.dimension(dx, child.dimension().y);			
		}
		widget.dimension(dx + 2 * gap, dy);
	}
	//=============================================================================================

	//=============================================================================================
	private void layoutHorizontal(Widget widget) {
		List<Widget> list = widget.children();
		float gap = 3f;
		float dx  = gap;
		float dy  = 0f;
		for (Widget child : list) {
			if (child.match(Flag.DISPLAYED)) {
				dy = Math.max(dy, child.dimension().y);			
				child.position().x = dx;
				child.position().y = gap;
				dx += child.dimension().x;
				dx += gap;
			}
		}
		for (Widget child : list) {
			child.dimension(child.dimension().x, dy);			
		}
		widget.dimension(dx, dy + 2 * gap);
	}
	//=============================================================================================
	
}
//*************************************************************************************************
