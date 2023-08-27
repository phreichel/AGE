//*************************************************************************************************
package ode.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.vecmath.Matrix4f;

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
	private Entity createEntity() {
		Entity entity = new Entity(this);
		entities.add(entity);
		return entity;
	}
	//=============================================================================================

	//=============================================================================================
	public Entity createCamera() {
		Entity entity = createEntity();
		KineticData kineticData = new KineticData();
		kineticDataMap.put(entity, kineticData);
		PoseData poseData = new PoseData();
		poseDataMap.put(entity, poseData);
		CameraData cameraData = new CameraData();
		cameraDataMap.put(entity, cameraData);
		return entity;
	}
	//=============================================================================================
	
	//=============================================================================================
	public Entity createBody() {
		Entity entity = createEntity();
		KineticData kineticData = new KineticData();
		kineticDataMap.put(entity, kineticData);
		PoseData poseData = new PoseData();
		poseDataMap.put(entity, poseData);
		return entity;
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
		PoseData poseData = entity.model.poseDataMap.get(entity);
		Matrix4f camTransform = new Matrix4f(poseData.pose);
		camTransform.transpose();
		graphics.beginSceneMode(camTransform, camData.fov, camData.near, camData.far);
		renderSystem.update(graphics);
	}
	//=============================================================================================
	
}
//*************************************************************************************************
