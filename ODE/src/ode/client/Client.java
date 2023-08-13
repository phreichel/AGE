//*************************************************************************************************
package ode.client;
//*************************************************************************************************

import ode.gui.GUI;
import ode.gui.Text;
import ode.gui.Widget;
import ode.gui.layout.VerticalLayout;
import ode.platform.Font;
import ode.platform.FontDecoration;
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
		Font font = platform.allocateFont("Terminal", 24, FontDecoration.BOLD);
		Widget w = new Widget();
		w.setLocation(50, 50);
		w.setSize(200, 50);
		w.setLayout(new VerticalLayout());
		gui.attachChild(w);
		for (int i=0; i<10; i++) {
			Text t = new Text(font);
			t.setSize(200, 24);
			t.setText("Algume alquin Xornidfsfa D5776fdg: gfg");
			w.attachChild(t);
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
