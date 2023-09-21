//*************************************************************************************************
package age.port;
//*************************************************************************************************

import javax.vecmath.Color4f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector2f;

/**************************************************************************************************
 * Interface to encapsulate calls to the native Graphics system for rendering 
 */
public interface Graphics {

	
	/**********************************************************************************************
	 * Switch into 3D mode. Setup of Perspective matrix and Enabling of 3D relevant settings
	 * 
	 * @param fovy the vertically visible field of view angle 
	 * @param near the distance of the near frustum plane
	 * @param far the distance of the far frustum plane
	 */
	public void mode3D(float fovy, float near, float far);

	/**********************************************************************************************
	 * Switch into 2D mode. Setup of Perspective matrix and Enabling of 2D relevant settings
	 */
	public void mode2D();

	/**********************************************************************************************
	 * Push the current transformation matrix on an internal stack
	 */
	public void pushTransformation();

	/**********************************************************************************************
	 * Pop and restore the current transformation matrix from an internal stack
	 */
	public void popTransformation();
	
	/**********************************************************************************************
	 * Apply the matrix transformation to the current transformation by multiplication
	 * 
	 * @param matrix the transformation matrix to apply to the current transformation
	 */
	public void applyTransformation(Matrix4f matrix);

	/**********************************************************************************************
	 * Apply a translation to the current transformation
	 * 
	 * @param pos the positional delta that is applied to the current transformation
	 */
	public void translate(Vector2f pos);

	/**********************************************************************************************
	 * Apply a translation to the current transformation
	 * 
	 * @param x the x positional delta that is applied to the current transformation
	 * @param y the y positional delta that is applied to the current transformation
	 */
	public void translate(float x, float y);

	/**********************************************************************************************
	 * Applies the given color as current color for further drawing operations
	 * 
	 * @param c the color to apply
	 */
	public void color(Color4f c);
	
	/**********************************************************************************************
	 * Applies the given color as current color for further drawing operations
	 * 
	 * @param r the red color fraction to apply. interval [0;1]
	 * @param g the green color fraction to apply. interval [0;1]
	 * @param b the blue color fraction to apply. interval [0;1]
	 */	
	public void color(float r, float g, float b);

	/**********************************************************************************************
	 * Applies the given color as current color for further drawing operations
	 * 
	 * @param r the red color fraction to apply. interval [0;1]
	 * @param g the green color fraction to apply. interval [0;1]
	 * @param b the blue color fraction to apply. interval [0;1]
	 * @param a the alpha channel fraction to apply. interval [0;1]
	 */	
	public void color(float r, float g, float b, float a);

	/**********************************************************************************************
	 * draws a rectangle in the z plane with z = 0, and x,y position 0,0
	 * @param dim the x,y dimension of the rectangle.
	 * @param hollow draws just the outline when true, else a solid rectangle.
	 */
	public void rectangle(Vector2f dim, boolean hollow);

	/**********************************************************************************************
	 * draws a rectangle in the z plane with z = 0
	 * @param pos the x,y position of the rectangle.
	 * @param dim the x,y dimension of the rectangle.
	 * @param hollow draws just the outline when true, else a solid rectangle.
	 */
	public void rectangle(Vector2f pos, Vector2f dim, boolean hollow);

	/**********************************************************************************************
	 * draws a rectangle in the z plane with z = 0
	 * @param x the x position of the rectangle.
	 * @param y the y position of the rectangle.
	 * @param w the width dimension of the rectangle.
	 * @param h the height dimension of the rectangle.
	 * @param hollow draws just the outline when true, else a solid rectangle.
	 */
	public void rectangle(float x, float y, float w, float h, boolean hollow);

	/**********************************************************************************************
	 * Draws a text string with a specific font
	 * @param x the x position of the top right text corner
	 * @param y the y position of the top right text corner
	 * @param text the text to draw
	 * @param Port internal font name of the mapped font
	 */
	public void text(float x, float y, CharSequence text, String font);
	
	/**********************************************************************************************
	 * Draws a multiline text into a box at position 0,0
	 * @param text the multiline text
	 * @param dimension the box dimension
	 * @param font the font name
	 * @param buffer an internally used buffer to hold line substring indices
	 */
	public void calcMultitext(String text, Vector2f dimension, String font, int[] buffer);
	
	/**********************************************************************************************
	 * Draws a multiline text into a box
	 * @param text the multiline text
	 * @param x the x position of the surrounding box
	 * @param y the y position of the surrounding box
	 * @param w the width dimension of the surrounding box
	 * @param h the height dimension of the surrounding box
	 * @param font the font name
	 * @param buffer an internally used buffer to hold line substring indices
	 */
	public void calcMultitext(String text, float width, float height, String font, int[] buffer);

	/**********************************************************************************************
	 * Draws a rectangular texture image
	 * @param x the bottom left x position
	 * @param y the bottom left y position
	 * @param w the width dimension
	 * @param h the height dimension
	 * @param texture the port internal name of the mapped texture.
	 */
	public void texture(float x, float y, float w, float h, String texture);
	
	/**********************************************************************************************
	 * Draws a 3D box at origin 0,0,0
	 * @param sx the x dimension from the box center to the left and right wall
	 * @param sy the y dimension from the box center to the top and bottom wall 
	 * @param sz the z dimension from the box center to the front and back wall
	 */
	public void drawBox(float sx, float sy, float sz);
	
}
//*************************************************************************************************
