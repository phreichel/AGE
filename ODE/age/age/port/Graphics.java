//*************************************************************************************************
package age.port;

import javax.vecmath.Color4f;
import javax.vecmath.Vector2f;

//*************************************************************************************************

//*************************************************************************************************
public interface Graphics {

	//=============================================================================================
	public void mode2D();
	public void pushTransformation();
	public void popTransformation();
	public void color(Color4f c);
	public void color(float r, float g, float b);
	public void color(float r, float g, float b, float a);
	public void translate(Vector2f pos);
	public void translate(float x, float y);
	public void rectangle(Vector2f dim, boolean hollow);
	public void rectangle(Vector2f pos, Vector2f dim, boolean hollow);
	public void rectangle(float x, float y, float w, float h, boolean hollow);
	//=============================================================================================
	
}
//*************************************************************************************************
