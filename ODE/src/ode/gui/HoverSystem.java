//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import java.util.Set;

import javax.vecmath.Vector2f;

import ode.event.Event;
import ode.event.Handler;
import ode.event.PointerData;

//*************************************************************************************************
public class HoverSystem implements Handler {

	//=============================================================================================
	private Set<Widget> widgets;
	//=============================================================================================

	//=============================================================================================
	public HoverSystem(Set<Widget> widgets) {
		this.widgets = widgets;
	}
	//=============================================================================================

	//=============================================================================================
	private float pointer_x;
	private float pointer_y;
	//=============================================================================================
	
	//=============================================================================================
	public void update() {
		float z = -1;
		Widget active = null;
		for (Widget widget : widgets) {
			FlagsData flagsData = widget.getFlagsData();
			flagsData.flags.remove(FlagEnum.HOVER);
			float[] globalPosition = getGlobalPosition(widget, new float[] { 0, 0, 0 });
			if (isInside(widget, globalPosition, pointer_x, pointer_y)) {
				float cmp = globalPosition[2];
				if (cmp > z) {
					z = cmp;
					active = widget;
				}
			}
		}
		if (active != null) {
			FlagsData flagsData = active.getFlagsData();
			flagsData.flags.add(FlagEnum.HOVER);
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
	public void handle(Event event) {
		PointerData pointerData = (PointerData) event.data[0];
		pointer_x = pointerData.x;
		pointer_y = pointerData.y;
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
	
}
//*************************************************************************************************