//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import java.util.HashSet;
import java.util.Set;

//*************************************************************************************************
public class GUI {

	//=============================================================================================
	public final Set<Widget> widgets = new HashSet<>();
	//=============================================================================================

	//=============================================================================================
	public Widget create() {
		Widget widget = new Widget(this);
		widgets.add(widget);
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
	
}
//*************************************************************************************************
