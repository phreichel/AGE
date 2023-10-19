/*
 * Commit: aa20ad72b29161d58217d659b23accf2664ef3cf
 * Date: 2023-09-26 23:19:19+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 * Commit: 01f6edb422e32c691886b08481b74af734d2419d
 * Date: 2023-09-25 00:22:26+02:00
 * Author: Philip Reichel
 * Comment: Added Mesh
 *
 * Commit: 03165a40e9032ea45bb2863cf809767cb6cdf6f1
 * Date: 2023-09-23 20:52:58+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 * Commit: 363b75a18db4122bb21de7cf1091c0bd6434b79b
 * Date: 2023-09-23 18:26:28+02:00
 * Author: Philip Reichel
 * Comment: Finished Switch to new Event Handling
Added Pointer Wheel Input
 *
 * Commit: 1fd12df4cfeb1c4d67818231e6df4905af8afdef
 * Date: 2023-09-23 17:22:11+02:00
 * Author: Philip Reichel
 * Comment: Almost reworked Multiline
 *
 * Commit: 0c072ef17032c66ffdd34170f7a2c79345c34eae
 * Date: 2023-09-23 10:27:57+02:00
 * Author: Philip Reichel
 * Comment: Updated GUI Handling - Halfway done
 *
 * Commit: 979912e4583b2e47ff4138caf044ae2005b4a274
 * Date: 2023-09-15 21:18:58+02:00
 * Author: Philip Reichel
 * Comment: Refactoring GUI Window Rendering
 *
 * Commit: 275f07861ea5f95dd90f184d1311a0a5a8cd510d
 * Date: 2023-09-14 17:55:15+02:00
 * Author: pre7618
 * Comment: GUI in progress
 *
 */

//*************************************************************************************************
package age.gui;
//*************************************************************************************************

import java.util.List;
import javax.vecmath.Vector2f;

import age.input.Button;
import age.input.Event;
import age.input.Events;
import age.input.InputType;
import age.util.X;

//*************************************************************************************************
public class Handler {

	//=============================================================================================
	private interface PointerPressHandle {
		public void handle(Widget widget, Event e, Vector2f localPos);
	}
	//=============================================================================================
	
	//=============================================================================================
	private Events events;
	private final GUI gui;
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
		events.assign(InputType.POINTER_WHEEL, this::handlePointer);
		events.assign(InputType.SURFACE_RESIZED, this::handleSurface);
	}
	//=============================================================================================

	//=============================================================================================
	public void handleKeyboard(Event e) {
	}
	//=============================================================================================

	//=============================================================================================
	private Widget last = null;
	//=============================================================================================
	
	//=============================================================================================
	public void handlePointer(Event e) {

		tmp.set(e.position());
		Widget widget = traverse(gui.root(), tmp);
		
		if (last != null) { last.clear(WFlag.HOVERED); }
		if (widget != null) widget.flag(WFlag.HOVERED);
		
		handlePointerFocusing(widget, e);
		handlePointer(widget, e, tmp);
		
		last = widget;
		
	}
	//=============================================================================================

	//=============================================================================================
	private void handlePointerFocusing(Widget widget, Event e) {
		if (e.type().equals(InputType.POINTER_PRESSED)) {
			Widget front = widget;
			while (front != null) {
				if (front.match(WFlag.FRAME)) {
					front.toFront();
				}
				front = front.parent();
			}
		}
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
		if (widget != null) {
			for (WFlag wFlag : widget.wFlags()) {
				switch (wFlag) {
					case  CONTEXT_MENU -> handleContextMenu(widget, e, localPos);
					case  DRAG_HANDLE -> handleDrag(widget, e, localPos);
					case  RESIZE_HANDLE -> handleResize(widget, e, localPos);
					case  CLOSE_HANDLE -> handlePointerPress(widget, e, localPos, this::handleClose);
					case  SCROLL_START -> handlePointerPress(widget, e, localPos, this::handleScrollStart);
					case  SCROLL_END -> handlePointerPress(widget, e, localPos, this::handleScrollEnd);
					case  SCROLLBAR_SLIDER -> handlePointerPress(widget, e, localPos, this::handleScrollSlider);
					case  SCROLLBAR_HANDLE -> handleScrollBar(widget, e, localPos);
					case  COMMAND_HANDLE -> handlePointerPress(widget, e, localPos, this::handleCommand);
					case  POINTER_SCROLL -> handlePointerScroll(widget, e, localPos);
					default -> {}
				}
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
			Widget dragged = activeWidget.component(WItem.DRAGGED_WIDGET, Widget.class);
			dragged.positionAdd(lastPointerPosition);
			lastPointerPosition.set(0, 0);
			activeWidget = null;
		} else if (
			e.type().equals(InputType.POINTER_MOVED) &&
			activeWidget != null
		) {
			lastPointerPosition.sub(e.position());
			lastPointerPosition.negate();
			Widget dragged = activeWidget.component(WItem.DRAGGED_WIDGET, Widget.class);
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
			Widget resized = activeWidget.component(WItem.RESIZED_WIDGET, Widget.class);
			resized.dimensionAdd(lastPointerPosition);
			lastPointerPosition.set(0, 0);
			activeWidget = null;
		} else if (
			e.type().equals(InputType.POINTER_MOVED) &&
			activeWidget != null
		) {
			lastPointerPosition.sub(e.position());
			lastPointerPosition.negate();
			Widget resized = activeWidget.component(WItem.RESIZED_WIDGET, Widget.class);
			resized.dimensionAdd(lastPointerPosition);
			lastPointerPosition.set(e.position());
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void handleScrollBar(
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

			Widget scrollbar = widget.component(WItem.SCROLL_WIDGET, Widget.class);
			
			boolean vertical = true;
			Scrollable scstate = scrollbar.component(WItem.SCROLLABLE_VERTICAL, Scrollable.class);
			if (scstate == null) {
				scstate = scrollbar.component(WItem.SCROLLABLE_HORIZONTAL, Scrollable.class);
				vertical = false;
			}

			Widget slider = scrollbar.children().get(0);
			float pageScale = (float) (scstate.page) / (float) (scstate.size + scstate.page - 1);
			float sliderRange = vertical ? slider.dimension().y : slider.dimension().x; 
			float handleRange = sliderRange * pageScale; 
			float indexRange  = scstate.size - 1;
			float indexStep = (sliderRange - handleRange) / indexRange;
			float delta = vertical ? lastPointerPosition.y : lastPointerPosition.x;
			int mark = scstate.mark + (int) Math.rint(delta / indexStep);
			scstate.mark(mark);
			
			lastPointerPosition.set(0, 0);
			activeWidget = null;
		
		} else if (
			e.type().equals(InputType.POINTER_MOVED) &&
			activeWidget != null
		) {
			lastPointerPosition.sub(e.position());
			lastPointerPosition.negate();

			Widget scrollbar = widget.component(WItem.SCROLL_WIDGET, Widget.class);
			
			boolean vertical = true;
			Scrollable scstate = scrollbar.component(WItem.SCROLLABLE_VERTICAL, Scrollable.class);
			if (scstate == null) {
				scstate = scrollbar.component(WItem.SCROLLABLE_HORIZONTAL, Scrollable.class);
				vertical = false;
			}

			Widget slider = scrollbar.children().get(0);
			float pageScale = (float) (scstate.page) / (float) (scstate.size + scstate.page - 1);
			float sliderRange = vertical ? slider.dimension().y : slider.dimension().x; 
			float handleRange = sliderRange * pageScale; 
			float indexRange  = scstate.size - 1;
			float indexStep = (sliderRange - handleRange) / indexRange;
			float delta = vertical ? lastPointerPosition.y : lastPointerPosition.x;
			int mark = scstate.mark + (int) Math.rint(delta / indexStep);
			scstate.mark(mark);

			lastPointerPosition.set(e.position());

		}
	}
	//=============================================================================================

	//=============================================================================================
	private void handlePointerScroll(
		Widget widget,
		Event e,
		Vector2f localPos) {
		Scrollable scstate = widget.component(WItem.SCROLLABLE_VERTICAL, Scrollable.class);
		float value = e.wheelY();
		int delta = (int) Math.rint(-value);
		scstate.scrollBy(delta);
	}
	//=============================================================================================
	
	//=============================================================================================
	private void handlePointerPress(
			Widget widget,
			Event e,
			Vector2f localPos,
			PointerPressHandle handle) {
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
				handle.handle(widget, e, localPos);
				activeWidget = null;
			}
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void handleClose(
		Widget widget,
		Event e,
		Vector2f localPos) {
		Widget closed = widget.component(WItem.CLOSED_WIDGET, Widget.class);
		closed.flag(WFlag.HIDDEN);
	}
	//=============================================================================================
	
	//=============================================================================================
	private void handleCommand(
		Widget widget,
		Event e,
		Vector2f localPos) {
		String command = widget.component(WItem.COMMAND, String.class);
		events.postTaskCommand(command);
	}
	//=============================================================================================
	
	//=============================================================================================
	private void handleScrollStart(
		Widget widget,
		Event e,
		Vector2f localPos) {
		Widget scrollbar = widget.component(WItem.SCROLL_WIDGET, Widget.class);
		Scrollable scstate = scrollbar.component(WItem.SCROLLABLE_VERTICAL, Scrollable.class);
		if (scstate == null) {
			scstate = scrollbar.component(WItem.SCROLLABLE_HORIZONTAL, Scrollable.class);
		}
		scstate.scrollOneToStart();
	}
	//=============================================================================================

	//=============================================================================================
	private void handleScrollEnd(
		Widget widget,
		Event e,
		Vector2f localPos) {
		Widget scrollbar = widget.component(WItem.SCROLL_WIDGET, Widget.class);
		Scrollable scstate = scrollbar.component(WItem.SCROLLABLE_VERTICAL, Scrollable.class);
		if (scstate == null) {
			scstate = scrollbar.component(WItem.SCROLLABLE_HORIZONTAL, Scrollable.class);
		}
		scstate.scrollOneToEnd();
	}
	//=============================================================================================
	
	//=============================================================================================
	private void handleScrollSlider(
		Widget widget,
		Event e,
		Vector2f localPos) {

		Widget scrollbar = widget.component(WItem.SCROLL_WIDGET, Widget.class);
		Widget handle = widget.children().get(0);
		
		boolean vertical = true;
		Scrollable scstate = scrollbar.component(WItem.SCROLLABLE_VERTICAL, Scrollable.class);
		if (scstate == null) {
			scstate = scrollbar.component(WItem.SCROLLABLE_HORIZONTAL, Scrollable.class);
			vertical = false;
		}

		float pos = vertical ? localPos.y : localPos.x;
		float cmp = vertical ? handle.position().y : handle.position().y;
		if (pos < cmp) {
			scstate.scrollPageToStart();
		} else {
			scstate.scrollPageToEnd();
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	private Widget traverse(Widget widget, Vector2f pos) {
		if (!widget.match(WFlag.HIDDEN)) {
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
	public void handleSurface(Event e) {
		gui
			.root()
			.dimension(e.dimension());
	}
	//=============================================================================================
	
}
//*************************************************************************************************
