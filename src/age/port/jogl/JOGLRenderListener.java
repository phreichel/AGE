//*************************************************************************************************
package age.port.jogl;
//*************************************************************************************************

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import age.port.Renderable;

//*************************************************************************************************
class JOGLRenderListener implements GLEventListener {

	//=============================================================================================
	private final JOGLPort port; 
	private final JOGLGraphics graphics;
	//=============================================================================================

	//=============================================================================================
	public JOGLRenderListener(JOGLPort port) {
		this.port = port;
		graphics = new JOGLGraphics();
	}
	//=============================================================================================
	
	//=============================================================================================
	public void init(GLAutoDrawable drawable) {
		graphics.assign(drawable);
		graphics.init();
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
		graphics.clear();
		for (Renderable renderable : port.get()) {
			renderable.render(graphics);
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
