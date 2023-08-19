//*************************************************************************************************
package ode.gui.system;
//*************************************************************************************************

import java.util.Map;
import java.util.Map.Entry;

import ode.event.Event;
import ode.event.Events;
import ode.gui.Widget;
import ode.gui.component.Hierarchy;

//*************************************************************************************************
public class HierarchySystem implements GUISystem {

	//=============================================================================================
	private Map<Widget, Hierarchy> hierarchyMap;
	//=============================================================================================
	
	//=============================================================================================
	public void registerEventHandlers(Events events) {
		events.register(Event.NONE, this::handleAddWidget);
		events.register(Event.NONE, this::handleDeleteWidget);
		events.register(Event.NONE, this::handleMoveWidget);
	}
	//=============================================================================================
	
	//=============================================================================================
	private void handleAddWidget(Event e) {
		Widget widget = (Widget) e.data[0];
		Widget parent = (Widget) e.data[1];
		Hierarchy widgetHierarchy = hierarchyMap.get(widget);
		Hierarchy parentHierarchy = hierarchyMap.get(parent);
		widgetHierarchy.parent = parent;
		Widget child = parentHierarchy.first;
		if (child == null) {
			parentHierarchy.first = widget;
			widgetHierarchy.prev = null;
			widgetHierarchy.next = null;
		} else {
			Hierarchy childHierarchy = hierarchyMap.get(child);
			while (childHierarchy.next != null) {
				child = childHierarchy.next;
				childHierarchy = hierarchyMap.get(child);
			}
			childHierarchy.next = widget;
			widgetHierarchy.prev = child;
			widgetHierarchy.next = null;
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void handleDeleteWidget(Event e) {
		Widget widget = (Widget) e.data[0];
	}
	//=============================================================================================

	//=============================================================================================
	private void handleMoveWidget(Event e) {
		Widget widget = (Widget) e.data[0];
		Widget parent = (Widget) e.data[1];
	}
	//=============================================================================================
	
	//=============================================================================================
	public void update() {
		for (Entry<Widget, Hierarchy> entry : hierarchyMap.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void update(Widget w, Hierarchy h) {
		
	}
	//=============================================================================================
	
}
//*************************************************************************************************
