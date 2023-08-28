//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import java.util.ArrayList;

import javax.vecmath.Vector2f;

import ode.event.Event;
import ode.event.Handler;
import ode.event.PointerData;

//*************************************************************************************************
public class TriggerSystem extends ArrayList<Widget> implements Handler {

	//=============================================================================================
	private static final long serialVersionUID = 1L;
	//=============================================================================================

	//=============================================================================================
	private TriggerEnum clickDetected = null;
	private float pointer_x;
	private float pointer_y;
	//=============================================================================================
	
	//=============================================================================================
	public void update(float dT) {
		handleDecay(dT);
		handleTrigger();
	}
	//=============================================================================================

	//=============================================================================================
	private void handleDecay(float dT) {
		final float TIME_FACTOR = 5f;
		for (Widget widget : this) {
			TriggerData triggerData = widget.getTriggerData();
			if (triggerData != null) {
				triggerData.decay = Math.max(0f, triggerData.decay - dT * TIME_FACTOR);
			}
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void handleTrigger() {
		if (clickDetected == null) return;
		Widget active = getActiveWidget();
		if (active != null) {
			TriggerData triggerData = active.getTriggerData();
			if (triggerData != null) {
				if (triggerData.decay < 0.001f) {
					Action action = triggerData.actionMap.get(clickDetected);
					if (action != null) {
						triggerData.decay = 1f;
						action.perform(active);
					}
				} 
			}
		}
		clickDetected = null;
	}
	//=============================================================================================

	//=============================================================================================
	private Widget getActiveWidget() {
		float z = -1;
		Widget active = null;
		for (Widget widget : this) {
			float[] globalPosition = getGlobalPosition(widget, new float[] { 0, 0, 0 });
			if (isInside(widget, globalPosition, pointer_x, pointer_y)) {
				float cmp = globalPosition[2];
				if (cmp > z) {
					z = cmp;
					active = widget;
				}
			}
		}
		return active;
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
		if (clickDetected != null) return;
		PointerData pointerData = (PointerData) event.data[0];
		pointer_x = pointerData.x;
		pointer_y = pointerData.y;
		if (pointerData.button == PointerData.BUTTON1 && pointerData.count == 1) {
			clickDetected = TriggerEnum.LEFT_CLICK;
		} else if (pointerData.button == PointerData.BUTTON1 && pointerData.count == 2) {
			clickDetected = TriggerEnum.LEFT_DOUBLE_CLICK;
		} else if (pointerData.button == PointerData.BUTTON2 && pointerData.count == 1) {
			clickDetected = TriggerEnum.RIGHT_CLICK;
		} else if (pointerData.button == PointerData.BUTTON2 && pointerData.count == 2) {
			clickDetected = TriggerEnum.RIGHT_DOUBLE_CLICK;
		} else if (pointerData.button == PointerData.BUTTON3 && pointerData.count == 1) {
			clickDetected = TriggerEnum.MIDDLE_CLICK;
		}
	}
	//=============================================================================================
	
}
//*************************************************************************************************