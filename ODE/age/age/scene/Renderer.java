//*************************************************************************************************
package age.scene;
//*************************************************************************************************

import javax.vecmath.Matrix4f;
import age.mesh.Mesh;
import age.port.Graphics;
import age.port.Renderable;
import age.rig.Rig;
import age.util.MathUtil;

//*************************************************************************************************
public class Renderer implements Renderable {

	//=============================================================================================
	private final Scene scene;
	//=============================================================================================

	//=============================================================================================
	public Renderer(Scene scene) {
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
		Camera data = node.component(NItem.CAMERA, Camera.class);
		Matrix4f src = new Matrix4f();
		src.setIdentity();
		Node n = node;
		while (n != null) {
			Matrix4f m = n.component(NItem.TRANSFORM, Matrix4f.class);
			if (m != null) src.mul(m, src);
			n = n.parent();
		}
		Matrix4f dst = new Matrix4f();
		dst.setIdentity();
		dst= MathUtil.cameraMatrix(src, dst);
		g.mode3D(data.fovy, data.near, data.far);
		g.applyTransformation(dst);
	}
	//=============================================================================================
	
	//=============================================================================================
	private void render(Graphics g, Node node) {
		Matrix4f m = node.component(NItem.TRANSFORM, Matrix4f.class);
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
		for (var flag : node.nFlags()) {
			switch (flag) {
				case BOX -> renderBox(g, node);
				case MESH -> renderMesh2(g, node);
				case RIG -> renderSkeleton(g, node);
				case RIG2 -> renderRig(g, node);
				default -> {}
			}
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void renderBox(Graphics g, Node node) {
		g.color(0f, .5f, 1f, 1f);
		g.drawBox(1, .1f, 1);
	}
	//=============================================================================================

	//=============================================================================================
	private void renderMesh2(Graphics g, Node node) {
		Mesh mesh = node.component(NItem.MESH, Mesh.class);
		g.drawMesh(mesh);
	}
	//=============================================================================================
	
	//=============================================================================================
	private void renderSkeleton(Graphics g, Node node) {
		g.color(1f, 0f, 0f, 1f);
		Rig rig = node.component(NItem.RIG, Rig.class);
		g.drawRig(rig);
	}
	//=============================================================================================

	//=============================================================================================
	private void renderRig(Graphics g, Node node) {
		g.color(1f, 0f, 0f, 1f);
		age.model.Rig rig = node.component(NItem.RIG2, age.model.Rig.class);
		g.drawRig(rig);
	}
	//=============================================================================================
	
}
//*************************************************************************************************
