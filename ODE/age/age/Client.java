//*************************************************************************************************
package age;
//*************************************************************************************************

import age.port.Port;
import age.port.jogl.JOGLPort;
import age.task.Tasks;
import age.clock.Clock;
import age.event.Events;
import age.log.Level;
import age.log.Logger;

//*************************************************************************************************
public class Client {

	//=============================================================================================
	private Clock clock = new Clock();
	private Events events = new Events();
	private Tasks tasks = new Tasks();
	private Port port = new JOGLPort();
	//=============================================================================================

	//=============================================================================================
	public void run() {
		port.create();
		port.assign(events);
		clock.addFPS(60, this::render);
		clock.addFPS(120, this::update);
		port.visible(true);
		Logger.log(Level.DEBUG, "Start Client Loop");
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
		Logger.log(Level.INFO, "Client Main Start");
		Client client = new Client();
		client.run();
		Logger.log(Level.INFO, "Client Main Stop");
	}
	//=============================================================================================
	
}
//*************************************************************************************************
