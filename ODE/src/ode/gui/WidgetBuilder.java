//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import javax.vecmath.Vector2f;

//*************************************************************************************************
public class WidgetBuilder {

	//=============================================================================================
	private GUI gui;
	private Widget widget;
	//=============================================================================================

	//=============================================================================================
	public WidgetBuilder(GUI gui) {
		this.gui = gui;
		widget = new Widget(gui);
		gui.widgets.add(widget);
	}
	//=============================================================================================

	//=============================================================================================
	public WidgetBuilder withFlagsData(FlagEnum ... flags) {
		FlagData flagsData = new FlagData();
		for (FlagEnum flag : flags) {
			flagsData.flags.add(flag);
		}
		gui.flagsMap.put(widget, flagsData);
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	public WidgetBuilder withPositionData(float x, float y) {
		Vector2f positionData = new Vector2f(x, y);
		gui.positionMap.put(widget, positionData);
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	public WidgetBuilder withDimensionData(float width, float height) {
		Vector2f dimensionData = new Vector2f(width, height);
		gui.dimensionMap.put(widget, dimensionData);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public WidgetBuilder withHierarchyData() {
		HierarchyData hierarchyData = new HierarchyData();
		gui.hierarchyMap.put(widget, hierarchyData);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public WidgetBuilder withLayoutData(float top, float bottom, float left, float right, float spacing, int alignment) {
		LayoutData layoutData = new LayoutData();
		layoutData.padding_top = top;
		layoutData.padding_bottom = bottom;
		layoutData.padding_left = left;
		layoutData.padding_right = right;
		layoutData.spacing = spacing;
		layoutData.alignment = alignment;
		gui.layoutMap.put(widget, layoutData);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public WidgetBuilder withRenderData(RenderEnum renderData) {
		gui.renderMap.put(widget, renderData);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public WidgetBuilder withTextData(String text) {
		gui.textMap.put(widget, text);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public WidgetBuilder withClickData(float decay, Action action) {
		ClickData clickData = new ClickData();
		clickData.decay = decay;
		clickData.action = action;
		gui.clickMap.put(widget, clickData);
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	public Widget build() {
		return widget;
	}
	//=============================================================================================
	
}
//*************************************************************************************************
