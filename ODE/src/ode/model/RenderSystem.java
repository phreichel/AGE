//*************************************************************************************************
package ode.model;
//*************************************************************************************************

import java.util.Map;
import java.util.Map.Entry;

import javax.vecmath.Matrix4f;

import ode.platform.Graphics;

//*************************************************************************************************
public class RenderSystem {

	//=============================================================================================
	private Map<Entity, RenderEnum> renderMap;
	//=============================================================================================

	//=============================================================================================
	public RenderSystem(Map<Entity, RenderEnum> renderMap) {
		this.renderMap = renderMap;
	}
	//=============================================================================================

	
	//=============================================================================================
	public void update(Graphics graphics) {
		for (Entry<Entity, RenderEnum> entry : renderMap.entrySet()) {
			Entity entity = entry.getKey();
			RenderEnum renderData = entry.getValue();
			switch (renderData) {
				case BOX: renderBox(graphics, entity); break;
				case PARTICLE: renderParticle(graphics, entity); break;
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

	//=============================================================================================
	private void renderParticle(Graphics graphics ,Entity entity) {
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