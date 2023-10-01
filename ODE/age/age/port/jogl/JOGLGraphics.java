//*************************************************************************************************
package age.port.jogl;
//*************************************************************************************************

import com.jogamp.opengl.GL2;
import static com.jogamp.opengl.GL2.*;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
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
import age.mesh.Submesh;
import age.gui.Multiline;
import age.gui.Scrollable;
import age.mesh.Material;
import age.mesh.MaterialFlag;
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

			texture = TextureIO.newTexture(new File("./assets/stone/diffuso.tif"), true);
			textures.put("diffuso.tif", texture);

			texture = TextureIO.newTexture(new File("./assets/stone/normal.png"), true);
			textures.put("normal.png", texture);
			
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
		gl.glEnable(GL_CULL_FACE);
		gl.glEnable(GL_DEPTH_TEST);
		gl.glClear(GL_DEPTH_BUFFER_BIT);
		
	    // Apply lighting
		gl.glEnable(GL_LIGHTING);
		gl.glEnable(GL_LIGHT0);
	    float[] lightPos = {0.0f, 0.0f, 1.0f, 0.0f};
	    gl.glLightfv(GL_LIGHT0, GL_POSITION, lightPos, 0);
	    
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
	private Set<UUID> meshes = new HashSet<>();
	private Map<UUID, Integer> positionVBOs = new HashMap<>();
	private Map<UUID, Integer> textureVBOs = new HashMap<>();
	private Map<UUID, Integer> normalVBOs = new HashMap<>();
	private Map<UUID, Integer> indexVBOs = new HashMap<>();
	private Map<UUID, Integer> indexVBOTypes = new HashMap<>();
	private Map<UUID, Integer> indexVBOLengths = new HashMap<>();
	//=============================================================================================
	
	//=============================================================================================
	public void drawMesh(Mesh mesh) {
		
		gl.glPushAttrib(GL_ENABLE_BIT);
		gl.glPushAttrib(GL_LIGHTING_BIT);
		gl.glPushAttrib(GL_TEXTURE_BIT);
		gl.glPushAttrib(GL_CURRENT_BIT);
		gl.glPushAttrib(GL_DEPTH_BUFFER_BIT);
		
		initMesh(mesh);
		
		gl.glShadeModel(GL_FLAT);
		
		gl.glEnableClientState(GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL_NORMAL_ARRAY);
		gl.glEnableClientState(GL_TEXTURE_COORD_ARRAY);		
		
		for (int i=0; i<mesh.submeshes.size(); i++) {
			
			Submesh submesh = mesh.submeshes.get(i);

			Material material = submesh.material;
			this.applyMaterial(material);

			// Set up vertex arrays
			gl.glBindBuffer(GL_ARRAY_BUFFER, positionVBOs.get(mesh.uuid));
			gl.glVertexPointer(3, GL_FLOAT, 0, 0);

			gl.glBindBuffer(GL_ARRAY_BUFFER, textureVBOs.get(mesh.uuid));
			gl.glTexCoordPointer(2, GL_FLOAT, 0, 0);

			gl.glBindBuffer(GL_ARRAY_BUFFER, normalVBOs.get(mesh.uuid));
			gl.glNormalPointer(GL_FLOAT, 0, 0);
			
			// Render using indexed drawing
			gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexVBOs.get(submesh.uuid));
			gl.glDrawElements(indexVBOTypes.get(submesh.uuid), indexVBOLengths.get(submesh.uuid), GL_UNSIGNED_INT, 0);
			
		}
		
		// Unbind buffers
        gl.glBindBuffer(GL_ARRAY_BUFFER, 0);
        gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        
		gl.glDisableClientState(GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL_NORMAL_ARRAY);
		gl.glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		
		gl.glPopAttrib();
		gl.glPopAttrib();
		gl.glPopAttrib();
		gl.glPopAttrib();
		gl.glPopAttrib();
        
	}
	//=============================================================================================

	//=============================================================================================
	private void initMesh(Mesh mesh) {
		
		if (meshes.contains(mesh.uuid)) return;  
		meshes.add(mesh.uuid);
		
		int vboIds[] = new int[3 + mesh.submeshes.size()];
		gl.glGenBuffers(3 + mesh.submeshes.size(), vboIds, 0);
		
		// Vertex positions
		positionVBOs.put(mesh.uuid, vboIds[0]);
		mesh.positions.rewind();
		gl.glBindBuffer(GL_ARRAY_BUFFER, vboIds[0]);
		gl.glBufferData(GL_ARRAY_BUFFER, mesh.positions.limit() * Float.BYTES, mesh.positions, GL_STATIC_DRAW);

		// Texture coordinates
		textureVBOs.put(mesh.uuid, vboIds[1]);
		mesh.textures.rewind();
		gl.glBindBuffer(GL_ARRAY_BUFFER, vboIds[1]);
		gl.glBufferData(GL_ARRAY_BUFFER, mesh.textures.limit() * Float.BYTES, mesh.textures, GL_STATIC_DRAW);

		// Normals
		normalVBOs.put(mesh.uuid, vboIds[2]);
		mesh.normals.rewind();
		gl.glBindBuffer(GL_ARRAY_BUFFER, vboIds[2]);
		gl.glBufferData(GL_ARRAY_BUFFER, mesh.normals.limit() * Float.BYTES, mesh.normals, GL_STATIC_DRAW);
		
		for (int i=0; i<mesh.submeshes.size(); i++) {
			Submesh submesh = mesh.submeshes.get(i);
			indexVBOs.put(submesh.uuid, vboIds[3+i]);
			indexVBOLengths.put(submesh.uuid, submesh.indices.limit());
			int type = switch (submesh.type) {
				case TRIANGLES -> GL_TRIANGLES;
				case LINES -> GL_LINES;
				default -> throw new X("Unsupported Type: %s", submesh.type);
			};
			indexVBOTypes.put(submesh.uuid, type);
			submesh.indices.rewind();
			gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboIds[3+i]);
			gl.glBufferData(GL_ELEMENT_ARRAY_BUFFER, submesh.indices.limit() * Integer.BYTES, submesh.indices, GL_STATIC_DRAW);
		}

		// Unbind buffers
        gl.glBindBuffer(GL_ARRAY_BUFFER, 0);
        gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		
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

	//=============================================================================================
	public void applyMaterial(Material m) {

		if (m.flags.contains(MaterialFlag.LIGHTING))
			gl.glEnable(GL_LIGHTING);
		else
			gl.glDisable(GL_LIGHTING);
		
		float[] buffer = new float[] {0f, 0f, 0f, 1f};
		buffer[0] = m.ambience.x;
		buffer[1] = m.ambience.y;
		buffer[2] = m.ambience.z;
		gl.glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, buffer, 0);
		buffer[0] = m.diffuse.x;
		buffer[1] = m.diffuse.y;
		buffer[2] = m.diffuse.z;
		gl.glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, buffer, 0);
		buffer[0] = m.specular.x;
		buffer[1] = m.specular.y;
		buffer[2] = m.specular.z;
		gl.glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, buffer, 0);
		buffer[0] = m.emission.x;
		buffer[1] = m.emission.y;
		buffer[2] = m.emission.z;
		gl.glMaterialfv(GL_FRONT_AND_BACK, GL_EMISSION, buffer, 0);
		gl.glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, m.shininess);
		
		gl.glColor3f(m.diffuse.x, m.diffuse.y, m.diffuse.z);
		
		if (m.diffuse_map != null) {
			Texture tex = textures.get(m.diffuse_map);
			tex.enable(gl);
			tex.bind(gl);
		}

		/*
		if (m.bump_map != null) {

			Texture tex = textures.get(m.bump_map);
			tex.enable(gl);
			tex.bind(gl);
			
	        // Enable automatic generation of texture coordinates
	        gl.glTexGeni(GL_S, GL_TEXTURE_GEN_MODE, GL_SPHERE_MAP);
	        gl.glTexGeni(GL_T, GL_TEXTURE_GEN_MODE, GL_SPHERE_MAP);
	        gl.glEnable(GL_TEXTURE_GEN_S);
	        gl.glEnable(GL_TEXTURE_GEN_T);		    

			// Combine the two textures during lighting calculations
		    gl.glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_COMBINE);
		    gl.glTexEnvi(GL_TEXTURE_ENV, GL_COMBINE_RGB, GL_DOT3_RGB);
		    gl.glTexEnvi(GL_TEXTURE_ENV, GL_SOURCE0_RGB, GL_TEXTURE);
		    gl.glTexEnvi(GL_TEXTURE_ENV, GL_SOURCE1_RGB, GL_PREVIOUS);
		    gl.glTexEnvi(GL_TEXTURE_ENV, GL_OPERAND0_RGB, GL_SRC_COLOR);
		    gl.glTexEnvi(GL_TEXTURE_ENV, GL_OPERAND1_RGB, GL_SRC_COLOR);
		}
		*/
		
	}
	//=============================================================================================

}
//*************************************************************************************************
