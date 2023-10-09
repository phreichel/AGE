//*************************************************************************************************
package age.model;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.List;
import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

//*************************************************************************************************
public class Rig2 {

	//=============================================================================================
	private final Animation animation;
	private final Model model;
	private final Influence influence;
	//=============================================================================================

	//=============================================================================================
	private float timevalue = 0f;
	private float timescale = 1f;
	//=============================================================================================
	
	//=============================================================================================
	private final List<Vector3f> bonePositions = new ArrayList<>();
	//=============================================================================================
	
	//=============================================================================================
	public Rig2(
		Animation animation,
		Model model,
		Influence influence
	) {
		this.animation = animation;
		this.model = model;
		this.influence = influence;
	}
	//=============================================================================================

	//=============================================================================================
	private void initSkeleton(
		Animation an,
		List<Vector3f> bp
	) {
		Matrix4f m = new Matrix4f();
		m.setIdentity();
		for (Bone b : an.skeleton.roots) {
			initBone(m, b, bp);
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void initBone(
		Matrix4f m,
		Bone bone,
		List<Vector3f> bp
	) {

		// calculate model position
		Vector3f pm = new Vector3f(bone.position);
		m.transform(pm);
		bp.add(pm);
		
		// setup local matrix
		Matrix4f ml = new Matrix4f();
		ml.setIdentity();
		ml.setTranslation(bone.position);
		ml.setRotation(bone.orientation);

		// setup model matrix
		Matrix4f mm = new Matrix4f();
		mm.mul(ml, m);

		// walk child bones
		for (Bone b : bone.children) {
			initBone(mm, b, bp);
		}

	}
	//=============================================================================================

	//=============================================================================================
	private void interpolateSkeleton(
		Animation      an, // input
		List<Vector3f> bp, // output
		List<Quat4f>   bo, // output
		List<Matrix4f> bm  // output
	) {
		// for each bone
		for (int i=0; i<an.keyframes.size(); i++) {
			
			// determine keyframes for interpolation
			Keyframes kfs = animation.keyframes.get(i);
			int a = 0;
			int b = -1;
			for (int j=0; j<kfs.list().size(); j++) {
				Keyframe kf = kfs.list().get(j);
				float time = kf.step() * an.steptime;
				if (time > timevalue) {
					b = j;
					break;
				} else {
					a = j;
				}
			}
			Keyframe kfa = kfs.list().get(a); 
			Keyframe kfb = kfs.list().get(b);

			// interpolate bone values
			Vector3f pos = bp.get(i);
			Quat4f   ori = bo.get(i);
			Matrix4f mat = bm.get(i);
			float ta = kfa.step() * an.steptime;
			float tb = kfb.step() * an.steptime;
			float d = tb-ta;
			if (d*d < 0.000001f) {
				pos.set(kfa.position());
				ori.set(kfa.orientation());
			} else {
				float alpha = (timevalue-ta) / (tb-ta);
				pos.interpolate(
					kfa.position(),
					kfb.position(),
					alpha);
				ori.interpolate(
					kfa.orientation(),
					kfb.orientation(),
					alpha);
				float scale = (1f-alpha) * kfa.scale() +  alpha * kfb.scale();
				mat.set(ori, pos, scale);
			}
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	public void updateSkeleton() {
		Matrix4f m = new Matrix4f();
		m.setIdentity();
		for (Bone b : animation.skeleton.roots) {
			initBone(m, b);
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void updateBone(
		Matrix4f m,
		Bone bone
	) {

		// calculate model position
		Vector3f pm = new Vector3f(bone.position);
		m.transform(pm);
		bonePositions.add(pm);
		
		// setup local matrix
		Matrix4f ml = new Matrix4f();
		ml.setIdentity();
		ml.setTranslation(bone.position);
		ml.setRotation(bone.orientation);

		// setup model matrix
		Matrix4f mm = new Matrix4f();
		mm.mul(ml, m);

		// walk child bones
		for (Bone b : bone.children) {
			initBone(mm, b);
		}

	}
	//=============================================================================================

	//=============================================================================================
	public void init() {
		initSkeleton(
			animation,    // input
			bonePositions // output
		);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void update(float dT) {

		timevalue = (timevalue + dT * timescale) % ((animation.steps-1) * animation.steptime);
		
		interpolateSkeleton(
			animation,         // input
			bonePositions,     // output
			boneOrientations,  // output
			boneLocalMatrices  // output
		);
		
		updateSkeleton(
			animation,         // input
			boneLocalMatrices, // input
			boneModelMatrices  // output
		);
		
		interpolateMesh(
			model,             // input
			animation,         // input
			influence,         // input
			vertexPositions,   // output
			vertexNormals      // output
		);

	}
	//=============================================================================================
	
}
//*************************************************************************************************
