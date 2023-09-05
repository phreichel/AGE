//*************************************************************************************************
package ode.client;
//*************************************************************************************************

import ode.clock.Clock;
import ode.gui.GUI;
import ode.gui.Widget;
import ode.msg.Msg;
import ode.msg.Msg.ID;
import ode.msg.MsgBox;
import ode.npa.Platform;

//*************************************************************************************************
public class Client {

	//=============================================================================================
	private final Clock clock;
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
		clock = new Clock();
		msgbox = new MsgBox();
		platform = new Platform();
		gui = new GUI();
		gui.assign(msgbox);
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
		clock.addFPS(120, this::updateTask);
		clock.addFPS(30, this::renderTask);
	}
	//=============================================================================================

	//=============================================================================================
	private void updateTask(int count, long period, float dT) {
		msgbox.update(period);
	}
	//=============================================================================================
	
	//=============================================================================================
	private void renderTask(int count, long period, float dT) {
		platform.render();
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
		terminate = false;
		platform.displayed(true);
		clock.init();
		while (!terminate) {
			clock.update();
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
