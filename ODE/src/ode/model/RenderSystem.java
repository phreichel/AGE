//*************************************************************************************************
package ode.model;
//*************************************************************************************************

import java.util.ArrayList;

import javax.vecmath.Matrix4f;

import ode.asset.ODETexture;
import ode.platform.Graphics;

//*************************************************************************************************
public class RenderSystem extends ArrayList<Entity> {

	//=============================================================================================
	private static final long serialVersionUID = 1L;
	//=============================================================================================

	//=============================================================================================
	public void update(Graphics graphics) {
		for (Entity entity : this) {
			RenderEnum renderData = entity.getComponent(EntityEnum.RENDER, RenderEnum.class);
			switch (renderData) {
				case BOX: renderBox(graphics, entity); break;
				default: break;
			}
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void renderBox(Graphics graphics ,Entity entity) {
		Matrix4f poseData = entity.getComponent(EntityEnum.POSE, Matrix4f.class);
		if (poseData != null) {
			ODETexture tx = graphics.loadTexture("asset/snippet.png");
			graphics.pushTransform();
			graphics.multMatrix(poseData);
			graphics.setColor(1f, 0f, 0f);
			graphics.useTexture(tx);
			graphics.drawBox(-1,-1,-1, 1, 1, 1);
			graphics.stopUseTexture();
			graphics.popTransform();
		}
	}
	//=============================================================================================

}
//*************************************************************************************************