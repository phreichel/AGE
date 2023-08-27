//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

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
	
	//=============================================================================================
	public float padding_top = 5f;
	public float padding_bottom = 5f;
	public float padding_left = 5f;
	public float padding_right = 5f;
	public float spacing   = 5f;
	public int   alignment = CENTER;
	//=============================================================================================

	//=============================================================================================
	public Action action = this::perform;
	//=============================================================================================

	//=============================================================================================
	private void perform(Widget widget) {
		DimensionData widgetDimensionData = widget.getDimensionData();
		LayoutData data = widget.getLayoutData();
		HierarchyData hierarchyData = widget.getHierarchyData();
		float offset = data.padding_top;
		float maxwidth = 0f;
		for (Widget child : hierarchyData.children) {
			FlagsData flagsData = widget.getFlagsData();
			if (!flagsData.flags.get(FlagsData.DISPLAYED)) continue;
			DimensionData dimensionData = child.getDimensionData();
			PositionData positionData = child.getPositionData();
			positionData.x = data.padding_left;
			positionData.y = offset;
			offset += dimensionData.height;
			offset += spacing;
			maxwidth = Math.max(maxwidth, dimensionData.width);
		}
		if ((alignment == CENTER) || (alignment == END)) {
			for (Widget child : hierarchyData.children) {
				DimensionData dimensionData = child.getDimensionData();
				PositionData positionData = child.getPositionData();
				float xalign = maxwidth - dimensionData.width;
				if (alignment == CENTER) xalign *= .5f;
				positionData.x += xalign;
			}
		}
		widgetDimensionData.width = data.padding_left + maxwidth + data.padding_right;
		widgetDimensionData.height = offset - spacing + padding_bottom;
	}
	//=============================================================================================
	
}
//*************************************************************************************************
