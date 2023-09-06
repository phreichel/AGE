//*************************************************************************************************
package ode.client;
//*************************************************************************************************

import ode.asset.Assets;
import ode.clock.Clock;
import ode.gui.GUI;
import ode.gui.Widget;
import ode.log.Logger;
import ode.msg.Msg;
import ode.msg.Msg.ID;
import ode.msg.MsgBox;
import ode.npa.Platform;

//*************************************************************************************************
public class Client {

	//=============================================================================================
	private final Assets assets;
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
		assets = new Assets();
		clock = new Clock();
		msgbox = new MsgBox();
		platform = new Platform();
		gui = new GUI();
		gui.assign(msgbox);
		platform.assign(msgbox);
		platform.assign(assets);
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
		Logger.configure("assets/logging.cfg");
		assets.loadTexts("assets/ode.cfg");
		assets.loadColors("assets/colors.cfg");
		assets.loadFonts("assets/fonts.cfg");
		assets.loadTextures("assets/textures.cfg");
		configurePlatform();
		configureWidgets();
	}
	//=============================================================================================

	//=============================================================================================
	private void configurePlatform() {
		String title = assets.resolveText("ode.platform.title");
		int width = assets.getInt("ode.platform.width");
		int height = assets.getInt("ode.platform.height");
		boolean maximized = assets.getBoolean("ode.platform.maximized");
		boolean fullscreen = assets.getBoolean("ode.platform.fullscreen");
		platform.title(title);
		platform.size(width, height);
		platform.maximized(maximized);
		platform.fullscreen(fullscreen);
	}
	//=============================================================================================
	
	//=============================================================================================
	private void configureWidgets() {
		Widget root = gui.newGroup().widget();
		root.position(50, 10);
		root.dimension(900, 800);
		Widget root2 = gui.newGroup().widget();
		root2.position(150, 25);
		root2.dimension(250, 50);
		Widget btnQuit = gui.newButton("Quit").widget();
		btnQuit.position(45, 15);
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
