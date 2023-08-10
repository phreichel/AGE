//*************************************************************************************************
package ode.client;
//*************************************************************************************************

import ode.gui.GUI;
import ode.gui.VerticalLayout;
import ode.gui.Widget;
import ode.platform.Platform;
import ode.schedule.Scheduler;

//*************************************************************************************************
public class Client {

	//=============================================================================================
	private final GUI gui = new GUI();
	private final Platform platform = new Platform();
	private final Scheduler scheduler = new Scheduler();
	//=============================================================================================
	
	//=============================================================================================
	public void configure(String[] args) {
		configureGUI(gui);
		platform.register(gui);
		scheduler.add(1000000000L / 60L, (n,p) -> platform.update());
	}
	//=============================================================================================

	//=============================================================================================
	private void configureGUI(GUI gui) {		
		Widget w = new Widget();
		w.setLocation(50, 50);
		w.setSize(200, 50);
		w.setLayout(new VerticalLayout());
		gui.attachChild(w);
		for (int i=0; i<10; i++) {
			Widget c = new Widget();
			c.setSize(10, 30);
			w.attachChild(c);
		}
		w.applyLayout();
	}
	//=============================================================================================
	
	//=============================================================================================
	public void run() {
		platform.init();
		scheduler.init();
		while (true) {
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
