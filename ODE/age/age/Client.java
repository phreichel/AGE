//*************************************************************************************************
package age;
//*************************************************************************************************

import age.port.Port;
import age.port.jogl.JOGLPort;
import age.scene.Camera;
import age.scene.Node;
import age.scene.NodeFlag;
import age.scene.Scene;
import age.task.Tasks;
import age.util.Util;

import javax.vecmath.AxisAngle4f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import age.clock.Clock;
import age.gui.Flag;
import age.gui.Multiline;
import age.gui.Widget;
import age.gui.GUI;
import age.gui.Window;
import age.input.InputEvents;
import age.log.Level;
import age.log.Log;

//*************************************************************************************************
public class Client {

	//=============================================================================================
	private Clock clock = new Clock();
	private InputEvents events = new InputEvents();
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
		Log.get("default").disable(Level.DEBUG);

		port.create();
		port.assign(events);
		scene.assign(port);
		gui.assign(port);
		gui.assign(events);
		tasks.assign(this);
		tasks.assign(port);
		tasks.assign(events);

		setupScene();
		setupGUI();
		
		clock.addFPS(60, this::render);
		clock.addFPS(120, this::update);

	}
	//=============================================================================================

	//=============================================================================================
	private void setupScene() {

		for (int i=0; i<7; i++) {
			for (int j=0; j<1; j++) {
				for (int k=0; k<7; k++) {
					Node n = new Node();
					n.flag(age.scene.Flag.BOX);
					Matrix4f m = new Matrix4f();
					m.setIdentity();
					m.set(new Vector3f((i-3) * 2.5f, (j) * 2.5f, (k-3) * 2.5f));
					n.component(NodeFlag.TRANSFORM, m);
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
		camNode.component(NodeFlag.TRANSFORM, camTransform);
		camNode.component(NodeFlag.CAMERA, camData);
		scene.root().attach(camNode);	
		scene.camera(camNode);
		
	}
	//=============================================================================================
	
	//=============================================================================================
	private void setupGUI() {

		Widget root = gui.root();

		String text = Util.readTextFile("assets/sample.txt");

		windowFrame = new Widget();
		windowFrame.position(root.position());
		windowFrame.dimension(root.dimension());
		windowFrame.dock(0, 1, 0, 1);
		root.add(windowFrame);
		
		for (int i=0; i<5; i++) {
			
			Window window = new Window();
			window.position(50 + 15*i, 50 + 35*i);
			windowFrame.add(window);
			Widget page = window.getPage();
			Multiline textTest = new Multiline();
			textTest.assign(tasks);
			textTest.dimension(
				page.width(),
				page.height());
			textTest.text(text);
			textTest.dock(0, 1, 0, 1);
			page.add(textTest);
			
		}

		sysMenuFrame = new Widget(Flag.CANVAS, Flag.HIDDEN);
		sysMenuFrame.position(0, 30);
		sysMenuFrame.dimension(30, 80);
		root.add(sysMenuFrame);
		
		Widget fsButton = new Widget(Flag.BUTTON);
		fsButton.position(5, 5);
		fsButton.dimension(20, 20);
		fsButton.image("fullscreen");
		fsButton.command("fullscreen");
		sysMenuFrame.add(fsButton);

		Widget dtButton = new Widget(Flag.BUTTON);
		dtButton.position(5, 30);
		dtButton.dimension(20, 20);
		dtButton.image("desk");
		dtButton.command("desk");
		sysMenuFrame.add(dtButton);
		
		Widget sdButtton = new Widget(Flag.BUTTON);
		sdButtton.position(5, 55);
		sdButtton.dimension(20, 20);
		sdButtton.image("shutdown");
		sdButtton.command("shutdown");
		sysMenuFrame.add(sdButtton);

		Widget sysButton = new Widget(Flag.BUTTON);
		sysButton.position(5, 5);
		sysButton.dimension(20, 20);
		sysButton.image("plus");
		sysButton.command("sysmenu");
		root.add(sysButton);

		tasks.assign("sysmenu", this::toggleSysmenu);
		tasks.assign("desk", this::toggleDesktop);
		
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
	private void loop() {
		running = true;
		Log.debug("Start Client Loop");
		port.visible(true);
		while (running) {
			clock.update();
			Thread.yield();
		}
		port.visible(false);
	}
	//=============================================================================================

	//=============================================================================================
	public void shutdown() {
		running = false;
	}
	//=============================================================================================
	
	//=============================================================================================
	private void render(int count, long nanoperiod, float dT) {
		port.render();
	}
	//=============================================================================================

	//=============================================================================================
	private void update(int count, long nanoperiod, float dT) {
		events.update();
		tasks.update();
	}
	//=============================================================================================
	
	//=============================================================================================
	public static void main(String[] args) {
		Log.info("Client Main Start");
		Client client = new Client();
		client.run();
		Log.info("Client Main Stop");
	}
	//=============================================================================================
	
}
//*************************************************************************************************
