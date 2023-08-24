//*************************************************************************************************
package ode.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ode.event.Events;
import ode.platform.Graphics;

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
		renderSystem.update();
	}
	//=============================================================================================

	//=============================================================================================
	public void render(Graphics graphics) {
		graphics.beginSceneMode();
	}
	//=============================================================================================
	
}
//*************************************************************************************************
