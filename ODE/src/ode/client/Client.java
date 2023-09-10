//*************************************************************************************************
package ode.client;
//*************************************************************************************************

import ode.asset.Assets;
import ode.clock.Clock;
import ode.gui.Actions;
import ode.gui.Factory;
import ode.gui.GUI;
import ode.log.Logger;
import ode.msg.Msg;
import ode.msg.Msg.ID;
import ode.msg.MsgBox;
import ode.npa.Platform;

//*************************************************************************************************
public class Client {

	//=============================================================================================
	private final Assets   assets;
	private final Clock    clock;
	private final MsgBox   msgbox;
	private final Platform platform;
	private final GUI      gui;
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
		gui = new GUI();
		gui.assign(msgbox);
		platform = new Platform();
		platform.assign(msgbox);
		platform.assign(assets);
		platform.assign(gui);
		gui.assign(platform);
		msgbox.subscribe(ID.TERMINATE, this::handleEvent);
		clock.addFPS(120, this::updateTask);
		clock.addFPS(30, this::renderTask);
	}
	//=============================================================================================

	//=============================================================================================
	private void updateTask(int count, long period, float dT) {
		msgbox.update(period);
		gui.update();
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
		Factory f = gui.factory(); 
		f.newVerticalBox()
			.root(gui)
			.position(10, 10)
			.child(
				f.newButton("Toggle Full Screen")
					.dimension(200, 20)
					.action("toggle fullscreen")
					.widget(),
				f.newButton("Quit")
					.action(Actions.QUIT)
					.widget(),
				f.newTextbox()
					.widget()
			);
		/*
		for (int i=0; i<10; i++) {
			f.newWindow("Sample Window " + (i+1), 800, 600)
				.position(50 + 20*i, 50 + 20*i)
				.root(gui)
				.widget()
				.children()
				.get(3)
				.attach(f
					.newButton("TEST")
					.position(30, 40)
					.widget()
				);
		}
		*/
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
