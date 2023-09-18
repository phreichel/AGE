//*************************************************************************************************
package age.clock;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.List;

/**************************************************************************************************
 * The system Clock.
 * Normally only one instance of that clock is created per application.
 * It manages and synchronizes multiple Alarm objects that each are responsible to manage the
 * time periodic triggering of a specific Task  
 */
public class Clock {

	/**********************************************************************************************
	 * The internal list to manage Alarm objects
	 */
	private final List<Alarm> alarms = new ArrayList<>();

	/**********************************************************************************************
	 * Method to add a Task to be periodically triggered after a given time period has elapsed. 
	 * @param nanoperiod the time period in nano seconds after which the Task is triggered.
	 * @param task the Task to be triggered and executed.
	 */
	public void add(long nanoperiod, Task task) {
		alarms.add(new Alarm(nanoperiod, task));
	}

	/**********************************************************************************************
	 * Method to add a Task to be periodically triggered after a given time period has elapsed. 
	 * @param fps the trigger frames per second (i. e. how often the Task is triggered per second)
	 * @param task the Task to be triggered and executed.
	 */
	public void addFPS(int fps, Task task) {
		alarms.add(new Alarm(1000000000L / fps, task));
	}
	
	/**********************************************************************************************
	 * Initializes time measurement for all Alarm objects added to the Clock
	 */
	public void init() {
		long nanotime = System.nanoTime();
		for (Alarm alarm : alarms) {
			alarm.init(nanotime);
		}
	}

	/**********************************************************************************************
	 * update time measurement for all Alarm objects added to the Clock, and indirectly triggers
	 * and executes Tasks when their Alarm period has elapsed.
	 */
	public void update() {
		long nanotime = System.nanoTime();
		for (Alarm alarm : alarms) {
			alarm.update(nanotime);
		}
	}
	
}
//*************************************************************************************************
