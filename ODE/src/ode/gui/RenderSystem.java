//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import java.util.ArrayList;

import javax.vecmath.Vector2f;

import ode.platform.Graphics;

//*************************************************************************************************
public class RenderSystem extends ArrayList<Widget> {

	//=============================================================================================
	private static final long serialVersionUID = 1L;
	//=============================================================================================
	
	//=============================================================================================
	private static final float FACTOR = 0.01f;
	private static final float OFFSET = 0.001f;
	//=============================================================================================
	
	//=============================================================================================
	public void update(Graphics graphics) {
		for (Widget widget : this) {
			render(widget, graphics);
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void render(Widget widget, Graphics graphics) {
		RenderEnum renderData = widget.getRenderData();
		switch (renderData) {
			case BOX: renderBox(widget, graphics); break;
			case LABEL: renderLabel(widget, graphics); break;
			case BUTTON: renderButton(widget, graphics); break;
			default: break;
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void renderBox(Widget widget, Graphics graphics) {
		Vector2f dimensionData = widget.getDimensionData();
		float[] globalPosition = getGlobalPosition(widget, new float[] {0, 0, 0});
		float x = globalPosition[0];
		float y = globalPosition[1];
		float z = globalPosition[2] * FACTOR;	
		float w = dimensionData.x;
		float h = dimensionData.y;
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
		TriggerData clickData = widget.getTriggerData();
		Vector2f dimensionData = widget.getDimensionData();
		FlagData flagsData = widget.getFlagsData();
		String label = widget.getTextData();
		float[] globalPosition = getGlobalPosition(widget, new float[] {0, 0, 0});
		float x = globalPosition[0];
		float y = globalPosition[1];
		float z = globalPosition[2] * FACTOR;	
		float w = dimensionData.x;
		float h = dimensionData.y;
		graphics.pushTransform();
		graphics.translate(x, y, z);
		if (!flagsData.flags.contains(FlagEnum.HOVER)) {
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
		Vector2f dimensionData = widget.getDimensionData();
		String label = widget.getTextData();
		float[] globalPosition = getGlobalPosition(widget, new float[] {0, 0, 0});
		float x = globalPosition[0];
		float y = globalPosition[1];
		float z = globalPosition[2] * FACTOR;	
		float w = dimensionData.x;
		float h = dimensionData.y;
		graphics.pushTransform();
		graphics.translate(x, y, z);
		graphics.setColor(1f, 1f, 1f);
		graphics.fillRectangle(0, 0, w, h);
		graphics.translate(0, 0, OFFSET);
		graphics.setColor(.2f, .2f, .2f);
		graphics.drawRectangle(0, 0, w, h);
		graphics.translate(0, 0, OFFSET);
		graphics.drawText(label, 0, 0, 0, w, h, 0, 0, 0);
		graphics.popTransform();
	}
	//=============================================================================================
	
	//=============================================================================================
	private float[] getGlobalPosition(Widget widget, float[] target) {
		if (widget == null) return target;
		HierarchyData hierarchyData = widget.getHierarchyData();
		Vector2f positionData = widget.getPositionData();
		target[0] += positionData.x;
		target[1] += positionData.y;
		target[2] += 1;
		return getGlobalPosition(hierarchyData.parent, target);
	}
	//=============================================================================================
	
}
//*************************************************************************************************
