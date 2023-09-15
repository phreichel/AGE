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
	private final Widgets widgets;
	//=============================================================================================

	//=============================================================================================
	private Widget hovered = null;
	private Widget dragged = null;
	private String command = null;
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
		if (hovered != null) hovered.clear(Flag.HOVER);
		hovered = hover(tmp, widgets.root());
		if (hovered != null) hovered.flag(Flag.HOVER);
		
		// to front
		if (e.type().equals(Type.POINTER_PRESSED)) {

			// to front
			Widget front = hovered;
			while (front != null) {
				if (front.match(Flag.FRAME)) {
					front.toFront();
				}
				front = front.parent();
			}

			// init title drag
			if (hovered != null) {
				if (hovered.match(Flag.TITLE) && e.button().equals(Button.BTN1)) {
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
						command = "move";
					}
				}
				// init size button drag
				else if ((hovered.match(Flag.BUTTON) &&  e.button().equals(Button.BTN1) && hovered.image().equals("size"))) {
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
						command = "size";
					}
				}
			}
		}
		
		else if (
			e.type().equals(Type.POINTER_MOVED) ||
			e.type().equals(Type.POINTER_RELEASED)) {
			if (dragged != null) {
				ref.sub(e.position(), ref);
				if (command.equals("move")) {
					dragged.positionAdd(ref);
				}
				else if (command.equals("size")) {
					dragged.positionAdd(0, ref.y);
					dragged.dimensionAdd(ref.x, -ref.y);
				}
				ref.set(e.position());
				if (e.type().equals(Type.POINTER_RELEASED)) {
					dragged = null;
				}
			}
		}

	}
	//=============================================================================================

	//=============================================================================================
	private Widget hover(Vector2f pos, Widget widget) {
		Widget result = null;
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
				result = hover(pos, child);
				if (result != null) break;
			}
			if (result == null) {
				result = widget;
			}
		}
		pos.add(widget.position());
		return result;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void handleSurface(Event e) {
		widgets.root().dimension(e.dimension());
	}
	//=============================================================================================
	
}
//*************************************************************************************************
