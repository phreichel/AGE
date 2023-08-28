//*************************************************************************************************
package ode.model;
//*************************************************************************************************

import java.util.ArrayList;

import javax.vecmath.Quat4f;

//*************************************************************************************************
public class KineticAngularSystem extends ArrayList<Entity> {

	//=============================================================================================
	private static final long serialVersionUID = 1L;
	//=============================================================================================
	
	//=============================================================================================
	public void update(float dT) {
		Quat4f orientationChangeRate = new Quat4f();
		for (Entity entity : this) {
			Quat4f rotationVelocityData = entity.getRotationVelocityData();
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