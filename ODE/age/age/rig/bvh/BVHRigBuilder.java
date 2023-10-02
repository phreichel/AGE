//*************************************************************************************************
package age.rig.bvh;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.vecmath.Matrix3f;
import javax.vecmath.Vector3f;
import age.rig.Bone;
import age.rig.Keyframe;
import age.rig.Skeleton;
import age.util.X;

//*************************************************************************************************
public class BVHRigBuilder implements BVHBuilder {

	//=============================================================================================
	private Skeleton    skeleton = null;
	private Stack<Bone> bones = new Stack<>();
	private Bone        bone  = null;
	//=============================================================================================

	//=============================================================================================
	private List<Bone>   anBone  = new ArrayList<>();
	private List<Bone>   chBone  = new ArrayList<>();
	private List<String> chName  = new ArrayList<>();
	//=============================================================================================
	
	//=============================================================================================
	public void startFile() {
		skeleton = new Skeleton();
		bones.clear();
		bone = null;
		anBone.clear();
		chBone.clear();
		chName.clear();
	}
	//=============================================================================================

	//=============================================================================================
	public void endFile() {
		bones.clear();
		bone = null;
		anBone.clear();
		chBone.clear();
		chName.clear();
	}
	//=============================================================================================

	//=============================================================================================
	public void startBone(
			String name,
			float ofsx,
			float ofsy,
			float ofsz
	) {
		Bone newBone = new Bone();
		if (bone != null) {
			bone.child(newBone);
			bones.push(bone);
		} else {
			skeleton.bone(newBone);
		}
		bone = newBone;
		bone.name(name);
		bone.translation(ofsx, ofsy, ofsz);
	}
	//=============================================================================================

	//=============================================================================================
	public void endBone() {
		bone = bones.isEmpty() ? null : bones.pop();
	}
	//=============================================================================================

	//=============================================================================================
	public void writeChannelCount(int count) {
		anBone.add(bone);
	}
	//=============================================================================================

	//=============================================================================================
	public void writeChannelName(String channelName) {
		chBone.add(bone);
		chName.add(channelName);
	}
	//=============================================================================================

	//=============================================================================================
	int fcount = 0;
	int findex = 0;
	float ftime = 0f;
	//=============================================================================================
	
	//=============================================================================================
	public void writeFrameCount(int frameCount) {
		fcount = frameCount;
	}
	//=============================================================================================

	//=============================================================================================
	public void writeFrameTime(float frameTime) {
		ftime = frameTime;
		float nettime = ftime * (fcount-1);
		skeleton.time(nettime);
	}
	//=============================================================================================

	//=============================================================================================
	private final Vector3f tx = new Vector3f();
	private final Vector3f rx = new Vector3f();
	//=============================================================================================

	//=============================================================================================
	private final Matrix3f mRz = new Matrix3f();  
	private final Matrix3f mRy = new Matrix3f();  
	private final Matrix3f mRx = new Matrix3f();  
	private final Matrix3f mRo = new Matrix3f();  
	//=============================================================================================
	
	//=============================================================================================
	public void writeFrameData(float... frameValues) {

		float ft = ftime * findex;
		
		for (Bone now : anBone) {
			Keyframe kf = now.keyframe(ft);
			tx.set(kf.translation());
			rx.set(0, 0, 0);
			for (int i=0; i<frameValues.length; i++) {
				Bone cmp = chBone.get(i);
				if (now == cmp) {
					String name = chName.get(i);
					if (name.equals("Xposition")) tx.x = frameValues[i];
					else if (name.equals("Yposition")) tx.y = frameValues[i];
					else if (name.equals("Zposition")) tx.z = frameValues[i];
					else if (name.equals("Xrotation")) rx.x = (float) Math.toRadians(frameValues[i]);
					else if (name.equals("Yrotation")) rx.y = (float) Math.toRadians(frameValues[i]);
					else if (name.equals("Zrotation")) rx.z = (float) Math.toRadians(frameValues[i]);
					else throw new X("unsupported channel: %s", name);
				}
			}
			kf.translation(tx);
			if (rx.lengthSquared() > 0.001f) {
				mRz.rotZ(rx.z);
				mRy.rotY(rx.y);
				mRx.rotX(rx.x);
				mRo.mul(mRz, mRy);
				mRo.mul(mRo, mRx);
				kf.orientation(mRo);
			}
		}

		findex++;
		
	}
	//=============================================================================================

	//=============================================================================================
	public Skeleton build() {
		Skeleton tmp = skeleton; 
		skeleton = null;
		return tmp;
	}
	//=============================================================================================
	
}
//*************************************************************************************************
