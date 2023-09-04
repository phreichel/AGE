//*************************************************************************************************
package ode.client;
//*************************************************************************************************

import ode.gui.GUI;
import ode.gui.Widget;
import ode.msg.Msg;
import ode.msg.Msg.ID;
import ode.msg.MsgBox;
import ode.npa.Platform;

//*************************************************************************************************
public class Client {

	//=============================================================================================
	private final MsgBox msgbox;
	private final Platform platform;
	private final GUI gui;
	//=============================================================================================

	//=============================================================================================
	private boolean terminate = false;
	//=============================================================================================

	//=============================================================================================
	private void handleEvent(Msg msg) {
		switch (msg.id()) {
		case TERMINATE:
			terminate = true;
			break;
		default:
			break;
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	public Client() {
		msgbox = new MsgBox();
		platform = new Platform();
		gui = new GUI();
		platform.assign(msgbox);
		platform.assign(gui);
		msgbox.subscribe(ID.TERMINATE, this::handleEvent);
		msgbox.subscribe(ID.KEY_PRESSED, gui::handleEvent);
		msgbox.subscribe(ID.KEY_RELEASED, gui::handleEvent);
		msgbox.subscribe(ID.KEY_TYPED, gui::handleEvent);
		msgbox.subscribe(ID.POINTER_MOVED, gui::handleEvent);
		msgbox.subscribe(ID.POINTER_PRESSED, gui::handleEvent);
		msgbox.subscribe(ID.POINTER_RELEASED, gui::handleEvent);
		msgbox.subscribe(ID.POINTER_CLICKED, gui::handleEvent);
		msgbox.subscribe(ID.POINTER_WHEEL, gui::handleEvent);
	}
	//=============================================================================================

	//=============================================================================================
	public void configure(String[] args) {
		configurePlatform();
		configureWidgets();
	}
	//=============================================================================================

	//=============================================================================================
	private void configurePlatform() {
		platform.title("ODE Client Window");
		platform.size(800, 600);
		platform.maximized(true);
	}
	//=============================================================================================
	
	//=============================================================================================
	private void configureWidgets() {
		Widget root = gui.newGroup().widget();
		root.position(50, 10);

		Widget root2 = gui.newGroup().widget();
		root2.position(5, 5);
		root2.dimension(250, 50);
		Widget btnQuit = gui.newButton("Quit").widget();
		btnQuit.position(5, 5);
		gui.root(root);
		root.attach(root2);
		root2.attach(btnQuit);
	}
	//=============================================================================================

	//=============================================================================================
	public void run() {
		platform.displayed(true);
		terminate = false;
		while (!terminate) {
			msgbox.update(1000000000L / 120L);
			platform.render();
			Thread.yield();
		}
		terminate = false;
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
