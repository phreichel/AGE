//*************************************************************************************************
package age;
//*************************************************************************************************

import age.port.Port;
import age.port.jogl.JOGLPort;
import age.task.Tasks;
import age.clock.Clock;
import age.event.Events;
import age.gui.Flag;
import age.gui.MultilineWidget;
import age.gui.Widget;
import age.gui.Widgets;
import age.gui.Window;
import age.log.Level;
import age.log.Log;

//*************************************************************************************************
public class Client {

	//=============================================================================================
	private Clock clock = new Clock();
	private Events events = new Events();
	private Widgets widgets = new Widgets();
	private Tasks tasks = new Tasks();
	private Port port = new JOGLPort();
	//=============================================================================================

	//=============================================================================================
	private boolean running = false;  
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
		widgets.assign(port);
		widgets.assign(events);
		tasks.assign(this);
		tasks.assign(port);
		tasks.assign(events);

		setupGUI();
		
		clock.addFPS(60, this::render);
		clock.addFPS(120, this::update);

	}
	//=============================================================================================

	//=============================================================================================
	private Widget sysMenuFrame;
	//=============================================================================================
	
	//=============================================================================================
	private void setupGUI() {

		Widget root = widgets.root();

		String text = Util.readFile("assets/sample.txt");

		Widget windowFrame = new Widget();
		windowFrame.position(root.position());
		windowFrame.dimension(root.dimension());
		windowFrame.dock(0, 1, 0, 1);

		root.add(windowFrame);
		for (int i=0; i<1; i++) {
			
			Window window = new Window();
			window.position(50 + 15*i, 50 + 35*i);
			windowFrame.add(window);
			Widget page = window.getPage();
			Widget textTest = new MultilineWidget();
			textTest.dimension(
				page.width(),
				page.height());
			textTest.text(text);
			textTest.dock(0, 1, 0, 1);
			page.add(textTest);
			
		}

		sysMenuFrame = new Widget(Flag.CANVAS, Flag.HIDDEN);
		sysMenuFrame.position(0, 30);
		sysMenuFrame.dimension(30, 55);
		root.add(sysMenuFrame);
		
		Widget fsButton = new Widget(Flag.BUTTON);
		fsButton.position(5, 5);
		fsButton.dimension(20, 20);
		fsButton.image("fullscreen");
		fsButton.command("fullscreen");
		sysMenuFrame.add(fsButton);
		
		Widget sdButtton = new Widget(Flag.BUTTON);
		sdButtton.position(5, 30);
		sdButtton.dimension(20, 20);
		sdButtton.image("shutdown");
		sdButtton.command("shutdown");
		sysMenuFrame.add(sdButtton);

		tasks.assign("sysmenu", this::toggleSysmenu);

		Widget sysButton = new Widget(Flag.BUTTON);
		sysButton.position(5, 5);
		sysButton.dimension(20, 20);
		sysButton.image("plus");
		sysButton.command("sysmenu");
		root.add(sysButton);
		
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
