//*************************************************************************************************
package age.model;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.List;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

//*************************************************************************************************
public class Bone {

	//=============================================================================================
	public final String name;
	public final int index;
	public final Bone parent;
	public final List<Bone> children = new ArrayList<>();
	public final Vector3f position = new Vector3f(0, 0, 0);
	public final Quat4f orientation = new Quat4f(0, 0, 0, 1);
	//=============================================================================================

	//=============================================================================================
	public Bone(
		String name,
		int index,
		Bone parent
	) {
		this.name = name;
		this.index = index;
		this.parent = parent;
		if (parent != null) parent.children.add(this);
	}
	//=============================================================================================
	
}
//*************************************************************************************************
