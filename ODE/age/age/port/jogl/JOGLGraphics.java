//*************************************************************************************************
package age.port.jogl;
//*************************************************************************************************

import com.jogamp.opengl.GL2;
import static com.jogamp.opengl.GL2.*;

import javax.vecmath.Color4f;
import javax.vecmath.Vector2f;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;
import age.port.Graphics;

//*************************************************************************************************
class JOGLGraphics implements Graphics {

	//=============================================================================================
	private final JOGLPort port;
	//=============================================================================================
	
	//=============================================================================================
	private GLAutoDrawable drawable = null;
	private GL2 gl = null;
	private GLU glu = null;
	//=============================================================================================

	//=============================================================================================
	public JOGLGraphics(JOGLPort port) {
		this.port = port;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void assign(GLAutoDrawable drawable) {
		this.drawable = drawable;
		gl = drawable.getGL().getGL2();
		glu = GLU.createGLU(gl);
	}
	//=============================================================================================

	//=============================================================================================
	public void clear() {
		gl.glClear(GL_COLOR_BUFFER_BIT);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void mode2D() {
		int w = drawable.getSurfaceWidth();
		int h = drawable.getSurfaceHeight();
		gl.glMatrixMode(GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluOrtho2D(-1, w, 0, h+1);
		gl.glMatrixMode(GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glDisable(GL_LIGHTING);
		gl.glDisable(GL_CULL_FACE);
		gl.glDisable(GL_DEPTH_TEST);
		gl.glClear(GL_DEPTH_BUFFER_BIT);
	}
	//=============================================================================================	

	//=============================================================================================
	public void mode3D(float fovy, float near, float far) {
		float w = drawable.getSurfaceWidth();
		float h = drawable.getSurfaceHeight();
		float aspect = w / h;
		gl.glMatrixMode(GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(fovy, aspect, near, far);
		gl.glMatrixMode(GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glEnable(GL_LIGHTING);
		gl.glEnable(GL_CULL_FACE);
		gl.glEnable(GL_DEPTH_TEST);
		gl.glClear(GL_DEPTH_BUFFER_BIT);
	}
	//=============================================================================================	
	
	//=============================================================================================
	public void pushTransformation() {
		gl.glPushMatrix();
	}
	//=============================================================================================

	//=============================================================================================
	public void popTransformation() {
		gl.glPopMatrix();
	}
	//=============================================================================================
	
	//=============================================================================================
	public void translate(Vector2f t) {
		translate(t.x, t.y);
	}
	//=============================================================================================

	//=============================================================================================
	public void translate(float x, float y) {
		gl.glTranslatef(x, y, 0);
	}
	//=============================================================================================

	//=============================================================================================
	public void color(Color4f c) {
		color(c.x, c.y, c.z, c.w);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void color(float r, float g, float b) {
		gl.glColor3f(r, g, b);
	}
	//=============================================================================================

	//=============================================================================================
	public void color(float r, float g, float b, float a) {
		gl.glColor4f(r, g, b, a);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void rectangle(Vector2f dim, boolean hollow) {
		rectangle(0, 0, dim.x, dim.y, hollow);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void rectangle(Vector2f pos, Vector2f dim, boolean hollow) {
		rectangle(pos.x, pos.y, dim.x, dim.y, hollow);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void rectangle(float x, float y, float w, float h, boolean hollow) {
		int mode = hollow ? GL_LINE : GL_POLYGON;
		gl.glPushClientAttrib(GL_POLYGON_MODE);
		gl.glPolygonMode(GL_FRONT_AND_BACK, mode);
		gl.glBegin(GL_QUADS);
		gl.glVertex2f(x+0, y+0);
		gl.glVertex2f(x+w, y+0);
		gl.glVertex2f(x+w, y+h);
		gl.glVertex2f(x+0, y+h);
		gl.glEnd();
		gl.glPopClientAttrib();
	}
	//=============================================================================================
	
}
//*************************************************************************************************
