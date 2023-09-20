//*************************************************************************************************
package age.gui;
//*************************************************************************************************

import age.event.Events;
import age.port.Port;

//*************************************************************************************************
//TODO: add javadoc comments
public class Widgets {

	//=============================================================================================
	private Widget root = new Widget();
	//=============================================================================================

	//=============================================================================================
	private EventHandling handling = new EventHandling(this);
	private Rendering rendering = new Rendering(this);
	//=============================================================================================
	
	//=============================================================================================
	public Widgets() {
		root.dimension(1024, 768);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void assign(Port port) {
		port.add(rendering);
	}
	//=============================================================================================

	//=============================================================================================
	public void assign(Events events) {
		handling.assign(events);
	}
	//=============================================================================================
	
	//=============================================================================================
	public Widget root() {
		return root;
	}
	//=============================================================================================
	
}
//*************************************************************************************************
