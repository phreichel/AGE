//*************************************************************************************************
package age.model.obj;
//*************************************************************************************************

import age.util.Scanner;

//*************************************************************************************************
public class ObjectScanner extends Scanner {

	//=============================================================================================
	protected boolean isKeyword(String s) {
		return
			s.equals("g") ||
			s.equals("o") ||
			s.equals("v") ||
			s.equals("s") ||
			s.equals("f") ||
			s.equals("l") ||
			s.equals("vn") ||
			s.equals("vt") ||
			s.equals("vp") ||
			s.equals("off") ||
			s.equals("mtllib") ||
			s.equals("usemtl");
	}
	//=============================================================================================

}
//*************************************************************************************************
