//*************************************************************************************************
package age.clock;
//*************************************************************************************************

/**************************************************************************************************
 * This package visible class represents a single alarm schedule that periodically triggers a Task after a preset time period  
 */
class Alarm {

	/**********************************************************************************************
	 * The time period between triggers in nano seconds 
	 */
	private long nanoperiod;
	
	/**********************************************************************************************
	 * The task that is triggered periodically after each nanoperiod 
	 */
	private Task task;

	/**********************************************************************************************
	 * The time mark in nanoseconds to measure when a period elapses  
	 */
	private long mark;

	/**********************************************************************************************
	 * Package visible constructor
	 * @param nanoperiod the time period in nano seconds after which periodically the task is triggered
	 * @param task the task that is triggered periodically each time the nanoperiod time has elapsed
	 */
	Alarm(long nanoperiod, Task task) {
		this.nanoperiod = nanoperiod;
		this.task = task;
	}
	
	/**********************************************************************************************
	 * Initializes the time measurement for this Alarm object
	 * @param nanotime the current system time stamp in nano precision
	 */
	public void init(long nanotime) {
		mark = nanotime;
	}
	
	/**********************************************************************************************
	 * Updates the time measurement for this Alarm object and periodically triggers the Task after the time period has elapsed 
	 * @param nanotime the current system time stamp in nano precision
	 */
	public void update(long nanotime) {
		long delta = nanotime-mark;
		if (delta >= nanoperiod) {
			long count = delta / nanoperiod;
			long frames = count * nanoperiod;
			mark += frames;
			trigger(
				(int) count,
				(float) frames / 1000000000L);
		}
	}

	/**********************************************************************************************
	 * Internal method that is called when the time period has elapsed and the task is to be triggered by this method 
	 * @param count the count of full time periods that did elapse since the last time the Task has been triggered. Normally the count is 1.
	 * @param dT the time in seconds since the last time the Task has been triggered. 
	 */
	private void trigger(int count, float dT) {
		task.run(count, nanoperiod, dT);
	}
	
}
//*************************************************************************************************

