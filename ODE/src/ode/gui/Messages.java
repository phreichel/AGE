//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.vecmath.Color4f;
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
		this.msgbox.subscribe(ID.KEY_TYPED, this);
		this.msgbox.subscribe(ID.POINTER_PRESSED, this);
		this.msgbox.subscribe(ID.POINTER_RELEASED, this);
		this.msgbox.subscribe(ID.POINTER_CLICKED, this);
		this.msgbox.subscribe(ID.POINTER_MOVED, this);
	}
	//=============================================================================================

	//=============================================================================================
	private final Vector2f dpos = new Vector2f();
	private final Vector2f ddim = new Vector2f();
	private final Color4f  tmp  = new Color4f();
	//=============================================================================================
	
	//=============================================================================================
	public void handle(Msg msg) {
		switch (msg.id()) {
			case KEY_TYPED:
				onKeyTyped(msg);
				break;
			case POINTER_PRESSED:
				onPointerPressed(msg);
				break;
			case POINTER_RELEASED:
				onPointerReleased(msg);
				break;
			case POINTER_CLICKED:
				onPointerClicked(msg);
				break;
			case POINTER_MOVED:
				onPointerMoved(msg);
				break;
			default: break;
		}
	}
	//=============================================================================================

	//=============================================================================================
	private Widget containsPointer(List<Widget> widgets, Msg msg, float x, float y) {
		List<Widget> rev = new ArrayList<>(widgets);
		Collections.reverse(rev);
		for (Widget widget : rev) {
			float locX = x - widget.position().x;
			float locY = y - widget.position().y;
			Widget hover = containsPointer(widget.children(), msg, locX, locY);
			if (hover != null) return hover;
			if (widget.match(Flag.DISPLAYED, Flag.REACTIVE)) {
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
	private void onKeyTyped(Msg msg) {
		KeyData data = msg.data(KeyData.class);
		if (data.key().equals(Key.ESCAPE)) {
			msgbox
				.build()
				.terminate()
				.post();
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	private void onPointerMoved(Msg msg) {
		PointerData data = msg.data(PointerData.class);
		if (activeWidget != null) {
			if (activeAction.equals(Flag.ACTION_PARENT_MOVE)) {
				Widget p = activeWidget.parent();
				float dx = data.x() - lastPointerPosition.x;
				float dy = data.y() - lastPointerPosition.y;
				float lx = Math.max(0, -(p.position().x + p.dimension().x - 100));
				float ly = Math.max(0, -(p.position().y));
				float hx = Math.min(0, -(p.position().x + 100 - gui.dimension().x));
				float hy = Math.min(0, -(p.position().y + 100 - gui.dimension().y));
				dpos.set(dx + lx + hx, dy + ly + hy);
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
					pointerInside.clear(Flag.HOVERED);
					if (pointerInside.match(Flag.ACTION_HOVER)) {
						pointerInside.background(tmp);
					}
				}
				pointerInside = newPointerInside;
				if (pointerInside != null) {
					pointerInside.set(Flag.HOVERED);
					if (pointerInside.match(Flag.ACTION_HOVER)) {
						tmp.set(pointerInside.background());
						pointerInside.background(1f, .4f, .4f, 1);
					}
				}				
			}
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	private void onPointerPressed(Msg msg) {
		if (pointerInside == null) return;
		if (pointerInside.match(Flag.REACTIVE)) {
			Widget widget = pointerInside;
			while ((widget != null) && !widget.match(Flag.LAYERABLE)) {
				widget = widget.parent();
			}
			if (widget != null) {
				widget.toFront();
			}
		}
		if (pointerInside.match(Flag.ACTION_PARENT_MOVE)) {
			activeWidget = pointerInside;
			activeAction = Flag.ACTION_PARENT_MOVE;
			PointerData pointerData = msg.data(PointerData.class);
			lastPointerPosition.set(pointerData.x(), pointerData.y());
		} else if (pointerInside.match(Flag.ACTION_PARENT_RESIZE)) {
			activeWidget = pointerInside;
			activeAction = Flag.ACTION_PARENT_RESIZE;
			PointerData pointerData = msg.data(PointerData.class);
			lastPointerPosition.set(pointerData.x(), pointerData.y());
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void onPointerReleased(Msg msg) {
		if (activeWidget != null) {
			activeWidget = null;
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void onPointerClicked(Msg msg) {
		if (pointerInside == null) return;
		if (pointerInside.match(Flag.ACTION_PARENT_CLOSE)) {
			pointerInside.parent().clear(Flag.DISPLAYED);
		}
	}
	//=============================================================================================
	
}
//*************************************************************************************************
