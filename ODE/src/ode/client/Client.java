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
	private boolean terminate = false;
	//=============================================================================================
	
	//=============================================================================================
	public void configure(String[] args) {
		Widget b1 = gui.createButton("QUIT", (w) -> terminate = true);
		Widget b2 = gui.createButton("UUID", (w) -> System.out.println(w.uuid));
		b2.setPosition(10, 40);
		Widget frame = gui.createBox();
		frame.setBounds(100, 100, 1720, 800);
		frame.attach(b1);
		frame.attach(b2);
		scheduler.add(1000000000L / 80L, (n, p) -> gui.update( (float) (n*p) / 1000000000L ));
		scheduler.add(1000000000L / 60L, (n, p) -> platform.update());
	}
	//=============================================================================================

	//=============================================================================================
	public void run() {
		terminate = false;
		scheduler.init();
		platform.setVisible(true);
		while (!terminate) {
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
