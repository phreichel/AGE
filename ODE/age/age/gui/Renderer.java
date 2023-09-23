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
		if (!widget.match(Flag.HIDDEN)) {
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
		for (Flag flag :widget.flags()) {
			switch (flag) {
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
		if (!widget.match(Flag.HOVERED))
			g.color(.8f, 0f, 0f);
		else
			g.color(1f, .3f, .3f);
		String image = widget.component(WidgetComponent.IMAGE_NAME, String.class);
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
		if (!widget.match(Flag.HOVERED))
			g.color(.2f, 0f, 0f);
		else
			g.color(.4f, 0f, 0f);
		g.rectangle(widget.dimension(), false);
		g.color(.4f, 0f, 0f);
		g.rectangle(widget.dimension(), true);
		String text = widget.component(WidgetComponent.TEXT, String.class);
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
		Multiline ml = (Multiline) widget;
		String text = ml.component(WidgetComponent.TEXT, String.class);
		g.color(0f, 0f, .5f);
		g.rectangle(
			widget.dimension(),
			false);
		g.color(1f, 0f, 0f);
		g.rectangle(
			widget.dimension(),
			true);
		g.calcMultitext(
			text,
			ml.dimension().x-6,
			ml.dimension().y-6,
			"text",
			ml.buffer());
		ml.update();
		for (int i=0; i<ml.page(); i++) {
			int idx = ml.offset() + i;
			if (idx >= ml.count()) break;
			CharSequence seq = ml.line(idx);
			g.text(3, 3 + (1+i)*ml.lineHeight(), seq, "text");
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
