//*************************************************************************************************
package ode.model;
//*************************************************************************************************

import java.util.ArrayList;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

//*************************************************************************************************
public class PoseSystem extends ArrayList<Entity> {

	//=============================================================================================
	private static final long serialVersionUID = 1L;
	//=============================================================================================
	
	//=============================================================================================
	public void update() {
		for (Entity entity : this) {
			Matrix4f poseData = entity.getPoseData();
			Vector3f position = entity.getPositionData();
			Quat4f orientation = entity.getOrientationData();
			poseData.setIdentity();
			if (position != null) poseData.setTranslation(position);
			if (orientation != null) poseData.setRotation(orientation);
		}
	}
	//=============================================================================================
	
}
//*************************************************************************************************