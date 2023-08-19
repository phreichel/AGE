//*************************************************************************************************
package ode.event;
//*************************************************************************************************

//*************************************************************************************************
public class KeyData {

	//=============================================================================================
	private static int inc = 0;
	//=============================================================================================

	//=============================================================================================
	/**
	 * represents "No Key"
	 */
	public static final int NONE = inc++;
	//=============================================================================================
	public static final int ESCAPE = inc++;
	public static final int F1 = inc++;
	public static final int F2 = inc++;
	public static final int F3 = inc++;
	public static final int F4 = inc++;
	public static final int F5 = inc++;
	public static final int F6 = inc++;
	public static final int F7 = inc++;
	public static final int F8 = inc++;
	public static final int F9 = inc++;
	public static final int F10 = inc++;
	public static final int F11 = inc++;
	public static final int F12 = inc++;
	public static final int PRINT = inc++;
	public static final int SCROLL_LOCK = inc++;
	public static final int PAUSE = inc++;
	public static final int ONE = inc++;
	public static final int TWO = inc++;
	public static final int THREE = inc++;
	public static final int FOUR = inc++;
	public static final int FIVE = inc++;
	public static final int SIX = inc++;
	public static final int SEVEN = inc++;
	public static final int EIGHT = inc++;
	public static final int NINE = inc++;
	public static final int ZERO = inc++;
	public static final int BACK_SPACE = inc++;
	public static final int TAB = inc++;
	public static final int SPACE = inc++;
	public static final int ENTER = inc++;
	public static final int A = inc++;
	public static final int B = inc++;
	public static final int C = inc++;
	public static final int D = inc++;
	public static final int E = inc++;
	public static final int F = inc++;
	public static final int G = inc++;
	public static final int H = inc++;
	public static final int I = inc++;
	public static final int J = inc++;
	public static final int K = inc++;
	public static final int L = inc++;
	public static final int M = inc++;
	public static final int N = inc++;
	public static final int O = inc++;
	public static final int P = inc++;
	public static final int Q = inc++;
	public static final int R = inc++;
	public static final int S = inc++;
	public static final int T = inc++;
	public static final int U = inc++;
	public static final int V = inc++;
	public static final int W = inc++;
	public static final int X = inc++;
	public static final int Y = inc++;
	public static final int Z = inc++;
	public static final int INSERT = inc++;
	public static final int DELETE = inc++;
	public static final int POS1 = inc++;
	public static final int END = inc++;
	public static final int PAGE_UP = inc++;
	public static final int PAGE_DOWN = inc++;
	public static final int UP = inc++;
	public static final int DOWN = inc++;
	public static final int LEFT = inc++;
	public static final int RIGHT = inc++;
	public static final int SHIFT = inc++;
	public static final int ALT = inc++;
	public static final int CONTROL = inc++;
	public static final int CAPS_LOCK = inc++;
	public static final int SYSTEM = inc++;
	public static final int MENU = inc++;
	public static final int NUM_LOCK = inc++;
	public static final int DIV = inc++;
	public static final int MUL = inc++;
	public static final int SUB = inc++;
	public static final int ADD = inc++;
	public static final int DECIMAL = inc++;
	public static final int KP_ONE = inc++;
	public static final int KP_TWO = inc++;
	public static final int KP_THREE = inc++;
	public static final int KP_FOUR = inc++;
	public static final int KP_FIVE = inc++;
	public static final int KP_SIX = inc++;
	public static final int KP_SEVEN = inc++;
	public static final int KP_EIGHT = inc++;
	public static final int KP_NINE = inc++;
	public static final int KP_ZERO = inc++;
	//=============================================================================================

	//=============================================================================================
	public int  code;
	public char character;
	//=============================================================================================

	//=============================================================================================
	public KeyData() {
		clear();
	}
	//=============================================================================================
	
	//=============================================================================================
	public KeyData(int code) {
		set(code);
	}
	//=============================================================================================
	
	//=============================================================================================
	public KeyData(int code, char character) {
		set(code, character);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void clear() {
		set(NONE, '\0');
	}
	//=============================================================================================

	//=============================================================================================
	public void set(int code) {
		set(code, '\0');
	}
	//=============================================================================================
	
	//=============================================================================================
	public void set(int code, char character) {
		this.code = code;
		this.character = character;
	}
	//=============================================================================================
	
}
//*************************************************************************************************
