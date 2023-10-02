//*************************************************************************************************
package age.rig.bvh;
//*************************************************************************************************

import age.util.Scanner;

//*************************************************************************************************
public class BVHScanner extends Scanner {

	//=============================================================================================
	protected boolean isKeyword(String s) {
		return
			s.equals("HIERARCHY") ||
			s.equals("ROOT") ||
			s.equals("OFFSET") ||
			s.equals("CHANNELS") ||
			s.equals("JOINT") ||
			s.equals("MOTION") ||
			s.equals("Xposition") ||
			s.equals("Yposition") ||
			s.equals("Zposition") ||
			s.equals("Xrotation") ||
			s.equals("Yrotation") ||
			s.equals("Zrotation") ||
			s.equals("End") ||
			s.equals("Site") ||
			s.equals("Frames") ||
			s.equals("Frame") ||
			s.equals("Time");
	}
	//=============================================================================================

}
//*************************************************************************************************
