//*************************************************************************************************
package age.gui;
//*************************************************************************************************

import java.util.List;
import age.port.Graphics;
import age.port.Renderable;

//*************************************************************************************************
public class Rendering implements Renderable {

	//=============================================================================================
	private final Widgets widgets;
	//=============================================================================================

	//=============================================================================================
	public Rendering(Widgets widgets) {
		this.widgets = widgets;
	}
	//=============================================================================================

	//=============================================================================================
	public void render(Graphics g) {
		Widget root = widgets.root();
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
		if (widget.match(Flag.BOX)) {
			g.color(.4f, 0f, 0f);
			g.rectangle(widget.dimension(), true);
		} else if (widget.match(Flag.FRAME)) {
			g.color(.2f, 0f, 0f);
			g.rectangle(widget.dimension(), false);
			g.color(1f, 0, 0);
			g.rectangle(widget.dimension(), true);
		} else if (widget.match(Flag.BUTTON)) {
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
		} else if (widget.match(Flag.CANVAS)) {
			g.color(0f, 0f, .2f);
			g.rectangle(widget.dimension(), false);
			g.color(1f, 0f, 0f);
			g.rectangle(widget.dimension(), true);
		} else if (widget.match(Flag.TITLE)) {
			if (!widget.match(Flag.HOVERED))
				g.color(.2f, 0f, 0f);
			else
				g.color(.4f, 0f, 0f);
			g.rectangle(widget.dimension(), false);
			g.color(.4f, 0f, 0f);
			g.rectangle(widget.dimension(), true);
			g.text(3, widget.dimension().y-3, widget.text());
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
