//*************************************************************************************************
package age.scene;
//*************************************************************************************************

/**************************************************************************************************
 * Scene Node component that represents a camera view frustum.
 */
public class Camera {

	/**********************************************************************************************
	 * The vertical camera field of view angle in degrees 
	 */
	public float fovy;

	/**********************************************************************************************
	 * The camera near clip plane of the view frustum in units of measurement
	 */
	public float near;

	/**********************************************************************************************
	 * The camera far clip plane of the view frustum in units of measurement
	 */
	public float far;

	/**********************************************************************************************
	 * Constructor
	 * @param fovy The vertical camera field of view angle in degrees
	 * @param near The camera near clip plane of the view frustum in units of measurement
	 * @param far The camera far clip plane of the view frustum in units of measurement
	 */
	public Camera(float fovy, float near, float far) {
		set(fovy, near, far);
	}

	/**********************************************************************************************
	 * Setter method
	 * @param fovy The vertical camera field of view angle in degrees
	 * @param near The camera near clip plane of the view frustum in units of measurement
	 * @param far The camera far clip plane of the view frustum in units of measurement
	 */
	public void set(float fovy, float near, float far)  {
		this.fovy = fovy;
		this.near = near;
		this.far = far;
	}
	
}
//*************************************************************************************************
