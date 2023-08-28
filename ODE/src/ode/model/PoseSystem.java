//*************************************************************************************************
package ode.model;
//*************************************************************************************************

import java.util.Map;
import java.util.Map.Entry;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

//*************************************************************************************************
public class PoseSystem {

	//=============================================================================================
	private Map<Entity, Matrix4f> poseMap;
	//=============================================================================================

	//=============================================================================================
	public PoseSystem(Map<Entity, Matrix4f> poseMap) {
		this.poseMap = poseMap;
	}
	//=============================================================================================

	//=============================================================================================
	public void update() {
		for (Entry<Entity, Matrix4f> entry : poseMap.entrySet()) {
			Entity entity = entry.getKey();
			Matrix4f poseData = entry.getValue();
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