/*
 * Commit: 39b8f74293b119c48ba58cce6da3104541cab12a
 * Date: 2023-09-26 00:54:32+02:00
 * Author: Philip Reichel
 * Comment: Added Walker Animation
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
 * Commit: a6ff64f7ddf561fda51d80cf514edfa3e03c3fbf
 * Date: 2023-09-19 23:41:44+02:00
 * Author: Philip Reichel
 * Comment: Further comments
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

//*************************************************************************************************
class Alarm {

	//=============================================================================================
	private long nanoperiod;
	private Task task;
	private long mark;
	//=============================================================================================

	//=============================================================================================
	Alarm(long nanoperiod, Task task) {
		this.nanoperiod = nanoperiod;
		this.task = task;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void init(long nanotime) {
		mark = nanotime;
	}
	//=============================================================================================
	
	//=============================================================================================
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
	//=============================================================================================

	//=============================================================================================
	private void trigger(int count, float dT) {
		task.run(count, nanoperiod, dT);
	}
	//=============================================================================================
	
}
//*************************************************************************************************
