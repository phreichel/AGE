//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import java.util.Map;
import java.util.Set;

import javax.vecmath.Vector2f;

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
			float[] globalPosition = getGlobalPosition(widget, new float[] { 0, 0, 0 });
			if (isInside(widget, globalPosition, pointer_x, pointer_y)) {
				float cmp = globalPosition[2];
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
	private boolean isInside(Widget widget, float[] globalPosition, float x, float y) {
		Vector2f dimensionData = widget.getDimensionData();
		float x1 = globalPosition[0];
		float y1 = globalPosition[1];
		float x2 = x1 + dimensionData.x;
		float y2 = y1 + dimensionData.y;
		return (x >= x1) && (x <= x2) && (y >= y1) && (y <= y2);
	}
	//=============================================================================================

	//=============================================================================================
	private float[] getGlobalPosition(Widget widget, float[] target) {
		if (widget == null) return target;
		HierarchyData hierarchyData = widget.getHierarchyData();
		Vector2f positionData = widget.getPositionData();
		target[0] += positionData.x;
		target[1] += positionData.y;
		target[2] += 1;
		return getGlobalPosition(hierarchyData.parent, target);
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