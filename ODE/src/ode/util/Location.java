//*************************************************************************************************
package ode.util;
//*************************************************************************************************

//*************************************************************************************************
public class Location {

	//=============================================================================================
	public float x;
	public float y;
	//=============================================================================================

	//=============================================================================================
	public Location() {
		
	}
	//=============================================================================================

	//=============================================================================================
	public Location(Location l) {
		set(l);
	}
	//=============================================================================================

	//=============================================================================================
	public Location(float x, float y) {
		set(x, y);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void set(Location l) {
		x = l.x;
		y = l.y;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}
	//=============================================================================================
	
}
//*************************************************************************************************
