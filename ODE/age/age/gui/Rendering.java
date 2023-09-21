//*************************************************************************************************
package age.gui;
//*************************************************************************************************

import java.util.List;
import age.port.Graphics;
import age.port.Renderable;

//*************************************************************************************************
public class Rendering implements Renderable {

	//=============================================================================================
	private final GUI gui;
	//=============================================================================================

	//=============================================================================================
	public Rendering(GUI gui) {
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
		if      (renderBox(g, widget));
		else if (renderFrame(g, widget));
		else if (renderButton(g, widget));
		else if (renderCanvas(g, widget));
		else if (renderTitle(g, widget));
		else if (renderHandle(g, widget));
		else if (renderMultiline(g, widget));
		else {
			// do nothing;
		}
	}
	//=============================================================================================

	//=============================================================================================
	private boolean renderBox(Graphics g, Widget widget) {
		if (widget.match(Flag.BOX)) {
			g.color(.4f, 0f, 0f);
			g.rectangle(widget.dimension(), true);
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean renderFrame(Graphics g, Widget widget) {
		if (widget.match(Flag.FRAME)) {
			g.color(.2f, 0f, 0f);
			g.rectangle(widget.dimension(), false);
			g.color(1f, 0, 0);
			g.rectangle(widget.dimension(), true);
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean renderButton(Graphics g, Widget widget) {
		if (widget.match(Flag.BUTTON)) {
			if (!widget.match(Flag.HOVERED))
				g.color(.8f, 0f, 0f);
			else
				g.color(1f, .3f, .3f);
			String image = widget.image();
			if (image != null) {
				g.texture(0, 0, widget.dimension().x, widget.dimension().y, image);
			} else {
				g.rectangle(widget.dimension(), false);
			}
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean renderCanvas(Graphics g, Widget widget) {
		if (widget.match(Flag.CANVAS)) {
			g.color(0f, 0f, .2f);
			g.rectangle(widget.dimension(), false);
			g.color(1f, 0f, 0f);
			g.rectangle(widget.dimension(), true);
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean renderTitle(Graphics g, Widget widget) {
		if (widget.match(Flag.TITLE)) {
			if (!widget.match(Flag.HOVERED))
				g.color(.2f, 0f, 0f);
			else
				g.color(.4f, 0f, 0f);
			g.rectangle(widget.dimension(), false);
			g.color(.4f, 0f, 0f);
			g.rectangle(widget.dimension(), true);
			g.text(3, widget.dimension().y-3, widget.text(), "title");
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean renderHandle(Graphics g, Widget widget) {
		if (widget.match(Flag.HANDLE)) {
			g.color(1f, 0f, 0f);
			g.rectangle(widget.dimension(), false);
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean renderMultiline(Graphics g, Widget widget) {
		if (widget.match(Flag.MULTILINE)) {
			Multiline ml = (Multiline) widget;
			g.color(0f, 0f, .5f);
			g.rectangle(
				widget.dimension(),
				false);
			g.color(1f, 0f, 0f);
			g.rectangle(
				widget.dimension(),
				true);
			g.calcMultitext(
				ml.text(),
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
			return true;
		}
		return false;
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
