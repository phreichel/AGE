//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import java.util.Map;
import java.util.Map.Entry;

import ode.platform.Graphics;

//*************************************************************************************************
public class RenderSystem {

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
			render(entry.getKey(), entry.getValue(), graphics);
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void render(Widget widget, RenderData data, Graphics graphics) {
		int type = data.type;
		float x = widget.globalX();
		float y = widget.globalY();
		float z = widget.globalZ() * 0.01f;	
		float w = widget.width;
		float h = widget.height;
		graphics.pushTransform();
		graphics.translate(x, y, z);
		if (type == RenderData.BOX) graphics.setColor(.3f, .3f, .5f);
		else if (type == RenderData.BUTTON) {
			if (!widget.flags.get(Widget.HOVER)) {
				graphics.setColor(.6f, .6f,  1f);
			} else {
				graphics.setColor(1f, .6f,  .6f);
			}
		}
		graphics.fillRectangle(0, 0, w, h);
		graphics.translate(0, 0, 0.001f);
		if (type == RenderData.BOX) graphics.setColor( 0, 0, .4f);
		else if (type == RenderData.BUTTON) graphics.setColor( 0, 0, .8f);
		graphics.drawRectangle(0, 0, w, h);
		graphics.popTransform();
	}
	//=============================================================================================
	
}
//*************************************************************************************************
