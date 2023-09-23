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
import age.util.X;

//*************************************************************************************************
class Handler {

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
	public Handler(GUI gui) {
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
		
		// frame actions
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
		if (activeWidget != null) {
			widget = activeWidget;
		}
		for (Flag flag : widget.flags()) {
			switch (flag) {
				case  CONTEXT_MENU -> handleContextMenu(widget, e, localPos);
				case  DRAG_HANDLE -> handleDrag(widget, e, localPos);
				case  RESIZE_HANDLE -> handleResize(widget, e, localPos);
				case  CLOSE_HANDLE -> handleClose(widget, e, localPos);
				case  SCROLL_UP_HANDLE -> handleScrollUp(widget, e, localPos);
				case  SCROLL_DOWN_HANDLE -> handleScrollDown(widget, e, localPos);
				case  SCROLL_BAR_HANDLE -> handleScrollBar(widget, e, localPos);
				case  COMMAND_HANDLE -> handleCommand(widget, e, localPos);
				default -> {}
			}
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void handleContextMenu(
		Widget widget,
		Event e,
		Vector2f localPos) {
		throw new X("not yet implemented");
	}
	//=============================================================================================

	//=============================================================================================
	private Widget activeWidget = null;
	private final Vector2f lastPointerPosition = new Vector2f();
	//=============================================================================================
	
	//=============================================================================================
	// TODO: Drag Logic is flawed
	private void handleDrag(
		Widget widget,
		Event e,
		Vector2f localPos) {
		if (
			e.type().equals(InputType.POINTER_PRESSED) &&
			e.button().equals(Button.BTN1) &&
			activeWidget == null
		) {
			activeWidget = widget;
			lastPointerPosition.set(e.position());
		} else if (
			e.type().equals(InputType.POINTER_RELEASED) &&
			e.button().equals(Button.BTN1) &&
			activeWidget != null
		) {
			lastPointerPosition.sub(e.position());
			lastPointerPosition.negate();
			Widget dragged = activeWidget.component(WidgetComponent.DRAGGED_WIDGET, Widget.class);
			dragged.positionAdd(lastPointerPosition);
			lastPointerPosition.set(0, 0);
			activeWidget = null;
		} else if (
			e.type().equals(InputType.POINTER_MOVED) &&
			activeWidget != null
		) {
			lastPointerPosition.sub(e.position());
			lastPointerPosition.negate();
			Widget dragged = activeWidget.component(WidgetComponent.DRAGGED_WIDGET, Widget.class);
			dragged.positionAdd(lastPointerPosition);
			lastPointerPosition.set(e.position());
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	private void handleResize(
		Widget widget,
		Event e,
		Vector2f localPos) {
		if (
			e.type().equals(InputType.POINTER_PRESSED) &&
			e.button().equals(Button.BTN1) &&
			activeWidget == null
		) {
			activeWidget = widget;
			lastPointerPosition.set(e.position());
		} else if (
			e.type().equals(InputType.POINTER_RELEASED) &&
			e.button().equals(Button.BTN1) &&
			activeWidget != null
		) {
			lastPointerPosition.sub(e.position());
			lastPointerPosition.negate();
			Widget resized = activeWidget.component(WidgetComponent.RESIZED_WIDGET, Widget.class);
			resized.dimensionAdd(lastPointerPosition);
			lastPointerPosition.set(0, 0);
			activeWidget = null;
		} else if (
			e.type().equals(InputType.POINTER_MOVED) &&
			activeWidget != null
		) {
			lastPointerPosition.sub(e.position());
			lastPointerPosition.negate();
			Widget resized = activeWidget.component(WidgetComponent.RESIZED_WIDGET, Widget.class);
			resized.dimensionAdd(lastPointerPosition);
			lastPointerPosition.set(e.position());
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void handleClose(
		Widget widget,
		Event e,
		Vector2f localPos) {
		if (
			e.type().equals(InputType.POINTER_PRESSED) &&
			e.button().equals(Button.BTN1) &&
			activeWidget == null) {
			activeWidget = widget;
			lastPointerPosition.set(e.position());
		} else if (
			e.type().equals(InputType.POINTER_RELEASED) &&
			e.button().equals(Button.BTN1) &&
			activeWidget != null)
		{
			if (contains(widget.dimension(), localPos)) {
				Widget closed = widget.component(WidgetComponent.CLOSED_WIDGET, Widget.class);
				closed.flag(Flag.HIDDEN);
			}
			activeWidget = null;
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	private void handleScrollUp(
		Widget widget,
		Event e,
		Vector2f localPos) {
	}
	//=============================================================================================

	//=============================================================================================
	private void handleScrollDown(
		Widget widget,
		Event e,
		Vector2f localPos) {
	}
	//=============================================================================================

	//=============================================================================================
	private void handleScrollBar(
		Widget widget,
		Event e,
		Vector2f localPos) {
	}
	//=============================================================================================

	//=============================================================================================
	private void handleCommand(
		Widget widget,
		Event e,
		Vector2f localPos) {
		if (
			e.type().equals(InputType.POINTER_PRESSED) &&
			e.button().equals(Button.BTN1) &&
			activeWidget == null) {
			activeWidget = widget;
		} else if (
			e.type().equals(InputType.POINTER_RELEASED) &&
			e.button().equals(Button.BTN1) &&
			activeWidget != null)
		{
			if (contains(widget.dimension(), localPos)) {
				String command = widget.component(WidgetComponent.COMMAND, String.class);
				events.postTaskCommand(command);
			}
			activeWidget = null;
		}
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
