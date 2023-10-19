/*
 * Commit: 7407dc59dacb80fc02cd2de06a33b6b78cc37f8e
 * Date: 2023-10-12 13:32:56+02:00
 * Author: pre7618
 * Comment: Test
 *
 * Commit: 756d85884eecc05d945ed908861257a13183c787
 * Date: 2023-10-10 21:18:54+02:00
 * Author: Philip Reichel
 * Comment: Cleaning UP
 *
 * Commit: 00a610434f6fec6c19360cdcbe9949c18fea5a3b
 * Date: 2023-10-10 10:10:29+02:00
 * Author: pre7618
 * Comment: ..
 *
 * Commit: 7a0e67a478c9bc474bec2860dbaccef928526344
 * Date: 2023-10-03 17:21:16+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 * Commit: 79a5191fc4ca527a12fa7cf477631efa44a9b707
 * Date: 2023-10-02 01:54:44+02:00
 * Author: Philip Reichel
 * Comment: Done some stuff with meshes.
 *
 * Commit: 989a153341f09c0168f323a44e0ffec01fbc07b7
 * Date: 2023-10-01 22:26:41+02:00
 * Author: Philip Reichel
 * Comment: VBO babay
 *
 * Commit: 921fe26242fec8aea55ed85b831bf841b11304e4
 * Date: 2023-09-30 17:42:43+02:00
 * Author: Philip Reichel
 * Comment: Added Material Parsing
Added Models
Remove Bigfile
Added Models
Revert "Added Models"

This reverts commit cd0e7c6f40612113283cf9300c40af491cd1bc2f.

 *
 * Commit: 729ac252a97c3fd77734b2d841dfdd0ec3de55fb
 * Date: 2023-09-27 19:14:06+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 * Commit: 79126a233e35010f5ee69ea22119499909797737
 * Date: 2023-09-27 16:31:28+02:00
 * Author: pre7618
 * Comment: Added Camview and Rig
 *
 * Commit: 2c0280077398a4634180c45fd2854627002dba37
 * Date: 2023-09-26 23:59:37+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 */

//*************************************************************************************************
package age.scene;
//*************************************************************************************************

import javax.vecmath.Matrix4f;
import age.port.Graphics;
import age.port.Renderable;
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
				case RIG -> renderRig(g, node);
				case MODEL -> renderModel(g, node);
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
	private void renderRig(Graphics g, Node node) {
		g.color(1f, 0f, 0f, 1f);
		age.model.Rig rig = node.component(NItem.RIG, age.model.Rig.class);
		g.drawRig(rig);
	}
	//=============================================================================================

	//=============================================================================================
	private void renderModel(Graphics g, Node node) {
		g.color(1f, 0f, 0f, 1f);
		age.model.Model model = node.component(NItem.MODEL, age.model.Model.class);
		g.drawModel(model);
	}
	//=============================================================================================
	
}
//*************************************************************************************************
