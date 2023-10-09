//*************************************************************************************************
package age.model;
//*************************************************************************************************

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

//*************************************************************************************************
public class Rig {

	//=============================================================================================
	public final Animation animation;
	public final Model model;
	public final Influence influence;
	//=============================================================================================

	//=============================================================================================
	private float timevalue = 0f;
	private float timescale = 1f;
	//=============================================================================================
	
	//=============================================================================================
	private List<Vector3f> initPositions  = new ArrayList<>();
	private List<Vector3f> deltaPositions = new ArrayList<>();
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
		Skeleton s = animation.skeleton;
		Matrix4f m = new Matrix4f();
		m.setIdentity();
		for (Bone b : s.roots) {
			init(b, m);
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void init(Bone b, Matrix4f pm) {
		Vector3f p = new Vector3f(b.position);
		pm.transform(p);
		initPositions.add(p);
		deltaPositions.add(new Vector3f());
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
	private int boneindex = 0;
	//=============================================================================================
	
	//=============================================================================================
	public void update(float dT) {		

		timevalue = (timevalue + dT * timescale) % ((animation.steps-1) * animation.steptime);

		Skeleton s = animation.skeleton;
		Matrix4f m = new Matrix4f();
		m.setIdentity();		
		boneindex = 0;
		for (Bone bn : s.roots) {
			update(bn, m);
		}
		
		Mesh ms = model.skin.mesh;
		for (int i=0; i<ms.size; i++) {
			Vector3f d = new Vector3f();
			boneindex = 0;
			for (Bone bn : s.roots) {
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

		Vector3f p = new Vector3f();
		Matrix4f m = new Matrix4f();
		m.setIdentity();
		
		Keyframes kfs = animation.keyframes.get(boneindex);
		int idx = findKeyframe(kfs);
		int idxa = Math.max(0, Math.min((idx+0), kfs.list().size()-1));
		int idxb = Math.max(0, Math.min((idx+1), kfs.list().size()-1));
		if (idxa == idxb) {
			Keyframe k = kfs.list().get(idxa);
			p.set(k.position());
			m.set(k.orientation(), k.position(), k.scale());
		} else {
			Keyframe ka = kfs.list().get(idxa);
			Keyframe kb = kfs.list().get(idxb);
			float ta = ka.step() * animation.steptime;
			float tb = kb.step() * animation.steptime;
			float alpha = (timevalue-ta) / (tb-ta);
			p.interpolate(ka.position(), kb.position(), alpha);
			Quat4f o = new Quat4f();
			o.interpolate(ka.orientation(), kb.orientation(), alpha);
			float s = (1f-alpha) * ka.scale() + alpha * kb.scale();
			m.set(o, p, s);
		}
		
		pm.transform(p);
		deltaPositions.get(boneindex).sub(p, initPositions.get(boneindex));
		
		boneindex++;
		for (Bone c : bn.children) {
			update(c, m);
		}

	}
	//=============================================================================================

	//=============================================================================================
	private int findKeyframe(Keyframes kfs) {
		int frameidx = -1;
		for (int i=0; i<kfs.list().size(); i++) {
			Keyframe k = kfs.list().get(i);
			float t = k.step() * animation.steptime;
			if (t > timevalue) break;
			frameidx = i;
		}
		return frameidx;
	}
	//=============================================================================================

	//=============================================================================================
	private void update(Bone bn, int vertexidx, Vector3f d) {
		Vector3f delta = deltaPositions.get(boneindex);
		float scale = influence.influences.get(boneindex).get(vertexidx);
		Vector3f scaledDelta = new Vector3f(delta);
		scaledDelta.scale(scale);
		d.add(scaledDelta);
		boneindex++;
		for (Bone c : bn.children) {
			update(c, vertexidx, d);
		}
	}
	//=============================================================================================
	
}
//*************************************************************************************************
