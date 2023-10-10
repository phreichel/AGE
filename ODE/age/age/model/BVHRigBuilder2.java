//*************************************************************************************************
package age.model;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Quat4f;
import age.rig.bvh.BVHBuilder;
import age.util.X;

//*************************************************************************************************
public class BVHRigBuilder2 implements BVHBuilder {

	//=============================================================================================
	private Animation animation = null;
	private Skeleton skeleton = null;
	private Bone bone = null;
	//=============================================================================================

	//=============================================================================================
	private int bone_index = 0;
	private int frame_index = 0;
	//=============================================================================================
	
	//=============================================================================================
	private List<Bone> chBone = new ArrayList<>();
	private List<String>  chName = new ArrayList<>();
	//=============================================================================================
	
	//=============================================================================================
	public void startFile() {
		clear();
		skeleton = new Skeleton();
		animation = new Animation(skeleton);
	}
	//=============================================================================================

	//=============================================================================================
	public void endFile() {}
	//=============================================================================================

	//=============================================================================================
	public void startBone(
			String name,
			float ofsx,
			float ofsy,
			float ofsz
	) {
		Bone newBone = new Bone(name, bone_index++, bone);
		animation.keyframes.put(newBone.index, new Keyframes());
		if (bone == null) skeleton.roots.add(newBone);
		newBone.position.set(ofsx, ofsy, ofsz);
		bone = newBone;
	}
	//=============================================================================================

	//=============================================================================================
	public void endBone() {
		bone = bone.parent;
	}
	//=============================================================================================

	//=============================================================================================
	public void writeChannelCount(int count) {}
	//=============================================================================================

	//=============================================================================================
	public void writeChannelName(String channelName) {
		chBone.add(bone);
		chName.add(channelName);
	}
	//=============================================================================================

	//=============================================================================================
	public void writeFrameCount(int frameCount) {
		animation.steps = frameCount;
	}
	//=============================================================================================

	//=============================================================================================
	public void writeFrameTime(float frameTime) {
		animation.steptime = frameTime;
	}
	//=============================================================================================

	//=============================================================================================
	public void writeFrameData(float ... frameValues) {
		
		assert (chBone.size() == frameValues.length);
		assert (chName.size() == frameValues.length);
		
		Map<Integer, Keyframe> map = new HashMap<>();
		for (int i=0; i<frameValues.length; i++) {
			
			Bone   b = chBone.get(i);
			String n = chName.get(i);
			float  v = frameValues[i];

			Keyframe kf = map.get(b.index);
			if (kf == null) {
				kf = new Keyframe();
				kf.step = frame_index;
				map.put(b.index, kf);
				Keyframes kfs = animation.keyframes.get(b.index);
				kfs.list.add(kf);
			}

			kf.position.set(0, 0, 0);
			kf.orientation.set(0, 0, 0, 1);
			
			if      (n.equals("Xposition")) kf.position.x = v;
			else if (n.equals("Yposition")) kf.position.y = v;
			else if (n.equals("Zposition")) kf.position.z = v;
			else if (n.equals("Xrotation")) orient(kf, 1, 0, 0,  v);
			else if (n.equals("Yrotation")) orient(kf, 0, 1, 0,  v);
			else if (n.equals("Zrotation")) orient(kf, 0, 0, 1,  v);
			else throw new X("unsupported channel: %s", n);
			
		}
		
		frame_index++;
		
	}
	//=============================================================================================

	//=============================================================================================
	private void orient(Keyframe kf, float x, float y, float z, float v) {
		AxisAngle4f aa = new AxisAngle4f(x, y, z, (float) Math.toRadians(v));
		Quat4f q = new Quat4f();
		q.set(aa);
		kf.orientation.mul(q);
		kf.orientation.normalize();
	}
	//=============================================================================================
	
	//=============================================================================================
	public Animation build() {
		Animation tmp = animation;
		clear();
		return tmp;
	}
	//=============================================================================================

	//=============================================================================================
	private void clear() {
		bone_index = 0;
		frame_index = 0;
		animation = null;
		skeleton = null;
		bone = null;
		chBone.clear();
		chName.clear();
	}
	//=============================================================================================
	
}
//*************************************************************************************************
