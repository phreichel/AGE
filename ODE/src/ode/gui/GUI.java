//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ode.msg.Key;
import ode.msg.KeyData;
import ode.msg.Msg;
import ode.msg.MsgBox;
import ode.msg.PointerData;
import ode.npa.Graphics;

//*************************************************************************************************
public class GUI {

	//=============================================================================================
	private MsgBox msgbox;
	//=============================================================================================

	//=============================================================================================
	private Factory factory = new Factory(this);
	private final Renderer renderer = new Renderer(this);
	//=============================================================================================
	
	//=============================================================================================
	private final List<Widget> roots = new ArrayList<>();
	private final List<Widget> roots_ro = Collections.unmodifiableList(roots);
	//=============================================================================================

	//=============================================================================================
	final List<Widget> widgets = new ArrayList<Widget>();
	private final List<Widget> widgets_ro = Collections.unmodifiableList(widgets);
	//=============================================================================================

	//=============================================================================================
	public void assign(MsgBox msgbox) {
		this.msgbox = msgbox;
	}
	//=============================================================================================
	
	//=============================================================================================
	public List<Widget> roots() {
		return roots_ro;
	}
	//=============================================================================================

	//=============================================================================================
	public void root(Widget ... widgets) {
		this.roots.addAll(List.of(widgets));
	}
	//=============================================================================================

	//=============================================================================================
	public void unroot(Widget ... widgets) {
		this.roots.removeAll(List.of(widgets));
	}
	//=============================================================================================

	//=============================================================================================
	public Factory factory() {
		return factory;
	}
	//=============================================================================================

	//=============================================================================================
	public void destroy(Widget ... widgets) {
		for (Widget widget : widgets) {
			unroot(widget);
			destroy(widget.children().toArray(new Widget[] {}));
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	public List<Widget> widgets() {
		return widgets_ro;
	}
	//=============================================================================================

	//=============================================================================================
	public List<Widget> unattached(List<Widget> dst) {
		for (Widget widget : widgets) {
			if (widget.parent() == null) {
				dst.add(widget);
			}
		}
		return dst;
	}
	//=============================================================================================
	
	//=============================================================================================
	public List<Widget> named(String name, List<Widget> dst) {
		for (Widget widget : widgets) {
			if (widget.name().equals(name)) {
				dst.add(widget);
			}
		}
		return dst;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder newBuilder() {
		return new Builder(this);
	}
	//=============================================================================================

	//=============================================================================================
	public void render(Graphics graphics) {
		renderer.render(graphics);
	}
	//=============================================================================================

	//=============================================================================================
	private Widget pointerInside = null;
	//=============================================================================================
	
	//=============================================================================================
	public void handleEvent(Msg msg) {
		switch (msg.id()) {
		case KEY_PRESSED: {
			KeyData data = msg.data(KeyData.class);
			if (data.key().equals(Key.ESCAPE)) {
				msgbox
					.build()
					.terminate()
					.post();
			}
			break;
		}
		case POINTER_WHEEL: {
			break;
		}
		case POINTER_PRESSED: {
			if (pointerInside != null) {
				pointerInside.firePointerPress(msg);
			}
			break;
		}
		case POINTER_RELEASED: {
			if (pointerInside != null) {
				pointerInside.firePointerRelease(msg);
			}
			break;
		}
		case POINTER_CLICKED: {
			if (pointerInside != null) {
				pointerInside.firePointerClick(msg);
			}
			break;
		}
		case POINTER_MOVED: {
			PointerData data = msg.data(PointerData.class);
			Widget newPointerInside = containsPointer(roots, msg, data.x(), data.y());
			if (pointerInside != newPointerInside) {
				if (pointerInside != null) {
					pointerInside.clear(Flag.HOVERED);
					pointerInside.firePointerExit(msg);
				}
				pointerInside = newPointerInside;
				if (pointerInside != null) {
					pointerInside.set(Flag.HOVERED);
					pointerInside.firePointerEnter(msg);
				}				
			}
			break;
		}
		default:
			break;
		}
	}
	//=============================================================================================

	//=============================================================================================
	private Widget containsPointer(List<Widget> widgets, Msg msg, float x, float y) {
		for (Widget widget : widgets) {
			if (widget.match(Flag.DISPLAYED)) {
				float locX = x - widget.position().x;
				float locY = y - widget.position().y;
				Widget hover = containsPointer(widget.children(), msg, locX, locY);
				if (hover != null) return hover; 
			}
		}
		for (Widget widget : widgets) {
			if (widget.match(Flag.DISPLAYED)) {
				float locX = x - widget.position().x;
				float locY = y - widget.position().y;
				if (
						(locX >= 0f) &&
						(locY >= 0f) &&
						(locX <= widget.dimension().x) &&
						(locY <= widget.dimension().y)) {
					return widget;
				}
			}
		}
		return null;
	}
	//=============================================================================================
	
}
//*************************************************************************************************
