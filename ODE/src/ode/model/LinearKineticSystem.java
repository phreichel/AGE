//*************************************************************************************************
package ode.model;
//*************************************************************************************************

import java.util.Map;
import java.util.Map.Entry;

import javax.vecmath.Vector3f;

//*************************************************************************************************
public class LinearKineticSystem {

	//=============================================================================================
	private Map<Entity, Vector3f> linearVelocityDataMap;
	//=============================================================================================

	//=============================================================================================
	public LinearKineticSystem(Map<Entity, Vector3f> linearVelocityDataMap) {
		this.linearVelocityDataMap = linearVelocityDataMap;
	}
	//=============================================================================================

	//=============================================================================================
	public void update(float dT) {
		Vector3f locationChangeRate = new Vector3f();
		for (Entry<Entity, Vector3f> entry : linearVelocityDataMap.entrySet()) {
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