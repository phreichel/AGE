//*************************************************************************************************
package ode.model;
//*************************************************************************************************

import java.util.ArrayList;

import javax.vecmath.Matrix4f;

import ode.platform.Graphics;

//*************************************************************************************************
public class RenderSystem extends ArrayList<Entity> {

	//=============================================================================================
	private static final long serialVersionUID = 1L;
	//=============================================================================================

	//=============================================================================================
	public void update(Graphics graphics) {
		for (Entity entity : this) {
			RenderEnum renderData = entity.getRenderData();
			switch (renderData) {
				case BOX: renderBox(graphics, entity); break;
				default: break;
			}
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void renderBox(Graphics graphics ,Entity entity) {
		Matrix4f poseData = entity.getPoseData();
		if (poseData != null) { 
			graphics.pushTransform();
			graphics.multMatrix(poseData);
			graphics.setColor(1, 0, 0);
			graphics.drawBox(-1,-1,-1, 1, 1, 1);
			graphics.popTransform();
		}
	}
	//=============================================================================================

}
//*************************************************************************************************