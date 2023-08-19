//*************************************************************************************************
package ode.event;
//*************************************************************************************************

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;

//*************************************************************************************************
public class NEWTInputAdapter implements KeyListener, MouseListener {

	//=============================================================================================
	private Events events = null;
	//=============================================================================================

	//=============================================================================================
	public void setEvents(Events events) {
		this.events = events;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void keyPressed(KeyEvent e) {
		if (!e.isAutoRepeat()) {
			KeyData keyData = new KeyData(translateKey(e.getKeySymbol()), e.getKeyChar());
			events.post(Event.KEY_PRESSED, keyData);
		}
		KeyData keyData = new KeyData(translateKey(e.getKeySymbol()), e.getKeyChar());
		events.post(Event.KEY_TYPED, keyData);
	}
	//=============================================================================================

	//=============================================================================================
	public void keyReleased(KeyEvent e) {
		if (!e.isAutoRepeat()) {
			KeyData keyData = new KeyData(translateKey(e.getKeySymbol()), e.getKeyChar());
			events.post(Event.KEY_PRESSED, keyData);
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	public void mouseClicked(MouseEvent e) {
		if (!e.isAutoRepeat()) {
			PointerData pointerData = new PointerData(
				e.getX(),
				e.getY(),
				translateButton(e.getButton()),
				e.getClickCount());
			events.post(Event.MOUSE_CLICKED, pointerData);
		}
	}
	//=============================================================================================

	//=============================================================================================
	public void mouseEntered(MouseEvent e) {
		if (!e.isAutoRepeat()) {
			PointerData pointerData = new PointerData(
				e.getX(),
				e.getY(),
				translateButton(e.getButton()),
				e.getClickCount());
			events.post(Event.MOUSE_ENTERED, pointerData);
		}
	}
	//=============================================================================================

	//=============================================================================================
	public void mouseExited(MouseEvent e) {
		if (!e.isAutoRepeat()) {
			PointerData pointerData = new PointerData(
				e.getX(),
				e.getY(),
				translateButton(e.getButton()),
				e.getClickCount());
			events.post(Event.MOUSE_EXITED, pointerData);
		}
	}
	//=============================================================================================

	//=============================================================================================
	public void mousePressed(MouseEvent e) {
		if (!e.isAutoRepeat()) {
			PointerData pointerData = new PointerData(
				e.getX(),
				e.getY(),
				translateButton(e.getButton()),
				e.getClickCount());
			events.post(Event.MOUSE_PRESSED, pointerData);
		}
	}
	//=============================================================================================

	//=============================================================================================
	public void mouseReleased(MouseEvent e) {
		if (!e.isAutoRepeat()) {
			PointerData pointerData = new PointerData(
				e.getX(),
				e.getY(),
				translateButton(e.getButton()),
				e.getClickCount());
			events.post(Event.MOUSE_RELEASED, pointerData);
		}
	}
	//=============================================================================================

	//=============================================================================================
	public void mouseMoved(MouseEvent e) {
		if (!e.isAutoRepeat()) {
			PointerData pointerData = new PointerData(
				e.getX(),
				e.getY(),
				translateButton(e.getButton()),
				e.getClickCount());
			events.post(Event.MOUSE_MOVED, pointerData);
		}
	}
	//=============================================================================================

	//=============================================================================================
	public void mouseDragged(MouseEvent e) {
		if (!e.isAutoRepeat()) {
			PointerData pointerData = new PointerData(
				e.getX(),
				e.getY(),
				translateButton(e.getButton()),
				e.getClickCount());
			events.post(Event.MOUSE_MOVED, pointerData);
		}
	}
	//=============================================================================================

	//=============================================================================================
	public void mouseWheelMoved(MouseEvent e) {
		if (!e.isAutoRepeat()) {
			PointerData pointerData = new PointerData(
				e.getX(),
				e.getY(),
				translateButton(e.getButton()),
				e.getClickCount());
			float s = e.getRotationScale();
			float r = e.getRotation()[0];
			pointerData.wheel = r * s;
			events.post(Event.MOUSE_WHEEL, pointerData);
		}
	}
	//=============================================================================================

	//=============================================================================================
	private final static int translateKey(int keyCode) {		
		switch (keyCode) {
		case KeyEvent.VK_ESCAPE: return KeyData.ESCAPE;
		case KeyEvent.VK_F1: return KeyData.F1;
		case KeyEvent.VK_F2: return KeyData.F2;
		case KeyEvent.VK_F3: return KeyData.F3;
		case KeyEvent.VK_F4: return KeyData.F4;
		case KeyEvent.VK_F5: return KeyData.F5;
		case KeyEvent.VK_F6: return KeyData.F6;
		case KeyEvent.VK_F7: return KeyData.F7;
		case KeyEvent.VK_F8: return KeyData.F8;
		case KeyEvent.VK_F9: return KeyData.F9;
		case KeyEvent.VK_F10: return KeyData.F10;
		case KeyEvent.VK_F11: return KeyData.F11;
		case KeyEvent.VK_F12: return KeyData.F12;
		case KeyEvent.VK_PRINTSCREEN: return KeyData.PRINT;
		case KeyEvent.VK_SCROLL_LOCK: return KeyData.SCROLL_LOCK;
		case KeyEvent.VK_PAUSE: return KeyData.PAUSE;
		case KeyEvent.VK_1: return KeyData.ONE;
		case KeyEvent.VK_2: return KeyData.TWO;
		case KeyEvent.VK_3: return KeyData.THREE;
		case KeyEvent.VK_4: return KeyData.FOUR;
		case KeyEvent.VK_5: return KeyData.FIVE;
		case KeyEvent.VK_6: return KeyData.SIX;
		case KeyEvent.VK_7: return KeyData.SEVEN;
		case KeyEvent.VK_8: return KeyData.EIGHT;
		case KeyEvent.VK_9: return KeyData.NINE;
		case KeyEvent.VK_0: return KeyData.ZERO;
		case KeyEvent.VK_SPACE: return KeyData.SPACE;
		case KeyEvent.VK_BACK_SPACE: return KeyData.BACK_SPACE;
		case KeyEvent.VK_TAB: return KeyData.TAB;
		case KeyEvent.VK_ENTER: return KeyData.ENTER;
		case KeyEvent.VK_A: return KeyData.A;
		case KeyEvent.VK_B: return KeyData.B;
		case KeyEvent.VK_C: return KeyData.C;
		case KeyEvent.VK_D: return KeyData.D;
		case KeyEvent.VK_E: return KeyData.E;
		case KeyEvent.VK_F: return KeyData.F;
		case KeyEvent.VK_G: return KeyData.G;
		case KeyEvent.VK_H: return KeyData.H;
		case KeyEvent.VK_I: return KeyData.I;
		case KeyEvent.VK_J: return KeyData.J;
		case KeyEvent.VK_K: return KeyData.K;
		case KeyEvent.VK_L: return KeyData.L;
		case KeyEvent.VK_M: return KeyData.M;
		case KeyEvent.VK_N: return KeyData.N;
		case KeyEvent.VK_O: return KeyData.O;
		case KeyEvent.VK_P: return KeyData.P;
		case KeyEvent.VK_Q: return KeyData.Q;
		case KeyEvent.VK_R: return KeyData.R;
		case KeyEvent.VK_S: return KeyData.S;
		case KeyEvent.VK_T: return KeyData.T;
		case KeyEvent.VK_U: return KeyData.U;
		case KeyEvent.VK_V: return KeyData.V;
		case KeyEvent.VK_W: return KeyData.W;
		case KeyEvent.VK_X: return KeyData.X;
		case KeyEvent.VK_Y: return KeyData.Y;
		case KeyEvent.VK_Z: return KeyData.Z;
		case KeyEvent.VK_CAPS_LOCK: return KeyData.CAPS_LOCK;
		case KeyEvent.VK_SHIFT: return KeyData.SHIFT;
		case KeyEvent.VK_CONTROL: return KeyData.CONTROL;
		case KeyEvent.VK_ALT: return KeyData.ALT;
		case KeyEvent.VK_WINDOWS: return KeyData.SYSTEM;
		case KeyEvent.VK_CONTEXT_MENU: return KeyData.MENU;
		case KeyEvent.VK_INSERT: return KeyData.INSERT;
		case KeyEvent.VK_DELETE: return KeyData.DELETE;
		case KeyEvent.VK_HOME: return KeyData.POS1;
		case KeyEvent.VK_END: return KeyData.END;
		case KeyEvent.VK_PAGE_UP: return KeyData.PAGE_UP;
		case KeyEvent.VK_PAGE_DOWN: return KeyData.PAGE_DOWN;
		case KeyEvent.VK_UP: return KeyData.UP;
		case KeyEvent.VK_DOWN: return KeyData.DOWN;
		case KeyEvent.VK_LEFT: return KeyData.LEFT;
		case KeyEvent.VK_RIGHT: return KeyData.RIGHT;
		case KeyEvent.VK_NUM_LOCK: return KeyData.NUM_LOCK;
		case KeyEvent.VK_NUMPAD1: return KeyData.KP_ONE;
		case KeyEvent.VK_NUMPAD2: return KeyData.KP_TWO;
		case KeyEvent.VK_NUMPAD3: return KeyData.KP_THREE;
		case KeyEvent.VK_NUMPAD4: return KeyData.KP_FOUR;
		case KeyEvent.VK_NUMPAD5: return KeyData.KP_FIVE;
		case KeyEvent.VK_NUMPAD6: return KeyData.KP_SIX;
		case KeyEvent.VK_NUMPAD7: return KeyData.KP_SEVEN;
		case KeyEvent.VK_NUMPAD8: return KeyData.KP_EIGHT;
		case KeyEvent.VK_NUMPAD9: return KeyData.KP_NINE;
		case KeyEvent.VK_NUMPAD0: return KeyData.KP_ZERO;
		case KeyEvent.VK_DIVIDE: return KeyData.DIV;
		case KeyEvent.VK_MULTIPLY: return KeyData.MUL;
		case KeyEvent.VK_SUBTRACT: return KeyData.SUB;
		case KeyEvent.VK_ADD: return KeyData.ADD;
		case KeyEvent.VK_DECIMAL: return KeyData.DECIMAL;
		default: return KeyData.NONE;
		}
	}
	//=============================================================================================

	//=============================================================================================
	private final static int translateButton(int buttonCode) {
		switch (buttonCode) {
		case MouseEvent.BUTTON1: return PointerData.BUTTON1;
		case MouseEvent.BUTTON2: return PointerData.BUTTON2;
		case MouseEvent.BUTTON3: return PointerData.BUTTON3;
		case MouseEvent.BUTTON4: return PointerData.BUTTON4;
		case MouseEvent.BUTTON5: return PointerData.BUTTON5;
		case MouseEvent.BUTTON6: return PointerData.BUTTON6;
		default: return PointerData.NONE;
		}
	}
	//=============================================================================================
	
}
//*************************************************************************************************
