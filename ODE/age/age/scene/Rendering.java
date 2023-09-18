//*************************************************************************************************
package age.scene;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Color3f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import age.Util;
import age.port.Graphics;
import age.port.Renderable;

//*************************************************************************************************
public class Rendering implements Renderable {

	//=============================================================================================
	private final Scene scene;
	//=============================================================================================

	//=============================================================================================
	public Rendering(Scene scene) {
		this.scene = scene;
	}
	//=============================================================================================

	//=============================================================================================
	public void render(Graphics g) {
		Node root = scene.root();
		Node camera = scene.camera();
		renderCamera(g, camera);
		render(g, root);
	}
	//=============================================================================================

	//=============================================================================================
	private void renderCamera(Graphics g, Node node) {
		Camera data = node.component(Part.CAMERA, Camera.class);
		Matrix4f src = new Matrix4f();
		src.setIdentity();
		Node n = node;
		while (n != null) {
			Matrix4f m = n.component(Part.TRANSFORM, Matrix4f.class);
			if (m != null) src.mul(m, src);
			n = n.parent();
		}
		Matrix4f dst = new Matrix4f();
		dst.setIdentity();
		dst= Util.camReverse(src, dst);
		g.mode3D(data.fovy, data.near, data.far);
		g.applyTransformation(dst);
	}
	//=============================================================================================
	
	//=============================================================================================
	private void render(Graphics g, Node node) {
		Matrix4f m = node.component(Part.TRANSFORM, Matrix4f.class);
		if (m != null) {
			g.pushTransformation();
			g.applyTransformation(m);
			renderNode(g, node);
			for (Node child : node.children()) {
				render(g, child);
			}
			g.popTransformation();
		} else {
			renderNode(g, node);
			for (Node child : node.children()) {
				render(g, child);
			}
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void renderNode(Graphics g, Node node) {
		if (node.match(Flag.BOX)) {
			g.color(0f, .5f, 1f, 1f);
			g.drawBox(1, 1, 1);
		}
	}
	//=============================================================================================
	
}
//*************************************************************************************************
