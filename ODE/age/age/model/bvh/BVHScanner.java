/*
 * Commit: 7407dc59dacb80fc02cd2de06a33b6b78cc37f8e
 * Date: 2023-10-12 13:32:56+02:00
 * Author: pre7618
 * Comment: Test
 *
 */

//*************************************************************************************************
package age.model.bvh;
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
