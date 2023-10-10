//*************************************************************************************************
package age.model;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.vecmath.Matrix3f;
import javax.vecmath.Vector3f;
import age.rig.bvh.BVHBuilder;
import age.util.X;

//*************************************************************************************************
public class BVHRigBuilder2 implements BVHBuilder {

	//=============================================================================================
	private Animation animation = null;
	private Skeleton skeleton = null;
	private Stack<Bone> bones = new Stack<>();
	private Bone bone = null;
	//=============================================================================================
	
	//=============================================================================================
	private List<Bone>   anBone  = new ArrayList<>();
	private List<Bone>   chBone  = new ArrayList<>();
	private List<String> chName  = new ArrayList<>();
	//=============================================================================================
	
	//=============================================================================================
	public void startFile() {
		skeleton = new Skeleton("SKELETON");
		animation = new Animation(skeleton);
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
		Bone newBone = new Bone(name, bone);
		if (bone != null) bones.push(bone);
		if (bone == null) skeleton.roots.add(newBone);
		newBone.position.set(ofsx, ofsy, ofsz);
		bone = newBone;
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
	int findex = 0;
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
		for (Bone now : anBone) {
			
			Keyframe kf = new Keyframe();
			kf.step = findex; 

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
