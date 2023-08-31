//*************************************************************************************************
package ode.client;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.AxisAngle4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import ode.event.Events;
import ode.gui.FlagData;
import ode.gui.FlagEnum;
import ode.gui.GUI;
import ode.gui.HierarchyData;
import ode.gui.Widget;
import ode.gui.WidgetEnum;
import ode.model.CameraData;
import ode.model.Entity;
import ode.model.EntityEnum;
import ode.model.Model;
import ode.model.RenderEnum;
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
	private Widget startMenu;
	private Widget pauseButton;
	private Widget bStart;
	private Widget bResume;
	private Widget bStop;
	private Widget[] hiscores = new Widget[10];
	//=============================================================================================
	
	//=============================================================================================
	public void configure(String[] args) {
		setupGUI();
		setupIdleScene();
		scheduler.add(1000000000L / 100L, (n, p) -> model.update((float) (n*p) / 1000000000L ));
		scheduler.add(1000000000L / 30L, (n, p) -> gui.update( (float) (n*p) / 1000000000L ));
		scheduler.add(1000000000L / 30L, (n, p) -> platform.update());
	}
	//=============================================================================================

	//=============================================================================================
	private void setupGUI() {
		bStart = gui.createButton("START", this::startGame);
		bResume = gui.createButton("RESUME", this::resumeGame);
		FlagData resumeFlagData = bResume.getComponent(WidgetEnum.FLAGS, FlagData.class);
		resumeFlagData.flags.remove(FlagEnum.DISPLAYED);
		bStop = gui.createButton("STOP", this::stopGame);
		FlagData stopFlagData = bStop.getComponent(WidgetEnum.FLAGS, FlagData.class);
		stopFlagData.flags.remove(FlagEnum.DISPLAYED);
		Widget b1 = gui.createButton("WINOWED", (w) -> platform.setFullscreen(false));
		Widget b2 = gui.createButton("FULLSCREEN", (w) -> platform.setFullscreen(true));
		Widget b3 = gui.createButton("QUIT", (w) -> terminate = true);
		Widget buttons = gui.createBox();
		attach(buttons, bStart);
		attach(buttons, bResume);
		attach(buttons, bStop);
		attach(buttons, b1);
		attach(buttons, b2);
		attach(buttons, b3);
		
		Widget scores = gui.createBox();
		for (int i=0; i<10; i++) {
			Widget score = gui.createLabel("---");
			attach(scores, score);
			hiscores[i] = score;
		}
		
		startMenu = gui.createBox();
		attach(startMenu, buttons);
		attach(startMenu, scores);
		Vector2f positionData = startMenu.getComponent(WidgetEnum.POSITION, Vector2f.class);
		positionData.set(5, 5);

		pauseButton = gui.createButton("+", this::pauseGame);
		Vector2f btnPositionData = pauseButton.getComponent(WidgetEnum.POSITION, Vector2f.class);
		btnPositionData.set(5, 5);
		Vector2f btnDimensionData = pauseButton.getComponent(WidgetEnum.DIMENSION, Vector2f.class);
		btnDimensionData.set(btnDimensionData.y, btnDimensionData.y);
		FlagData flagData = pauseButton.getComponent(WidgetEnum.FLAGS, FlagData.class);
		flagData.flags.remove(FlagEnum.DISPLAYED);
	}
	//=============================================================================================

	//=============================================================================================
	private void setupIdleScene() {
	
		Entity camera = model.createCamera();		
		CameraData cameraData = camera.getComponent(EntityEnum.CAMERA, CameraData.class);
		cameraData.active = true;
		Quat4f rotationVelocityData = camera.getComponent(EntityEnum.ANGULAR_VELOCITY, Quat4f.class);
		rotationVelocityData.set(new AxisAngle4f(0, 0, -1, (float) Math.toRadians(15f)));

		for (int i=0; i<25; i++) {
			Entity body = model.createBody(RenderEnum.BOX);
			Vector3f bodyPositionData = body.getComponent(EntityEnum.POSITION, Vector3f.class);
			bodyPositionData.set(0, 0, -5f);

			Vector3f linearVelocityData = body.getComponent(EntityEnum.LINEAR_VELOCITY, Vector3f.class);
			float a = (float) ((Math.random() - .5) * 0.2);
			float b = (float) ((Math.random() - .5) * 0.2);
			float c = (float) (Math.random() * 0.2);
			linearVelocityData.set(a, b, -.5f - c);

			Quat4f bodyRotationVelocityData = body.getComponent(EntityEnum.ANGULAR_VELOCITY, Quat4f.class);
			Vector3f nrm = new Vector3f(
				(float) (Math.random() - .5),
				(float) (Math.random() - .5),
				(float) (Math.random() - .5));
			nrm.normalize();
			float deg = (float) Math.random() * 90f;
			bodyRotationVelocityData.set(new AxisAngle4f(nrm, (float) Math.toRadians(deg)));
		}		
	}
	//=============================================================================================

	//=============================================================================================
	private void setupGameScene() {
		{
			Entity camera = model.createCamera();
			CameraData cameraData = camera.getComponent(EntityEnum.CAMERA, CameraData.class);
			cameraData.active = true;
			Vector3f position = camera.getComponent(EntityEnum.POSITION, Vector3f.class);
			position.set(0, 1, 10f);
			Quat4f orientation = camera.getComponent(EntityEnum.ORIENTATION, Quat4f.class);
			orientation.set(new AxisAngle4f(1, 0, 0, (float) Math.toRadians(10)));
		}
		for (int i=-11; i<12; i++) {
			for (int j=0; j<15; j++) {
				Entity block = model.createBody(RenderEnum.BLOCK);
				Vector3f position = block.getComponent(EntityEnum.POSITION, Vector3f.class);
				position.set(i, j * .5f, 0f);
			}
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	private void clearScene() {
		List<Entity> ettys = new ArrayList<>(model.entities);
		for (Entity etty : ettys) {
			model.remove(etty);
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	private void attach(Widget parent, Widget child) {
		HierarchyData parentHierarchy = parent.getComponent(WidgetEnum.HIERARCHY, HierarchyData.class);
		HierarchyData childHierarchy = child.getComponent(WidgetEnum.HIERARCHY, HierarchyData.class);
		parentHierarchy.children.add(child);
		childHierarchy.parent = parent;
	}
	//=============================================================================================

	//=============================================================================================
	public void startGame(Widget widget) {
		{
			FlagData flagData =startMenu.getComponent(WidgetEnum.FLAGS, FlagData.class);
			flagData.flags.remove(FlagEnum.DISPLAYED);
		}{
			FlagData flagData =pauseButton.getComponent(WidgetEnum.FLAGS, FlagData.class);
			flagData.flags.add(FlagEnum.DISPLAYED);
		}{
			FlagData flagData =bStart.getComponent(WidgetEnum.FLAGS, FlagData.class);
			flagData.flags.remove(FlagEnum.DISPLAYED);
		}{
			FlagData flagData =bResume.getComponent(WidgetEnum.FLAGS, FlagData.class);
			flagData.flags.add(FlagEnum.DISPLAYED);
		}{
			FlagData flagData =bStop.getComponent(WidgetEnum.FLAGS, FlagData.class);
			flagData.flags.add(FlagEnum.DISPLAYED);
		}
		clearScene();
		setupGameScene();
	}
	//=============================================================================================

	//=============================================================================================
	public void pauseGame(Widget widget) {
		{
			FlagData flagData =pauseButton.getComponent(WidgetEnum.FLAGS, FlagData.class);
			flagData.flags.remove(FlagEnum.DISPLAYED);
		}{
			FlagData flagData =startMenu.getComponent(WidgetEnum.FLAGS, FlagData.class);
			flagData.flags.add(FlagEnum.DISPLAYED);
		}
	}
	//=============================================================================================

	//=============================================================================================
	public void resumeGame(Widget widget) {
		{
			FlagData flagData =startMenu.getComponent(WidgetEnum.FLAGS, FlagData.class);
			flagData.flags.remove(FlagEnum.DISPLAYED);
		}{
			FlagData flagData =pauseButton.getComponent(WidgetEnum.FLAGS, FlagData.class);
			flagData.flags.add(FlagEnum.DISPLAYED);
		}
	}
	//=============================================================================================

	//=============================================================================================
	public void stopGame(Widget widget) {
		{
			FlagData flagData =startMenu.getComponent(WidgetEnum.FLAGS, FlagData.class);
			flagData.flags.add(FlagEnum.DISPLAYED);
		}{
			FlagData flagData =pauseButton.getComponent(WidgetEnum.FLAGS, FlagData.class);
			flagData.flags.remove(FlagEnum.DISPLAYED);
		}{
			FlagData flagData =bStart.getComponent(WidgetEnum.FLAGS, FlagData.class);
			flagData.flags.add(FlagEnum.DISPLAYED);
		}{
			FlagData flagData =bResume.getComponent(WidgetEnum.FLAGS, FlagData.class);
			flagData.flags.remove(FlagEnum.DISPLAYED);
		}{
			FlagData flagData =bStop.getComponent(WidgetEnum.FLAGS, FlagData.class);
			flagData.flags.remove(FlagEnum.DISPLAYED);
		}
		clearScene();
		setupIdleScene();
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
