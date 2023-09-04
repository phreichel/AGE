//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import java.util.HashMap;
import java.util.Map;

import javax.vecmath.Color4f;

import ode.gui.Style.KEY;

//*************************************************************************************************
public class Theme {

	//=============================================================================================
	private Style base;
	private final Map<Type, Style> styles = new HashMap<>();
	//=============================================================================================

	//=============================================================================================
	public Theme() {
		construct();
	}
	//=============================================================================================

	//=============================================================================================
	public void construct() {
		
		base = new Style();
		base.put(KEY.FOREGROUND, new Color4f(1, 0, 0, 1));
		base.put(KEY.BACKGROUND, new Color4f(.8f, .8f, .8f, 1));
		base.put(KEY.BORDER_LIGHT, new Color4f(.6f, 1f, .6f, 1));
		base.put(KEY.BORDER_DARK, new Color4f(.3f, .6f, .3f, 1));
		
		Style button = base.derive();
		button.put(KEY.FOREGROUND, new Color4f(0, 0, 1, 1));
		button.put(KEY.BACKGROUND, new Color4f(.4f, .4f, 1f, 1));

		Style group = base.derive();
		group.put(KEY.BACKGROUND, new Color4f(.3f, .3f, .3f, 1));
		group.put(KEY.BORDER_LIGHT, new Color4f(1f, 1f, 1f, 1));
		group.put(KEY.BORDER_DARK, new Color4f(0f, 0f, 0f, 1));
	
		styles.put(Type.BUTTON, button);
		styles.put(Type.GROUP, group);

	}
	//=============================================================================================
	
	//=============================================================================================
	public Style get(Type type) {
		Style style= styles.get(type);
		if (style == null) style = base;
		return style;
	}
	//=============================================================================================
	
}
//*************************************************************************************************
