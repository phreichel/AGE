//*************************************************************************************************
package ode.client;
//*************************************************************************************************

import ode.event.Events;
import ode.gui.GUI;
import ode.gui.Widget;
import ode.platform.Platform;
import ode.schedule.Scheduler;

//*************************************************************************************************
public class Client {

	//=============================================================================================
	private final Events events = new Events();
	private final GUI gui = new GUI();
	private final Platform platform = new Platform(events, gui);
	private final Scheduler scheduler = new Scheduler();
	//=============================================================================================

	//=============================================================================================
	private void updateRenderRelevantThings(long count, long period) {
		gui.update();
		platform.update();
	}
	//=============================================================================================
	
	//=============================================================================================
	public void configure(String[] args) {
		scheduler.add(1000000000L / 60L, this::updateRenderRelevantThings);
		Widget box = gui.createBox();
		gui.getRoot().attach(box);
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
