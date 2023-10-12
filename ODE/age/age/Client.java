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
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import age.clock.Clock;
import age.gui.WFlag;
import age.gui.Widget;
import age.gui.GUI;
import age.input.Events;
import age.log.Level;
import age.log.Log;
import age.mesh.Mesh;
import age.model.Animation;
import age.model.Factory;
import age.model.Influence;
import age.model.Model;
import age.model.Rig;

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

		age.mesh.Mesh mesh = age.mesh.Mesh.factory().siglet(30, 7);
		Node meshNode = new Node(NFlag.MESH);
		meshNode.component(NItem.MESH, mesh);
		scene.root().attach(meshNode);

		Mesh bmesh = Mesh.factory().model("assets/stone/Stone.obj");
		Node bNode = new Node(NFlag.MESH);
		bNode.component(NItem.MESH, bmesh);
		meshNode.attach(bNode);

		Mesh ptmesh = Mesh.factory().model("assets/palm_tree/10446_Palm_Tree_v1_max2010_iteration-2.obj");
		Matrix4f ptMx = new Matrix4f();
		ptMx.setIdentity();
		ptMx.rotX( (float) Math.toRadians(-90f));
		ptMx.setScale(0.01f);
		
		for (int i=0; i<100; i++) {
			float ds = (float) (Math.random() * 0.005f);
			float da = (float) (Math.random() * Math.PI * 2);
			float dd = 7f + (float) (Math.random() * 20f);
			float dx = (float) (Math.sin(da) * dd);
			float dz = (float) (Math.cos(da) * dd);
			Matrix4f ptMxC = new Matrix4f(ptMx);
			ptMxC.setTranslation(new Vector3f(dx, 0f, dz));
			ptMxC.setScale(ptMxC.getScale() + ds);
			Node ptNode = new Node(NFlag.MESH);
			ptNode.component(NItem.MESH, ptmesh);
			ptNode.component(NItem.TRANSFORM, ptMxC);
			meshNode.attach(ptNode);
		}
		
		Model model = Factory.instance.model("assets/yrig/yrig.obj");
		Animation animation = Factory.instance.animation("assets/yrig/yrig.bvh");
		Influence influence = new Influence(animation.skeleton, model.skin.mesh); 
		
		int   n = 6;
		float r = 360f / n;
		for (int i=0; i<n; i++) {

			Node skelNode1 = new Node();
			skelNode1.component(NItem.TRANSFORM, MathUtil.rotY(i*r));
			skelNode1.component(NItem.TRANSFORM_ANIMATION, MathUtil.rotY(-12f));
			scene.animator().add(NFlag.TRANSFORM, skelNode1);
			
			Matrix4f m = new Matrix4f();
			Matrix4f m2 = new Matrix4f();
			m.rotX( (float) Math.toRadians(-90) );
			m2.rotY( (float) Math.toRadians(90) );
			m.mul(m2, m);
			m.setTranslation(new Vector3f(0, 0, -5));
			
			Node rigNode = new Node(NFlag.RIG2);
			Rig rig = new Rig(animation, model, influence);
			rigNode.component(NItem.RIG2, rig);
			scene.animator().add(NFlag.RIG2, rigNode);
			rigNode.component(NItem.TRANSFORM, m);
			skelNode1.attach(rigNode);
			
			meshNode.attach(skelNode1);
		}
		
	}
	//=============================================================================================
	
	//=============================================================================================
	private void setupGUI() {

		Log.info("GUI Setup");
		
		age.gui.Factory factory = gui.factory(); 
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
