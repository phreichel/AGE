//*************************************************************************************************
package ode.client;
//*************************************************************************************************

import javax.vecmath.AxisAngle4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import ode.event.Events;
import ode.gui.GUI;
import ode.gui.HierarchyData;
import ode.gui.PositionData;
import ode.gui.Widget;
import ode.model.CameraData;
import ode.model.Entity;
import ode.model.Model;
import ode.platform.Platform;
import ode.schedule.Scheduler;

//*************************************************************************************************
public class Client {

	//=============================================================================================
	private final Events events = new Events();
	private final GUI gui = new GUI(events);
	private final Model model = new Model(events);
	private final Platform platform = new Platform(events, model, gui);
	private final Scheduler scheduler = new Scheduler();
	//=============================================================================================

	//=============================================================================================
	private boolean terminate = false;
	//=============================================================================================
	
	//=============================================================================================
	public void configure(String[] args) {
		
		Widget b1 = gui.createButton("START", (w) -> System.out.println(w.uuid));
		Widget b2 = gui.createButton("QUIT", (w) -> terminate = true);
		Widget buttons = gui.createBox();
		attach(buttons, b1);
		attach(buttons, b2);
		
		Widget scores = gui.createBox();
		for (int i=0; i<10; i++) {
			Widget score = gui.createLabel("???");
			attach(scores, score);
		}
		
		Widget frame = gui.createBox();
		attach(frame, buttons);
		attach(frame, scores);
		PositionData positionData = frame.getPositionData();
		positionData.x = 30;
		positionData.y = 30;

		Entity camera = model.createCamera();
		
		CameraData cameraData = camera.getCameraData();
		cameraData.active = true;
		
		Quat4f rotationVelocityData = camera.getRotationVelocityData();
		rotationVelocityData.set(new AxisAngle4f(0, 0, -1, (float) Math.toRadians(15f)));

		for (int i=0; i<25; i++) {
			
			Entity body = model.createBody();
			
			Vector3f bodyPositionData = body.getPositionData();
			bodyPositionData.set(0, 0, -5f);
			
			Vector3f linearVelocityData = body.getLinearVelocityData();
			float a = (float) ((Math.random() - .5) * 0.2);
			float b = (float) ((Math.random() - .5) * 0.2);
			float c = (float) (Math.random() * 0.2);
			linearVelocityData.set(a, b, -.5f - c);

			Quat4f bodyRotationVelocityData = body.getRotationVelocityData();
			Vector3f nrm = new Vector3f(
				(float) (Math.random() - .5),
				(float) (Math.random() - .5),
				(float) (Math.random() - .5));
			nrm.normalize();
			float deg = (float) Math.random() * 90f;
			bodyRotationVelocityData.set(new AxisAngle4f(nrm, (float) Math.toRadians(deg)));
			
		}
		
		scheduler.add(1000000000L / 100L, (n, p) -> model.update((float) (n*p) / 1000000000L ));
		scheduler.add(1000000000L / 30L, (n, p) -> gui.update( (float) (n*p) / 1000000000L ));
		scheduler.add(1000000000L / 30L, (n, p) -> platform.update());

	}
	//=============================================================================================

	//=============================================================================================
	private void attach(Widget parent, Widget child) {
		HierarchyData parentHierarchy = parent.getHierarchyData();
		HierarchyData childHierarchy = child.getHierarchyData();
		parentHierarchy.children.add(child);
		childHierarchy.parent = parent;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void run() {
		terminate = false;
		scheduler.init();
		platform.setVisible(true);
		while (!terminate) {
			events.update();
			scheduler.update();
			Thread.yield();
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	public static void main(String[] args) {
		Client client = new Client();
		client.configure(args);
		client.run();
	}
	//=============================================================================================

}
//*************************************************************************************************
