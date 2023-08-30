//*************************************************************************************************
package ode.model;
//*************************************************************************************************

import java.util.HashSet;
import java.util.Set;

import ode.event.Events;
import ode.platform.Graphics;

//*************************************************************************************************
public class Model {
	
	//=============================================================================================
	public Model(Events events) {}
	//=============================================================================================

	//=============================================================================================
	public Set<Entity> entities = new HashSet<>();
	//=============================================================================================

	//=============================================================================================
	private PoseSystem           poseSystem           = new PoseSystem();
	private KineticLinearSystem  kineticLinearSystem  = new KineticLinearSystem();
	private KineticAngularSystem kineticAngularSystem = new KineticAngularSystem();
	private CameraSystem         cameraSystem         = new CameraSystem();
	private RenderSystem         renderSystem         = new RenderSystem();
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
			.register(kineticLinearSystem)
			.register(kineticAngularSystem)
			.register(poseSystem)
			.register(cameraSystem)
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
			.register(kineticLinearSystem)
			.register(kineticAngularSystem)
			.register(poseSystem)
			.register(renderSystem)
			.build();
	}
	//=============================================================================================

	//=============================================================================================
	public void remove(Entity entity) {
		entities.remove(entity);
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
		cameraSystem.update(graphics);
		renderSystem.update(graphics);
	}
	//=============================================================================================
	
}
//*************************************************************************************************
