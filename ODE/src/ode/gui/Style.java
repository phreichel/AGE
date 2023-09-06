//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import java.util.HashMap;
import java.util.Map;

import javax.vecmath.Color4f;

//*************************************************************************************************
public class Style {

	//=============================================================================================
	public static final String FOREGROUND = "fg";
	public static final String BACKGROUND = "bg";
	public static final String BACKGROUND_HOVER = "bg_hover";
	public static final String BORDER_LIGHT = "border_light";
	public static final String BORDER_DARK = "border_dark";
	//=============================================================================================
	
	//=============================================================================================
	private Style parent;
	private Map<String, Object> properties= new HashMap<>();
	//=============================================================================================

	//=============================================================================================
	public Style() {
		this.parent = null;
	}
	//=============================================================================================

	//=============================================================================================
	Style(Style parent) {
		this.parent = parent;
	}
	//=============================================================================================

	//=============================================================================================
	public Style derive() {
		return new Style(this);
	}
	//=============================================================================================

	//=============================================================================================
	public Style put(String key, Object value) {
		properties.put(key, value);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Style putColor(String key, float r, float g, float b, float a) {
		properties.put(key, new Color4f(r, g, b, a));
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	public Object get(String key) {
		Object value = properties.get(key);
		return (value != null) ? value : (parent != null) ? parent.get(key) : null;
	}
	//=============================================================================================

	//=============================================================================================
	@SuppressWarnings("unchecked")
	public <C> C get(String key, Class<C> clazz) {
		C value = (C) properties.get(key);
		return (value != null) ? value : (parent != null) ? parent.get(key, clazz) : null;
	}
	//=============================================================================================

	//=============================================================================================
	public Color4f getColor(String key) {
		return get(key, Color4f.class);
	}
	//=============================================================================================
	
}
//*************************************************************************************************
