//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import java.util.EnumMap;
import java.util.Map;

import ode.util.ODEException;

//*************************************************************************************************
public class Widget {

	//=============================================================================================
	private final Map<WidgetEnum, Object> components = new EnumMap<>(WidgetEnum.class);
	//=============================================================================================

	//=============================================================================================
	public void addComponent(WidgetEnum ident, Object component) {
		if (!ident.getComponentClass().isInstance(component)) {
			throw new ODEException("Invalid Component Class. expected: " + ident.getComponentClass().getName() + " detected: " + component.getClass().getName());
		}
		components.put(ident, component);
	}
	//=============================================================================================

	//=============================================================================================
	public Object getComponent(WidgetEnum ident) {
		return components.get(ident);
	}
	//=============================================================================================

	//=============================================================================================
	@SuppressWarnings("unchecked")
	public <C> C getComponent(WidgetEnum ident, Class<C> componentClass) {
		return (C) components.get(ident);
	}
	//=============================================================================================
	
}
//*************************************************************************************************
