//*************************************************************************************************
package age.clock;
//*************************************************************************************************

/**************************************************************************************************
 * The Task interface that defines the call parameters for a Task reference method   
 */
public interface Task {

	/**********************************************************************************************
	 * The definition of the Task reference method 
	 * @param count number of full nano periods that have elapsed since last call. Normally 1
	 * @param nanoperiod the time period in nano seconds after which the Task normally is triggered 
	 * @param dT the time in seconds that did elapse since last call.  
	 */
	public void run(int count, long nanoperiod, float dT);

}
//*************************************************************************************************
