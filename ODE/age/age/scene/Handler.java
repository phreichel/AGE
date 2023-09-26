//*************************************************************************************************
package age.scene;
//*************************************************************************************************

import age.input.Event;
import age.input.Events;
import age.input.InputType;

//*************************************************************************************************
public class Handler {

	//=============================================================================================
	private Events events;
	private Scene  scene;
	//=============================================================================================

	//=============================================================================================
	public Handler(Scene scene) {
		this.scene = scene;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void assign(Events events) {
		this.events = events;
		events.assign(InputType.KEY_TYPED, this::handleEvent);
		events.assign(InputType.KEY_PRESSED, this::handleEvent);
		events.assign(InputType.KEY_RELEASED, this::handleEvent);
		events.assign(InputType.POINTER_MOVED, this::handleEvent);
		events.assign(InputType.POINTER_PRESSED, this::handleEvent);
		events.assign(InputType.POINTER_RELEASED, this::handleEvent);
		events.assign(InputType.POINTER_CLICKED, this::handleEvent);
		events.assign(InputType.POINTER_WHEEL, this::handleEvent);
	}
	//=============================================================================================

	//=============================================================================================
	private void handleEvent(Event e) {
		
	}
	//=============================================================================================
	
}
//*************************************************************************************************
