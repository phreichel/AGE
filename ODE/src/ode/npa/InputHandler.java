//*************************************************************************************************
package ode.npa;
//*************************************************************************************************

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;

import ode.msg.Button;
import ode.msg.Key;
import ode.msg.MsgBox;

//*************************************************************************************************
public class InputHandler implements KeyListener, MouseListener {

	//=============================================================================================
	private MsgBox msgbox;
	//=============================================================================================

	//=============================================================================================
	public void assign(MsgBox msgbox) {
		this.msgbox = msgbox;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void mouseClicked(MouseEvent e) {
		Button btn = txButton(e.getButton());
		int cnt = e.getClickCount();
		float x = e.getX();
		float y = e.getY();
		msgbox
			.build()
			.pointerClicked(btn, cnt, x, y)
			.post();
	}
	//=============================================================================================

	//=============================================================================================
	public void mouseEntered(MouseEvent e) {
		float x = e.getX();
		float y = e.getY();
		msgbox
			.build()
			.pointerEntered(x, y)
			.post();
	}
	//=============================================================================================

	//=============================================================================================
	public void mouseExited(MouseEvent e) {
		float x = e.getX();
		float y = e.getY();
		msgbox
			.build()
			.pointerExited(x, y)
			.post();
	}
	//=============================================================================================

	//=============================================================================================
	public void mousePressed(MouseEvent e) {
		Button btn = txButton(e.getButton());
		int cnt = e.getClickCount();
		float x = e.getX();
		float y = e.getY();
		msgbox
			.build()
			.pointerPressed(btn, cnt, x, y)
			.post();
	}
	//=============================================================================================

	//=============================================================================================
	public void mouseReleased(MouseEvent e) {
		Button btn = txButton(e.getButton());
		int cnt = e.getClickCount();
		float x = e.getX();
		float y = e.getY();
		msgbox
			.build()
			.pointerReleased(btn, cnt, x, y)
			.post();
	}
	//=============================================================================================

	//=============================================================================================
	public void mouseMoved(MouseEvent e) {
		float x = e.getX();
		float y = e.getY();
		msgbox
			.build()
			.pointerMoved(x, y)
			.post();
	}
	//=============================================================================================

	//=============================================================================================
	public void mouseDragged(MouseEvent e) {
		float x = e.getX();
		float y = e.getY();
		msgbox
			.build()
			.pointerMoved(x, y)
			.post();
	}
	//=============================================================================================

	//=============================================================================================
	public void mouseWheelMoved(MouseEvent e) {
		float rx = e.getRotation()[0];
		float ry = e.getRotation()[1];
		//float rz = e.getRotation()[2];
		float x = e.getX();
		float y = e.getY();
		msgbox
			.build()
			.pointerWheel(rx, ry, x, y)
			.post();
	}
	//=============================================================================================

	//=============================================================================================
	public void keyPressed(KeyEvent e) {
		Key key = txKey(e.getKeySymbol());
		char keyChar = e.getKeyChar();
		if (!e.isAutoRepeat()) {
			msgbox
				.build()
				.keyPressed(key, keyChar)
				.post();
		}
		msgbox
			.build()
			.keyTyped(key, keyChar)
			.post();
	}
	//=============================================================================================

	//=============================================================================================
	public void keyReleased(KeyEvent e) {
		Key key = txKey(e.getKeySymbol());
		char keyChar = e.getKeyChar();
		if (!e.isAutoRepeat()) {
			msgbox
				.build()
				.keyReleased(key, keyChar)
				.post();
		}
	}
	//=============================================================================================

	//=============================================================================================
	private Button txButton(short btnCode) {
		switch (btnCode) {
		case MouseEvent.BUTTON1: return Button.BUTTON1;
		case MouseEvent.BUTTON2: return Button.BUTTON2;
		case MouseEvent.BUTTON3: return Button.BUTTON3;
		case MouseEvent.BUTTON4: return Button.BUTTON4;
		case MouseEvent.BUTTON5: return Button.BUTTON5;
		case MouseEvent.BUTTON6: return Button.BUTTON6;
		default : return null;
		}
	}
	
	//=============================================================================================
	private Key txKey(short keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_ESCAPE : return Key.ESCAPE;
		case KeyEvent.VK_F1 : return Key.F1;
		case KeyEvent.VK_F2 : return Key.F2;
		case KeyEvent.VK_F3 : return Key.F3;
		case KeyEvent.VK_F4 : return Key.F4;
		case KeyEvent.VK_F5 : return Key.F5;
		case KeyEvent.VK_F6 : return Key.F6;
		case KeyEvent.VK_F7 : return Key.F7;
		case KeyEvent.VK_F8 : return Key.F8;
		case KeyEvent.VK_F9 : return Key.F9;
		case KeyEvent.VK_F10 : return Key.F10;
		case KeyEvent.VK_F11 : return Key.F11;
		case KeyEvent.VK_F12 : return Key.F12;
		case KeyEvent.VK_PRINTSCREEN : return Key.PRINT_SCREEN;
		case KeyEvent.VK_SCROLL_LOCK: return Key.SCROLL_LOCK;
		case KeyEvent.VK_PAUSE: return Key.PAUSE;
		case KeyEvent.VK_1: return Key.KB1;
		case KeyEvent.VK_2: return Key.KB2;
		case KeyEvent.VK_3: return Key.KB3;
		case KeyEvent.VK_4: return Key.KB4;
		case KeyEvent.VK_5: return Key.KB5;
		case KeyEvent.VK_6: return Key.KB6;
		case KeyEvent.VK_7: return Key.KB7;
		case KeyEvent.VK_8: return Key.KB8;
		case KeyEvent.VK_9: return Key.KB9;
		case KeyEvent.VK_0: return Key.KB0;
		case KeyEvent.VK_A: return Key.A;
		case KeyEvent.VK_B: return Key.B;
		case KeyEvent.VK_C: return Key.C;
		case KeyEvent.VK_D: return Key.D;
		case KeyEvent.VK_E: return Key.E;
		case KeyEvent.VK_F: return Key.F;
		case KeyEvent.VK_G: return Key.G;
		case KeyEvent.VK_H: return Key.H;
		case KeyEvent.VK_I: return Key.I;
		case KeyEvent.VK_J: return Key.J;
		case KeyEvent.VK_K: return Key.K;
		case KeyEvent.VK_L: return Key.L;
		case KeyEvent.VK_M: return Key.M;
		case KeyEvent.VK_N: return Key.N;
		case KeyEvent.VK_O: return Key.O;
		case KeyEvent.VK_P: return Key.P;
		case KeyEvent.VK_Q: return Key.Q;
		case KeyEvent.VK_R: return Key.R;
		case KeyEvent.VK_S: return Key.S;
		case KeyEvent.VK_T: return Key.T;
		case KeyEvent.VK_U: return Key.U;
		case KeyEvent.VK_V: return Key.V;
		case KeyEvent.VK_W: return Key.W;
		case KeyEvent.VK_X: return Key.X;
		case KeyEvent.VK_Y: return Key.Y;
		case KeyEvent.VK_Z: return Key.Z;
		case KeyEvent.VK_BACK_SPACE: return Key.BACK_SPACE;
		case KeyEvent.VK_TAB: return Key.TAB;
		case KeyEvent.VK_ENTER: return Key.ENTER;
		case KeyEvent.VK_CAPS_LOCK: return Key.CAPS_LOCK;
		case KeyEvent.VK_SHIFT: return Key.SHIFT;
		case KeyEvent.VK_CONTROL: return Key.CONTROL;
		case KeyEvent.VK_WINDOWS: return Key.SYSTEM;
		case KeyEvent.VK_ALT: return Key.ALT;
		case KeyEvent.VK_SPACE: return Key.SPACE;
		case KeyEvent.VK_CONTEXT_MENU: return Key.MENU;
		case KeyEvent.VK_INSERT: return Key.INSERT;
		case KeyEvent.VK_DELETE: return Key.DELETE;
		case KeyEvent.VK_HOME: return Key.POS1;
		case KeyEvent.VK_END: return Key.END;
		case KeyEvent.VK_PAGE_UP: return Key.PAGE_UP;
		case KeyEvent.VK_PAGE_DOWN: return Key.PAGE_DOWN;
		case KeyEvent.VK_UP: return Key.UP;
		case KeyEvent.VK_LEFT: return Key.LEFT;
		case KeyEvent.VK_DOWN: return Key.DOWN;
		case KeyEvent.VK_RIGHT: return Key.RIGHT;
		case KeyEvent.VK_NUM_LOCK: return Key.NUM_LOCK;
		case KeyEvent.VK_DIVIDE: return Key.DIVIDE;
		case KeyEvent.VK_MULTIPLY: return Key.MULTIPLY;
		case KeyEvent.VK_SUBTRACT: return Key.SUBTRACT;
		case KeyEvent.VK_ADD: return Key.ADD;
		case KeyEvent.VK_DECIMAL: return Key.DECIMAL;
		case KeyEvent.VK_NUMPAD7: return Key.NP7;
		case KeyEvent.VK_NUMPAD8: return Key.NP8;
		case KeyEvent.VK_NUMPAD9: return Key.NP9;
		case KeyEvent.VK_NUMPAD4: return Key.NP4;
		case KeyEvent.VK_NUMPAD5: return Key.NP5;
		case KeyEvent.VK_NUMPAD6: return Key.NP6;
		case KeyEvent.VK_NUMPAD1: return Key.NP1;
		case KeyEvent.VK_NUMPAD2: return Key.NP2;
		case KeyEvent.VK_NUMPAD3: return Key.NP3;
		case KeyEvent.VK_NUMPAD0: return Key.NP0;
		default: return null;
		}
	}
	//=============================================================================================
	
}
//*************************************************************************************************
