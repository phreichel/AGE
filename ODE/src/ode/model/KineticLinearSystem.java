//*************************************************************************************************
package ode.model;
//*************************************************************************************************

import java.util.Map;
import java.util.Map.Entry;

import javax.vecmath.Vector3f;

//*************************************************************************************************
public class KineticLinearSystem {

	//=============================================================================================
	private Map<Entity, Vector3f> linearVelocityMap;
	//=============================================================================================

	//=============================================================================================
	public KineticLinearSystem(Map<Entity, Vector3f> linearVelocityMap) {
		this.linearVelocityMap = linearVelocityMap;
	}
	//=============================================================================================

	//=============================================================================================
	public void update(float dT) {
		Vector3f locationChangeRate = new Vector3f();
		for (Entry<Entity, Vector3f> entry : linearVelocityMap.entrySet()) {
			Entity entity = entry.getKey();
			Vector3f linearVelocityData = entry.getValue();

			Vector3f positionData = entity.getPositionData();
			if (positionData != null) {
				locationChangeRate.scale(dT * .5f, linearVelocityData);
				positionData.add(locationChangeRate);
			}
		}
	}
	//=============================================================================================
	
}
//*************************************************************************************************