//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import java.util.List;

import javax.vecmath.Color4f;

import ode.gui.Widget.TAG;
import ode.npa.Graphics;

//*************************************************************************************************
public class Renderer {

	//=============================================================================================
	private List<Widget> roots;
	//=============================================================================================

	//=============================================================================================
	public Renderer(List<Widget> roots) {
		this.roots = roots;
	}
	//=============================================================================================

	//=============================================================================================
	public void render(Graphics graphics) {
		graphics.ortho();
		renderSiblings(roots, graphics);
	}
	//=============================================================================================

	//=============================================================================================
	private void renderSiblings(List<Widget> siblings, Graphics graphics) {
		for (Widget widget : siblings) {
			graphics.pushTransformation();
			graphics.translate(widget.position());
			renderWidget(widget, graphics);
			graphics.popTransformation();
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void renderWidget(Widget widget, Graphics graphics) {
		if (widget.match(TAG.DISPLAY)) {
			renderArea(widget, graphics);
			renderSiblings(widget.children(), graphics);
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void renderArea(Widget widget, Graphics graphics) {
		switch (widget.type()) {
		case LABEL: renderLabel(widget, graphics); break;
		case BUTTON: renderButton(widget, graphics); break;
		case TEXTFIELD: renderTextfield(widget, graphics); break;
		case GROUP: renderGroup(widget, graphics); break;
		default: renderGeneric(widget, graphics); break;
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void renderLabel(Widget widget, Graphics graphics) {
		renderGeneric(widget, graphics);
		graphics.drawText(widget.text(), 3, widget.dimension().y-3, "ode.system");
	}
	//=============================================================================================
	
	//=============================================================================================
	private void renderButton(Widget widget, Graphics graphics) {
		renderGeneric(widget, graphics);
		renderBorder(widget, graphics);
		graphics.drawText(widget.text(), 3, widget.dimension().y-3, "ode.system");
	}
	//=============================================================================================
	
	//=============================================================================================
	private void renderTextfield(Widget widget, Graphics graphics) {
		renderGeneric(widget, graphics);
		graphics.drawText(widget.text(), 3, widget.dimension().y-3, "ode.system");
	}
	//=============================================================================================

	//=============================================================================================
	private void renderGroup(Widget widget, Graphics graphics) {
		renderGeneric(widget, graphics);
		renderBorder(widget, graphics);
	}
	//=============================================================================================
	
	//=============================================================================================
	private void renderGeneric(Widget widget, Graphics graphics) {
		Color4f fg = widget.style().getColor(Style.FOREGROUND);
		Color4f bg = widget.tagged(TAG.HOVER) ?
			widget.style().getColor(Style.BACKGROUND_HOVER) :
			widget.style().getColor(Style.BACKGROUND);
		graphics.color(bg);
		graphics.drawImage(0, 0, widget.dimension().x, widget.dimension().y, "ode.test");
		graphics.color(fg);
		graphics.drawRectangle(0, 0, widget.dimension().x, widget.dimension().y);
	}
	//=============================================================================================

	//=============================================================================================
	private void renderBorder(Widget widget, Graphics graphics) {
		Color4f b1 = widget.style().getColor(Style.BORDER_LIGHT);
		Color4f b2 = widget.style().getColor(Style.BORDER_DARK);
		graphics.color(b1);
		graphics.draw2DLineStrip(
			1, widget.dimension().y-1,
			1, 1,
			widget.dimension().x-2, 1);
		graphics.color(b2);
		graphics.draw2DLineStrip(
			widget.dimension().x-1, 1,
			widget.dimension().x-1, widget.dimension().y-1,
			2, widget.dimension().y-1);
	}
	//=============================================================================================
	
}
//*************************************************************************************************
