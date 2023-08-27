//*************************************************************************************************
package ode.model;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

//*************************************************************************************************

//*************************************************************************************************
public class EntityBuilder {

	//=============================================================================================
	private Model model;
	private Entity entity;
	//=============================================================================================

	//=============================================================================================
	public EntityBuilder(Model model) {
		this.model = model;
		this.entity = new Entity(model);
		model.entities.add(entity);
	}
	//=============================================================================================

	//=============================================================================================
	public EntityBuilder withCameraData(float fovy, float near, float far, boolean active) {
		CameraData cameraData = new CameraData();
		cameraData.fov = fovy;
		cameraData.near = near;
		cameraData.far = far;
		cameraData.active = active;
		model.cameraDataMap.put(entity, cameraData);
		return this;		
	}
	//=============================================================================================

	//=============================================================================================
	public EntityBuilder withKineticData(Vector3f velocity, Quat4f rotation) {
		KineticData kineticData = new KineticData();
		kineticData.velocity.set(velocity);
		kineticData.rotation.set(rotation);
		model.kineticDataMap.put(entity, kineticData);
		return this;		
	}
	//=============================================================================================

	//=============================================================================================
	public EntityBuilder withPoseData(Vector3f location, Quat4f orientation) {
		PoseData poseData = new PoseData();
		poseData.location.set(location);
		poseData.orientation.set(orientation);
		poseData.pose.setIdentity();
		poseData.pose.setRotation(orientation);
		poseData.pose.setTranslation(location);
		model.poseDataMap.put(entity, poseData);
		return this;		
	}
	//=============================================================================================
	
	//=============================================================================================
	public Entity build() {
		return entity;
	}
	//=============================================================================================
	
}
//*************************************************************************************************
