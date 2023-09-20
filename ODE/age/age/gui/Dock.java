//*************************************************************************************************
package age.gui;
//*************************************************************************************************

/**************************************************************************************************
 * The class represents a numerical value for each side of a rectangle  
 */
public class Dock {

	/**********************************************************************************************
	 * numerical value for the left side of the rectangle 
	 */
	private float left = 0;

	/**********************************************************************************************
	 * numerical value for the right side of the rectangle 
	 */
	private float right = 0;

	/**********************************************************************************************
	 * numerical value for the top side of the rectangle 
	 */
	private float top = 0;

	/**********************************************************************************************
	 * numerical value for the bottom side of the rectangle 
	 */
	private float bottom = 0;

	/**********************************************************************************************
	 * getter method for the top side property 
	 */
	public float top() {
		return top;
	}

	/**********************************************************************************************
	 * getter method for the bottom side property 
	 */
	public float bottom() {
		return bottom;
	}

	/**********************************************************************************************
	 * getter method for the left side property 
	 */
	public float left() {
		return left;
	}

	/**********************************************************************************************
	 * getter method for the right side property 
	 */
	public float right() {
		return right;
	}

	/**********************************************************************************************
	 * copy method to copy the properties from the dock parameter
	 * @param dock the object the properties are copied from  
	 */
	public void set(Dock dock) {
		this.left = dock.left;
		this.right = dock.right;
		this.top = dock.top;
		this.bottom = dock.bottom;
	}
	
	/**********************************************************************************************
	 * setter method to set the property values for the four sides of a rectangle  
	 * @param left value for the left side property
	 * @param right value for the right side property
	 * @param top value for the top side property
	 * @param bottom value for the bottom side property
	 */
	public void set(float left, float right, float top, float bottom) {
		this.left   = left;
		this.right  = right;
		this.top    = top;
		this.bottom = bottom;
	}
	
}
//*************************************************************************************************
