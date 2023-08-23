//*************************************************************************************************
package ode.model;
//*************************************************************************************************

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

//*************************************************************************************************
public class PoseData {

	//=============================================================================================
	public final Vector3f location = new Vector3f();
	public final Vector3f scale = new Vector3f();
	public final Quat4f orientation = new Quat4f();
	public final Matrix4f pose = new Matrix4f();
	public final Matrix4f world = new Matrix4f();
	//=============================================================================================
	
}
//*************************************************************************************************