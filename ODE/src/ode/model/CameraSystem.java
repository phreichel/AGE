//*************************************************************************************************
package ode.model;
//*************************************************************************************************

import java.util.ArrayList;

import javax.vecmath.Matrix4f;

import ode.platform.Graphics;
import ode.util.ODEException;

//*************************************************************************************************
public class CameraSystem extends ArrayList<Entity> {

	//=============================================================================================
	private static final long serialVersionUID = 1L;
	//=============================================================================================

	//=============================================================================================
	public void update(Graphics graphics) {
		for (Entity entity : this) {
			CameraData cameraData = entity.getComponent(EntityEnum.CAMERA, CameraData.class);
			if (cameraData.active) {
				Matrix4f camTransform = new Matrix4f(entity.getComponent(EntityEnum.POSE, Matrix4f.class));
				camTransform.transpose();
				camTransform.m03 = - camTransform.m30;
				camTransform.m13 = - camTransform.m31;
				camTransform.m23 = - camTransform.m32;
				camTransform.m30 = 0;
				camTransform.m31 = 0;
				camTransform.m32 = 0;
				graphics.beginSceneMode(camTransform, cameraData.fov, cameraData.near, cameraData.far);
				return;
			}
		}
		throw new ODEException("No Active Camera");
	}
	//=============================================================================================

}
//*************************************************************************************************