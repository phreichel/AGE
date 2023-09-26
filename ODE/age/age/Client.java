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
import age.skeleton.Skeleton;
import age.task.Tasks;
import age.util.MathUtil;
import java.util.ArrayList;
import java.util.List;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import org.hipparchus.linear.MatrixUtils;

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
		scene.assign(port);
		gui.assign(port);
		gui.assign(events);
		tasks.assign(events);
	}
	//=============================================================================================

	//=============================================================================================
	private Skeleton skeleton = null; 
	private List<Node> skelNodes = new ArrayList<>(); 
	//=============================================================================================
	
	//=============================================================================================
	private void setupScene() {

		Log.info("Scene Setup");
		
		Mesh mesh = Mesh.factory().siglet(30,  10);
		Node meshNode = new Node(NFlag.MESH);
		meshNode.component(
			NItem.MESH,
			mesh);
		meshNode.component(
			NItem.TRANSFORM,
			MathUtil.identityMatrix());
		meshNode.component(
			NItem.TRANSFORM_ANIMATION,
			MathUtil.rotY(5f));
		scene
			.root()
			.attach(meshNode);
		scene
			.animations()
			.add(NFlag.TRANSFORM, meshNode);
		
		skeleton = age
			.skeleton.Factory
			.create();
		skeleton.speed(4f);
		for (int i=0; i<10; i++) {
			Node skelNode1 = new Node();
			skelNode1.component(
				NItem.TRANSFORM,
				MathUtil.rotY(i * 36f));
			skelNode1.component(
				NItem.TRANSFORM_ANIMATION,
				MathUtil.rotY(-30f));
			scene.animations().add(
					NFlag.TRANSFORM,
					skelNode1);
			Node skelNode2 = new Node(NFlag.SKELETON);
			skelNode2.component(
				NItem.SKELETON,
				skeleton);
			Matrix4f m = MathUtil.rotY(90f);
			m.setTranslation(new Vector3f(0, 0, -5));
			skelNode2.component(
				NItem.TRANSFORM,
				m);
			skelNodes.add(skelNode2);
			skelNode1.attach(skelNode2);
			meshNode.attach(skelNode1);
		}
		scene.animations().add(NFlag.SKELETON, skelNodes.get(0));
		
		Node camNode = new Node();
		Camera camData = new Camera(45f, .4f, 1000f);
		camNode.component(NItem.CAMERA, camData);
		scene.camera(camNode);
		skelNodes.get(0).attach(camNode);
		Matrix4f cm = MathUtil.rotY(120f);
		cm.setTranslation(new Vector3f(0, 2.5f, 0));
		camNode.component(NItem.TRANSFORM, cm);
		//scene.root().attach(camNode);
				
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
