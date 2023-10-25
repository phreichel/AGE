//*************************************************************************************************
package age.model;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.List;

//*************************************************************************************************
public class Skeleton {

	//=============================================================================================
	public final List<Bone> roots = new ArrayList<>();
	//=============================================================================================

	//=============================================================================================
	public int count() {
		return count(roots);
	}
	//=============================================================================================

	//=============================================================================================
	private int count(List<Bone> bones) {
		int count = 0;
		for (Bone b : bones) {
			count++;
			count += count(b.children);
		}
		return count;
	}
	//=============================================================================================
	
}
//*************************************************************************************************