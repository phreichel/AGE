//*************************************************************************************************
package age.rig;
//*************************************************************************************************

//*************************************************************************************************
public class Factory {

	//=============================================================================================
	public static Skeleton create() {
		
		Skeleton s = new Skeleton();
		Keyframe k = null;

		s.time(4f);

		Bone bHip = s.bone("hip");
		bHip.translation(0f, 1.3f, 0f);
		k = bHip.keyframe(0f);
		k.addOrientation(0, 1, 0, -10);
		k.addOrientation(0, 0, 1, -10);
		k = bHip.keyframe(2f);
		k.addOrientation(0, 1, 0, 10);
		k.addOrientation(0, 0, 1, 10);
		k = bHip.keyframe(4f);
		k.addOrientation(0, 1, 0, -10);
		k.addOrientation(0, 0, 1, -10);
		
		
		Bone bHipLeft = bHip.child("hip.left");
		bHipLeft.translation(-.2f, -.1f, 0f);
		k = bHipLeft.keyframe(0f);
		k = bHipLeft.keyframe(1f);
		k.addOrientation(1, 0, 0, -45f);
		k = bHipLeft.keyframe(2f);
		k = bHipLeft.keyframe(3f);
		k.addOrientation(1, 0, 0, 60f);
		k = bHipLeft.keyframe(4f);

		Bone bKneeLeft = bHipLeft.child("knee.left");
		bKneeLeft.name("knee.left");
		bKneeLeft.translation(0f, -.6f, .1f);
		k = bKneeLeft.keyframe(0f);
		k.addOrientation(1, 0, 0, 70f);
		k = bKneeLeft.keyframe(1f);
		k = bKneeLeft.keyframe(2f);
		k = bKneeLeft.keyframe(2.25f);
		k = bKneeLeft.keyframe(3f);
		k.addOrientation(1, 0, 0, 70f);
		k = bKneeLeft.keyframe(4f);
		k.addOrientation(1, 0, 0, 70f);

		Bone bFootLeft = bKneeLeft.child("foot.left");
		bFootLeft.translation(.1f, -.6f, -.1f);

		Bone bHipRight = bHip.child("hip.right");
		bHipRight.translation(.2f, -.1f, 0f);
		k = bHipRight.keyframe(0f);
		k = bHipRight.keyframe(1f);
		k.addOrientation(1, 0, 0, 60f);
		k = bHipRight.keyframe(2f);
		k = bHipRight.keyframe(3f);
		k.addOrientation(1, 0, 0, -45f);
		k = bHipRight.keyframe(4f);

		Bone bKneeRight = bHipRight.child("knee.right");
		bKneeRight.translation(0f, -.6f, .1f);
		k = bKneeRight.keyframe(0f);
		k = bKneeRight.keyframe(.25f);
		k = bKneeRight.keyframe(1f);
		k.addOrientation(1, 0, 0, 70f);
		k = bKneeRight.keyframe(2f);
		k.addOrientation(1, 0, 0, 70f);
		k = bKneeRight.keyframe(3f);
		k = bKneeRight.keyframe(4f);

		Bone bFootRight = bKneeRight.child("foot.right");
		bFootRight.translation(-.1f, -.6f, -.1f);

		Bone bChest = bHip.child("chest");
		bChest.translation(0f, .5f, .1f);
		k = bChest.keyframe(0);
		k.addOrientation(0, 1, 0, 20);
		k.addOrientation(0, 0, 1, 10);
		k = bChest.keyframe(2f);
		k.addOrientation(0, 1, 0, -20);
		k.addOrientation(0, 0, 1, -10);
		k = bChest.keyframe(4f);
		k.addOrientation(0, 1, 0, 20);
		k.addOrientation(0, 0, 1, 10);

		Bone bNeck = bChest.child("neck");
		bNeck.translation(0, .1f, -.05f);

		Bone bShoulderLeft = bNeck.child("shoulder.left");
		bShoulderLeft.translation(-.3f, .05f, -.05f);
		k = bShoulderLeft.keyframe(0);
		k = bShoulderLeft.keyframe(1);
		k.addOrientation(1, 0, 0, 45);
		k = bShoulderLeft.keyframe(2);
		k = bShoulderLeft.keyframe(3);
		k.addOrientation(1, 0, 0, -15);
		k = bShoulderLeft.keyframe(4);

		Bone bEllbowLeft = bShoulderLeft.child("ellbow.left");
		bEllbowLeft.translation(0f, -.5f, 0f);
		k = bEllbowLeft.keyframe(0);
		k = bEllbowLeft.keyframe(1);
		k.addOrientation(1, 0, 0, -30);
		k = bEllbowLeft.keyframe(2);
		k = bEllbowLeft.keyframe(3);
		k.addOrientation(1, 0, 0, 5);
		k = bEllbowLeft.keyframe(4);

		Bone bWristLeft = bEllbowLeft.child("wrist.left");
		bWristLeft.translation(0f, 0f, .5f);
		
		Bone bShoulderRight = bNeck.child("shoulder.right");
		bShoulderRight.translation( .3f, .05f, -.05f);
		k = bShoulderRight.keyframe(0);
		k = bShoulderRight.keyframe(1);
		k.addOrientation(1, 0, 0, -15);
		k = bShoulderRight.keyframe(2);
		k = bShoulderRight.keyframe(3);
		k.addOrientation(1, 0, 0, 45);
		k = bShoulderRight.keyframe(4);

		Bone bEllbowRight = bShoulderRight.child("ellbow.right");
		bEllbowRight.translation(0f, -.5f, 0f);
		k = bEllbowRight.keyframe(0);
		k = bEllbowRight.keyframe(1);
		k.addOrientation(1, 0, 0, 5);
		k = bEllbowRight.keyframe(2);
		k = bEllbowRight.keyframe(3);
		k.addOrientation(1, 0, 0, -30);
		k = bEllbowRight.keyframe(4);

		Bone bWristRight = bEllbowRight.child("wrist.right");
		bWristRight.translation(0f, 0f, .5f);
		
		return s;
		
	}
	//=============================================================================================

}
//*************************************************************************************************
