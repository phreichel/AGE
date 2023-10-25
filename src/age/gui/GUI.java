//*************************************************************************************************
package age.gui;
//*************************************************************************************************

import age.input.Events;
import age.port.Port;

//*************************************************************************************************
public class GUI {

	//=============================================================================================
	private Widget root = new Widget();
	//=============================================================================================

	//=============================================================================================
	private final Handler  handler = new Handler(this);
	private final Layouter layouter = new Layouter();
	private final Renderer renderer = new Renderer(this);
	//=============================================================================================

	//=============================================================================================	
	private final Factory factory = new Factory(this);
	//=============================================================================================
	
	//=============================================================================================
	public GUI() {
		root.dimension(1024, 768);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void assign(Port port) {
		port.add(renderer);
	}
	//=============================================================================================

	//=============================================================================================
	public void assign(Events events) {
		handler.assign(events);
	}
	//=============================================================================================
	
	//=============================================================================================
	public Widget root() {
		return root;
	}
	//=============================================================================================

	//=============================================================================================
	public Factory factory() {
		return this.factory;
	}
	//=============================================================================================

	//=============================================================================================
	public Layouter layouter() {
		return this.layouter;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void update() {
		layouter.update();
	}
	//=============================================================================================
	
}
//*************************************************************************************************
