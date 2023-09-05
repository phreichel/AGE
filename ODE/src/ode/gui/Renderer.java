//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import java.util.List;

import javax.vecmath.Color4f;
import javax.vecmath.Vector3f;

import ode.npa.Graphics;

//*************************************************************************************************
public class Renderer {

	//=============================================================================================
	private List<Widget> roots;
	//=============================================================================================

	//=============================================================================================
	private Vector3f ppos;
	//=============================================================================================
	
	//=============================================================================================
	public Renderer(Vector3f ppos, List<Widget> roots) {
		this.ppos = ppos;
		this.roots = roots;
	}
	//=============================================================================================

	//=============================================================================================
	public void render(Graphics graphics) {
		graphics.ortho();
		renderSiblings(roots, graphics);
		graphics.color(1, 1, 0, 1);
		float s = ppos.z;
		float h = s *.5f;
		graphics.drawRectangle(ppos.x-h, ppos.y-h, s, s);
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
		renderArea(widget, graphics);
		renderSiblings(widget.children(), graphics);
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
		Color4f fg = widget.style().foreground();
		Color4f bg = widget.style().background();
		graphics.color(bg);
		graphics.drawImage(0, 0, widget.dimension().x, widget.dimension().y, "ode.test");
		//graphics.fillRectangle(0, 0, widget.dimension().x, widget.dimension().y);
		graphics.color(fg);
		graphics.drawRectangle(0, 0, widget.dimension().x, widget.dimension().y);
	}
	//=============================================================================================

	//=============================================================================================
	private void renderBorder(Widget widget, Graphics graphics) {
		Color4f b1 = widget.style().borderLight();
		Color4f b2 = widget.style().borderDark();
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
