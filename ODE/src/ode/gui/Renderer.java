//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import java.util.List;

import javax.vecmath.Color4f;

import ode.npa.Graphics;

//*************************************************************************************************
public class Renderer {

	//=============================================================================================
	private GUI gui;
	//=============================================================================================

	//=============================================================================================
	public Renderer(GUI gui) {
		this.gui = gui;
	}
	//=============================================================================================

	//=============================================================================================
	public void render(Graphics graphics) {
		graphics.ortho();
		renderSiblings(gui.roots(), graphics);
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
		if (widget.match(Flag.DISPLAYED)) {
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
		case FRAME: renderGroup(widget, graphics); break;
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
		Color4f fg = new Color4f(.8f, .8f, .8f, 1);
		Color4f bg = new Color4f(.6f, .6f, .6f, 1);
		graphics.color(bg);
		graphics.fillRectangle(0, 0, widget.dimension().x, widget.dimension().y);		
		graphics.color(fg);
		graphics.drawRectangle(0, 0, widget.dimension().x, widget.dimension().y);
	}
	//=============================================================================================

	//=============================================================================================
	private void renderBorder(Widget widget, Graphics graphics) {
		Color4f b1 = new Color4f(1f, 1f, 1f, 1f);
		Color4f b2 = new Color4f(.4f, .4f, .4f, 1f);
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
