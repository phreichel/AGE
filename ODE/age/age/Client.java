//*************************************************************************************************
package age;
//*************************************************************************************************

import age.port.Port;
import age.port.jogl.JOGLPort;
import age.scene.Camera;
import age.scene.Node;
import age.scene.NodeComponent;
import age.scene.Scene;
import age.task.Tasks;
import age.util.Util;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import age.clock.Clock;
import age.gui.Factory;
import age.gui.Flag;
import age.gui.Widget;
import age.gui.GUI;
import age.input.Events;
import age.log.Level;
import age.log.Log;

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
	private Widget windowFrame;
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
		clock.addFPS(60, this::render);
		clock.addFPS(120, this::update);

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
		
		for (int i=0; i<7; i++) {
			for (int j=0; j<1; j++) {
				for (int k=0; k<7; k++) {
					Node n = new Node();
					n.flag(age.scene.Flag.BOX);
					Matrix4f m = new Matrix4f();
					m.setIdentity();
					m.set(new Vector3f((i-3) * 2.5f, (j) * 2.5f, (k-3) * 2.5f));
					n.component(NodeComponent.TRANSFORM, m);
					scene.root().attach(n);
				}
			}
		}
		
		Node camNode = new Node();
		Matrix4f camTransform = new Matrix4f();
		Camera camData = new Camera(35f, .4f, 1000f);
		camTransform.setIdentity();
		float a = (float) Math.toRadians(-49);
		AxisAngle4f rot = new AxisAngle4f(1, 0, 0, a);
		camTransform.setRotation(rot);
		camTransform.setTranslation(new Vector3f(-2, 15, 15));
		camNode.component(NodeComponent.TRANSFORM, camTransform);
		camNode.component(NodeComponent.CAMERA, camData);
		scene.root().attach(camNode);	
		scene.camera(camNode);
		
	}
	//=============================================================================================
	
	//=============================================================================================
	private void setupGUI() {

		Log.info("GUI Setup");
		
		Factory factory = gui.factory(); 
		Widget root = gui.root();

		String text = Util.readTextFile("assets/sample.txt");
		text = text.replace("\r", "");
		text = text.replace("\t", "    ");

		windowFrame = new Widget();
		windowFrame.position(root.position());
		windowFrame.dimension(root.dimension());
		windowFrame.dock(0, 1, 0, 1);
		root.add(windowFrame);
		
		for (int i=0; i<5; i++) {
			
			Widget window = factory.createWindow(800, 600, "Window");
			window.position(50 + 15*i, 50 + 35*i);
			windowFrame.add(window);
			Widget page = window.children().get(2);
			
			Widget textTest = factory.createMultiLine(text);
			textTest.dimension(
				page.width(),
				page.height());
			textTest.dock(0, 1, 0, 1);
			page.add(textTest);
			
		}

		sysMenuFrame = new Widget(Flag.CANVAS, Flag.HIDDEN);
		sysMenuFrame.position(0, 30);
		sysMenuFrame.dimension(30, 80);
		root.add(sysMenuFrame);
		
		
		Widget fsButton = factory.createIconButton("fullscreen", "fullscreen");
		fsButton.position(5, 5);
		sysMenuFrame.add(fsButton);

		Widget dtButton = factory.createIconButton("desk", "desk");
		dtButton.position(5, 30);
		sysMenuFrame.add(dtButton);
		
		Widget sdButton = factory.createIconButton("shutdown", "shutdown");
		sdButton.position(5, 55);
		sysMenuFrame.add(sdButton);

		Widget sysButton = factory.createIconButton("plus", "sysmenu");
		sysButton.position(5, 5);
		root.add(sysButton);

		tasks.assign("fullscreen", this::toggleFullscreen);
		tasks.assign("desk", this::toggleDesktop);
		tasks.assign("shutdown", this::shutdown);
		tasks.assign("sysmenu", this::toggleSysmenu);
		
	}
	//=============================================================================================

	//=============================================================================================
	private void toggleSysmenu() {
		if (sysMenuFrame.match(Flag.HIDDEN))
			sysMenuFrame.clear(Flag.HIDDEN);
		else 
			sysMenuFrame.flag(Flag.HIDDEN);
	}
	//=============================================================================================

	//=============================================================================================
	private void toggleDesktop() {
		if (windowFrame.match(Flag.HIDDEN))
			windowFrame.clear(Flag.HIDDEN);
		else 
			windowFrame.flag(Flag.HIDDEN);
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
		port.render();
	}
	//=============================================================================================

	//=============================================================================================
	private void update(
			int count,
			long nanoperiod,
			float dT) {
		events.update();
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
