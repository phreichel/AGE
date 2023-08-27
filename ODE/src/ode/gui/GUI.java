//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ode.event.Event;
import ode.event.Events;
import ode.platform.Graphics;

//*************************************************************************************************
public class GUI {

	//=============================================================================================
	public float width = 800;
	public float height = 600;	
	//=============================================================================================
	
	//=============================================================================================
	public final Set<Widget> widgets = new HashSet<>();
	public final Map<Widget, ClickData> clickMap = new HashMap<>();
	public final Map<Widget, DimensionData> dimensionMap = new HashMap<>();
	public final Map<Widget, FlagsData> flagsMap = new HashMap<>();
	public final Map<Widget, HierarchyData> hierarchyMap = new HashMap<>();
	public final Map<Widget, LayoutData> layoutMap = new HashMap<>();
	public final Map<Widget, PositionData> positionMap = new HashMap<>();
	public final Map<Widget, RenderData> renderMap = new HashMap<>();
	public final Map<Widget, String> textMap = new HashMap<>();
	//=============================================================================================

	//=============================================================================================
	private RenderSystem renderSystem = new RenderSystem(renderMap);
	private HoverSystem hoverSystem = new HoverSystem(widgets);
	private ClickSystem clickSystem = new ClickSystem(widgets, clickMap);
	private LayoutSystem layoutSystem = new LayoutSystem(layoutMap);
	//=============================================================================================

	//=============================================================================================
	public GUI(Events events) {
		events.register(Event.MOUSE_CLICKED, clickSystem);
		events.register(Event.MOUSE_MOVED, hoverSystem);
	}
	//=============================================================================================
	
	//=============================================================================================
	private Widget createWidget() {
		Widget widget = new Widget(this);
		widgets.add(widget);
		return widget;
	}
	//=============================================================================================

	//=============================================================================================
	public Widget createBox() {
		Widget widget = createWidget();
		FlagsData flagsData = new FlagsData();
		flagsMap.put(widget, flagsData);
		PositionData positionData = new PositionData();
		positionData.x = 10;
		positionData.y = 10;
		positionMap.put(widget, positionData);
		DimensionData dimensionData = new DimensionData();
		dimensionData.width = 100;
		dimensionData.height = 100;
		dimensionMap.put(widget, dimensionData);
		HierarchyData hierarchyData = new HierarchyData();
		hierarchyMap.put(widget, hierarchyData);
		RenderData renderData = new RenderData();
		renderData.type = RenderData.BOX;
		renderMap.put(widget, renderData);
		LayoutData layoutData = new LayoutData();
		layoutMap.put(widget, layoutData);
		return widget;
	}
	//=============================================================================================

	//=============================================================================================
	public Widget createLabel(String label) {
		Widget widget = createWidget();
		FlagsData flagsData = new FlagsData();
		flagsMap.put(widget, flagsData);
		PositionData positionData = new PositionData();
		positionData.x = 0;
		positionData.y = 0;
		positionMap.put(widget, positionData);
		DimensionData dimensionData = new DimensionData();
		dimensionData.width = 200;
		dimensionData.height = 20;
		dimensionMap.put(widget, dimensionData);
		HierarchyData hierarchyData = new HierarchyData();
		hierarchyMap.put(widget, hierarchyData);
		RenderData renderData = new RenderData();
		renderData.type = RenderData.LABEL;
		renderMap.put(widget, renderData);
		textMap.put(widget, label);
		return widget;
	}
	//=============================================================================================

	//=============================================================================================
	public Widget createButton(String label, Action action) {
		Widget widget = createWidget();
		FlagsData flagsData = new FlagsData();
		flagsMap.put(widget, flagsData);
		PositionData positionData = new PositionData();
		positionData.x = 0;
		positionData.y = 0;
		positionMap.put(widget, positionData);
		DimensionData dimensionData = new DimensionData();
		dimensionData.width = 120;
		dimensionData.height = 20;
		dimensionMap.put(widget, dimensionData);
		HierarchyData hierarchyData = new HierarchyData();
		hierarchyMap.put(widget, hierarchyData);
		RenderData renderData = new RenderData();
		renderData.type = RenderData.BUTTON;
		renderMap.put(widget, renderData);
		textMap.put(widget, label);
		ClickData clickData = new ClickData();
		clickData.decay = 0;
		clickData.action = action;
		clickMap.put(widget,  clickData);
		return widget;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void resize(float width, float height) {
		this.width = width;
		this.height = height;
	}
	//=============================================================================================

	//=============================================================================================
	public void update(float dT) {
		clickSystem.update(dT);
		hoverSystem.update();
		layoutSystem.update();
	}
	//=============================================================================================
	
	//=============================================================================================
	public void render(Graphics graphics) {
		graphics.beginGUIMode();
		renderSystem.update(graphics);
	}
	//=============================================================================================

}
//*************************************************************************************************
