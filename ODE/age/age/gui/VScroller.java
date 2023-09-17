//*************************************************************************************************
package age.gui;

import java.util.UUID;

import age.task.Task;
import age.task.Tasks;

//*************************************************************************************************

//*************************************************************************************************
public class VScroller extends Widget {

	//=============================================================================================
	private Widget btnUp;
	private Widget bar;
	private Widget btnDn;
	//=============================================================================================

	//=============================================================================================
	private UUID uuid = UUID.randomUUID();
	//=============================================================================================
	
	//=============================================================================================
	public VScroller() {
		create();
	}
	//=============================================================================================

	//=============================================================================================
	public void assign(Tasks tasks, Task taskUp, Task taskDn) {

		String cmdUp = "up-" + uuid.toString();
		String cmdDn = "down-" + uuid.toString();
		
		tasks.assign(cmdUp, taskUp);
		tasks.assign(cmdDn, taskDn);
		
	}
	//=============================================================================================
	
	//=============================================================================================
	private void create() {
		
		String cmdUp = "up-" + uuid.toString();
		String cmdDn = "down-" + uuid.toString();
		
		dimension(10, 70);
		dock(1, 1, 0, 1);
		
		btnUp = new Widget(Flag.BUTTON);
		btnUp.position(0, 0);
		btnUp.dimension(11, 10);
		btnUp.image("arrowup");
		btnUp.command(cmdUp);
		btnUp.dock(0, 1, 0, 0);
		add(btnUp);

		bar = new Widget(Flag.BOX);
		bar.position(0, 12);
		bar.dimension(10, 46);
		bar.dock(0, 1, 0, 1);
		add(bar);
		
		btnDn = new Widget(Flag.BUTTON);
		btnDn.position(0, 60);
		btnDn.dimension(11, 10);
		btnDn.dock(0, 1, 1, 1);
		btnDn.image("arrowdown");
		btnDn.command(cmdDn);
		add(btnDn);
		
	}
	//=============================================================================================

}
//*************************************************************************************************
