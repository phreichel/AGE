/*
 * Commit: aa20ad72b29161d58217d659b23accf2664ef3cf
 * Date: 2023-09-26 23:19:19+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 * Commit: 01f6edb422e32c691886b08481b74af734d2419d
 * Date: 2023-09-25 00:22:26+02:00
 * Author: Philip Reichel
 * Comment: Added Mesh
 *
 * Commit: 9234eb72a89c40494fa4d9381686b27791a85924
 * Date: 2023-09-23 23:05:56+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 * Commit: 363b75a18db4122bb21de7cf1091c0bd6434b79b
 * Date: 2023-09-23 18:26:28+02:00
 * Author: Philip Reichel
 * Comment: Finished Switch to new Event Handling
Added Pointer Wheel Input
 *
 * Commit: 1fd12df4cfeb1c4d67818231e6df4905af8afdef
 * Date: 2023-09-23 17:22:11+02:00
 * Author: Philip Reichel
 * Comment: Almost reworked Multiline
 *
 * Commit: 0c072ef17032c66ffdd34170f7a2c79345c34eae
 * Date: 2023-09-23 10:27:57+02:00
 * Author: Philip Reichel
 * Comment: Updated GUI Handling - Halfway done
 *
 * Commit: 84341809c117c5bfe4a2250eb07c27d65f20777f
 * Date: 2023-09-22 22:47:47+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 */

//*************************************************************************************************
package age.gui;
//*************************************************************************************************

//*************************************************************************************************
public class Factory {

	//=============================================================================================
	private final GUI gui;
	//=============================================================================================

	//=============================================================================================
	private static final float G =  5f; 
	private static final float T =  2f; 
	private static final float B = 20f; 
	private static final float S = 10f; 
	//=============================================================================================
	
	//=============================================================================================
	public Factory(GUI gui) {
		this.gui = gui;
	}
	//=============================================================================================
	
	//=============================================================================================
	public Widget createIconButton(
			String image,
			String command) {
		Widget button = new Widget(WFlag.BUTTON);
		button.dimension(20, 20);
		button.flag(WFlag.COMMAND_HANDLE);
		button.component(WItem.COMMAND, command);
		button.component(WItem.IMAGE_NAME, image);
		return button;
	}
	//=============================================================================================

	//=============================================================================================
	public Widget createWindow(float w, float h, String caption) {
		
		final float W = w;
		final float H = h;

		Widget window = new Widget();
		window.flag(WFlag.FRAME);
		window.dimension(W, H);
		
		Widget title = new Widget();
		title.flag(WFlag.TITLE);
		title.flag(WFlag.DRAG_HANDLE);
		title.component(WItem.DRAGGED_WIDGET, window);
		title.position(G, G);
		title.dimension(W-B-3*G, B);
		title.dock(0, 1, 0, 0);
		title.component(WItem.TEXT, caption);
		
		Widget close = new Widget();
		close.flag(WFlag.BUTTON);
		close.flag(WFlag.CLOSE_HANDLE);
		close.component(WItem.CLOSED_WIDGET, window);
		close.position(W-B-G, G);
		close.dimension(B, B);
		close.dock(1, 1, 0, 0);
		close.component(WItem.IMAGE_NAME, "close");
		
		Widget page = new Widget();
		page.flag(WFlag.CANVAS);
		page.position(G, B + 2*G);
		page.dimension(W - 2*G, H - B - 3*G);
		page.dock(0, 1, 0, 1);

		Widget size = new Widget();
		size.flag(WFlag.BUTTON);
		size.flag(WFlag.RESIZE_HANDLE);
		size.component(WItem.RESIZED_WIDGET, window);
		size.position(W-B-G, H-B-G);
		size.dimension(B, B);
		size.dock(1, 1, 1, 1);
		size.component(WItem.IMAGE_NAME, "size");
		
		window.add(title);
		window.add(close);
		window.add(page);
		window.add(size);
		
		return window;
	
	}
	//=============================================================================================

	//=============================================================================================
	public Widget createVerticalScrollBar(Widget target) {

		final float Y = 100f;
		
		Scrollable scstate = target.component(WItem.SCROLLABLE_VERTICAL, Scrollable.class);
		
		Widget scrollbar = new Widget();
		scrollbar.flag(WFlag.SCROLLBAR);
		gui.layouter().add(WFlag.SCROLLBAR, scrollbar);
		scrollbar.component(WItem.SCROLLABLE_VERTICAL, scstate);
		scrollbar.dimension(S, Y);
		scrollbar.dock(1, 1, 0, 1);

		Widget slidebar = new Widget();
		slidebar.flag(WFlag.BOX);
		slidebar.flag(WFlag.SCROLLBAR_SLIDER);
		slidebar.component(WItem.SCROLL_WIDGET, scrollbar);
		slidebar.position(0, S+T);
		slidebar.dimension(S, Y - 2*(S+T));
		slidebar.dock(0, 1, 0, 1);
		
		Widget handle = new Widget();
		handle.flag(WFlag.HANDLE);
		handle.flag(WFlag.SCROLLBAR_HANDLE);
		handle.component(WItem.SCROLL_WIDGET, scrollbar);
		handle.position(0, 0);
		handle.dimension(S, 3*S);
		
		Widget btnStart = new Widget();
		btnStart.flag(WFlag.BUTTON);
		btnStart.flag(WFlag.SCROLL_START);
		btnStart.component(WItem.SCROLL_WIDGET, scrollbar);
		btnStart.position(0, 0);
		btnStart.dimension(S, S);
		btnStart.component(WItem.IMAGE_NAME, "arrowup");
		
		Widget btnEnd = new Widget();
		btnEnd.flag(WFlag.BUTTON);
		btnEnd.flag(WFlag.SCROLL_END);
		btnEnd.component(WItem.SCROLL_WIDGET, scrollbar);
		btnEnd.position(0, Y-S);
		btnEnd.dimension(S, S);
		btnEnd.dock(0, 1, 1, 1);
		btnEnd.component(WItem.IMAGE_NAME, "arrowdown");
		
		slidebar.add(handle);
		scrollbar.add(slidebar);
		scrollbar.add(btnStart);
		scrollbar.add(btnEnd);

		return scrollbar;

	}
	//=============================================================================================

	//=============================================================================================
	public Widget createMultiLine(String text) {

		Widget spacer = new Widget();
		spacer.dimension(600, 800);
		
		Scrollable state = new Scrollable();
		Multiline  multi = new Multiline();
		
		Widget multiline = new Widget();
		multiline.flag(WFlag.MULTILINE);
		multiline.flag(WFlag.POINTER_SCROLL);
		multiline.component(WItem.SCROLLABLE_VERTICAL, state);
		multiline.component(WItem.MULTILINE_STATE, multi);
		multiline.component(WItem.TEXT, text);
		multiline.dimension(580, 800);
		multiline.dock(0, 1, 0, 1);
		spacer.add(multiline);
		
		Widget scroller = createVerticalScrollBar(multiline);
		scroller.position(600-15, 5);
		scroller.dimension(10, 770);
		spacer.add(scroller);
		
		return spacer;

	}
	//=============================================================================================
	
}
//*************************************************************************************************

