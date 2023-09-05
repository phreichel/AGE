//*************************************************************************************************
package ode.npa;
//*************************************************************************************************

import javax.vecmath.Color4f;
import javax.vecmath.Vector2f;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.texture.Texture;

import ode.asset.Assets;

//*************************************************************************************************
public class Graphics {

	//=============================================================================================
	private GLAutoDrawable window;
	private GL2 gl;
	private GLU glu;
	private Assets assets;
	//=============================================================================================

	//=============================================================================================
	public void assign(GLAutoDrawable window) {
		this.window = window;
		this.gl = this.window.getGL().getGL2();
		this.glu = GLU.createGLU(this.gl);
	}
	//=============================================================================================

	//=============================================================================================
	public void assign(Assets assets) {
		this.assets = assets;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void clearColor(Color4f c) {
		gl.glClearColor(c.x, c.y, c.z, c.w);
	}
	//=============================================================================================	

	//=============================================================================================
	public void clearColor(float r, float g, float b, float a) {
		gl.glClearColor(r, g, b, a);
	}
	//=============================================================================================	
	
	//=============================================================================================
	public void clear() {
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
	}
	//=============================================================================================

	//=============================================================================================
	public void ortho() {
		gl.glDisable(GL2.GL_LIGHTING);
		gl.glDisable(GL2.GL_DEPTH_TEST);
		gl.glDisable(GL2.GL_CULL_FACE);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		int w = window.getSurfaceWidth();
		int h = window.getSurfaceHeight();
		glu.gluOrtho2D(-1, w, h, -1);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
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
	public void translate(Vector2f position) {
		gl.glTranslatef(position.x, position.y, 0f);
	}
	//=============================================================================================

	//=============================================================================================
	public void color(Color4f c) {
		if (c.w != 1f) {
			gl.glEnable(GL2.GL_BLEND);
		} else {
			gl.glDisable(GL2.GL_BLEND);
		}
		gl.glColor4f(c.x, c.y, c.z, c.w);
	}
	//=============================================================================================	

	//=============================================================================================
	public void color(float r, float g, float b, float a) {
		if (a != 1f) {
			gl.glEnable(GL2.GL_BLEND);
		} else {
			gl.glDisable(GL2.GL_BLEND);
		}
		gl.glColor4f(r, g, b, a);
	}
	//=============================================================================================	
	
	//=============================================================================================
	public void fillRectangle(float x, float y, float w, float h) {
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex2f(x+0,y+0);
		gl.glVertex2f(x+w,y+0);
		gl.glVertex2f(x+w,y+h);
		gl.glVertex2f(x+0,y+h);
		gl.glEnd();
	}
	//=============================================================================================

	//=============================================================================================
	public void drawRectangle(float x, float y, float w, float h) {
		draw2DLineLoop(
			x+0, y+0,
			x+w, y+0,
			x+w, y+h,
			x+0, y+h);
	}
	//=============================================================================================

	//=============================================================================================
	public void drawImage(float x, float y, float w, float h, String name) {
		Texture texture = assets.getTexture(name);
		texture.bind(gl);
		texture.enable(gl);
		gl.glBegin(GL2.GL_QUADS);
		gl.glTexCoord2f(0,1);
		gl.glVertex2f(x+0,y+0);
		gl.glTexCoord2f(1,1);
		gl.glVertex2f(x+w,y+0);
		gl.glTexCoord2f(1,0);
		gl.glVertex2f(x+w,y+h);
		gl.glTexCoord2f(0,0);
		gl.glVertex2f(x+0,y+h);
		gl.glEnd();
		texture.disable(gl);
	}
	//=============================================================================================

	//=============================================================================================
	public void drawText(String text, float x, float y, String name) {
		TextRenderer textRenderer = assets.getFont(name);
		gl.glScalef(1, -1, 1);
		textRenderer.begin3DRendering();
		textRenderer.draw3D(text, x, -y, 0, 1);
		textRenderer.end3DRendering();
		gl.glScalef(1, -1, 1);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void draw2DLineStrip(float ... coords) {
		gl.glBegin(GL2.GL_LINE_STRIP);
		for (int i=0; i<coords.length;) {
			float x = coords[i++];
			float y = coords[i++];
			gl.glVertex2f(x,y);
		}
		gl.glEnd();
	}
	//=============================================================================================
	
	//=============================================================================================
	public void draw2DLineLoop(float ... coords) {
		gl.glBegin(GL2.GL_LINE_LOOP);
		for (int i=0; i<coords.length;) {
			float x = coords[i++];
			float y = coords[i++];
			gl.glVertex2f(x,y);
		}
		gl.glEnd();
	}
	//=============================================================================================
	
}
//*************************************************************************************************
