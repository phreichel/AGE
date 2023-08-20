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
		float x = widget.global_x;
		float y = widget.global_y;
		float z = widget.global_z;
		float w = widget.width;
		float h = widget.height;
		graphics.pushTransform();
		graphics.translate(x, y, z*0.1f);
		graphics.setColor(.4f, 0, 0);
		graphics.fillRectangle(0, 0, w, h);
		graphics.translate(0, 0, 0.01f);
		graphics.setColor(1, 0, 0);
		graphics.drawRectangle(0, 0, w, h);
		graphics.popTransform();
	}
	//=============================================================================================
	
}
//*************************************************************************************************
