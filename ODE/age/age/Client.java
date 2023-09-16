//*************************************************************************************************
package age;
//*************************************************************************************************

import age.port.Port;
import age.port.jogl.JOGLPort;
import age.task.Tasks;
import age.clock.Clock;
import age.event.Events;
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
	public void run() {
	
		Log.get("default").disable(Level.DEBUG);

		port.create();
		port.assign(events);
		widgets.assign(port);
		widgets.assign(events);

		for (int i=0; i<5; i++) {
			Window window = new Window();
			window.position(50 + 15*i, 50 + 35*i);
			widgets.root().add(window);
		}
		
		clock.addFPS(60, this::render);
		clock.addFPS(120, this::update);
		
		Log.debug("Start Client Loop");

		port.fullscreen(true);
		port.visible(true);
		while (true) {
			clock.update();
			Thread.yield();
		}

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
		tasks.update(dT);
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
