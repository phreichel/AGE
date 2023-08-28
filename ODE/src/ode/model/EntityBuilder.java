//*************************************************************************************************
package ode.model;

import javax.vecmath.Matrix4f;
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
	public EntityBuilder withLinearVelocityData() {
		Vector3f linearVelocityData = new Vector3f();
		model.linearVelocityDataMap.put(entity, linearVelocityData);
		return this;		
	}
	//=============================================================================================
	
	//=============================================================================================
	public EntityBuilder withLinearVelocityData(Vector3f linearVelocity) {
		Vector3f linearVelocityData = new Vector3f(linearVelocity);
		model.linearVelocityDataMap.put(entity, linearVelocityData);
		return this;		
	}
	//=============================================================================================

	//=============================================================================================
	public EntityBuilder withLinearVelocityData(float vx, float vy, float vz) {
		Vector3f linearVelocityData = new Vector3f(vx, vy, vz);
		model.linearVelocityDataMap.put(entity, linearVelocityData);
		return this;		
	}
	//=============================================================================================

	//=============================================================================================
	public EntityBuilder withRotationVelocityData() {
		Quat4f rotationVelocityData = new Quat4f(0, 0, 0, 1);
		model.rotationVelocityDataMap.put(entity, rotationVelocityData);
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	public EntityBuilder withRotationVelocityData(Quat4f rotationVelocity) {
		Quat4f rotationVelocityData = new Quat4f(rotationVelocity);
		model.rotationVelocityDataMap.put(entity, rotationVelocityData);
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	public EntityBuilder withPositionData() {
		Vector3f positionData = new Vector3f();
		model.positionDataMap.put(entity, positionData);
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	public EntityBuilder withPositionData(Vector3f position) {
		Vector3f positionData = new Vector3f(position);
		model.positionDataMap.put(entity, positionData);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public EntityBuilder withPositionData(float x, float y, float z) {
		Vector3f positionData = new Vector3f(x, y, z);
		model.positionDataMap.put(entity, positionData);
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	public EntityBuilder withOrientationData() {
		Quat4f orientationData = new Quat4f(0, 0, 0, 1);
		model.orientationDataMap.put(entity, orientationData);
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	public EntityBuilder withOrientationData(Quat4f orientation) {
		Quat4f orientationData = new Quat4f(orientation);
		model.orientationDataMap.put(entity, orientationData);
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	public EntityBuilder withPoseData() {
		Matrix4f poseData = new Matrix4f();
		poseData.setIdentity();
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
