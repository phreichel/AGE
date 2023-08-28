//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import javax.vecmath.Vector2f;

import ode.util.Inset;

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
	
	//============================================================================================s=
	public float spacing   = 5f;
	public int   alignment = CENTER;
	//=============================================================================================

	//=============================================================================================
	public Action action = this::perform;
	//=============================================================================================

	//=============================================================================================
	private void perform(Widget widget) {
		Vector2f widgetDimensionData = widget.getDimensionData();
		HierarchyData hierarchyData = widget.getHierarchyData();
		Inset paddingData = widget.getPaddingData();
		float offset = paddingData.top;
		float maxwidth = 0f;
		for (Widget child : hierarchyData.children) {
			FlagData flagsData = widget.getFlagsData();
			if (!flagsData.flags.contains(FlagEnum.DISPLAYED)) continue;
			Vector2f dimensionData = child.getDimensionData();
			Vector2f positionData = child.getPositionData();
			positionData.x = paddingData.left;
			positionData.y = offset;
			offset += dimensionData.y;
			offset += spacing;
			maxwidth = Math.max(maxwidth, dimensionData.x);
		}
		if ((alignment == CENTER) || (alignment == END)) {
			for (Widget child : hierarchyData.children) {
				Vector2f dimensionData = child.getDimensionData();
				Vector2f positionData = child.getPositionData();
				float xalign = maxwidth - dimensionData.x;
				if (alignment == CENTER) xalign *= .5f;
				positionData.x += xalign;
			}
		}
		widgetDimensionData.x = paddingData.left + maxwidth + paddingData.right;
		widgetDimensionData.y = offset - spacing + paddingData.bottom;
	}
	//=============================================================================================
	
}
//*************************************************************************************************
