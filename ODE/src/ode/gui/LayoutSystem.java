//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import java.util.ArrayList;

import javax.vecmath.Vector2f;

import ode.util.Inset;

//*************************************************************************************************
public class LayoutSystem  extends ArrayList<Widget> {

	//=============================================================================================
	private static final long serialVersionUID = 1L;
	//=============================================================================================
	
	//=============================================================================================
	public void update() {
		for (Widget widget : this) {
			LayoutEnum layoutData = widget.getComponent(WidgetEnum.LAYOUT, LayoutEnum.class);
			switch (layoutData) {
				case VERTICAL: layoutVertical(widget); break;
				default: break;
			}
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	private void layoutVertical(Widget widget) {
		final float SPACING = 5f;
		HierarchyData hierarchyData = widget.getComponent(WidgetEnum.HIERARCHY, HierarchyData.class);
		Vector2f widgetDimensionData = widget.getComponent(WidgetEnum.DIMENSION, Vector2f.class);
		AlignEnum alignData = widget.getComponent(WidgetEnum.ALIGN, AlignEnum.class);
		Inset paddingData = widget.getComponent(WidgetEnum.PADDING, Inset.class);
		float offset = paddingData.top;
		float maxwidth = 0f;
		for (Widget child : hierarchyData.children) {
			FlagData flagsData = widget.getComponent(WidgetEnum.FLAGS, FlagData.class);
			if (!flagsData.flags.contains(FlagEnum.DISPLAYED)) continue;
			Vector2f dimensionData = child.getComponent(WidgetEnum.DIMENSION, Vector2f.class);
			Vector2f positionData = child.getComponent(WidgetEnum.POSITION, Vector2f.class);
			positionData.x = paddingData.left;
			positionData.y = offset;
			offset += dimensionData.y;
			offset += SPACING;
			maxwidth = Math.max(maxwidth, dimensionData.x);
		}
		if (alignData.equals(AlignEnum.CENTER) || alignData.equals(AlignEnum.END)) {
			for (Widget child : hierarchyData.children) {
				Vector2f dimensionData = child.getComponent(WidgetEnum.DIMENSION, Vector2f.class);
				Vector2f positionData = child.getComponent(WidgetEnum.POSITION, Vector2f.class);
				float xalign = maxwidth - dimensionData.x;
				if (alignData.equals(AlignEnum.CENTER)) xalign *= .5f;
				positionData.x += xalign;
			}
		}
		widgetDimensionData.x = paddingData.left + maxwidth + paddingData.right;
		widgetDimensionData.y = offset - SPACING + paddingData.bottom;
	}
	//=============================================================================================
	
	
}
//*************************************************************************************************