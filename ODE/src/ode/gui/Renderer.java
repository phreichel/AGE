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
		if (!widget.match(Flag.DISPLAYED)) return;
		renderArea(widget, graphics);
		renderSiblings(widget.children(), graphics);
	}
	//=============================================================================================

	//=============================================================================================
	private void renderArea(Widget widget, Graphics graphics) {

		// render background
		Color4f bg = widget.background();
		if (bg.w != 0f) {
			graphics.color(bg);
			graphics.fillRectangle(0, 0, widget.dimension().x, widget.dimension().y);
		}
		
		// render foreground
		Color4f fg = widget.foreground();
		if (fg.w != 0f) {
			graphics.color(fg);
			graphics.drawRectangle(0, 0, widget.dimension().x, widget.dimension().y);
		}
		
		// render text
		String text = widget.text();
		String font = widget.font();
		if (font == null) font = "ode.font.default";
		if (text != null) {
			graphics.drawText(text, 0, 0 + widget.dimension().y, font);
		}
	}
	//=============================================================================================

}
//*************************************************************************************************
