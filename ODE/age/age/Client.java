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

/**************************************************************************************************
 * The AGE Application Client main class.
 */
public class Client {

	/**********************************************************************************************
	 * The schedule clock.
	 */
	private Clock clock = new Clock();
	
	/**********************************************************************************************
	 * The event system, handling and transforming input events.
	 */
	private InputEvents events = new InputEvents();
	
	/**********************************************************************************************
	 * The 3D scene system.
	 */
	private Scene scene = new Scene();
	
	/**********************************************************************************************
	 * The widget system (the GUI)
	 */
	private GUI gui = new GUI();
	
	/**********************************************************************************************
	 * The task system. Launches named tasks, that can be posted and queued, on a specific run loop phase.
	 */
	private Tasks tasks = new Tasks();
	
	/**********************************************************************************************
	 * The port system. Provides and encapsulates the low level machine dependent parts of the Client application.
	 */
	private Port port = new JOGLPort();

	/**********************************************************************************************
	 * The run loop running indicator
	 */
	private boolean running = false;  
	
	/**********************************************************************************************
	 * Initializes and executes the Client run loop.
	 */
	public void run() {
		setup();
		loop();
	}

	/**********************************************************************************************
	 * Initializes the Client application and its parts.
	 */
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

	/**********************************************************************************************
	 * Sets up the initial 3D Scene 
	 */
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
	
	/**********************************************************************************************
	 * Special internally used GUI Frame (The single System menu Button)
	 */
	private Widget sysMenuFrame;

	/**********************************************************************************************
	 * Special internally used GUI Frame (The invisible frame that serves as canvas for all GUI windows) 
	 */
	private Widget windowFrame;
	
	/**********************************************************************************************
	 * Sets up the initial GUI elements.
	 */
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

	/**********************************************************************************************
	 * Private action method to toggle the visibility of the System Menu
	 */
	private void toggleSysmenu() {
		if (sysMenuFrame.match(Flag.HIDDEN))
			sysMenuFrame.clear(Flag.HIDDEN);
		else 
			sysMenuFrame.flag(Flag.HIDDEN);
	}

	/**********************************************************************************************
	 * Private action method to toggle the visibility of the Desktop and its contents
	 */
	private void toggleDesktop() {
		if (windowFrame.match(Flag.HIDDEN))
			windowFrame.clear(Flag.HIDDEN);
		else 
			windowFrame.flag(Flag.HIDDEN);
	}
	
	/**********************************************************************************************
	 * The Client loop that maintains the program logic
	 */
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

	/**********************************************************************************************
	 * Sets the internal Client loop run state to false, and therefore stops the client run loop
	 */
	public void shutdown() {
		running = false;
	}
	
	/**********************************************************************************************
	 * Private scheduler action to update the display contents and render the Client surface. 
	 * @param count The number of (skipped) schedule frames since last call
	 * @param nanoperiod The duration of a schedule frame in nano seconds
	 * @param dT The time elapsed since last schedule call, in Seconds.
	 */
	private void render(int count, long nanoperiod, float dT) {
		port.render();
	}

	/**********************************************************************************************
	 * Private scheduler action to update the logic of the Client application
	 * @param count The number of (skipped) schedule frames since last call
	 * @param nanoperiod The duration of a schedule frame in nano seconds
	 * @param dT The time elapsed since last schedule call, in Seconds.
	 */
	private void update(int count, long nanoperiod, float dT) {
		events.update();
		tasks.update();
	}
	
	/**********************************************************************************************
	 * The static Client main method to create a client instance and launch the application. 
	 * @param args Program arguments, unused atm.
	 */
	public static void main(String[] args) {
		Log.info("Client Main Start");
		Client client = new Client();
		client.run();
		Log.info("Client Main Stop");
	}
	
}
//*************************************************************************************************
