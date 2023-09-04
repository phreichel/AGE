//*************************************************************************************************
package ode.msg;
//*************************************************************************************************

import ode.msg.Msg.ID;

//*************************************************************************************************
public class Builder {

	//=============================================================================================
	private MsgBox msgbox;
	private Msg msg = null;
	//=============================================================================================

	//=============================================================================================
	Builder(MsgBox msgbox, Msg msg) {
		this.msgbox = msgbox;
		this.msg = msg;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder keyPressed(Key key, char keyChar) {
		init(ID.KEY_PRESSED);
		KeyData data = msg.data(KeyData.class);
		data.set(key, keyChar);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder keyReleased(Key key, char keyChar) {
		init(ID.KEY_RELEASED);
		KeyData data = msg.data(KeyData.class);
		data.set(key, keyChar);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder keyTyped(Key key, char keyChar) {
		init(ID.KEY_TYPED);
		KeyData data = msg.data(KeyData.class);
		data.set(key, keyChar);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder pointerPressed(Button button, int count, float x, float y) {
		init(ID.POINTER_PRESSED);
		PointerData data = msg.data(PointerData.class);
		data.set(button, x, y);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder pointerReleased(Button button, int count, float x, float y) {
		init(ID.POINTER_RELEASED);
		PointerData data = msg.data(PointerData.class);
		data.set(button, x, y);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder pointerClicked(Button button, int count, float x, float y) {
		init(ID.POINTER_CLICKED);
		PointerData data = msg.data(PointerData.class);
		data.set(button, count, x, y);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder pointerMoved(float x, float y) {
		init(ID.POINTER_MOVED);
		PointerData data = msg.data(PointerData.class);
		data.set(x, y);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder pointerEntered(float x, float y) {
		init(ID.POINTER_ENTERED);
		PointerData data = msg.data(PointerData.class);
		data.set(x, y);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder pointerExited(float x, float y) {
		init(ID.POINTER_EXITED);
		PointerData data = msg.data(PointerData.class);
		data.set(x, y);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder pointerWheel(float wx, float wy, float x, float y) {
		init(ID.POINTER_WHEEL);
		PointerData data = msg.data(PointerData.class);
		data.set(wx, wy, x, y);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder terminate() {
		init(ID.TERMINATE);
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	private void init(Msg.ID id) {
		Class<?> cls = null;
		switch (id) {
		case KEY_PRESSED:
		case KEY_RELEASED:
		case KEY_TYPED:
			cls = KeyData.class;
			break;
		case POINTER_ENTERED:
		case POINTER_EXITED:
		case POINTER_MOVED:
		case POINTER_PRESSED:
		case POINTER_RELEASED:
		case POINTER_CLICKED:
			cls = PointerData.class;
			break;
		default:
			cls = null;
			break;
		}
		msg.init(id, cls);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void post() {
		msgbox.post(msg);
	}
	//=============================================================================================
	
}
//*************************************************************************************************
