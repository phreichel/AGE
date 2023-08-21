//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import java.util.Map;
import java.util.Set;

import ode.event.Event;
import ode.event.Handler;
import ode.event.PointerData;

//*************************************************************************************************
public class ClickSystem implements Handler {

	//=============================================================================================
	private Set<Widget> widgets;
	private Map<Widget, ClickData> clickDataMap;
	//=============================================================================================

	//=============================================================================================
	public ClickSystem(Set<Widget> widgets, Map<Widget, ClickData> clickDataMap) {
		this.widgets = widgets;
		this.clickDataMap = clickDataMap;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean clickDetected = false;
	private float pointer_x;
	private float pointer_y;
	//=============================================================================================
	
	//=============================================================================================
	public void update(float dT) {

		for (Widget widget : widgets) {
			ClickData clickData = clickDataMap.get(widget);
			if (clickData != null) {
				if (clickData.decay != 0) {
					clickData.decay -= dT * 8;
					clickData.decay = Math.max(0f, clickData.decay);
				}
			}
		}

		if (!clickDetected) return;
		clickDetected = false;

		float z = -1;
		Widget active = null;
		for (Widget widget : widgets) {
			if (isInside(widget, pointer_x, pointer_y)) {
				float cmp = widget.globalZ();
				if (cmp > z) {
					z = cmp;
					active = widget;
				}
			}
		}
		
		ClickData clickData = clickDataMap.get(active);
		if (clickData != null) {
			if (clickData.decay == 0f) {
				clickData.decay = 1f;
				clickData.action.perform(active);
			} 
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