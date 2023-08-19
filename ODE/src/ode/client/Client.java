//*************************************************************************************************
package ode.client;
//*************************************************************************************************

import ode.event.Events;
import ode.platform.Platform;
import ode.schedule.Scheduler;

//*************************************************************************************************
public class Client {

	//=============================================================================================
	private final Events events = new Events();
	private final Platform platform = new Platform(events);
	private final Scheduler scheduler = new Scheduler();
	//=============================================================================================
	
	//=============================================================================================
	public void configure(String[] args) {
		scheduler.add(1000000000L / 60L, (n,p) -> platform.update());
	}
	//=============================================================================================

	//=============================================================================================
	public void run() {
		platform.setVisible(true);
		scheduler.init();
		while (true) {
			events.update();
			scheduler.update();
			Thread.yield();
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	public static void main(String[] args) {
		Client client = new Client();
		client.configure(args);
		client.run();
	}
	//=============================================================================================

}
//*************************************************************************************************
