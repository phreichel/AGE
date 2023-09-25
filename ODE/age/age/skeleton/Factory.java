//*************************************************************************************************
package age.skeleton;

import javax.vecmath.AxisAngle4f;
import javax.vecmath.Quat4f;

import com.google.common.math.Quantiles;

//*************************************************************************************************

//*************************************************************************************************
public class Factory {

	//=============================================================================================
	public static Skeleton create() {
		
		Skeleton s = new Skeleton();
		Keyframe k = null;

		AxisAngle4f aa = new AxisAngle4f();
		Quat4f aq = new Quat4f();
	
		Bone bHipLeft = new Bone();
		bHipLeft.name("hip.left");
		bHipLeft.translation(-.3f, 1.2f, 0f);
		
		k = new Keyframe();
		k.time(0);
		k.translation(bHipLeft.translation());
		k.orientation(bHipLeft.orientation());
		bHipLeft.keyframe(k);
		
		k = new Keyframe();
		k.time(5);
		k.translation(bHipLeft.translation());
		k.orientation(bHipLeft.orientation());
		aa.set(1f, 0f , 0f, (float) Math.toRadians(25f));
		aq.set(aa);
		k.orientation().mul(aq, k.orientation());
		bHipLeft.keyframe(k);
		
		s.bone(bHipLeft);

		Bone bKneeLeft = new Bone();
		bKneeLeft.name("knee.left");
		bKneeLeft.translation(-.1f, -.6f, .1f);
		bHipLeft.child(bKneeLeft);

		Bone bFootLeft = new Bone();
		bFootLeft.name("foot.left");
		bFootLeft.translation(.1f, -.6f, -.1f);
		bKneeLeft.child(bFootLeft);

		Bone bHipRight = new Bone();
		bHipRight.name("hip.right");
		bHipRight.translation(.3f, 1.2f, 0f);
		s.bone(bHipRight);

		Bone bKneeRight = new Bone();
		bKneeRight.name("knee.right");
		bKneeRight.translation(.1f, -.6f, .1f);
		bHipRight.child(bKneeRight);

		Bone bFootRight = new Bone();
		bFootRight.name("foot.right");
		bFootRight.translation(-.1f, -.6f, -.1f);
		bKneeRight.child(bFootRight);
	
		return s;
		
	}
	//=============================================================================================

}
//*************************************************************************************************
