//*************************************************************************************************
package age.gui;
//*************************************************************************************************

import java.util.List;

import age.port.Graphics;
import age.port.Renderable;

//*************************************************************************************************
public class Renderer implements Renderable {

	//=============================================================================================
	private final GUI gui;
	//=============================================================================================

	//=============================================================================================
	public Renderer(GUI gui) {
		this.gui = gui;
	}
	//=============================================================================================

	//=============================================================================================
	public void render(Graphics g) {
		Widget root = gui.root();
		g.mode2D();
		render(g, root);
	}
	//=============================================================================================

	//=============================================================================================
	private void render(Graphics g, Widget widget) {
		if (!widget.match(WFlag.HIDDEN)) {
			g.pushTransformation();
			g.translate(widget.position());
			renderWidget(g, widget);
			renderChildren(g, widget.children());
			g.popTransformation();
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void renderWidget(Graphics g, Widget widget) {
		for (WFlag wFlag :widget.wFlags()) {
			switch (wFlag) {
				case BOX -> renderBox(g, widget);
				case FRAME -> renderFrame(g, widget);
				case BUTTON -> renderButton(g, widget);
				case CANVAS -> renderCanvas(g, widget);
				case TITLE -> renderTitle(g, widget);
				case HANDLE -> renderHandle(g, widget);
				case MULTILINE -> renderMultiline(g, widget);
				default -> {}
			}
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void renderBox(Graphics g, Widget widget) {
		g.color(.4f, 0f, 0f);
		g.rectangle(widget.dimension(), true);
	}
	//=============================================================================================

	//=============================================================================================
	private void renderFrame(Graphics g, Widget widget) {
		g.color(.2f, 0f, 0f);
		g.rectangle(widget.dimension(), false);
		g.color(1f, 0, 0);
		g.rectangle(widget.dimension(), true);
	}
	//=============================================================================================

	//=============================================================================================
	private void renderButton(Graphics g, Widget widget) {
		if (!widget.match(WFlag.HOVERED))
			g.color(.8f, 0f, 0f);
		else
			g.color(1f, .3f, .3f);
		String image = widget.component(WItem.IMAGE_NAME, String.class);
		if (image != null) {
			g.texture(0, 0, widget.dimension().x, widget.dimension().y, image);
		} else {
			g.rectangle(widget.dimension(), false);
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void renderCanvas(Graphics g, Widget widget) {
		g.color(0f, 0f, .2f);
		g.rectangle(widget.dimension(), false);
		g.color(1f, 0f, 0f);
		g.rectangle(widget.dimension(), true);
	}
	//=============================================================================================

	//=============================================================================================
	private void renderTitle(Graphics g, Widget widget) {
		if (!widget.match(WFlag.HOVERED))
			g.color(.2f, 0f, 0f);
		else
			g.color(.4f, 0f, 0f);
		g.rectangle(widget.dimension(), false);
		g.color(.4f, 0f, 0f);
		g.rectangle(widget.dimension(), true);
		String text = widget.component(WItem.TEXT, String.class);
		g.text(3, widget.dimension().y-3, text, "title");
	}
	//=============================================================================================

	//=============================================================================================
	private void renderHandle(Graphics g, Widget widget) {
		g.color(1f, 0f, 0f);
		g.rectangle(widget.dimension(), false);
	}
	//=============================================================================================

	//=============================================================================================
	private void renderMultiline(Graphics g, Widget widget) {
		
		String text = widget.component(WItem.TEXT, String.class);
		
		Scrollable scstate = widget.component(WItem.SCROLLABLE_VERTICAL, Scrollable.class);
		Multiline  mlstate = widget.component(WItem.MULTILINE_STATE, Multiline.class);

		g.color(0f, 0f, .5f);
		g.rectangle(
			widget.dimension(),
			false);

		g.color(1f, 0f, 0f);
		g.rectangle(
			widget.dimension(),
			true);
		
		if (!widget.match(WFlag.CLEAN)) {
			g.calcMultitext(
				text,
				widget.dimension().x-6,
				widget.dimension().y-6,
				"text",
				scstate,
				mlstate);
			widget.flag(WFlag.CLEAN);
		}
		
		for (int i=0; i<scstate.page; i++) {
			int idx = scstate.mark + i;
			if (idx >= scstate.size) break;
			CharSequence seq = text.subSequence(
				mlstate.indices.get(idx*2+0),
				mlstate.indices.get(idx*2+1)
			);
			g.text(3, 3 + (1+i) * mlstate.height, seq, "text");
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	private void renderChildren(Graphics g, List<Widget> children) {
		for (Widget child : children) {
			render(g, child);
		}
	}
	//=============================================================================================
	
}
//*************************************************************************************************
