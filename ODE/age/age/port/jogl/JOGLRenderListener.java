/*
 * Commit: 4ba52177b03c84982e184472f4e51a9157e9a67f
 * Date: 2023-09-21 20:15:06+02:00
 * Author: Philip Reichel
 * Comment: Cleaning all up
 *
 * Commit: efba03d014f9f8690fc57eb3949bb36f7ed2e269
 * Date: 2023-09-19 07:28:45+02:00
 * Author: Philip Reichel
 * Comment: todo tasks
 *
 * Commit: d119faba58fda709df89ef025cf506e6ae694957
 * Date: 2023-09-16 13:30:06+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 * Commit: 979912e4583b2e47ff4138caf044ae2005b4a274
 * Date: 2023-09-15 21:18:58+02:00
 * Author: Philip Reichel
 * Comment: Refactoring GUI Window Rendering
 *
 * Commit: 275f07861ea5f95dd90f184d1311a0a5a8cd510d
 * Date: 2023-09-14 17:55:15+02:00
 * Author: pre7618
 * Comment: GUI in progress
 *
 * Commit: 343fe7d3b3a248993aa01b34632a93f7d5c8e09c
 * Date: 2023-09-12 07:49:14+02:00
 * Author: Philip Reichel
 * Comment: Relocation and new Design Merger
 *
 */

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
