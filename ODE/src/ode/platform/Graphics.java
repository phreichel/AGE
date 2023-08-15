//*************************************************************************************************L
package ode.platform;
//*************************************************************************************************

import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.awt.TextRenderer;

import ode.util.Color;
import ode.util.Dimension;

//*************************************************************************************************
public class Graphics {

	//=============================================================================================
	private GLAutoDrawable window;
	private GL2 gl;
	private GLU glu;
	//=============================================================================================

	//=============================================================================================
	public void init(GLAutoDrawable drawable) {
		window = drawable;
		gl = window.getGL().getGL2();
		glu = GLU.createGLU(gl);
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
		gl.glDisable(GL2.GL_DEPTH_TEST);
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
	public void translate(float x, float y) {
		gl.glTranslatef(x, y, 0f);
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
	public void drawString(float x, float y, Font font, String text, Color color) {
		drawString(x, y , font, text, color.red, color.green, color.blue, color.alpha);
	}
	//=============================================================================================
	
	
	//=============================================================================================
	private final Dimension STRING_SIZE = new Dimension();
	//=============================================================================================
	public void drawString(float x, float y, Font font, String text, float r, float g, float b, float a) {
		textSize(text, font, STRING_SIZE);
		float height = STRING_SIZE.height;
		gl.glPushMatrix();
		gl.glTranslatef(x, y + height, 0f);
		gl.glScalef(1f, -1f, 1f);
		TextRenderer textRenderer = font.getRenderer();
		textRenderer.setColor(r, g, b, a);
		textRenderer.begin3DRendering();
		textRenderer.draw3D(text, 0, 0, 0 , 1);
		textRenderer.end3DRendering();
		gl.glPopMatrix();
	}
	//=============================================================================================

	//=============================================================================================
	public void textSize(String text, Font font, Dimension size) {
		Rectangle2D rect = font.getRenderer().getBounds(text);
		size.set((float) rect.getWidth(), (float) rect.getHeight());
	}
	//=============================================================================================

	//=============================================================================================
	private static final AffineTransform FONT_TRANSFORM = new AffineTransform(1, 0, 1, 0, 0, 0);
	//=============================================================================================
	public float textDescent(String text, Font font) {
		FontRenderContext ctx = new FontRenderContext(FONT_TRANSFORM, false, false);
		LineMetrics metrics = font.getRenderer().getFont().getLineMetrics(text, ctx);
		float descent = metrics.getDescent();
		return descent;
	}
	//=============================================================================================
	
}
//*************************************************************************************************