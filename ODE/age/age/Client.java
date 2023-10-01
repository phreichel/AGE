//*************************************************************************************************
package age;
//*************************************************************************************************

import age.port.Port;
import age.port.jogl.JOGLPort;
import age.rig.Rig;
import age.rig.Skeleton;
import age.scene.Camera;
import age.scene.NFlag;
import age.scene.Node;
import age.scene.NItem;
import age.scene.Scene;
import age.task.Tasks;
import age.util.MathUtil;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import age.clock.Clock;
import age.gui.Factory;
import age.gui.WFlag;
import age.gui.Widget;
import age.gui.GUI;
import age.input.Events;
import age.log.Level;
import age.log.Log;
import age.mesh.Mesh;

//*************************************************************************************************
public class Client {

	//=============================================================================================
	private Clock clock = new Clock();
	private Events events = new Events();
	private Scene scene = new Scene();
	private GUI gui = new GUI();
	private Tasks tasks = new Tasks();
	private Port port = new JOGLPort();
	private boolean running = false;  
	//=============================================================================================

	//=============================================================================================
	private Widget sysMenuFrame;
	//=============================================================================================
	
	//=============================================================================================
	public void run() {
		setup();
		loop();
	}
	//=============================================================================================

	//=============================================================================================
	private void setup() {
		
		port.create();

		dependencyInjections();

		setupScene();
		setupGUI();
		
		Log.info("Adjusting Port Window");
		port.position(150, 150);
		port.size(800, 600);
		
		Log.info("Setting Clock Tasks");
		
		clock.addFPS(60, this::update);
		clock.addFPS(60, this::render);

	}
	//=============================================================================================

	//=============================================================================================
	private void dependencyInjections() {
		Log.info("Dependency Injections");
		port.assign(events);
		scene.assign(events);
		scene.assign(port);
		gui.assign(events);
		gui.assign(port);
		tasks.assign(events);
	}
	//=============================================================================================

	//=============================================================================================
	private void setupScene() {

		Log.info("Scene Setup");

		Node camNode = new Node();
		Camera camData = new Camera(45f, .4f, 1000f);
		camNode.component(NItem.CAMERA, camData);
		Matrix4f cm = MathUtil.rotY(150f);
		cm.setTranslation(new Vector3f(0, 2, 0));
		camNode.component(NItem.TRANSFORM, cm);
		scene.freeCamController().add(camNode);
		scene.camera(camNode);
		scene.root().attach(camNode);

		Mesh mesh = Mesh.factory().siglet2(30, 6);
		Node meshNode = new Node(NFlag.MESH2);
		meshNode.component(NItem.MESH2, mesh);
		meshNode.component(NItem.TRANSFORM, MathUtil.identityMatrix());
		meshNode.component(NItem.TRANSFORM_ANIMATION, MathUtil.rotY(5f));
		scene.root().attach(meshNode);
		scene.animator().add(NFlag.TRANSFORM, meshNode);

		Mesh bmesh = Mesh.factory().model2("assets/stone/Stone.obj");
		Node bNode = new Node(NFlag.MESH2);
		bNode.component(NItem.MESH2, bmesh);
		meshNode.attach(bNode);
		
		Skeleton skeleton = age.rig.Factory.create();
		for (int i=0; i<10; i++) {
			Node skelNode1 = new Node();
			skelNode1.component(
				NItem.TRANSFORM,
				MathUtil.rotY(i * 36f));
			skelNode1.component(
				NItem.TRANSFORM_ANIMATION,
				MathUtil.rotY(-30f));
			scene.animator().add(NFlag.TRANSFORM, skelNode1);
			Node skelNode2 = new Node(NFlag.RIG);
			Rig rig = new Rig(skeleton);
			rig.scale(1f + (float) Math.random());
			skelNode2.component(NItem.RIG, rig);
			scene.animator().add(NFlag.RIG, skelNode2);
			Matrix4f m = MathUtil.rotY(90f);
			m.setTranslation(new Vector3f(0, 0, -5));
			skelNode2.component(
				NItem.TRANSFORM,
				m);
			skelNode1.attach(skelNode2);
			meshNode.attach(skelNode1);
		}
		
	}
	//=============================================================================================
	
	//=============================================================================================
	private void setupGUI() {

		Log.info("GUI Setup");
		
		Factory factory = gui.factory(); 
		Widget root = gui.root();

		Widget btnSysmenu = factory.createIconButton("plus", "sysmenu");
		btnSysmenu.position(5, 5);
		root.add(btnSysmenu);
		
		sysMenuFrame = new Widget(WFlag.CANVAS, WFlag.VSTACK, WFlag.HIDDEN);
		gui.layouter().add(WFlag.VSTACK, sysMenuFrame);
		sysMenuFrame.position(0, 30);
		sysMenuFrame.add(factory.createIconButton("fullscreen", "fullscreen"));
		sysMenuFrame.add(factory.createIconButton("shutdown", "shutdown"));
		root.add(sysMenuFrame);

		tasks.assign("fullscreen", this::toggleFullscreen);
		tasks.assign("shutdown", this::shutdown);
		tasks.assign("sysmenu", this::toggleSysmenu);
		
	}
	//=============================================================================================

	//=============================================================================================
	private void toggleSysmenu() {
		if (sysMenuFrame.match(WFlag.HIDDEN))
			sysMenuFrame.clear(WFlag.HIDDEN);
		else 
			sysMenuFrame.flag(WFlag.HIDDEN);
	}
	//=============================================================================================

	//=============================================================================================
	private void toggleFullscreen() {
		boolean toggle = !port.fullscreen();
		port.fullscreen(toggle);
	}
	//=============================================================================================

	//=============================================================================================
	private void loop() {
		running = true;
		Log.info("Entering Client Loop");
		port.visible(true);
		while (running) {
			clock.update();
		}
		port.visible(false);
		Log.info("Leaving Client Loop");
	}
	//=============================================================================================

	//=============================================================================================
	public void shutdown() {
		running = false;
	}
	//=============================================================================================
	
	//=============================================================================================
	private void render(
			int count,
			long nanoperiod,
			float dT) {
		scene.update(dT);
		port.render();
	}
	//=============================================================================================

	//=============================================================================================
	private void update(
			int count,
			long nanoperiod,
			float dT) {
		events.update();
		gui.update();
		tasks.update();
	}
	//=============================================================================================
	
	//=============================================================================================
	public static void main(String[] args) {
		Log.get("default").disable(Level.DEBUG);
		Log.info("Client Start");
		Client client;
		client = new Client();
		client.run();
		Log.info("Client Stop");
	}
	//=============================================================================================
	
}
//*************************************************************************************************
