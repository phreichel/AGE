//*************************************************************************************************
package age.port.jogl;
//*************************************************************************************************

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import age.event.Button;
import age.event.Events;
import age.event.Key;

//*************************************************************************************************
class JOGLEventListener implements KeyListener, MouseListener {

	//=============================================================================================
	private Events events = null;
	//=============================================================================================

	//=============================================================================================
	public void assign(Events events) {
		this.events = events;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void mouseClicked(MouseEvent e) {
		events.postPointerClicked(
			translateButton(e.getButton()),
			e.getClickCount(),
			e.getX(),
			e.getY());
	}
	//=============================================================================================

	//=============================================================================================
	public void mouseEntered(MouseEvent e) {
		events.postPointerEntered(
			e.getX(),
			e.getY());
	}
	//=============================================================================================

	//=============================================================================================
	public void mouseExited(MouseEvent e) {
		events.postPointerExited(
			e.getX(),
			e.getY());
	}
	//=============================================================================================

	//=============================================================================================
	public void mousePressed(MouseEvent e) {
		events.postPointerPressed(
			translateButton(e.getButton()),
			e.getClickCount(),
			e.getX(),
			e.getY());
	}
	//=============================================================================================

	//=============================================================================================
	public void mouseReleased(MouseEvent e) {
		events.postPointerReleased(
			translateButton(e.getButton()),
			e.getClickCount(),
			e.getX(),
			e.getY());
	}
	//=============================================================================================

	//=============================================================================================
	public void mouseMoved(MouseEvent e) {
		events.postPointerMoved(
			e.getX(),
			e.getY());
	}
	//=============================================================================================

	//=============================================================================================
	public void mouseDragged(MouseEvent e) {
		events.postPointerMoved(
			e.getX(),
			e.getY());
	}
	//=============================================================================================

	//=============================================================================================
	public void mouseWheelMoved(MouseEvent e) {
	}
	//=============================================================================================

	//=============================================================================================
	public void keyPressed(KeyEvent e) {
		Key key = translateKey(e.getKeySymbol());
		if (!e.isAutoRepeat()) {
			events.postKeyPressed(
				key,
				e.getKeyChar());
		}
		events.postKeyTyped(
			key,
			e.getKeyChar());
	}
	//=============================================================================================

	//=============================================================================================
	public void keyReleased(KeyEvent e) {
		if (!e.isAutoRepeat()) {
			events.postKeyReleased(
				translateKey(e.getKeySymbol()),
				e.getKeyChar());
		}
	}
	//=============================================================================================

	//=============================================================================================
	private Button translateButton(short button) {
		switch (button) {
		case MouseEvent.BUTTON1: return Button.BTN1;
		case MouseEvent.BUTTON2: return Button.BTN2;
		case MouseEvent.BUTTON3: return Button.BTN3;
		case MouseEvent.BUTTON4: return Button.BTN4;
		case MouseEvent.BUTTON5: return Button.BTN5;
		case MouseEvent.BUTTON6: return Button.BTN6;
		case MouseEvent.BUTTON7: return Button.BTN7;
		case MouseEvent.BUTTON8: return Button.BTN8;
		default: return Button.NONE;
		}
	}
	//=============================================================================================

	//=============================================================================================
	private Key translateKey(short key) {
		switch (key) {
		case KeyEvent.VK_ESCAPE: return Key.ESCAPE;
		case KeyEvent.VK_F1: return Key.F1;
		case KeyEvent.VK_F2: return Key.F2;
		case KeyEvent.VK_F3: return Key.F3;
		case KeyEvent.VK_F4: return Key.F4;
		case KeyEvent.VK_F5: return Key.F5;
		case KeyEvent.VK_F6: return Key.F6;
		case KeyEvent.VK_F7: return Key.F7;
		case KeyEvent.VK_F8: return Key.F8;
		case KeyEvent.VK_F9: return Key.F9;
		case KeyEvent.VK_F10: return Key.F10;
		case KeyEvent.VK_F11: return Key.F11;
		case KeyEvent.VK_F12: return Key.F12;
		case KeyEvent.VK_PRINTSCREEN: return Key.PRINT_SCREEN;
		case KeyEvent.VK_SCROLL_LOCK: return Key.SCROLL_LOCK;
		case KeyEvent.VK_PAUSE: return Key.PAUSE;
		case KeyEvent.VK_1: return Key._1;
		case KeyEvent.VK_2: return Key._2;
		case KeyEvent.VK_3: return Key._3;
		case KeyEvent.VK_4: return Key._4;
		case KeyEvent.VK_5: return Key._5;
		case KeyEvent.VK_6: return Key._6;
		case KeyEvent.VK_7: return Key._7;
		case KeyEvent.VK_8: return Key._8;
		case KeyEvent.VK_9: return Key._9;
		case KeyEvent.VK_0: return Key._0;
		case KeyEvent.VK_Q: return Key.Q;
		case KeyEvent.VK_W: return Key.W;
		case KeyEvent.VK_E: return Key.E;
		case KeyEvent.VK_R: return Key.R;
		case KeyEvent.VK_T: return Key.T;
		case KeyEvent.VK_Z: return Key.Z;
		case KeyEvent.VK_U: return Key.U;
		case KeyEvent.VK_I: return Key.I;
		case KeyEvent.VK_O: return Key.O;
		case KeyEvent.VK_P: return Key.P;
		case KeyEvent.VK_A: return Key.A;
		case KeyEvent.VK_S: return Key.S;
		case KeyEvent.VK_D: return Key.D;
		case KeyEvent.VK_F: return Key.F;
		case KeyEvent.VK_G: return Key.G;
		case KeyEvent.VK_H: return Key.H;
		case KeyEvent.VK_J: return Key.J;
		case KeyEvent.VK_K: return Key.K;
		case KeyEvent.VK_L: return Key.L;
		case KeyEvent.VK_Y: return Key.Y;
		case KeyEvent.VK_X: return Key.X;
		case KeyEvent.VK_C: return Key.C;
		case KeyEvent.VK_V: return Key.V;
		case KeyEvent.VK_B: return Key.B;
		case KeyEvent.VK_N: return Key.N;
		case KeyEvent.VK_M: return Key.M;
		case KeyEvent.VK_PLUS: return Key.PLUS;
		case KeyEvent.VK_MINUS: return Key.MINUS;
		case KeyEvent.VK_BACK_SPACE: return Key.BACK_SPACE;
		case KeyEvent.VK_TAB: return Key.TAB;
		case KeyEvent.VK_CAPS_LOCK: return Key.CAPS_LOCK;
		case KeyEvent.VK_CONTROL: return Key.CONTROL;
		case KeyEvent.VK_SHIFT: return Key.SHIFT;
		case KeyEvent.VK_WINDOWS: return Key.SYSTEM;
		case KeyEvent.VK_ALT: return Key.ALT;
		case KeyEvent.VK_CONTEXT_MENU: return Key.MENU;
		case KeyEvent.VK_ENTER: return Key.ENTER;
		case KeyEvent.VK_SPACE: return Key.SPACE;
		case KeyEvent.VK_INSERT: return Key.INSERT;
		case KeyEvent.VK_DELETE: return Key.DELETE;
		case KeyEvent.VK_HOME: return Key.POS1;
		case KeyEvent.VK_END: return Key.END;
		case KeyEvent.VK_PAGE_UP: return Key.PAGE_UP;
		case KeyEvent.VK_PAGE_DOWN: return Key.PAGE_DOWN;
		case KeyEvent.VK_UP: return Key.UP;
		case KeyEvent.VK_DOWN: return Key.DOWN;
		case KeyEvent.VK_LEFT: return Key.LEFT;
		case KeyEvent.VK_RIGHT: return Key.RIGHT;
		case KeyEvent.VK_NUM_LOCK: return Key.NUM_LOCK;
		case KeyEvent.VK_DIVIDE: return Key.DIVIDE;
		case KeyEvent.VK_MULTIPLY: return Key.MULTIPLY;
		case KeyEvent.VK_SUBTRACT: return Key.SUBTRACT;
		case KeyEvent.VK_ADD: return Key.ADD;
		case KeyEvent.VK_DECIMAL: return Key.DECIMAL;
		case KeyEvent.VK_NUMPAD0: return Key.NP_0;
		case KeyEvent.VK_NUMPAD1: return Key.NP_1;
		case KeyEvent.VK_NUMPAD2: return Key.NP_2;
		case KeyEvent.VK_NUMPAD3: return Key.NP_3;
		case KeyEvent.VK_NUMPAD4: return Key.NP_4;
		case KeyEvent.VK_NUMPAD5: return Key.NP_5;
		case KeyEvent.VK_NUMPAD6: return Key.NP_6;
		case KeyEvent.VK_NUMPAD7: return Key.NP_7;
		case KeyEvent.VK_NUMPAD8: return Key.NP_8;
		case KeyEvent.VK_NUMPAD9: return Key.NP_9;
		default: return Key.NONE;
		}
	}
	//=============================================================================================
	
}
//*************************************************************************************************
