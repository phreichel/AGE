//*************************************************************************************************
package ode.platform;
//*************************************************************************************************

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import ode.gui.ODEWidgets;

//*************************************************************************************************
public class ODEGraphicsHandler implements GLEventListener {

	//=============================================================================================
	private final ODEGraphics graphics = new ODEGraphics();
	private ODEWidgets widgets = null;
	//=============================================================================================

	//=============================================================================================
	public void assign(ODEWidgets widgets) {
		this.widgets = widgets;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void init(GLAutoDrawable drawable) {
		graphics.assign(drawable);
	}
	//=============================================================================================

	//=============================================================================================
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		graphics.assign(drawable);
	}
	//=============================================================================================
	

	//=============================================================================================
	public void display(GLAutoDrawable drawable) {
		graphics.assign(drawable);
		if (widgets != null) {
			widgets.render(graphics);
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	public void dispose(GLAutoDrawable drawable) {
		graphics.assign(drawable);
	}
	//=============================================================================================
	
}
//*************************************************************************************************