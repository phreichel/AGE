/*
 * Commit: 7407dc59dacb80fc02cd2de06a33b6b78cc37f8e
 * Date: 2023-10-12 13:32:56+02:00
 * Author: pre7618
 * Comment: Test
 *
 * Commit: 00a610434f6fec6c19360cdcbe9949c18fea5a3b
 * Date: 2023-10-10 10:10:29+02:00
 * Author: pre7618
 * Comment: ..
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import age.model.Rig;
import age.util.MathUtil;
import age.util.X;

//*************************************************************************************************
public class Animator {

	//=============================================================================================
	private Map<NFlag, List<Node>> register = new HashMap<>();
	//=============================================================================================
	
	//=============================================================================================
	public void add(NFlag flag, Node node) {
		var list = register.get(flag);
		if (list == null) {
			list = new ArrayList<>(5);
			register.put(flag, list);
		}
		list.add(node);
	}
	//=============================================================================================

	//=============================================================================================
	public void remove(Node node) {
		for (var list : register.values()) {
			list.remove(node);
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	public void update(float dT) {
		for (var entry : register.entrySet()) {
			var flag = entry.getKey();
			var list = entry.getValue();
			for (var node : list) {
				switch (flag) {
					case TRANSFORM -> animateTransform(node, dT); 
					case RIG -> animateSkeleton(node, dT);
					default -> throw new X("Unsupported Animation Flag: %s", flag);
				}
			}
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void animateTransform(Node node, float dT) {

		var rrr = new AxisAngle4f();
		var rt = new Vector3f();
		var rr = new Quat4f();

		Matrix4f rx = node.component(NItem.TRANSFORM_ANIMATION, Matrix4f.class);
	
		rx.get(rt);
		rt.scale(dT);
		
		rx.get(rr);
		rrr.set(rr);
		rrr.angle *= dT;
		
		Matrix4f rd = MathUtil.identityMatrix();
		rd.setRotation(rrr);
		rd.setTranslation(rt);
		
		Matrix4f tx = node.component(NItem.TRANSFORM, Matrix4f.class);
		tx.mul(rd, tx);
		
	}
	//=============================================================================================

	//=============================================================================================
	private void animateSkeleton(Node node, float dT) {
		Rig r = node.component(NItem.RIG, Rig.class);
		r.update(dT);
	}
	//=============================================================================================
	
}
//*************************************************************************************************
