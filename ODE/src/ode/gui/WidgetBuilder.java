//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import java.util.List;

import javax.vecmath.Vector2f;

import ode.util.Inset;

//*************************************************************************************************
public class WidgetBuilder {

	//=============================================================================================
	private GUI gui;
	private Widget widget;
	//=============================================================================================

	//=============================================================================================
	public WidgetBuilder(GUI gui) {
		this.gui = gui;
		widget = new Widget();
		gui.widgets.add(widget);
	}
	//=============================================================================================

	//=============================================================================================
	public WidgetBuilder withFlagsData(FlagEnum ... flags) {
		FlagData flagsData = new FlagData();
		for (FlagEnum flag : flags) {
			flagsData.flags.add(flag);
		}
		widget.addComonent(WidgetComponentEnum.FLAGS, flagsData);
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	public WidgetBuilder withPositionData(float x, float y) {
		Vector2f positionData = new Vector2f(x, y);
		widget.addComonent(WidgetComponentEnum.POSITION, positionData);
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	public WidgetBuilder withDimensionData(float width, float height) {
		Vector2f dimensionData = new Vector2f(width, height);
		widget.addComonent(WidgetComponentEnum.DIMENSION, dimensionData);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public WidgetBuilder withHierarchyData() {
		HierarchyData hierarchyData = new HierarchyData();
		widget.addComonent(WidgetComponentEnum.HIERARCHY, hierarchyData);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public WidgetBuilder withLayoutData(LayoutEnum layoutEnum) {
		widget.addComonent(WidgetComponentEnum.LAYOUT, layoutEnum);
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	public WidgetBuilder withAlignData(AlignEnum alignment) {
		widget.addComonent(WidgetComponentEnum.ALIGN, alignment);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public WidgetBuilder withRenderData(RenderEnum renderData) {
		widget.addComonent(WidgetComponentEnum.RENDER, renderData);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public WidgetBuilder withTextData(String text) {
		widget.addComonent(WidgetComponentEnum.TEXT, text);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public WidgetBuilder withTriggerData(TriggerEnum trigger, Action action) {
		TriggerData triggerData = widget.getTriggerData();
		if (triggerData == null) {
			triggerData = new TriggerData();
			widget.addComonent(WidgetComponentEnum.TRIGGER, triggerData);
		}
		triggerData.actionMap.put(trigger, action);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public WidgetBuilder withPaddingData(float top, float bottom, float left, float right) {
		Inset paddingData = new Inset();
		paddingData.top = top;
		paddingData.bottom = bottom;
		paddingData.left = left;
		paddingData.right = right;
		widget.addComonent(WidgetComponentEnum.PADDING, paddingData);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public WidgetBuilder register(List<Widget> system) {
		system.add(widget);
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
	