//*************************************************************************************************
package age.port;
//*************************************************************************************************

import javax.vecmath.Color4f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector2f;
import age.gui.Multiline;
import age.gui.Scrollable;
import age.mesh.Element;
import age.mesh.Mesh;
import age.rig.Rig;

//*************************************************************************************************
public interface Graphics {

	//=============================================================================================
	public void mode3D(float fovy, float near, float far);
	public void mode2D();
	//=============================================================================================

	//=============================================================================================
	public void pushTransformation();
	public void popTransformation();
	//=============================================================================================

	public void applyTransformation(Matrix4f matrix);
	public void translate(Vector2f pos);
	public void translate(float x, float y);
	//=============================================================================================

	//=============================================================================================
	public void color(Color4f c);
	public void color(float r, float g, float b);
	public void color(float r, float g, float b, float a);
	//=============================================================================================

	//=============================================================================================
	public void rectangle(Vector2f dim, boolean hollow);
	public void rectangle(Vector2f pos, Vector2f dim, boolean hollow);
	public void rectangle(float x, float y, float w, float h, boolean hollow);
	//=============================================================================================

	//=============================================================================================
	public void text(float x, float y, CharSequence text, String font);
	public void calcMultitext(String text, float width, float height, String font, Scrollable scstate, Multiline mlstate);
	//=============================================================================================

	//=============================================================================================
	public void texture(float x, float y, float w, float h, String texture);
	//=============================================================================================
	
	//=============================================================================================
	public void drawBox(float sx, float sy, float sz);
	public void drawMesh(Mesh mesh);
	public void drawElement(Element element);
	public void drawRig(Rig rig);
	//=============================================================================================
	
}
//*************************************************************************************************
