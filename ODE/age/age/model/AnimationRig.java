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
	private Model model;
	private Animation animation;
	private Influence influence;
	//=============================================================================================

	//=============================================================================================
	private List<Vector3f> bonePositions     = new ArrayList<>();
	private List<Quat4f>   boneOrientations  = new ArrayList<>();
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
	public Mesh mesh() {
		return model.skin().mesh();
	}
	//=============================================================================================
	
	//=============================================================================================
	public Skin skin() {
		return model.skin();
	}
	//=============================================================================================

	//=============================================================================================
	public Model model() {
		return model;
	}
	//=============================================================================================

	//=============================================================================================
	public Skeleton skeleton() {
		return animation.skeleton();
	}
	//=============================================================================================
	
	//=============================================================================================
	public Animation animation() {
		return animation;
	}
	//=============================================================================================

	//=============================================================================================
	public Influence influence() {
		return influence;
	}
	//=============================================================================================

	//=============================================================================================
	public int steps() {
		return animation.steps();
	}
	//=============================================================================================

	//=============================================================================================
	public float steptime() {
		return animation.steptime();
	}
	//=============================================================================================

	//=============================================================================================
	public float timescale() {
		return timescale;
	}
	//=============================================================================================

	//=============================================================================================
	public float timevalue() {
		return timevalue;
	}
	//=============================================================================================

	//=============================================================================================
	public void update(float dT) {
		timevalue = (timevalue + dT * timescale) % ((steps()-1) * steptime());
		interpolateSkeleton();
		updateSkeletonMatrices();
		interpolateMesh();
	}
	//=============================================================================================

	//=============================================================================================
	private void interpolateSkeleton() {
		
		// for each bone
		for (int i=0; i<animation.keyframes().size(); i++) {
			
			// determine keyframes for interpolation
			Keyframes kfs = animation.keyframes().get(i);
			int a = 0;
			int b = -1;
			
			for (int j=0; j<kfs.list().size(); j++) {
				Keyframe kf = kfs.list().get(j);
				float time = kf.step() * steptime();
				if (time > timevalue()) {
					b = j;
					break;
				} else {
					a = j;
				}
			}

			Keyframe kfa = kfs.list().get(a); 
			Keyframe kfb = kfs.list().get(b);
			
			// interpolate bone values
			Vector3f pos = bonePositions.get(i);
			Quat4f   ori = boneOrientations.get(i);
			Matrix4f mat = boneLocalMatrices.get(i);

			float ta = kfa.step() * steptime();
			float tb = kfb.step() * steptime();
			float d = tb-ta;
			
			if (d*d < 0.000001f) {
				
				pos.set(kfa.position());
				ori.set(kfa.orientation());
				
			} else {

				float alpha = (timevalue()-ta) / (tb-ta);
				
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
	private void updateSkeletonMatrices() {
		Matrix4f identity = new Matrix4f();
		identity.setIdentity();
		for (Bone bone : skeleton().roots()) {
			updateSkeletonMatrices(identity, bone);
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void updateSkeletonMatrices(Matrix4f parentMatrix, Bone bone) {
		int idx = skeleton().bones().indexOf(bone);
		Matrix4f localMatrix = boneLocalMatrices.get(idx);
		Matrix4f modelMatrix = boneModelMatrices.get(idx);
		modelMatrix.mul(parentMatrix, localMatrix);
		for (Bone child : bone.children()) {
			updateSkeletonMatrices(modelMatrix, child);
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	private Vector3f _pos = new Vector3f();
	private Vector3f _bon = new Vector3f();
	private Vector3f _dlt = new Vector3f();
	//=============================================================================================
	
	//=============================================================================================
	private void interpolateMesh() {

		// for each vertex
		for (int i=0; i<mesh().size; i++) {
		
			// get default vertex position
			_pos.x = mesh().positions.get(i*3+0);
			_pos.y = mesh().positions.get(i*3+1);
			_pos.z = mesh().positions.get(i*3+2);
			
			// for each bone
			for (int j=0; j<skeleton().bones().size(); j++) {

				// get default bone position
				_bon.set(0, 0, 0);
				
				_dlt.sub(_pos, _bon);
				FloatBuffer buffer = influence().influences().get(j);
				float scale = buffer.get(i);
				
				Matrix4f mat = boneMatrices.get(j);
				
				// TODO
				
				
			}
		}
	}
	//=============================================================================================
	
}
//*************************************************************************************************
