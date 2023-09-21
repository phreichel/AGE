//*************************************************************************************************
package age.port;
//*************************************************************************************************

import javax.vecmath.Color4f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector2f;

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
	public void calcMultitext(String text, Vector2f dimension, String font, int[] buffer);
	public void calcMultitext(String text, float width, float height, String font, int[] buffer);
	//=============================================================================================

	//=============================================================================================
	public void texture(float x, float y, float w, float h, String texture);
	//=============================================================================================
	
	//=============================================================================================
	public void drawBox(float sx, float sy, float sz);
	//=============================================================================================
	
}
//*************************************************************************************************
