//*************************************************************************************************
package age.clock;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.List;

//*************************************************************************************************
public class Clock {

	//=============================================================================================
	private final List<Alarm> alarms = new ArrayList<>();
	//=============================================================================================

	//=============================================================================================
	public void add(long nanoperiod, Task task) {
		alarms.add(new Alarm(nanoperiod, task));
	}
	//=============================================================================================

	//=============================================================================================
	public void addFPS(int fps, Task task) {
		alarms.add(new Alarm(1000000000L / fps, task));
	}
	//=============================================================================================
	
	//=============================================================================================
	public void init() {
		long nanotime = System.nanoTime();
		for (Alarm alarm : alarms) {
			alarm.init(nanotime);
		}
	}
	//=============================================================================================

	//=============================================================================================
	public void update() {
		long allNext = Long.MAX_VALUE;
		long nanotime = System.nanoTime();
		for (Alarm alarm : alarms) {
			long next = alarm.update(nanotime);
			allNext = Math.min(allNext, next);
		}
		try {
			long ms = allNext / 1000000L;
			long ns = allNext % 1000000L;
			Thread.sleep(ms, (int) ns);
		} catch (Exception e) {}
	}
	//=============================================================================================
	
}
//*************************************************************************************************
