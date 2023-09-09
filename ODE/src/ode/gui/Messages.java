//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import java.util.List;

import javax.vecmath.Vector2f;

import ode.msg.Key;
import ode.msg.KeyData;
import ode.msg.Msg;
import ode.msg.MsgBox;
import ode.msg.MsgHandler;
import ode.msg.PointerData;
import ode.msg.Msg.ID;

//*************************************************************************************************
public class Messages implements MsgHandler {

	//=============================================================================================
	private final GUI gui;
	private MsgBox msgbox;
	//=============================================================================================

	//=============================================================================================
	private Widget pointerInside = null;
	//=============================================================================================

	//=============================================================================================
	private Widget   activeWidget = null;
	private Flag     activeAction = null;
	private Vector2f lastPointerPosition = new Vector2f();
	//=============================================================================================
	
	//=============================================================================================
	public Messages(GUI gui) {
		this.gui = gui;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void assign(MsgBox msgbox) {
		this.msgbox = msgbox;
		this.msgbox.subscribe(ID.TERMINATE, this);
		this.msgbox.subscribe(ID.KEY_PRESSED, this);
		this.msgbox.subscribe(ID.KEY_RELEASED, this);
		this.msgbox.subscribe(ID.KEY_TYPED, this);
		this.msgbox.subscribe(ID.POINTER_MOVED, this);
		this.msgbox.subscribe(ID.POINTER_PRESSED, this);
		this.msgbox.subscribe(ID.POINTER_RELEASED, this);
		this.msgbox.subscribe(ID.POINTER_CLICKED, this);
		this.msgbox.subscribe(ID.POINTER_WHEEL, this);
	}
	//=============================================================================================

	//=============================================================================================
	private final Vector2f dpos = new Vector2f();
	private final Vector2f ddim = new Vector2f();
	//=============================================================================================
	
	//=============================================================================================
	public void handle(Msg msg) {
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
				firePointerPress(msg, pointerInside);
			}
			break;
		}
		case POINTER_RELEASED: {
			if (activeWidget != null) {
				activeWidget = null;
			} else if (pointerInside != null) {
				firePointerRelease(msg, pointerInside);
			}
			break;
		}
		case POINTER_CLICKED: {
			if (pointerInside != null) {
				firePointerClick(msg, pointerInside);
			}
			break;
		}
		case POINTER_MOVED: {
			PointerData data = msg.data(PointerData.class);
			if (activeWidget != null) {
				if (activeAction.equals(Flag.ACTION_PARENT_MOVE)) {
					float dx = data.x() - lastPointerPosition.x;
					float dy = data.y() - lastPointerPosition.y;
					dpos.set(dx, dy);
					activeWidget.parent().position().add(dpos);
					lastPointerPosition.set(data.x(), data.y());
				} else if (activeAction.equals(Flag.ACTION_PARENT_RESIZE)) {
					float dx = data.x() - lastPointerPosition.x;
					float dy = data.y() - lastPointerPosition.y;
					dpos.set(0,  -dy);
					ddim.set(dx, -dy);
					activeWidget.parent().position().sub(dpos);
					activeWidget.parent().dimension().add(ddim);
					lastPointerPosition.set(data.x(), data.y());
				}
			} else {
				Widget newPointerInside = containsPointer(gui.roots(), msg, data.x(), data.y());
				if (pointerInside != newPointerInside) {
					if (pointerInside != null) {
						firePointerExit(msg, pointerInside);
					}
					pointerInside = newPointerInside;
					if (pointerInside != null) {
						firePointerEnter(msg, pointerInside);
					}				
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

	//=============================================================================================
	private void firePointerEnter(Msg msg, Widget widget) {
		widget.set(Flag.HOVERED);
		if (widget.match(Flag.BUTTON)) {
			widget.background(1f, .4f, .4f, 1);
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void firePointerExit(Msg msg, Widget widget) {
		widget.clear(Flag.HOVERED);
		if (widget.match(Flag.BUTTON)) {
			widget.background(.4f, .4f, 1f, 1);
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	private void firePointerPress(Msg msg, Widget widget) {
		if (widget.match(Flag.ACTION_PARENT_MOVE)) {
			activeWidget = widget;
			activeAction = Flag.ACTION_PARENT_MOVE;
			PointerData pointerData = msg.data(PointerData.class);
			lastPointerPosition.set(pointerData.x(), pointerData.y());
		} else if (widget.match(Flag.ACTION_PARENT_RESIZE)) {
			activeWidget = widget;
			activeAction = Flag.ACTION_PARENT_RESIZE;
			PointerData pointerData = msg.data(PointerData.class);
			lastPointerPosition.set(pointerData.x(), pointerData.y());
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void firePointerRelease(Msg msg, Widget widget) {
	}
	//=============================================================================================

	//=============================================================================================
	private void firePointerClick(Msg msg, Widget widget) {
		if (widget.match(Flag.ACTION_PARENT_CLOSE)) {
			widget.parent().clear(Flag.DISPLAYED);
		}
	}
	//=============================================================================================
	
}
//*************************************************************************************************
