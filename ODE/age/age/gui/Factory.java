//*************************************************************************************************
package age.gui;
//*************************************************************************************************

//*************************************************************************************************
public class Factory {

	//=============================================================================================
	public Widget createIconButton(
			String image,
			String command) {
		Widget button = new Widget(Flag.BUTTON);
		button.dimension(20, 20);
		button.flag(Flag.COMMAND_HANDLE);
		button.component(WidgetComponent.COMMAND, command);
		button.component(WidgetComponent.IMAGE_NAME, image);
		return button;
	}
	//=============================================================================================

	//=============================================================================================
	public Widget createWindow(float w, float h, String caption) {
		
		final float W = w;
		final float H = h;
		final float V = 20;
		final float G = 5;

		Widget window = new Widget();
		window.flag(Flag.FRAME);
		window.dimension(W, H);
		
		Widget title = new Widget();
		title.flag(Flag.TITLE);
		title.flag(Flag.DRAG_HANDLE);
		title.component(WidgetComponent.DRAGGED_WIDGET, window);
		title.position(G, G);
		title.dimension(W-V-3*G, V);
		title.dock(0, 1, 0, 0);
		title.component(WidgetComponent.TEXT, caption);
		
		Widget close = new Widget();
		close.flag(Flag.BUTTON);
		close.flag(Flag.CLOSE_HANDLE);
		close.component(WidgetComponent.CLOSED_WIDGET, window);
		close.position(W-V-G, G);
		close.dimension(V, V);
		close.dock(1, 1, 0, 0);
		close.component(WidgetComponent.IMAGE_NAME, "close");
		
		Widget page = new Widget();
		page.flag(Flag.CANVAS);
		page.position(G, V+2*G);
		page.dimension(W-2*G, H-V-3*G);
		page.dock(0, 1, 0, 1);

		Widget size = new Widget();
		size.flag(Flag.BUTTON);
		size.flag(Flag.RESIZE_HANDLE);
		size.component(WidgetComponent.RESIZED_WIDGET, window);
		size.position(W-V-G, H-V-G);
		size.dimension(V, V);
		size.dock(1, 1, 1, 1);
		size.component(WidgetComponent.IMAGE_NAME, "size");
		
		window.add(title);
		window.add(close);
		window.add(page);
		window.add(size);
		
		return window;
	
	}
	//=============================================================================================

	//=============================================================================================
	public Widget createVerticalScrollBar(Widget target) {

		ScrollableState scstate = target.component(WidgetComponent.SCROLLABLE_VERTICAL, ScrollableState.class);
		
		Widget scrollbar = new Widget();
		scrollbar.flag(Flag.SCROLLBAR);
		scrollbar.component(WidgetComponent.SCROLLABLE_VERTICAL, scstate);
		scrollbar.dimension(10, 70);
		scrollbar.dock(1, 1, 0, 1);

		Widget slidebar = new Widget();
		slidebar.flag(Flag.BOX);
		slidebar.position(0, 12);
		slidebar.dimension(10, 46);
		slidebar.dock(0, 1, 0, 1);
		
		Widget handle = new Widget();
		handle.flag(Flag.HANDLE);
		handle.flag(Flag.SCROLLBAR_HANDLE);
		handle.component(WidgetComponent.SCROLL_WIDGET, scrollbar);
		handle.position(0, 0);
		handle.dimension(10, 20);
		
		Widget btnStart = new Widget();
		btnStart.flag(Flag.BUTTON);
		btnStart.flag(Flag.SCROLL_START);
		btnStart.component(WidgetComponent.SCROLL_WIDGET, scrollbar);
		btnStart.position(0, 0);
		btnStart.dimension(11, 10);
		btnStart.component(WidgetComponent.IMAGE_NAME, "arrowup");
		
		Widget btnEnd = new Widget();
		btnEnd.flag(Flag.BUTTON);
		btnEnd.flag(Flag.SCROLL_END);
		btnEnd.component(WidgetComponent.SCROLL_WIDGET, scrollbar);
		btnEnd.position(0, 60);
		btnEnd.dimension(11, 10);
		btnEnd.dock(0, 1, 1, 1);
		btnEnd.component(WidgetComponent.IMAGE_NAME, "arrowdown");
		
		slidebar.add(handle);
		scrollbar.add(slidebar);
		scrollbar.add(btnStart);
		scrollbar.add(btnEnd);

		return scrollbar;

	}
	//=============================================================================================

	//=============================================================================================
	public Widget createMultiLine(boolean verticalScroll) {

		ScrollableState state = new ScrollableState();
		MultilineState  multi = new MultilineState();
		
		Widget multiline = new Widget();
		multiline.flag(Flag.MULTILINE);
		multiline.component(WidgetComponent.SCROLLABLE_VERTICAL, state);
		multiline.component(WidgetComponent.MULTILINE_STATE, multi);
		multiline.dimension(600, 800);
		
		if (verticalScroll) {
			Widget scroller = createVerticalScrollBar(multiline);
			scroller.position(600-15, 5);
			scroller.dimension(10, 770);
			multiline.add(scroller);
		}
		
		return multiline;

	}
	//=============================================================================================
	
}
//*************************************************************************************************

