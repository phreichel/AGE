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
	private Widget handle;
	private Widget btnDn;
	//=============================================================================================

	//=============================================================================================
	private UUID uuid = UUID.randomUUID();
	//=============================================================================================

	//=============================================================================================
	private int size;
	private int page;
	private int mark;
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
	public void set(int size, int page, int mark) {

		this.size = size;
		this.page = page;
		this.mark = mark;
		
		float sFull = this.size + this.page - 1;
		float sPart = this.page;
		float scale = sPart / sFull; 
		
		float tFull = bar.dimension().y;
		float tPart = tFull * scale;
		
		handle.dimension().y = tPart;

		float srFull = this.size-1;
		float srPart = this.mark;
		float srScale = srPart / srFull;
		
		float trFull = tFull - tPart;
		float trPart = trFull * srScale;
		
		handle.position().y = trPart;
		
	}
	//=============================================================================================

	//=============================================================================================
	public void value(int mark) {
		set(this.size, this.page, mark);
	}
	//=============================================================================================

	//=============================================================================================
	public Widget handle() {
		return this.handle;
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
		
		handle = new Widget(Flag.HANDLE);
		handle.position(0, 0);
		handle.dimension(10, 20);
		bar.add(handle);
		
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
