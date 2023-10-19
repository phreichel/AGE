/*
 * Commit: aa20ad72b29161d58217d659b23accf2664ef3cf
 * Date: 2023-09-26 23:19:19+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 */

//*************************************************************************************************
package age.gui;
//*************************************************************************************************

//*************************************************************************************************
public class Scrollable {

	//=============================================================================================
	public int size;
	public int page;
	public int mark;
	//=============================================================================================

	//=============================================================================================
	public void set(
			int size,
			int page,
			int mark) {
		this.size = size;
		this.page = page;
		this.mark(mark);
	}
	//=============================================================================================

	//=============================================================================================
	public void mark(int mark) {
		this.mark = mark;
		this.mark = Math.max(0, this.mark);
		this.mark = Math.min(this.size - 1, this.mark);
	}
	//=============================================================================================

	//=============================================================================================
	public void scrollToStart() {
		this.mark(0);
	}
	//=============================================================================================

	//=============================================================================================
	public void scrollToEnd() {
		this.mark(this.size + this.page - 1);
	}
	//=============================================================================================

	//=============================================================================================
	public void scrollBy(int delta) {
		this.mark(this.mark + delta);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void scrollOneToStart() {
		this.mark(this.mark-1);
	}
	//=============================================================================================

	//=============================================================================================
	public void scrollOneToEnd() {
		this.mark(this.mark+1);
	}
	//=============================================================================================

	//=============================================================================================
	public void scrollPageToStart() {
		this.mark(this.mark-this.page+1);
	}
	//=============================================================================================

	//=============================================================================================
	public void scrollPageToEnd() {
		this.mark(this.mark+this.page-1);
	}
	//=============================================================================================
	
}
//*************************************************************************************************
