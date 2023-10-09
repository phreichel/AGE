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
public class AnimationRig {

	//=============================================================================================
	public final Model model;
	public final Animation animation;
	public final Influence influence;
	//=============================================================================================

	//=============================================================================================
	final int steps;
	final float steptime;
	//=============================================================================================
	
	//=============================================================================================
	private List<Vector3f> bonePositions = new ArrayList<>();
	private List<Quat4f>   boneOrientations = new ArrayList<>();
	private List<Matrix4f> boneLocalMatrices = new ArrayList<>();
	private List<Matrix4f> boneModelMatrices = new ArrayList<>();
	//=============================================================================================

	//=============================================================================================
	private FloatBuffer vertexPositions;
	private FloatBuffer vertexNormals;
	//=============================================================================================

	//=============================================================================================
	private float timevalue = 0f;
	private float timescale = 1f;
	//=============================================================================================

	//=============================================================================================
	public AnimationRig(
		Model model,
		Animation animation,
		Influence influence) {
		
		this.model = model;
		this.animation = animation;
		this.influence = influence;
		
		int vsize = model.skin.mesh.positions.limit();
		int nsize = model.skin.mesh.normals.limit();
		
		vertexPositions = FloatBuffer.allocate(vsize);
		vertexNormals = FloatBuffer.allocate(nsize);

		steps = animation.steps;
		steptime = animation.steptime;
		
	}
	//=============================================================================================
	
	//=============================================================================================
	public void update(float dT) {

		timevalue = (timevalue + dT * timescale) % ((steps-1) * steptime);
		
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
				float time = kf.step() * steptime;
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
			float ta = kfa.step() * steptime;
			float tb = kfb.step() * steptime;
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
	private void updateSkeleton(
		Animation an,       // input
		List<Matrix4f> blm, // input
		List<Matrix4f> bmm  // output
	) {
		Matrix4f identity = new Matrix4f();
		identity.setIdentity();
		for (Bone bone : an.skeleton.roots) {
			updateSkeleton(an, identity, bone, blm, bmm);
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void updateSkeleton(
		Animation an,          // input
		Matrix4f parentMatrix, // input
		Bone bone,             // input
		List<Matrix4f> blm,    // input
		List<Matrix4f> bmm     // output (partially modified)
	) {
		int idx = an.skeleton.bones.indexOf(bone);
		Matrix4f localMatrix = blm.get(idx);
		Matrix4f modelMatrix = bmm.get(idx);
		modelMatrix.mul(parentMatrix, localMatrix);
		for (Bone child : bone.children) {
			updateSkeleton(an, modelMatrix, child, blm, bmm);
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	private Vector3f _nrm = new Vector3f();
	//=============================================================================================
	
	//=============================================================================================
	private void interpolateMesh(
		Model m,        // input
		Animation an,   // input
		Influence inf,  // input
		FloatBuffer vp, // output
		FloatBuffer vn  // output
	) {
		// for each vertex
		for (int i=0; i<m.skin.mesh.size; i++) {
			
			Vector3f displace = new Vector3f();
			
			// for each bone
			for (int j=0; j<an.skeleton.bones.size(); j++) {

				Bone bone = an.skeleton.bones.get(j);
				
				// get default bone position
				Vector3f restPos = bone.position;

				// get interpolated bone position
				Vector3f interpolPos = this.bonePositions.get(j);

				// calculate bone displace
				Vector3f deltaPos = new Vector3f();
				deltaPos.sub(interpolPos, restPos);
				float scale = inf
					.influences
					.get(j)
					.get(i);
				deltaPos.scale(scale);

				// accumulate displaces
				displace.add(deltaPos);

			}

			// get default vertex position
			Vector3f position = new Vector3f();
			position.x = m.skin.mesh.positions.get(i*3+0);
			position.y = m.skin.mesh.positions.get(i*3+1);
			position.z = m.skin.mesh.positions.get(i*3+2);

			// modify position
			position.add(displace);

			// write back to vertex buffer
			vp.put(i*3+0, position.x);
			vp.put(i*3+1, position.y);
			vp.put(i*3+2, position.z);

			// write back to normal buffer
			_nrm.x = m.skin.mesh.normals.get(i*3+0);
			_nrm.y = m.skin.mesh.normals.get(i*3+1);
			_nrm.z = m.skin.mesh.normals.get(i*3+2);
			
			// write back to normal buffer
			vn.put(i*3+0, _nrm.x);
			vn.put(i*3+1, _nrm.y);
			vn.put(i*3+2, _nrm.z);
			
		}
	}
	//=============================================================================================
	
}
//*************************************************************************************************
