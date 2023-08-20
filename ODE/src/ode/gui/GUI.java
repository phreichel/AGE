//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ode.platform.Graphics;

//*************************************************************************************************
public class GUI {

	//=============================================================================================
	public final Set<Widget> widgets = new HashSet<>();
	public final Map<Widget, RenderData> renderMap = new HashMap<>();
	//=============================================================================================

	//=============================================================================================
	private RenderSystem renderSystem = new RenderSystem(renderMap);
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
		widget.setBounds(10, 10, 500, 200);
		RenderData renderData = new RenderData();
		renderData.type = RenderData.BOX;
		renderMap.put(widget, renderData);
		return widget;
	}
	//=============================================================================================

	//=============================================================================================
	public Widget createButton() {
		Widget widget = createWidget();
		widget.setBounds(10, 10, 80, 20);
		RenderData renderData = new RenderData();
		renderData.type = RenderData.BUTTON;
		renderMap.put(widget, renderData);
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
	public void render(Graphics graphics) {
		graphics.beginGUIMode();
		renderSystem.update(graphics);
	}
	//=============================================================================================
	
}
//*************************************************************************************************
