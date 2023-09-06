//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import java.util.HashMap;
import java.util.Map;

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
		base.putColor(Style.FOREGROUND, 1, 0, 0, 1)
			.putColor(Style.BACKGROUND, .8f, .8f, .8f, 1)
			.putColor(Style.BACKGROUND_HOVER, 1f, 1f, .8f, 1)
			.putColor(Style.BORDER_LIGHT, .6f, 1f, .6f, 1)
			.putColor(Style.BORDER_DARK, .3f, .6f, .3f, 1);
		
		Style button = base
			.derive()
			.putColor(Style.FOREGROUND, 0, 0, 1, 1)
			.putColor(Style.BACKGROUND, .4f, .4f, 1f, 1)
			.putColor(Style.BACKGROUND_HOVER, .8f, .8f, 1f, 1);

		Style group = base
			.derive()
			.putColor(Style.BACKGROUND, .3f, .3f, .3f, 1)
			.putColor(Style.BACKGROUND_HOVER, .6f, .6f, .3f, 1)
			.putColor(Style.BORDER_LIGHT, 1f, .5f, .5f, 1)
			.putColor(Style.BORDER_DARK, .5f, 0f, 0f, 1);
	
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
