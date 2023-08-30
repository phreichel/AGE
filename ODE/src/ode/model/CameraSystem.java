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
				graphics.beginSceneMode(camTransform, cameraData.fov, cameraData.near, cameraData.far);
				return;
			}
		}
		throw new ODEException("No Active Camera");
	}
	//=============================================================================================

}
//*************************************************************************************************