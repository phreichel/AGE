//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import ode.client.Graphics;

//*************************************************************************************************
public class GUI extends Widget {

	//=============================================================================================
	public void resize(float width, float height) {
		
	}
	//=============================================================================================

	//=============================================================================================
	public void render(Widget widget, Graphics g) {
		for (Widget child : children) {
			render(child, g);
		}
	}
	//=============================================================================================
	
}
//*************************************************************************************************