//*************************************************************************************************
package ode.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import ode.event.Events;
import ode.platform.Graphics;
import ode.util.ODEException;

//*************************************************************************************************

//*************************************************************************************************
public class Model {
	
	//=============================================================================================
	public Model(Events events) {
	}
	//=============================================================================================

	//=============================================================================================
	public Set<Entity> entities = new HashSet<>();
	public Map<Entity, CameraData> cameraDataMap = new HashMap<>();
	public Map<Entity, Vector3f> positionDataMap = new HashMap<>();
	public Map<Entity, Quat4f> orientationDataMap = new HashMap<>();
	public Map<Entity, Matrix4f> poseDataMap = new HashMap<>();
	public Map<Entity, Vector3f> linearVelocityDataMap = new HashMap<>();
	public Map<Entity, Quat4f> rotationVelocityDataMap = new HashMap<>();
	//=============================================================================================

	//=============================================================================================
	private PoseSystem poseSystem = new PoseSystem(poseDataMap);
	private LinearKineticSystem linearKineticSystem = new LinearKineticSystem(linearVelocityDataMap);
	private RotationKineticSystem rotationKineticSystem = new RotationKineticSystem(rotationVelocityDataMap);
	private RenderSystem renderSystem = new RenderSystem(entities);
	//=============================================================================================

	//=============================================================================================
	public EntityBuilder build() {
		return new EntityBuilder(this);
	}
	//=============================================================================================
	
	//=============================================================================================
	public Entity createCamera() {
		return build()
			.withPositionData()
			.withOrientationData()
			.withPoseData()
			.withLinearVelocityData()
			.withRotationVelocityData()
			.withCameraData(65f, .4f, 1000f, true)
			.build();
	}
	//=============================================================================================
	
	//=============================================================================================
	public Entity createBody() {
		return build()
			.withPositionData()
			.withOrientationData()
			.withPoseData()
			.withLinearVelocityData()
			.withRotationVelocityData()
			.build();
	}
	//=============================================================================================

	//=============================================================================================
	public void remove(Entity entity) {
		entities.remove(entity);
		cameraDataMap.remove(entity);
		positionDataMap.remove(entity);
		poseDataMap.remove(entity);
		orientationDataMap.remove(entity);
		linearVelocityDataMap.remove(entity);
		rotationVelocityDataMap.remove(entity);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void update(float dT) {
		linearKineticSystem.update(dT);
		rotationKineticSystem.update(dT);
		poseSystem.update();
	}
	//=============================================================================================

	//=============================================================================================
	public void render(Graphics graphics) {
		Entity entity = null;
		CameraData camData = null;
		for (Entry<Entity, CameraData> entry : cameraDataMap.entrySet()) {
			camData = entry.getValue();
			if (camData.active) {
				entity = entry.getKey();
				break;
			}
		}
		if (entity == null) throw new ODEException("No Active Camera");
		Matrix4f camTransform = new Matrix4f(entity.getPoseData());
		camTransform.transpose();
		graphics.beginSceneMode(camTransform, camData.fov, camData.near, camData.far);
		renderSystem.update(graphics);
	}
	//=============================================================================================
	
}
//*************************************************************************************************
