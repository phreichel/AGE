//*************************************************************************************************
package ode.platform;
//*************************************************************************************************

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

//*************************************************************************************************
class NEWTGraphicsAdapter implements GLEventListener {

	//=============================================================================================
	private Graphics graphics = new Graphics();
	//=============================================================================================
	
	//=============================================================================================
	public void init(GLAutoDrawable drawable) {
	}
	//=============================================================================================

	//=============================================================================================
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
	}
	//=============================================================================================
	
	//=============================================================================================
	public void display(GLAutoDrawable drawable) {
		graphics.init(drawable);
	}
	//=============================================================================================

	//=============================================================================================
	public void dispose(GLAutoDrawable drawable) {
	}
	//=============================================================================================

}
//*************************************************************************************************
