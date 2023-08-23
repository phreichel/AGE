//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import java.util.Map;
import java.util.Map.Entry;

import ode.platform.Graphics;

//*************************************************************************************************
public class RenderSystem {

	//=============================================================================================
	private static final float FACTOR = 0.01f;
	private static final float OFFSET = 0.001f;
	//=============================================================================================
	
	//=============================================================================================
	private Map<Widget, RenderData> renderMap;
	//=============================================================================================

	//=============================================================================================
	public RenderSystem(Map<Widget, RenderData> renderMap) {
		this.renderMap = renderMap;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void update(Graphics graphics) {
		for (Entry<Widget, RenderData> entry : renderMap.entrySet()) {
			render(entry.getKey(), graphics);
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void render(Widget widget, Graphics graphics) {
		RenderData renderData = widget.gui.renderMap.get(widget);
		if (renderData.type == RenderData.BOX) renderBox(widget, graphics);
		if (renderData.type == RenderData.LABEL) renderLabel(widget, graphics);
		if (renderData.type == RenderData.BUTTON) renderButton(widget, graphics);
	}
	//=============================================================================================

	//=============================================================================================
	private void renderBox(Widget widget, Graphics graphics) {
		float x = widget.globalX();
		float y = widget.globalY();
		float z = widget.globalZ() * FACTOR;	
		float w = widget.width;
		float h = widget.height;
		graphics.pushTransform();
		graphics.translate(x, y, z);
		graphics.setColor(.3f, .3f, .5f);
		graphics.fillRectangle(0, 0, w, h);
		graphics.translate(0, 0, 0.001f);
		graphics.setColor( 0, 0, .4f);
		graphics.drawRectangle(0, 0, w, h);
		graphics.popTransform();
	}
	//=============================================================================================

	//=============================================================================================
	private void renderButton(Widget widget, Graphics graphics) {
		String label = widget.gui.textMap.get(widget);
		ClickData clickData = widget.gui.clickMap.get(widget);
		float x = widget.globalX();
		float y = widget.globalY();
		float z = widget.globalZ() * FACTOR;	
		float w = widget.width;
		float h = widget.height;
		graphics.pushTransform();
		graphics.translate(x, y, z);
		if (!widget.flags.get(Widget.HOVER)) {
			graphics.setColor(.4f, .4f, 1f);
		} else {
			graphics.setColor(.6f, .6f, 1f);
		}
		graphics.fillRectangle(0, 0, w, h);
		if (clickData.decay > 0) {
			graphics.translate(0, 0, OFFSET);
			graphics.setColor(1, 1, 1);
			graphics.fillRectangle(w * (1f-clickData.decay) * .5f, 0, w * clickData.decay, h);
		}
		graphics.translate(0, 0, OFFSET);
		graphics.setColor( 0, 0, .8f);
		graphics.drawRectangle(0, 0, w, h);
		graphics.translate(0, 0, OFFSET);
		graphics.drawText(label, 0, 0, 0, w, h, 1, 1, 1);
		graphics.popTransform();
	}
	//=============================================================================================

	//=============================================================================================
	private void renderLabel(Widget widget, Graphics graphics) {
		String label = widget.gui.textMap.get(widget);
		float x = widget.globalX();
		float y = widget.globalY();
		float z = widget.globalZ() * FACTOR;	
		float w = widget.width;
		float h = widget.height;
		graphics.pushTransform();
		graphics.translate(x, y, z);
		graphics.setColor(.4f, .4f, .4f);
		graphics.fillRectangle(0, 0, w, h);
		graphics.translate(0, 0, OFFSET);
		graphics.setColor(.2f, .2f, .2f);
		graphics.drawRectangle(0, 0, w, h);
		graphics.translate(0, 0, OFFSET);
		graphics.drawText(label, 0, 0, 0, w, h, 1, 1, 1);
		graphics.popTransform();
	}
	//=============================================================================================
	
}
//*************************************************************************************************
