/*
 * Commit: 39b8f74293b119c48ba58cce6da3104541cab12a
 * Date: 2023-09-26 00:54:32+02:00
 * Author: Philip Reichel
 * Comment: Added Walker Animation
 *
 * Commit: 01f6edb422e32c691886b08481b74af734d2419d
 * Date: 2023-09-25 00:22:26+02:00
 * Author: Philip Reichel
 * Comment: Added Mesh
 *
 * Commit: 2853cdf971fb84f71142c23c08cd28f08585066b
 * Date: 2023-09-23 19:11:40+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 * Commit: 4ba52177b03c84982e184472f4e51a9157e9a67f
 * Date: 2023-09-21 20:15:06+02:00
 * Author: Philip Reichel
 * Comment: Cleaning all up
 *
 * Commit: c9fba54298823be83ab349a7f2e0eb3b2c607a44
 * Date: 2023-09-18 22:02:46+02:00
 * Author: Philip Reichel
 * Comment: upd
 *
 * Commit: 8dc6c41a602679fa36a34d3681764629b4ec6197
 * Date: 2023-09-12 13:19:01+02:00
 * Author: pre7618
 * Comment: Added Logging for AGE and Events thread safety
 *
 */

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
	// TODO: revisit the thread sleep mechanism (it is not implemented correctly)
	public void update() {
		long nanotime = System.nanoTime();
		for (Alarm alarm : alarms) {
			alarm.update(nanotime);
		}
	}
	//=============================================================================================
	
}
//*************************************************************************************************
