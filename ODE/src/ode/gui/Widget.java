//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import java.util.HashMap;
import java.util.Map;

//*************************************************************************************************
public class Widget {

	//=============================================================================================
	private final Map<Class<?>, Object> components = new HashMap<>();
	//=============================================================================================

	//=============================================================================================
	@SuppressWarnings("unchecked")
	public <T extends Object> T get(Class<T> cls) {
		return (T) components.get(cls);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void add(Class<?> cls, Object component) {
		components.put(cls, component);
	}
	//=============================================================================================
	
}
//*************************************************************************************************