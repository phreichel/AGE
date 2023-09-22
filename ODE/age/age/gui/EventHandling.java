//*************************************************************************************************
package age.gui;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.vecmath.Vector2f;
import age.input.Button;
import age.input.Event;
import age.input.Events;
import age.input.InputType;

//*************************************************************************************************
class EventHandling {

	//=============================================================================================
	private Events events;
	private final GUI gui;
	private Widget hovered = null;
	private Widget dragged = null;
	private String action = null;
	private final Vector2f ref = new Vector2f();
	private final Vector2f tmp = new Vector2f();
	//=============================================================================================
	
	//=============================================================================================
	public EventHandling(GUI gui) {
		this.gui = gui;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void assign(Events events) {
		this.events = events;
		events.assign(InputType.KEY_TYPED, this::handleKeyboard);
		events.assign(InputType.KEY_PRESSED, this::handleKeyboard);
		events.assign(InputType.KEY_RELEASED, this::handleKeyboard);
		events.assign(InputType.POINTER_MOVED, this::handlePointer);
		events.assign(InputType.POINTER_PRESSED, this::handlePointer);
		events.assign(InputType.POINTER_RELEASED, this::handlePointer);
		events.assign(InputType.POINTER_CLICKED, this::handlePointer);
		events.assign(InputType.SURFACE_RESIZED, this::handleSurface);
	}
	//=============================================================================================

	//=============================================================================================
	public void handleKeyboard(Event e) {
	}
	//=============================================================================================

	//=============================================================================================
	public void handlePointer(Event e) {

		tmp.set(e.position());
		Widget widget = traverse(gui.root(), tmp);
		handlePointer(widget, e, tmp);
		
		tmp.set(e.position());
		
		// hover
		if (hovered != null) hovered.clear(Flag.HOVERED);
		hovered = hovered(tmp, gui.root());
		if (hovered != null) hovered.flag(Flag.HOVERED);

		pressedFrameToFront(e);
		buttonClickAction(e);
		
		// frame actions
		startFrameSizeAction(e);
		startFrameDragAction(e);
		startScrollHandleAction(e);
		updateDragAction(e);
		stopDragAction(e);
		
	}
	//=============================================================================================

	//=============================================================================================
	private void handlePointer(
		Widget widget,
		Event e,
		Vector2f localPos) {
	}
	//=============================================================================================
	
	//=============================================================================================
	private Widget traverse(Widget widget, Vector2f pos) {
		if (!widget.match(Flag.HIDDEN)) {
			pos.sub(widget.position());
			if (contains(widget.dimension(), pos)) {
				List<Widget> list = widget.children();
				for (int i=list.size()-1; i>= 0; i--) {
					Widget child = list.get(i);
					Widget result = traverse(child, pos);
					if (result != null) {
						return result;
					}
				}
				return widget;
			}
			pos.add(widget.position());
		}
		return null;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean contains(Vector2f dimension, Vector2f pos) {
		return
			(pos.x >= 0f) &&
			(pos.x <= dimension.x) &&
			(pos.y >= 0f) &&
			(pos.y <= dimension.y);
	}
	//=============================================================================================
	
	//=============================================================================================
	private void pressedFrameToFront(Event e) {
		Widget front = hovered;
		if (e.type().equals(InputType.POINTER_PRESSED)) {
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
			e.type().equals(InputType.POINTER_RELEASED) &&
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
			e.type().equals(InputType.POINTER_PRESSED) &&
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
			e.type().equals(InputType.POINTER_PRESSED) &&
			e.button().equals(Button.BTN1)
		) {
			updateActionState(e, "move");
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void startScrollHandleAction(Event e) {
		if (
			dragged == null &&
			hovered != null &&
			hovered.match(Flag.HANDLE) &&
			e.type().equals(InputType.POINTER_PRESSED) &&
			e.button().equals(Button.BTN1)
		) {
			updateActionState(e, "handle");
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	private void updateActionState(Event e, String action) {
		Widget frame = hovered;
		Flag flag = Flag.FRAME;
		if (action.equals("handle")) {
			flag = Flag.HANDLE;
		}
		while (frame != null) {
			if (frame.match(flag)) {
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
	private void updateDragAction(Event e) {
		if (
			dragged != null &&
			e.type().equals(InputType.POINTER_MOVED)
		) {
			ref.sub(e.position(), ref);
			if (action.equals("move")) {
				dragged.positionAdd(ref);
			} else if (action.equals("handle")) {
				// TODO: THIS IS A HACK!!! and has to be refactored for framework quality.
				Multiline ml = (Multiline) dragged.parent().parent().parent();
				ml.rescale(ref.y);
			} else if (action.equals("size")) {
				dragged.dimensionAdd(ref.x, ref.y);
			}
			ref.set(e.position());
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	private void stopDragAction(Event e) {
		if (
			dragged != null &&
			e.type().equals(InputType.POINTER_RELEASED)
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
		gui
			.root()
			.dimension(e.dimension());
	}
	//=============================================================================================
	
}
//*************************************************************************************************
