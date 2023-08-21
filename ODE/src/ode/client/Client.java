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
	private final GUI gui = new GUI(events);
	private final Platform platform = new Platform(events, gui);
	private final Scheduler scheduler = new Scheduler();
	//=============================================================================================

	//=============================================================================================
	public void configure(String[] args) {
		scheduler.add(1000000000L / 60L, (n, p) -> platform.update());
		scheduler.add(1000000000L / 120L, (n, p) -> gui.update());
		Widget b1 = gui.createButton("Button 1");
		Widget b2 = gui.createButton("Button 2");
		b2.setPosition(10, 40);
		Widget frame = gui.createBox();
		frame.attach(b1);
		frame.attach(b2);
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
