//*************************************************************************************************
package age.rig;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//*************************************************************************************************
public class Skeleton {

	//=============================================================================================
	private static final Factory factory = new Factory();
	//=============================================================================================

	//=============================================================================================
	public static Factory factory() {
		return factory;
	}
	//=============================================================================================
	
	//=============================================================================================
	private float      time  = 0f;
	private List<Bone> bones = new ArrayList<>();
	private List<Bone> bones_ro = Collections.unmodifiableList(bones);
	//=============================================================================================

	//=============================================================================================
	public void bone(Bone bone) {
		bones.add(bone);
	}
	//=============================================================================================

	//=============================================================================================
	public Bone bone(String name) {
		Bone bone = new Bone();
		bone.name(name);
		bones.add(bone);
		return bone;
	}
	//=============================================================================================
	
	//=============================================================================================
	public List<Bone> bones() {
		return bones_ro;
	}
	//=============================================================================================

	//=============================================================================================
	public float time() {
		return time;
	}
	//=============================================================================================

	//=============================================================================================
	public void time(float time) {
		this.time = time;
	}
	//=============================================================================================

	//=============================================================================================
	public String toString() {
		String str = "SKELETON {";
		str += "TIME: " + time;
		str += "; ";
		str += "BONES: ";
		for (Bone ch : bones) str += ch.toString();
		str += "}";
		return str;
	}
	//=============================================================================================
	
}
//*************************************************************************************************
