//*************************************************************************************************
package ode.npa;
//*************************************************************************************************

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import ode.asset.Assets;
import ode.gui.GUI;

//*************************************************************************************************
public class GraphicsHandler implements GLEventListener {

	//=============================================================================================
	private final Graphics graphics = new Graphics();
	private Assets assets = null;
	private GUI widgets = null;
	//=============================================================================================

	//=============================================================================================
	public void assign(Assets assets) {
		this.assets = assets;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void assign(GUI widgets) {
		this.widgets = widgets;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void init(GLAutoDrawable drawable) {
		graphics.assign(assets);
		graphics.assign(drawable);
	}
	//=============================================================================================

	//=============================================================================================
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		graphics.assign(assets);
		graphics.assign(drawable);
	}
	//=============================================================================================
	

	//=============================================================================================
	public void display(GLAutoDrawable drawable) {
		graphics.assign(assets);
		graphics.assign(drawable);
		assets.loadPendingFonts();
		assets.loadPendingTextures();
		graphics.clearColor(0f, 0f, .2f, 1f);
		graphics.clear();
		if (widgets != null) {
			widgets.render(graphics);
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	public void dispose(GLAutoDrawable drawable) {
		graphics.assign(assets);
		graphics.assign(drawable);
	}
	//=============================================================================================
	
}
//*************************************************************************************************
