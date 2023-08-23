//*************************************************************************************************
package ode.client;
//*************************************************************************************************

import ode.event.Events;
import ode.gui.GUI;
import ode.gui.Widget;
import ode.model.Model;
import ode.platform.Platform;
import ode.schedule.Scheduler;

//*************************************************************************************************
public class Client {

	//=============================================================================================
	private final Events events = new Events();
	private final GUI gui = new GUI(events);
	private final Model model = new Model(events);
	private final Platform platform = new Platform(events, model, gui);
	private final Scheduler scheduler = new Scheduler();
	//=============================================================================================

	//=============================================================================================
	private boolean terminate = false;
	//=============================================================================================
	
	//=============================================================================================
	public void configure(String[] args) {
		
		Widget b1 = gui.createButton("START", (w) -> System.out.println(w.uuid));
		Widget b2 = gui.createButton("QUIT", (w) -> terminate = true);
		Widget buttons = gui.createBox();
		buttons.attach(b1);
		buttons.attach(b2);
		
		Widget scores = gui.createBox();
		for (int i=0; i<10; i++) {
			Widget score = gui.createLabel("???");
			scores.attach(score);
		}
		
		Widget frame = gui.createBox();
		frame.attach(buttons);
		frame.attach(scores);
		frame.setPosition(30, 30);
		
		scheduler.add(1000000000L / 100L, (n, p) -> model.update((float) (n*p) / 1000000000L ));
		scheduler.add(1000000000L / 30L, (n, p) -> gui.update( (float) (n*p) / 1000000000L ));
		scheduler.add(1000000000L / 30L, (n, p) -> platform.update());

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
