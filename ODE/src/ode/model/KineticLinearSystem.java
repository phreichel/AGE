//*************************************************************************************************
package ode.model;
//*************************************************************************************************

import java.util.ArrayList;

import javax.vecmath.Vector3f;

//*************************************************************************************************
public class KineticLinearSystem extends ArrayList<Entity> {

	//=============================================================================================
	private static final long serialVersionUID = 1L;
	//=============================================================================================
	
	//=============================================================================================
	public void update(float dT) {
		Vector3f locationChangeRate = new Vector3f();
		for (Entity entity : this) {
			Vector3f linearVelocityData = entity.getLinearVelocityData();
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