//*************************************************************************************************
package ode.model;
//*************************************************************************************************

import java.util.Map;
import java.util.Map.Entry;

import javax.vecmath.Quat4f;

//*************************************************************************************************
public class RotationKineticSystem {

	//=============================================================================================
	private Map<Entity, Quat4f> rotationVelocityDataMap;
	//=============================================================================================

	//=============================================================================================
	public RotationKineticSystem(Map<Entity, Quat4f> rotationVelocityDataMap) {
		this.rotationVelocityDataMap = rotationVelocityDataMap;
	}
	//=============================================================================================

	//=============================================================================================
	public void update(float dT) {
		Quat4f orientationChangeRate = new Quat4f();
		for (Entry<Entity, Quat4f> entry : rotationVelocityDataMap.entrySet()) {
			Entity entity = entry.getKey();
			Quat4f rotationVelocityData = entry.getValue();

			Quat4f orientationData = entity.getOrientationData();
			if (orientationData != null) {
				rotationVelocityData.normalize();
				orientationChangeRate.scale(dT, rotationVelocityData);
				orientationChangeRate.w = 1f;
				orientationData.mul(orientationChangeRate, orientationData);
				orientationData.normalize();
			}
		}
	}
	//=============================================================================================
	
}
//*************************************************************************************************