/*
 * Commit: abb79765d6c7fa32d18352f742be87ce2b7688c6
 * Date: 2023-10-12 05:01:43+02:00
 * Author: Philip Reichel
 * Comment: ,,
 *
 * Commit: 18d2f76ceedb6003ef53119b6e00bf0da3c4ff1d
 * Date: 2023-10-11 17:52:14+02:00
 * Author: pre7618
 * Comment: First Animated Rigged Mesh!
 *
 * Commit: beb06c5be3d51bd9db17f304dc0b8a891b88218b
 * Date: 2023-10-11 17:16:39+02:00
 * Author: pre7618
 * Comment: Made Rigging possible?
 *
 * Commit: 58407180b4578ab8fe91b005b6bcf51e549abaa7
 * Date: 2023-10-11 00:53:39+02:00
 * Author: Philip Reichel
 * Comment: Rigging revisited.
 *
 * Commit: d7587bbba419eaa855b68a9e0ccc30ec13873658
 * Date: 2023-10-10 17:02:07+02:00
 * Author: pre7618
 * Comment: Update to crappy version
 *
 * Commit: 00a610434f6fec6c19360cdcbe9949c18fea5a3b
 * Date: 2023-10-10 10:10:29+02:00
 * Author: pre7618
 * Comment: ..
 *
 * Commit: 543df522f819ea4a67e616d2be4c08edf6003716
 * Date: 2023-10-10 02:33:39+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 * Commit: 56452c50c87b9409aa1f7f7d8c4e35eacc26b975
 * Date: 2023-10-10 01:22:04+02:00
 * Author: Philip Reichel
 * Comment: ..
 *
 */

//*************************************************************************************************
package age.model;
//*************************************************************************************************

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import javax.vecmath.Matrix4f;
import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

//*************************************************************************************************
public class Rig {

	//=============================================================================================
	public final Animation animation;
	public final Model     model;
	public final Influence influence;
	//=============================================================================================

	//=============================================================================================
	private float timevalue = 0f;
	private float timescale = 1f;
	//=============================================================================================
	
	//=============================================================================================
	public final List<Vector3f> initPositions  = new ArrayList<>();
	public final List<Vector3f> deltaPositions = new ArrayList<>();
	//=============================================================================================

	//=============================================================================================
	public final FloatBuffer meshPositions;
	//=============================================================================================
	
	//=============================================================================================
	public Rig(
		Animation animation,
		Model model,
		Influence influence
	) {
		this.animation = animation;
		this.model = model;
		this.influence = influence;
		meshPositions = FloatBuffer.allocate(this.model.skin.mesh.positions.limit());
		this.model.skin.mesh.positions.rewind();
		meshPositions.put(this.model.skin.mesh.positions);
		init();
	}
	//=============================================================================================

	//=============================================================================================
	private void init() {
		Matrix4f m = new Matrix4f();
		m.setIdentity();
		for (Bone b : animation.skeleton.roots) {
			init(b, m);
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void init(Bone b, Matrix4f pm) {
		Point3f p = new Point3f(b.position);
		pm.transform(p);
		initPositions.add(new Vector3f());
		deltaPositions.add(new Vector3f());
		initPositions.get(b.index).set(p);
		Matrix4f m = new Matrix4f();
		m.setIdentity();
		m.setRotation(b.orientation);
		m.setTranslation(b.position);
		m.mul(pm, m);
		for (Bone c : b.children) {
			init(c, m);
		}
	}
	//=============================================================================================

	//=============================================================================================
	public void update(float dT) {
		
		timevalue = (timevalue + dT * timescale) % ((animation.steps-1) * animation.steptime);
		Matrix4f m = new Matrix4f();
		m.setIdentity();		
		for (Bone bn : animation.skeleton.roots) {
			update(bn, m);
		}
		
		Mesh ms = model.skin.mesh;
		for (int i=0; i<ms.size; i++) {
			Vector3f d = new Vector3f();
			for (Bone bn : animation.skeleton.roots) {
				update(bn, i, d);
			}
			meshPositions.put(i*3+0, ms.positions.get(i*3+0) + d.x);
			meshPositions.put(i*3+1, ms.positions.get(i*3+1) + d.y);
			meshPositions.put(i*3+2, ms.positions.get(i*3+2) + d.z);
		}
		
	}
	//=============================================================================================

	//=============================================================================================
	private void update(Bone bn, Matrix4f pm) {

		Keyframes kfs = animation.keyframes.get(bn.index);
		int idx = findKeyframe(kfs);
		int idxa = Math.max(0, Math.min((idx+0), kfs.list.size()-1));
		int idxb = Math.max(0, Math.min((idx+1), kfs.list.size()-1));

		Vector3f p = new Vector3f();
		Matrix4f m = new Matrix4f();
		m.setIdentity();
	
		if (idxa == idxb) {
			if (kfs.list.size() > 0) {
				Keyframe k = kfs.list.get(idxa);
				p.set(k.position);
				m.set(k.orientation, k.position, k.scale);
			} else {
				p.set(bn.position);
				m.set(bn.orientation, bn.position, 1f);
			}
		} else {
			Keyframe ka = kfs.list.get(idxa);
			Keyframe kb = kfs.list.get(idxb);
			float ta = ka.step * animation.steptime;
			float tb = kb.step * animation.steptime;
			float alpha = (timevalue-ta) / (tb-ta);
			p.interpolate(ka.position, kb.position, alpha);
			p.add(bn.position);
			Quat4f o = new Quat4f();
			o.interpolate(ka.orientation, kb.orientation, alpha);
			o.mul(bn.orientation, o);
			o.normalize();
			float s = (1f-alpha) * ka.scale + alpha * kb.scale;
			m.set(o, p, s);
		}
		Point3f pp = new Point3f(p); 
		m.mul(pm, m);
		pm.transform(pp);
		deltaPositions.get(bn.index).set(pp);
		for (Bone c : bn.children) {
			update(c, m);
		}
	}
	//=============================================================================================

	//=============================================================================================
	private int findKeyframe(Keyframes kfs) {
		int frameidx = -1;
		for (int i=0; i<kfs.list.size(); i++) {
			Keyframe k = kfs.list.get(i);
			float t = k.step * animation.steptime;
			if (t > timevalue) break;
			frameidx = i;
		}
		return frameidx;
	}
	//=============================================================================================

	//=============================================================================================
	private void update(Bone bn, int vertexidx, Vector3f d) {
		Vector3f delta = new Vector3f();
		delta.sub(deltaPositions.get(bn.index), initPositions.get(bn.index));
		float scale = influence.influences[vertexidx][bn.index];
		Vector3f scaledDelta = new Vector3f(delta);
		scaledDelta.scale(-scale);
		d.add(scaledDelta);
		for (Bone c : bn.children) {
			update(c, vertexidx, d);
		}
	}
	//=============================================================================================

}
//*************************************************************************************************
