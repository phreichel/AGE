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

import age.log.Log;

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
		/*
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
		*/
	}
	//=============================================================================================

	//=============================================================================================
	// TODO: REVISIT THIS - THIS CODE PRODUCES ONLY GARBAGE
	private void update(Bone bn, Matrix4f pm) {
		Vector3f p = new Vector3f();
		Matrix4f m = new Matrix4f();
		m.setIdentity();
		Keyframes kfs = animation.keyframes.get(bn.index);
		int idx = findKeyframe(kfs);
		int idxa = Math.max(0, Math.min((idx+0), kfs.list.size()-1));
		int idxb = Math.max(0, Math.min((idx+1), kfs.list.size()-1));
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
			p.interpolate(kb.position, ka.position, alpha);
			Quat4f o = new Quat4f();
			o.interpolate(kb.orientation, ka.orientation, alpha);
			float s = (1f-alpha) * ka.scale + alpha * kb.scale;
			m.set(o, p, s);
		}
		Point3f pp = new Point3f(p); 
		pm.transform(pp);
		deltaPositions.get(bn.index).set(pp);
		m.mul(pm, m);
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
		Vector3f delta = deltaPositions.get(bn.index);
		//float scale = influence.influences.get(boneindex).get(vertexidx);
		float scale = 0f;
		Vector3f scaledDelta = new Vector3f(delta);
		scaledDelta.scale(scale);
		d.add(scaledDelta);
		for (Bone c : bn.children) {
			update(c, vertexidx, d);
		}
	}
	//=============================================================================================

}
//*************************************************************************************************
