//*************************************************************************************************
package ode.msg;
//*************************************************************************************************

//*************************************************************************************************
public class PointerData {

	//=============================================================================================
	private Button button;
	private int count;
	private float wx;
	private float wy;
	private float x;
	private float y;
	//=============================================================================================

	//=============================================================================================
	void set(float x, float y) {
		this.button = null;
		this.count = 0;
		this.wx = 0;
		this.wy = 0;
		this.x = x;
		this.y = y;
	}
	//=============================================================================================
	
	//=============================================================================================
	void set(Button button, float x, float y) {
		this.button = button;
		this.count = 0;
		this.wx = 0;
		this.wy = 0;
		this.x = x;
		this.y = y;
	}
	//=============================================================================================

	//=============================================================================================
	void set(float wx, float wy, float x, float y) {
		this.button = null;		
		this.count = 0;
		this.wx = wx;
		this.wy = wy;
		this.x = x;
		this.y = y;
	}
	//=============================================================================================
	
	//=============================================================================================
	void set(Button button, int count, float x, float y) {
		this.button = button;
		this.count = count;
		this.wx = 0;
		this.wy = 0;
		this.x = x;
		this.y = y;
	}
	//=============================================================================================
	
	//=============================================================================================
	public Button button() {
		return button;
	}
	//=============================================================================================

	//=============================================================================================
	public int count() {
		return count;
	}
	//=============================================================================================
	
	//=============================================================================================
	public float x() {
		return x;
	}
	//=============================================================================================

	//=============================================================================================
	public float y() {
		return y;
	}
	//=============================================================================================

	//=============================================================================================
	public float wheelX() {
		return wx;
	}
	//=============================================================================================

	//=============================================================================================
	public float wheelY() {
		return wy;
	}
	//=============================================================================================
	
}
//*************************************************************************************************
