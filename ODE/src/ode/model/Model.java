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
	public Map<Entity, PoseData> poseDataMap = new HashMap<>();
	public Map<Entity, KineticData> kineticDataMap = new HashMap<>();
	//=============================================================================================

	//=============================================================================================
	private PoseSystem poseSystem = new PoseSystem(poseDataMap);
	private KineticSystem kineticSystem = new KineticSystem(poseDataMap, kineticDataMap);
	private RenderSystem renderSystem = new RenderSystem(entities);
	//=============================================================================================
	
	//=============================================================================================
	public Entity createCamera() {
		return new EntityBuilder(this)
			.withPoseData(new Vector3f(), new Quat4f(0, 0, 0, 1))
			.withKineticData(new Vector3f(), new Quat4f(0, 0, 0, 1))
			.withCameraData(65f, .4f, 1000f, true)
			.build();
	}
	//=============================================================================================
	
	//=============================================================================================
	public Entity createBody() {
		return new EntityBuilder(this)
			.withPoseData(new Vector3f(), new Quat4f(0, 0, 0, 1))
			.withKineticData(new Vector3f(), new Quat4f(0, 0, 0, 1))
			.build();
	}
	//=============================================================================================
	
	//=============================================================================================
	public void update(float dT) {
		kineticSystem.update(dT);
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
		PoseData poseData = entity.getPoseData();
		Matrix4f camTransform = new Matrix4f(poseData.pose);
		camTransform.transpose();
		graphics.beginSceneMode(camTransform, camData.fov, camData.near, camData.far);
		renderSystem.update(graphics);
	}
	//=============================================================================================
	
}
//*************************************************************************************************
