//*************************************************************************************************
package age.gui;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.vecmath.Vector2f;
import age.event.Button;
import age.event.Event;
import age.event.Events;
import age.event.Type;

//*************************************************************************************************
class Handling {

	//=============================================================================================
	private Events events;
	private final Widgets widgets;
	//=============================================================================================

	//=============================================================================================
	private Widget hovered = null;
	private Widget dragged = null;
	private String action = null;
	private final Vector2f ref = new Vector2f();
	private final Vector2f tmp = new Vector2f();
	//=============================================================================================
	
	//=============================================================================================
	public Handling(Widgets widgets) {
		this.widgets = widgets;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void assign(Events events) {
		this.events = events;
		events.assign(Type.KEY_PRESSED, this::handleKeyboard);
		events.assign(Type.KEY_RELEASED, this::handleKeyboard);
		events.assign(Type.KEY_TYPED, this::handleKeyboard);
		events.assign(Type.POINTER_PRESSED, this::handlePointer);
		events.assign(Type.POINTER_RELEASED, this::handlePointer);
		events.assign(Type.POINTER_CLICKED, this::handlePointer);
		events.assign(Type.POINTER_MOVED, this::handlePointer);
		events.assign(Type.SURFACE_RESIZED, this::handleSurface);
	}
	//=============================================================================================

	//=============================================================================================
	public void handleKeyboard(Event e) {
	}
	//=============================================================================================

	//=============================================================================================
	public void handlePointer(Event e) {
		
		tmp.set(e.position());

		// hover
		if (hovered != null) hovered.clear(Flag.HOVERED);
		hovered = hovered(tmp, widgets.root());
		if (hovered != null) hovered.flag(Flag.HOVERED);

		pressedFrameToFront(e);
		buttonClickAction(e);
		
		// frame actions
		startFrameSizeAction(e);
		startFrameDragAction(e);
		updateFrameAction(e);
		stopFrameAction(e);
		
	}
	//=============================================================================================

	//=============================================================================================
	private void pressedFrameToFront(Event e) {
		Widget front = hovered;
		if (e.type().equals(Type.POINTER_PRESSED)) {
			while (front != null) {
				if (front.match(Flag.FRAME)) {
					front.toFront();
				}
				front = front.parent();
			}
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void buttonClickAction(Event e) {
		if (
			hovered != null &&
			hovered.match(Flag.BUTTON) &&
			hovered.command() != null &&
			e.type().equals(Type.POINTER_RELEASED) &&
			e.button().equals(Button.BTN1)
		) {
			events.postTaskCommand(hovered.command());
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	private void startFrameSizeAction(Event e) {
		if (
			dragged == null &&
			hovered != null &&
			hovered.match(Flag.BUTTON) &&
			e.type().equals(Type.POINTER_PRESSED) &&
			e.button().equals(Button.BTN1) &&
			hovered.image().equals("size")
		) {
			updateActionState(e, "size");
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	private void startFrameDragAction(Event e) {
		if (
			dragged == null &&
			hovered != null &&
			hovered.match(Flag.TITLE) &&
			e.type().equals(Type.POINTER_PRESSED) &&
			e.button().equals(Button.BTN1)
		) {
			updateActionState(e, "move");
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void updateActionState(Event e, String action) {
		Widget frame = hovered;
		while (frame != null) {
			if (frame.match(Flag.FRAME)) {
				break;
			}
			frame = frame.parent();
		}
		if (frame != null) {
			dragged = frame;
			ref.set(e.position());
			this.action = action;
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	private void updateFrameAction(Event e) {
		if (
			dragged != null &&
			e.type().equals(Type.POINTER_MOVED)
		) {
			ref.sub(e.position(), ref);
			if (action.equals("move")) {
				dragged.positionAdd(ref);
			}
			else if (action.equals("size")) {
				dragged.positionAdd(0, ref.y);
				dragged.dimensionAdd(ref.x, -ref.y);
			}
			ref.set(e.position());
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	private void stopFrameAction(Event e) {
		if (
			dragged != null &&
			e.type().equals(Type.POINTER_RELEASED)
		) {
			dragged = null;
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	private Widget hovered(Vector2f pos, Widget widget) {
		Widget result = null;
		if (!widget.match(Flag.HIDDEN)) {
			pos.sub(widget.position());
			Vector2f dim = widget.dimension();
			if (
				(pos.x >= 0f) &&
				(pos.y >= 0f) &&
				(pos.x <= dim.x) &&
				(pos.y <= dim.y)
			) {
				List<Widget> rev = new ArrayList<>(widget.children());
				Collections.reverse(rev);
				for (Widget child : rev) {
					result = hovered(pos, child);
					if (result != null) break;
				}
				if (result == null) {
					result = widget;
				}
			}
			pos.add(widget.position());
		}
		return result;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void handleSurface(Event e) {
		widgets
			.root()
			.dimension(e.dimension());
	}
	//=============================================================================================
	
}
//*************************************************************************************************
