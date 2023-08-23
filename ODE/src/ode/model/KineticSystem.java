//*************************************************************************************************
package ode.model;
//*************************************************************************************************

import java.util.Map;
import java.util.Map.Entry;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

//*************************************************************************************************
public class KineticSystem {

	//=============================================================================================
	private Map<Entity, PoseData> poseDataMap;
	private Map<Entity, KineticData> kineticDataMap;
	//=============================================================================================

	//=============================================================================================
	public KineticSystem(
			Map<Entity, PoseData> poseDataMap,
			Map<Entity, KineticData> kineticDataMap) {
		this.poseDataMap = poseDataMap;
		this.kineticDataMap = kineticDataMap;
	}
	//=============================================================================================

	//=============================================================================================
	public void update(float dT) {
		Vector3f locationRate = new Vector3f();
		Quat4f   orientationRate = new Quat4f();
		for (Entry<Entity, KineticData> entry : kineticDataMap.entrySet()) {
			Entity entity = entry.getKey();
			KineticData kineticData = entry.getValue();
			PoseData poseData = poseDataMap.get(entity);
			locationRate.scale(dT, kineticData.velocity);
			orientationRate.scale(dT, kineticData.rotation);
			poseData.location.add(locationRate);
			poseData.orientation.mul(orientationRate);
		}
	}
	//=============================================================================================
	
}
//*************************************************************************************************