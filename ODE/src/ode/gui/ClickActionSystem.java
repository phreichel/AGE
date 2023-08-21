//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import java.util.Map;
import java.util.Set;

import ode.event.Event;
import ode.event.Handler;
import ode.event.PointerData;

//*************************************************************************************************
public class ClickActionSystem implements Handler {

	//=============================================================================================
	private Set<Widget> widgets;
	private Map<Widget, Action> clickActions;
	//=============================================================================================

	//=============================================================================================
	public ClickActionSystem(Set<Widget> widgets, Map<Widget, Action> clickActions) {
		this.widgets = widgets;
		this.clickActions = clickActions;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean clickDetected = false;
	private float pointer_x;
	private float pointer_y;
	//=============================================================================================
	
	//=============================================================================================
	public void update() {
		if (!clickDetected) return;
		clickDetected = false;
		float z = -1;
		Widget active = null;
		for (Widget widget : widgets) {
			if (isInside(widget, pointer_x, pointer_y)) {
				float cmp = widget.globalZ();
				if (cmp > z) {
					cmp = z;
					active = widget;
				}
			}
		}
		Action action = clickActions.get(active);
		if (action != null) {
			action.perform(active);
		}
	}
	//=============================================================================================

	//=============================================================================================
	private boolean isInside(Widget widget, float x, float y) {
		float x1 = widget.globalX();
		float y1 = widget.globalY();
		float x2 = x1 + widget.width;
		float y2 = y1 + widget.height;
		return (x >= x1) && (x <= x2) && (y >= y1) && (y <= y2);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void handle(Event event) {
		if (event.type != Event.MOUSE_CLICKED) return;
		PointerData pointerData = (PointerData) event.data[0];
		if (pointerData.button != PointerData.BUTTON1) return;
		if (pointerData.count < 1) return;
		pointer_x = pointerData.x;
		pointer_y = pointerData.y;
		clickDetected = true;
	}
	//=============================================================================================
	
}
//*************************************************************************************************