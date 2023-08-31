//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import java.util.ArrayList;

import javax.vecmath.Vector2f;

import ode.event.Event;
import ode.event.Handler;
import ode.event.PointerData;

//*************************************************************************************************
public class HoverSystem extends ArrayList<Widget> implements Handler {

	//=============================================================================================
	private static final long serialVersionUID = 1L;
	//=============================================================================================
	
	//=============================================================================================
	private float pointer_x;
	private float pointer_y;
	//=============================================================================================
	
	//=============================================================================================
	public void update() {
		float z = -1;
		Widget active = null;
		for (Widget widget : this) {
			FlagData flagsData = widget.getComponent(WidgetEnum.FLAGS, FlagData.class);
			flagsData.flags.remove(FlagEnum.HOVER);
			if (flagsData.flags.contains(FlagEnum.DISPLAYED)) {
				float[] globalPosition = getGlobalPosition(widget, new float[] { 0, 0, 0 });
				if (isInside(widget, globalPosition, pointer_x, pointer_y)) {
					float cmp = globalPosition[2];
					if (cmp > z) {
						z = cmp;
						active = widget;
					}
				}
			}
		}
		if (active != null) {
			FlagData flagsData = active.getComponent(WidgetEnum.FLAGS, FlagData.class);
			flagsData.flags.add(FlagEnum.HOVER);
		}
	}
	//=============================================================================================

	//=============================================================================================
	private boolean isInside(Widget widget, float[] globalPosition, float x, float y) {
		Vector2f dimensionData = widget.getComponent(WidgetEnum.DIMENSION, Vector2f.class);
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
		HierarchyData hierarchyData = widget.getComponent(WidgetEnum.HIERARCHY, HierarchyData.class);
		Vector2f positionData = widget.getComponent(WidgetEnum.POSITION, Vector2f.class);
		target[0] += positionData.x;
		target[1] += positionData.y;
		target[2] += 1;
		return getGlobalPosition(hierarchyData.parent, target);
	}
	//=============================================================================================
	
}
//*************************************************************************************************