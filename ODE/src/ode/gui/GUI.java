//*************************************************************************************************
package ode.gui;

import ode.platform.Graphics;

//*************************************************************************************************

//*************************************************************************************************
public class GUI extends Widget {

	//=============================================================================================
	public void resize(float width, float height) {
		setSize(width, height);
	}
	//=============================================================================================

	//=============================================================================================
	public void render(Graphics g) {
		g.beginGUIMode();
		super.render(g);
	}
	//=============================================================================================
	
}
//*************************************************************************************************