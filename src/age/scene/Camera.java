//*************************************************************************************************
package age.scene;
//*************************************************************************************************

//*************************************************************************************************
public class Camera {

	//=============================================================================================
	public float fovy;
	public float near;
	public float far;
	//=============================================================================================

	//=============================================================================================
	public Camera(float fovy, float near, float far) {
		set(fovy, near, far);
	}
	//=============================================================================================

	//=============================================================================================
	public void set(float fovy, float near, float far)  {
		this.fovy = fovy;
		this.near = near;
		this.far = far;
	}
	//=============================================================================================
	
}
//*************************************************************************************************
