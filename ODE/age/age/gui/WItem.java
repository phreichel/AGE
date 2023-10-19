/*
 * Commit: aa20ad72b29161d58217d659b23accf2664ef3cf
 * Date: 2023-09-26 23:19:19+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 */

//*************************************************************************************************
package age.gui;
//*************************************************************************************************

import age.util.X;

//*************************************************************************************************
public enum WItem {

	//=============================================================================================
	TEXT(String.class),
	COMMAND(String.class),
	IMAGE_NAME(String.class),
	DRAGGED_WIDGET(Widget.class),
	RESIZED_WIDGET(Widget.class),
	CLOSED_WIDGET(Widget.class),
	SCROLLABLE_HORIZONTAL(Scrollable.class),
	SCROLLABLE_VERTICAL(Scrollable.class),
	SCROLL_WIDGET(Widget.class),
	MULTILINE_STATE(Multiline.class);
	//=============================================================================================

	//=============================================================================================
	private Class<?> cls;
	//=============================================================================================
	
	//=============================================================================================
	private WItem(Class<?> cls) {
		this.cls = cls;
	}
	//=============================================================================================

	//=============================================================================================
	public void check(Object object) {
		if (!cls.isInstance(object)) {
			throw new X(this.toString() + ": " + cls.getName() + " expected.");
		}
	}
	//=============================================================================================
	
}
//*************************************************************************************************
