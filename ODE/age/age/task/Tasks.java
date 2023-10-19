/*
 * Commit: b9aac866e336cadf8afb32426af821162d9ade40
 * Date: 2023-09-23 18:35:56+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 * Commit: 8e1aad78b74edd61cd0e139ade57196ed44c219a
 * Date: 2023-09-22 22:14:40+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 * Commit: 4ba52177b03c84982e184472f4e51a9157e9a67f
 * Date: 2023-09-21 20:15:06+02:00
 * Author: Philip Reichel
 * Comment: Cleaning all up
 *
 * Commit: dd56eccbb9b27eab45e556efde8d3bb7d0f4ce97
 * Date: 2023-09-20 17:16:57+02:00
 * Author: pre7618
 * Comment: Renamings
 *
 * Commit: efba03d014f9f8690fc57eb3949bb36f7ed2e269
 * Date: 2023-09-19 07:28:45+02:00
 * Author: Philip Reichel
 * Comment: todo tasks
 *
 * Commit: 0a0626956b5c49e78d12757c077c1f9694a9dcc2
 * Date: 2023-09-16 21:35:52+02:00
 * Author: Philip Reichel
 * Comment: System Menu and Improved Messaging
 *
 * Commit: d45900c73e4996bb998fd5104e99961af312eea3
 * Date: 2023-09-12 14:50:52+02:00
 * Author: pre7618
 * Comment: Added special Task phase
 *
 */

//*************************************************************************************************
package age.task;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import age.input.Event;
import age.input.Events;
import age.input.InputType;

//*************************************************************************************************
public class Tasks {

	//=============================================================================================
	private final List<String> inbox  = new ArrayList<>();
	private final List<String> outbox = new ArrayList<>();
	private final Map<String, Task> tasks = new HashMap<>();
	//=============================================================================================

	//=============================================================================================
	public void assign(Events events) {
		events.assign(InputType.TASK_COMMAND, this::handleEvent);
	}
	//=============================================================================================

	//=============================================================================================
	private void handleEvent(Event e) {
		put(e.command());
	}
	//=============================================================================================
	
	//=============================================================================================
	public void put(String command) {
		synchronized (inbox) {			
			inbox.add(command);
		}
	}
	//=============================================================================================

	//=============================================================================================
	public void assign(String command, Task task) {
		tasks.put(command, task);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void update() {
		synchronized (inbox) {
			outbox.addAll(inbox);
			inbox.clear();
		}
		for (String command : outbox) {
			perform(command);
		}
		outbox.clear();
	}
	//=============================================================================================

	//=============================================================================================
	private void perform(String command) {
		Task task = tasks.get(command);
		if (task != null) {
			task.perform();
		}
	}
	//=============================================================================================

}
//*************************************************************************************************
