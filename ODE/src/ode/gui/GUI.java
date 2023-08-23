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
	private float width = 800;
	private float height = 600;	
	//=============================================================================================
	
	//=============================================================================================
	public final Set<Widget> widgets = new HashSet<>();
	public final Map<Widget, RenderData> renderMap = new HashMap<>();
	public final Map<Widget, ClickData> clickMap = new HashMap<>();
	public final Map<Widget, String> textMap = new HashMap<>();
	public final Map<Widget, LayoutData> layoutMap = new HashMap<>();
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
		widget.setBounds(10, 10, 100, 100);
		RenderData renderData = new RenderData();
		renderData.type = RenderData.BOX;
		renderMap.put(widget, renderData);
		LayoutData layoutData = new LayoutData();
		layoutMap.put(widget, layoutData);
		return widget;
	}
	//=============================================================================================

	//=============================================================================================
	public Widget createButton(String label, Action action) {
		Widget widget = createWidget();
		widget.setBounds(0, 0, 120, 20);
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
	public boolean delete(Widget widget) {
		if (widget.parent != null) return false;
		if (!widget.children.isEmpty()) return false;
		widgets.remove(widget);
		return true;
	}
	//=============================================================================================

	//=============================================================================================
	public boolean attach(Widget parent, Widget widget) {
		if (widget.parent != null) return false;
		widget.parent = parent;
		parent.children.add(widget);
		return true;
	}
	//=============================================================================================
	
	//=============================================================================================
	public boolean detach(Widget widget) {
		if (widget.parent == null) return false;
		widget.parent.children.remove(widget);
		widget.parent = null;
		return true;
	}
	//=============================================================================================

	//=============================================================================================
	public boolean moveToTop(Widget widget) {
		if (widget.parent == null) return false;
		widget.parent.children.remove(widget);
		widget.parent.children.add(0, widget);
		return true;
	}
	//=============================================================================================

	//=============================================================================================
	public boolean moveToBottom(Widget widget) {
		if (widget.parent == null) return false;
		widget.parent.children.remove(widget);
		widget.parent.children.add(widget);
		return true;
	}
	//=============================================================================================

	//=============================================================================================
	public boolean moveUp(Widget widget) {
		if (widget.parent == null) return false;
		int idxFrom = widget.parent.children.indexOf(widget);
		int idxTo = idxFrom-1;
		if (idxTo >= 0) {
			widget.parent.children.remove(widget);
			widget.parent.children.add(idxTo, widget);
		}
		return true;
	}
	//=============================================================================================

	//=============================================================================================
	public boolean moveDown(Widget widget) {
		if (widget.parent == null) return false;
		int idxFrom = widget.parent.children.indexOf(widget);
		int idxTo = idxFrom+1;
		if (idxTo <= widget.parent.children.size()) {
			widget.parent.children.remove(widget);
			widget.parent.children.add(idxTo, widget);
		}
		return true;
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
