//*************************************************************************************************
package age.port.jogl;
//*************************************************************************************************

import com.jogamp.opengl.GL2;
import static com.jogamp.opengl.GL2.*;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.vecmath.Color4f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.TextureIO;
import age.mesh.Mesh;
import age.gui.Multiline;
import age.gui.Scrollable;
import age.mesh.Element;
import age.mesh.ElementType;
import age.port.Graphics;
import age.rig.Bone;
import age.rig.Rig;
import age.util.X;
import age.util.MathUtil;

//*************************************************************************************************
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
			throw new X(e);
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
		buffer = MathUtil.toGLMatrix(matrix, buffer);
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
	public void calcMultitext(String text, float width, float height, String font, Scrollable scstate, Multiline mlstate) {
		mlstate.indices.clear();
		TextRenderer textRenderer = fonts.get(font);
		Rectangle2D rect = textRenderer.getBounds("_");
		double space = rect.getWidth();
		int mark = 0;
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

			double delta = width - lineWidth;
			if (text.charAt(i) == '\n') {
				mlstate.indices.add(mark);
				mlstate.indices.add(i);
				mark = i+1;
			} else if (delta < space) {
				mlstate.indices.add(mark);
				mlstate.indices.add(i);
				mark = i;
			}
		}
		if (mark != text.length()) {
			mlstate.indices.add(mark);
			mlstate.indices.add(text.length());
		}
		scstate.size = mlstate.indices.size() / 2;
		scstate.page = (int) Math.floor(height / lineHeight);
		mlstate.height = (int) Math.ceil(lineHeight);
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
	
	//=============================================================================================
	public void drawMesh(Mesh mesh) {
		for (var i = 0; i < mesh.size(); i++) {
			var element = mesh.get(i);
			drawElement(element);
		}
	}
	//=============================================================================================

	//=============================================================================================
	public void drawElement(Element element) {
		var mask = 0;
		mask |= element.hasNormals() ? 1 : 0;
		mask |= element.hasColors() ? 2 : 0;
		mask |= element.hasTextures() ? 4 : 0;
		
		switch (mask) {
		case 0 -> drawElementV(element);
		case 1 -> drawElementNV(element);
		case 2 -> drawElementCV(element);
		case 3 -> drawElementCNV(element);
		case 4 -> drawElementTV(element);
		case 5 -> drawElementTNV(element);
		case 6 -> drawElementTCV(element);
		case 7 -> drawElementTCNV(element);
		default -> throw new X("unsupported vertex type");
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void drawElementV(Element element) {
		int[] indices = element.indices();
		float[] vertices = element.vertices();
		gl.glPushAttrib(GL2.GL_ENABLE_BIT);
		gl.glDisable(GL2.GL_LIGHTING);
		var mode = glMode(element.type());
		gl.glBegin(mode);
		for (var i=0; i<indices.length; i++) {
			var idx = indices[i];
			var idx3 = idx * 3;
			gl.glVertex3f(vertices[idx3+0], vertices[idx3+1], vertices[idx3+2]);
		}
		gl.glEnd();
		gl.glPopAttrib();
	}
	//=============================================================================================
	
	//=============================================================================================
	private void drawElementNV(Element element) {
		int[] indices = element.indices();
		float[] normals = element.normals();
		float[] vertices = element.vertices();
		var mode = glMode(element.type());
		gl.glBegin(mode);
		for (var i=0; i<indices.length; i++) {
			var idx = indices[i];
			var idx3 = idx * 3;
			gl.glNormal3f(normals[idx3+0], normals[idx3+1], normals[idx3+2]);
			gl.glVertex3f(vertices[idx3+0], vertices[idx3+1], vertices[idx3+2]);
		}
		gl.glEnd();
	}
	//=============================================================================================
	
	//=============================================================================================
	private void drawElementCV(Element element) {
		int[] indices = element.indices();
		float[] colors = element.colors();
		float[] vertices = element.vertices();
		gl.glPushAttrib(GL2.GL_ENABLE_BIT);
		gl.glDisable(GL2.GL_LIGHTING);
		var mode = glMode(element.type());
		gl.glBegin(mode);
		for (var i=0; i<indices.length; i++) {
			var idx = indices[i];
			var idx3 = idx * 3;
			gl.glColor3f(colors[idx3+0], colors[idx3+1], colors[idx3+2]);
			gl.glVertex3f(vertices[idx3+0], vertices[idx3+1], vertices[idx3+2]);
		}
		gl.glEnd();
		gl.glPopAttrib();
	}
	//=============================================================================================
	
	//=============================================================================================
	private void drawElementCNV(Element element) {
		int[] indices = element.indices();
		float[] colors = element.colors();
		float[] normals = element.normals();
		float[] vertices = element.vertices();
		var mode = glMode(element.type());
		gl.glBegin(mode);
		for (var i=0; i<indices.length; i++) {
			var idx = indices[i];
			var idx3 = idx * 3;
			gl.glColor3f(colors[idx3+0], colors[idx3+1], colors[idx3+2]);
			gl.glNormal3f(normals[idx3+0], normals[idx3+1], normals[idx3+2]);
			gl.glVertex3f(vertices[idx3+0], vertices[idx3+1], vertices[idx3+2]);
		}
		gl.glEnd();
	}
	//=============================================================================================
	
	//=============================================================================================
	private void drawElementTV(Element element) {
		int[] indices = element.indices();
		float[] textures = element.textures();
		float[] vertices = element.vertices();
		gl.glPushAttrib(GL2.GL_ENABLE_BIT);
		gl.glDisable(GL2.GL_LIGHTING);
		var mode = glMode(element.type());
		gl.glBegin(mode);
		for (var i=0; i<indices.length; i++) {
			var idx = indices[i];
			var idx2 = idx * 2;
			var idx3 = idx * 3;
			gl.glTexCoord2f(textures[idx2+0], textures[idx2+1]);
			gl.glVertex3f(vertices[idx3+0], vertices[idx3+1], vertices[idx3+2]);
		}
		gl.glEnd();
		gl.glPopAttrib();
	}
	//=============================================================================================
	
	//=============================================================================================
	private void drawElementTNV(Element element) {
		int[] indices = element.indices();
		float[] normals = element.normals();
		float[] textures = element.textures();
		float[] vertices = element.vertices();
		var mode = glMode(element.type());
		gl.glBegin(mode);
		for (var i=0; i<indices.length; i++) {
			var idx = indices[i];
			var idx2 = idx * 2;
			var idx3 = idx * 3;
			gl.glNormal3f(normals[idx3+0], normals[idx3+1], normals[idx3+2]);
			gl.glTexCoord2f(textures[idx2+0], textures[idx2+1]);
			gl.glVertex3f(vertices[idx3+0], vertices[idx3+1], vertices[idx3+2]);
		}
		gl.glEnd();
	}
	//=============================================================================================
	
	//=============================================================================================
	private void drawElementTCV(Element element) {
		int[] indices = element.indices();
		float[] colors = element.colors();
		float[] textures = element.textures();
		float[] vertices = element.vertices();
		gl.glPushAttrib(GL2.GL_ENABLE_BIT);
		gl.glDisable(GL2.GL_LIGHTING);
		var mode = glMode(element.type());
		gl.glBegin(mode);
		for (var i=0; i<indices.length; i++) {
			var idx = indices[i];
			var idx2 = idx * 2;
			var idx3 = idx * 3;
			gl.glColor3f(colors[idx3+0], colors[idx3+1], colors[idx3+2]);
			gl.glTexCoord2f(textures[idx2+0], textures[idx2+1]);
			gl.glVertex3f(vertices[idx3+0], vertices[idx3+1], vertices[idx3+2]);
		}
		gl.glEnd();
		gl.glPopAttrib();
	}
	//=============================================================================================
	
	//=============================================================================================
	private void drawElementTCNV(Element element) {
		int[] indices = element.indices();
		float[] colors = element.colors();
		float[] normals = element.normals();
		float[] textures = element.textures();
		float[] vertices = element.vertices();
		var mode = glMode(element.type());
		gl.glBegin(mode);
		for (var i=0; i<indices.length; i++) {
			var idx = indices[i];
			var idx2 = idx * 2;
			var idx3 = idx * 3;
			gl.glColor3f(colors[idx3+0], colors[idx3+1], colors[idx3+2]);
			gl.glNormal3f(normals[idx3+0], normals[idx3+1], normals[idx3+2]);
			gl.glTexCoord2f(textures[idx2+0], textures[idx2+1]);
			gl.glVertex3f(vertices[idx3+0], vertices[idx3+1], vertices[idx3+2]);
		}
		gl.glEnd();
	}
	//=============================================================================================
	
	//=============================================================================================
	private int glMode(ElementType type) {
		return switch(type) {
			case POINTS -> GL2.GL_POINTS;
			case LINES -> GL2.GL_LINES;
			case LINE_STRIP -> GL2.GL_LINE_STRIP;
			case LINE_LOOP -> GL2.GL_LINE_LOOP;
			case TRIANGLES -> GL2.GL_TRIANGLES;
			case TRIANGLE_STRIP -> GL2.GL_TRIANGLE_STRIP;
			case TRIANGLE_FAN -> GL2.GL_TRIANGLE_FAN;
			case QUADS -> GL2.GL_QUADS;
			case QUAD_STRIP -> GL2.GL_QUAD_STRIP;
		};
	}
	//=============================================================================================

	//=============================================================================================
	public void drawRig(Rig rig) {
		gl.glPushAttrib(GL2.GL_ENABLE_BIT);
		gl.glDisable(GL2.GL_LIGHTING);
		drawBones(rig.skeleton().bones(), rig.time());
		gl.glPopAttrib();
	}
	//=============================================================================================

	//=============================================================================================
	private void drawBones(List<Bone> bones, float t) {
		for (var bone : bones) {
			drawBone(bone, t);
		}
	}
	//=============================================================================================

	//=============================================================================================
	private Vector3f r1 = new Vector3f();
	private Vector3f r2 = new Vector3f();
	private Quat4f   o1 = new Quat4f();
	private Quat4f   o2 = new Quat4f();
	private Matrix4f mx = new Matrix4f();
	//=============================================================================================
	
	//=============================================================================================
	private void drawBone(Bone bone, float t) {
		bone.interpolate(r1, o1, t);
		mx.setIdentity();
		mx.setRotation(o1);
		mx.setTranslation(r1);
		buffer = MathUtil.toGLMatrix(mx, buffer);
		gl.glPushMatrix();
		gl.glMultMatrixf(buffer, 0);
		for (var child : bone.children()) {
			child.interpolate(r2, o2, t);
			gl.glBegin(GL_LINES);
			gl.glVertex3f(0, 0, 0);
			gl.glVertex3f(r2.x, r2.y, r2.z);
			gl.glEnd();
		}
		drawBones(bone.children(), t);
		gl.glPopMatrix();
	}
	//=============================================================================================
	
}
//*************************************************************************************************
