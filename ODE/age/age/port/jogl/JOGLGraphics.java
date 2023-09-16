//*************************************************************************************************
package age.port.jogl;
//*************************************************************************************************

import com.jogamp.opengl.GL2;
import static com.jogamp.opengl.GL2.*;
import java.awt.Font;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.vecmath.Color4f;
import javax.vecmath.Vector2f;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.TextureIO;
import age.AGEException;
import age.port.Graphics;

//*************************************************************************************************
class JOGLGraphics implements Graphics {

	//=============================================================================================
	private GLAutoDrawable drawable = null;
	private GL2 gl = null;
	private GLU glu = null;
	//=============================================================================================

	//=============================================================================================
	private TextRenderer titleText;
	//=============================================================================================

	//=============================================================================================
	private Map<String, Texture> textures = new HashMap<>();
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

		titleText = new TextRenderer(Font.decode("Arial BOLD 16"), true);
		titleText.setColor(1f, 0f, 0f, 1f);
		titleText.setSmoothing(true);
		
		try {
			
			Texture texture = null;

			texture = TextureIO.newTexture(new File("./assets/textures/gui/close_inv.png"), true);
			textures.put("close", texture);
			
			texture = TextureIO.newTexture(new File("./assets/textures/gui/size_inv.png"), true);
			textures.put("size", texture);
			
		} catch (Exception e) {
			throw new AGEException(e);
		}
		
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
		glu.gluOrtho2D(-1, w, h, -1);
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
	public void text(float x, float y, String text) {
		gl.glScalef(1, -1, 1);
		titleText.begin3DRendering();
		titleText.draw3D(text, x, -y, 0f, 1f);
		titleText.end3DRendering();
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
	
}
//*************************************************************************************************
