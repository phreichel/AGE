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

import age.util.MathUtil;
import age.util.X;

//*************************************************************************************************
public class Animations {

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
					default -> throw new X("Unsupported Animation Flag: %s", flag);
				}
			}
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void animateTransform(Node node, float dT) {
		Matrix4f tx = node.component(NItem.TRANSFORM, Matrix4f.class);
		Matrix4f rx = node.component(NItem.TRANSFORM_ANIMATION, Matrix4f.class);
		
		var rr = new Quat4f();
		var rrr = new AxisAngle4f();
		var rt = new Vector3f();
		
		rx.get(rr);
		rx.get(rt);

		rrr.set(rr);
		rrr.angle *= dT;
		
		Matrix4f rd = MathUtil.identityMatrix();
		rd.setRotation(rrr);
		rd.setTranslation(rt);
		
		tx.mul(rd, tx);
	}
	//=============================================================================================
	
}
//*************************************************************************************************
