//*************************************************************************************************
package ode.clock;
//*************************************************************************************************

//*************************************************************************************************
public interface Task {

	//=============================================================================================
	public void run(int count, long nanoperiod, float dT);
	//=============================================================================================

}
//*************************************************************************************************