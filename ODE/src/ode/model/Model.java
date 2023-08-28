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
	public Map<Entity, CameraData> cameraMap = new HashMap<>();
	public Map<Entity, Vector3f> positionMap = new HashMap<>();
	public Map<Entity, Quat4f> orientationMap = new HashMap<>();
	public Map<Entity, Matrix4f> poseMap = new HashMap<>();
	public Map<Entity, Vector3f> linearVelocityMap = new HashMap<>();
	public Map<Entity, RenderEnum> renderMap = new HashMap<>();
	public Map<Entity, Quat4f> rotationVelocityMap = new HashMap<>();
	//=============================================================================================

	//=============================================================================================
	private PoseSystem poseSystem = new PoseSystem(poseMap);
	private KineticLinearSystem kineticLinearSystem = new KineticLinearSystem(linearVelocityMap);
	private KineticAngularSystem kineticAngularSystem = new KineticAngularSystem(rotationVelocityMap);
	private RenderSystem renderSystem = new RenderSystem(renderMap);
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
			.withRenderData(RenderEnum.BOX)
			.build();
	}
	//=============================================================================================

	//=============================================================================================
	public void remove(Entity entity) {
		entities.remove(entity);
		cameraMap.remove(entity);
		positionMap.remove(entity);
		poseMap.remove(entity);
		orientationMap.remove(entity);
		linearVelocityMap.remove(entity);
		renderMap.remove(entity);
		rotationVelocityMap.remove(entity);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void update(float dT) {
		kineticLinearSystem.update(dT);
		kineticAngularSystem.update(dT);
		poseSystem.update();
	}
	//=============================================================================================

	//=============================================================================================
	public void render(Graphics graphics) {
		Entity entity = null;
		CameraData camData = null;
		for (Entry<Entity, CameraData> entry : cameraMap.entrySet()) {
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
