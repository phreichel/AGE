//*************************************************************************************************
package age.port.jogl;
//*************************************************************************************************

import com.jogamp.opengl.GL2;
import static com.jogamp.opengl.GL2.*;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.vecmath.Color4f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector2f;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.TextureIO;
import age.AGEException;
import age.Util;
import age.port.Graphics;

//*************************************************************************************************
//TODO:add javadoc comments
class JOGLGraphics implements Graphics {

	//=============================================================================================
	private GLAutoDrawable drawable = null;
	private GL2 gl = null;
	private GLU glu = null;
	//=============================================================================================

	//=============================================================================================
	private Map<String, Texture> textures = new HashMap<>();
	private Map<String, TextRenderer> fonts = new HashMap<>();
	//=============================================================================================
	
	//=============================================================================================
	public void assign(GLAutoDrawable drawable) {
		this.drawable = drawable;
		gl = drawable.getGL().getGL2();
		glu = GLU.createGLU(gl);
	}
	//=============================================================================================

	//=============================================================================================
	public void init() {
		
		gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		gl.glHint(GL_GENERATE_MIPMAP_HINT, GL_NICEST);
		gl.glHint(GL_POINT_SMOOTH_HINT, GL_NICEST);
		gl.glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
		gl.glHint(GL_POLYGON_SMOOTH_HINT, GL_NICEST);
		gl.glHint(GL_FOG_HINT, GL_NICEST);
		
		gl.glEnable(GL_COLOR_MATERIAL);
		gl.glColorMaterial(GL_FRONT_AND_BACK, GL_AMBIENT_AND_DIFFUSE);

		try {
			
			Texture texture = null;

			texture = TextureIO.newTexture(new File("./assets/textures/gui/close_inv.png"), true);
			textures.put("close", texture);
			
			texture = TextureIO.newTexture(new File("./assets/textures/gui/size_inv.png"), true);
			textures.put("size", texture);

			texture = TextureIO.newTexture(new File("./assets/textures/gui/plus_inv.png"), true);
			textures.put("plus", texture);

			texture = TextureIO.newTexture(new File("./assets/textures/gui/shutdown_inv.png"), true);
			textures.put("shutdown", texture);

			texture = TextureIO.newTexture(new File("./assets/textures/gui/fullscreen_inv.png"), true);
			textures.put("fullscreen", texture);

			texture = TextureIO.newTexture(new File("./assets/textures/gui/arrowup_inv.png"), true);
			textures.put("arrowup", texture);

			texture = TextureIO.newTexture(new File("./assets/textures/gui/arrowdown_inv.png"), true);
			textures.put("arrowdown", texture);

			texture = TextureIO.newTexture(new File("./assets/textures/gui/desk.png"), true);
			textures.put("desk", texture);
			
		} catch (Exception e) {
			throw new AGEException(e);
		}

		{
		
			TextRenderer font = null;
		
			font = new TextRenderer(Font.decode("Arial BOLD 16"), false);
			font.setColor(1f, 0f, 0f, 1f);
			font.setSmoothing(true);
			fonts.put("title", font);

			font = new TextRenderer(Font.decode("Monospaced PLAIN 14"), false);
			font.setColor(1f, .9f, 0f, 1f);
			font.setSmoothing(false);
			fonts.put("text", font);
			
		}

	}
	//=============================================================================================
	
	//=============================================================================================
	public void clear() {
		gl.glClear(GL_COLOR_BUFFER_BIT);
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
		gl.glEnable(GL_LIGHT0);
		gl.glEnable(GL_CULL_FACE);
		gl.glEnable(GL_DEPTH_TEST);
		gl.glClear(GL_DEPTH_BUFFER_BIT);
	}
	//=============================================================================================	
	
	//=============================================================================================
	public void mode2D() {
		int w = drawable.getSurfaceWidth();
		int h = drawable.getSurfaceHeight();
		gl.glMatrixMode(GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluOrtho2D(-1, w, h, -1);
		gl.glMatrixMode(GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glDisable(GL_LIGHTING);
		gl.glDisable(GL_CULL_FACE);
		gl.glDisable(GL_DEPTH_TEST);
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
	private float[] buffer = new float[16];
	//=============================================================================================
	
	//=============================================================================================
	public void applyTransformation(Matrix4f matrix) {
		buffer = Util.toGLMatrix(matrix, buffer);
		gl.glMultMatrixf(buffer, 0);
		//gl.glMatrixMultfEXT(GL_MODELVIEW, buffer, 0);
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
		int mode = hollow ? GL_LINE : GL_FILL;
		gl.glPushAttrib(GL_POLYGON_BIT);
		gl.glPolygonMode(GL_FRONT_AND_BACK, mode);
		gl.glBegin(GL_QUADS);
		gl.glVertex2f(x+0, y+0);
		gl.glVertex2f(x+w, y+0);
		gl.glVertex2f(x+w, y+h);
		gl.glVertex2f(x+0, y+h);
		gl.glEnd();
		gl.glPopAttrib();
	}
	//=============================================================================================

	//=============================================================================================
	public void text(float x, float y, CharSequence text, String font) {
		TextRenderer textRenderer = fonts.get(font);
		gl.glScalef(1, -1, 1);
		textRenderer.begin3DRendering();
		textRenderer.draw3D(text, x, -y, 0f, 1f);
		textRenderer.end3DRendering();
		gl.glScalef(1, -1, 1);
	}
	//=============================================================================================

	//=============================================================================================
	public void texture(float x, float y, float w, float h, String texture) {
		
		Texture tex = textures.get(texture);
		tex.bind(gl);
		tex.enable(gl);
		
		TextureCoords coords = tex.getImageTexCoords();
		float tx1 = coords.left();
		float tx2 = coords.right();
		float ty1 = coords.top();
		float ty2 = coords.bottom();
		
		gl.glBegin(GL_QUADS);
		gl.glTexCoord2f(tx1, ty1);
		gl.glVertex2f(x+0, y+0);
		gl.glTexCoord2f(tx2, ty1);
		gl.glVertex2f(x+w, y+0);
		gl.glTexCoord2f(tx2, ty2);
		gl.glVertex2f(x+w, y+h);
		gl.glTexCoord2f(tx1, ty2);
		gl.glVertex2f(x+0, y+h);
		gl.glEnd();
		
		tex.disable(gl);
		
	}
	//=============================================================================================

	//=============================================================================================
	public void calcMultitext(String text, Vector2f dimension, String font, int[] buffer) {
		calcMultitext(
			text,
			dimension.x,
			dimension.y,
			font,
			buffer);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void calcMultitext(String text, float width, float height, String font, int[] buffer) {
		TextRenderer textRenderer = fonts.get(font);
		Rectangle2D rect = textRenderer.getBounds("_");
		double space = rect.getWidth();
		int mark = 0;
		int count = 3;
		double lineHeight = 0;
		for (int i=0; i<text.length(); i++) {

			CharSequence seq = text.subSequence(mark, i);
			rect = textRenderer.getBounds(seq);
			double lineWidth = rect.getWidth();
			lineHeight = Math.max(lineHeight, rect.getHeight());
			
			// fix that empty spaces at the start of line are not considered in getBounds
			for (int j=0; j<seq.length(); j++) {
				if (seq.charAt(j) != ' ') break;
				lineWidth += space;
			}

			// failsafe in case buffer is too small
			if ((buffer.length - count) < 4) break;
			
			double delta = width - lineWidth;
			if (text.charAt(i) == '\n') {
				buffer[count++] = mark;
				buffer[count++] = i;
				mark = i+1;
			} else if (delta < space) {
				buffer[count++] = mark;
				buffer[count++] = i;
				mark = i;
			}
		}
		if (mark != text.length()) {
			buffer[count++] = mark; 
			buffer[count++] = text.length();
		}
		buffer[0] = (count / 2) - 1;
		buffer[1] = (int) Math.floor(height / lineHeight);
		buffer[2] = (int) Math.ceil(lineHeight);
	}
	//=============================================================================================

	//=============================================================================================
	public void drawBox(float sx, float sy, float sz) {
		gl.glBegin(GL_QUADS);
		gl.glNormal3f(0, 0, 1);
		gl.glVertex3f(-sx,-sy, sz);
		gl.glVertex3f( sx,-sy, sz);
		gl.glVertex3f( sx, sy, sz);
		gl.glVertex3f(-sx, sy, sz);
		gl.glNormal3f(0, 0,-1);
		gl.glVertex3f( sx,-sy,-sz);
		gl.glVertex3f(-sx,-sy,-sz);
		gl.glVertex3f(-sx, sy,-sz);
		gl.glVertex3f( sx, sy,-sz);
		gl.glNormal3f(0, 1, 0);
		gl.glVertex3f(-sx, sy, sz);
		gl.glVertex3f( sx, sy, sz);
		gl.glVertex3f( sx, sy,-sz);
		gl.glVertex3f(-sx, sy,-sz);
		gl.glNormal3f(0,-1, 0);
		gl.glVertex3f(-sx,-sy,-sz);
		gl.glVertex3f( sx,-sy,-sz);
		gl.glVertex3f( sx,-sy, sz);
		gl.glVertex3f(-sx,-sy, sz);
		gl.glNormal3f( 1, 0, 0);
		gl.glVertex3f( sx,-sy, sz);
		gl.glVertex3f( sx,-sy,-sz);
		gl.glVertex3f( sx, sy,-sz);
		gl.glVertex3f( sx, sy, sz);
		gl.glNormal3f(-1, 0, 0);
		gl.glVertex3f(-sx,-sy,-sz);
		gl.glVertex3f(-sx,-sy, sz);
		gl.glVertex3f(-sx, sy, sz);
		gl.glVertex3f(-sx, sy,-sz);
		gl.glEnd();
	}
	//=============================================================================================
	
}
//*************************************************************************************************
