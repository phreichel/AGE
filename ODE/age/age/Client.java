//*************************************************************************************************
package age;
//*************************************************************************************************

import age.port.Port;
import age.port.jogl.JOGLPort;
import age.scene.Camera;
import age.scene.NFlag;
import age.scene.Node;
import age.scene.NItem;
import age.scene.Scene;
import age.task.Tasks;
import age.util.MathUtil;

import javax.vecmath.AxisAngle4f;
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
		
		clock.addFPS(120, this::update);
		clock.addFPS(60, this::render);
		clock.addFPS(60, this::render);

	}
	//=============================================================================================

	//=============================================================================================
	private void dependencyInjections() {
		Log.info("Dependency Injections");
		port.assign(events);
		scene.assign(port);
		gui.assign(port);
		gui.assign(events);
		tasks.assign(events);
	}
	//=============================================================================================
	
	//=============================================================================================
	private void setupScene() {

		Log.info("Scene Setup");
		
		Mesh mesh = Mesh.factory().siglet(15,  6);
		Node node = new Node(NFlag.MESH);
		node.component(NItem.MESH, mesh);
		node.component(NItem.TRANSFORM, MathUtil.identityMatrix());
		node.component(NItem.TRANSFORM_ANIMATION, MathUtil.rotY( (float) Math.toRadians(2f)));
		scene.root().attach(node);
		scene.animations().add(NFlag.TRANSFORM, node);
		
		Node camNode = new Node();
		Matrix4f camTransform = new Matrix4f();
		Camera camData = new Camera(35f, .4f, 1000f);
		camTransform.setIdentity();
		float a = (float) Math.toRadians(-10);
		AxisAngle4f rot = new AxisAngle4f(1, 0, 0, a);
		camTransform.setRotation(rot);
		camTransform.setTranslation(new Vector3f(0, 4, 15));
		camNode.component(NItem.TRANSFORM, camTransform);
		camNode.component(NItem.CAMERA, camData);
		scene.root().attach(camNode);	
		scene.camera(camNode);
		
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
			Thread.yield();
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
