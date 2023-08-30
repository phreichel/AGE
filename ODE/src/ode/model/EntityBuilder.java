//*************************************************************************************************
package ode.model;
//*************************************************************************************************

import java.util.List;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

//*************************************************************************************************
public class EntityBuilder {

	//=============================================================================================
	private Entity entity;
	//=============================================================================================

	//=============================================================================================
	public EntityBuilder(Model model) {
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
		entity.addComponent(EntityEnum.CAMERA, cameraData);
		return this;		
	}
	//=============================================================================================

	//=============================================================================================
	public EntityBuilder withLinearVelocityData() {
		Vector3f linearVelocityData = new Vector3f();
		entity.addComponent(EntityEnum.LINEAR_VELOCITY, linearVelocityData);
		return this;		
	}
	//=============================================================================================
	
	//=============================================================================================
	public EntityBuilder withLinearVelocityData(Vector3f linearVelocity) {
		Vector3f linearVelocityData = new Vector3f(linearVelocity);
		entity.addComponent(EntityEnum.LINEAR_VELOCITY, linearVelocityData);
		return this;		
	}
	//=============================================================================================

	//=============================================================================================
	public EntityBuilder withLinearVelocityData(float vx, float vy, float vz) {
		Vector3f linearVelocityData = new Vector3f(vx, vy, vz);
		entity.addComponent(EntityEnum.LINEAR_VELOCITY, linearVelocityData);
		return this;		
	}
	//=============================================================================================

	//=============================================================================================
	public EntityBuilder withRotationVelocityData() {
		Quat4f rotationVelocityData = new Quat4f(0, 0, 0, 1);
		entity.addComponent(EntityEnum.ANGULAR_VELOCITY, rotationVelocityData);
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	public EntityBuilder withRotationVelocityData(Quat4f rotationVelocity) {
		Quat4f rotationVelocityData = new Quat4f(rotationVelocity);
		entity.addComponent(EntityEnum.ANGULAR_VELOCITY, rotationVelocityData);
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	public EntityBuilder withPositionData() {
		Vector3f positionData = new Vector3f();
		entity.addComponent(EntityEnum.POSITION, positionData);
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	public EntityBuilder withPositionData(Vector3f position) {
		Vector3f positionData = new Vector3f(position);
		entity.addComponent(EntityEnum.POSITION, positionData);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public EntityBuilder withPositionData(float x, float y, float z) {
		Vector3f positionData = new Vector3f(x, y, z);
		entity.addComponent(EntityEnum.POSITION, positionData);
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	public EntityBuilder withOrientationData() {
		Quat4f orientationData = new Quat4f(0, 0, 0, 1);
		entity.addComponent(EntityEnum.ORIENTATION, orientationData);
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	public EntityBuilder withOrientationData(Quat4f orientation) {
		Quat4f orientationData = new Quat4f(orientation);
		entity.addComponent(EntityEnum.ORIENTATION, orientationData);
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	public EntityBuilder withPoseData() {
		Matrix4f poseData = new Matrix4f();
		poseData.setIdentity();
		entity.addComponent(EntityEnum.POSE, poseData);
		return this;		
	}
	//=============================================================================================

	//=============================================================================================
	public EntityBuilder withRenderData(RenderEnum renderData) {
		entity.addComponent(EntityEnum.RENDER, renderData);
		return this;		
	}
	//=============================================================================================

	//=============================================================================================
	public EntityBuilder register(List<Entity> system) {
		system.add(entity);
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
