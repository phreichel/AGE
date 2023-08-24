//*************************************************************************************************L
package ode.platform;
//*************************************************************************************************

import java.awt.Font;
import java.awt.geom.Rectangle2D;

import javax.vecmath.Matrix4f;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.awt.TextRenderer;

//*************************************************************************************************
public class Graphics {

	//=============================================================================================
	private GLAutoDrawable window;
	private GL2 gl;
	private GLU glu;
	//=============================================================================================

	//=============================================================================================
	private TextRenderer textRenderer = new TextRenderer(Font.decode("Arial BOLD 16"), false);
	//=============================================================================================
	
	//=============================================================================================
	public void init(GLAutoDrawable drawable) {
		window = drawable;
		gl = window.getGL().getGL2();
		glu = GLU.createGLU(gl);
		textRenderer.setSmoothing(false);
	}
	//=============================================================================================

	//=============================================================================================
	public void startFrame() {
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
	}
	//=============================================================================================

	//=============================================================================================
	public void clearDepth() {
		gl.glClear(GL2.GL_DEPTH_BUFFER_BIT);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void setColor(float r, float g, float b) {
		gl.glColor3f(r, g, b);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void setColor(float r, float g, float b, float a) {
		gl.glColor4f(r, g, b, a);
	}
	//=============================================================================================

	//=============================================================================================
	public void beginGUIMode() {
		gl.glDisable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glDisable(GL2.GL_CULL_FACE);
		gl.glEnable(GL2.GL_COLOR_MATERIAL);
		gl.glColorMaterial(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluOrtho2D(-1, window.getSurfaceWidth(), window.getSurfaceHeight(), -1);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
	}
	//=============================================================================================

	//=============================================================================================
	public void beginSceneMode() {
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glEnable(GL2.GL_CULL_FACE);
		gl.glEnable(GL2.GL_COLOR_MATERIAL);
		gl.glColorMaterial(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		float aspect = (float) window.getSurfaceWidth() / window.getSurfaceHeight();
		glu.gluPerspective(35f, aspect, 0.1f, 1000f);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
	}
	//=============================================================================================

	//=============================================================================================
	public void pushTransform() {
		gl.glPushMatrix();
	}
	//=============================================================================================
	
	//=============================================================================================
	public void popTransform() {
		gl.glPopMatrix();
	}
	//=============================================================================================

	//=============================================================================================
	float[] matrix = new float[16];
	//=============================================================================================

	//=============================================================================================
	public void applyMatrix(Matrix4f m) {
		
	}
	//=============================================================================================
	
	//=============================================================================================
	public void translate(float x, float y, float z) {
		gl.glTranslatef(x, y, z);
	}
	//=============================================================================================

	//=============================================================================================
	public void drawRectangle(float x, float y, float w, float h) {
		gl.glBegin(GL2.GL_LINE_LOOP);
		gl.glVertex2f(x+0, y+0);
		gl.glVertex2f(x+w, y+0);
		gl.glVertex2f(x+w, y+h);
		gl.glVertex2f(x+0, y+h);
		gl.glEnd();
	}
	//=============================================================================================

	//=============================================================================================
	public void fillRectangle(float x, float y, float w, float h) {
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex2f(x+0, y+0);
		gl.glVertex2f(x+w, y+0);
		gl.glVertex2f(x+w, y+h);
		gl.glVertex2f(x+0, y+h);
		gl.glEnd();
	}
	//=============================================================================================

	//=============================================================================================
	public void drawText(
			String text,
			float x, float y, float z,
			float w, float h,
			float r, float g, float b) {
		gl.glPushMatrix();
		gl.glScalef(1f, -1f, 1f);
		Rectangle2D rect = textRenderer.getBounds(text);
		float ofsx = (w - (float) rect.getWidth()) * .5f;
		float ofsy = (h - (float) rect.getHeight()) * .5f;
		textRenderer.setColor(r, g, b, 1);
		textRenderer.begin3DRendering();
		textRenderer.draw3D(text, x + ofsx, y-(h-ofsy), z, 1);
		textRenderer.end3DRendering();
		gl.glPopMatrix();
	}
	//=============================================================================================
	
}
//*************************************************************************************************