//*************************************************************************************************
package age.skeleton;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

//*************************************************************************************************
public class Bone {

	//=============================================================================================
	private String name;
	//=============================================================================================

	//=============================================================================================
	private Bone parent = null;
	//=============================================================================================

	//=============================================================================================
	private List<Bone> children = new ArrayList<>();
	private List<Bone> children_ro = Collections.unmodifiableList(children);
	//=============================================================================================
	
	//=============================================================================================
	private Vector3f translation = new Vector3f();
	private Quat4f orientation = new Quat4f(0, 0, 0, 1);
	//=============================================================================================

	//=============================================================================================
	private List<Keyframe> keyframes = new ArrayList<>();
	private List<Keyframe> keyframes_ro = Collections.unmodifiableList(keyframes);
	//=============================================================================================

	//=============================================================================================
	public String name() {
		return name;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void name(String name) {
		this.name = name;
	}
	//=============================================================================================

	//=============================================================================================
	public Bone parent() {
		return parent;
	}
	//=============================================================================================

	//=============================================================================================
	public Bone child(String name) {
		Bone bone = new Bone();
		bone.name(name);
		children.add(bone);
		return bone;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void child(Bone bone) {
		bone.parent = this;
		children.add(bone);
	}
	//=============================================================================================
	
	//=============================================================================================
	public List<Bone> children() {
		return children_ro;
	}
	//=============================================================================================
	
	//=============================================================================================
	public Vector3f translation() {
		return translation;
	}
	//=============================================================================================

	//=============================================================================================
	public void translation(Vector3f t) {
		translation.set(t);
	}
	//=============================================================================================

	//=============================================================================================
	public void translation(float x, float y, float z) {
		translation.set(x, y, z);
	}
	//=============================================================================================
	
	//=============================================================================================
	public Quat4f orientation() {
		return orientation;
	}
	//=============================================================================================

	//=============================================================================================
	public void orientation(Quat4f o) {
		orientation.set(o);
	}
	//=============================================================================================

	//=============================================================================================
	public Keyframe keyframe(float time) {
		Keyframe k = new Keyframe();
		k.time(time);
		k.translation(this.translation);
		k.orientation(this.orientation);
		keyframe(k);
		return k;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void keyframe(Keyframe keyframe) {
		int i= 0;
		for (; i<keyframes.size(); i++) {
			Keyframe cmp = keyframes.get(i);
			if (cmp.time() > keyframe.time()) break;
		}
		keyframes.add(i, keyframe);
	}
	//=============================================================================================
	
	//=============================================================================================
	public List<Keyframe> keyframes() {
		return keyframes_ro;
	}
	//=============================================================================================

	//=============================================================================================
	public void interpolate(Vector3f r, Quat4f o, float time) {

		var idx = findIndex(time);

		if ((idx < 0) || (idx >= keyframes.size()-1)) {
			r.set(this.translation);
			o.set(this.orientation);
			
		} else {
			
			var k1 = keyframes.get(idx+0);
			var k2 = keyframes.get(idx+1);

			var prt = time - k1.time();
			var net = k2.time() - k1.time();
			var s = prt / net;

			r.interpolate(k1.translation(), k2.translation(), s);
			o.interpolate(k1.orientation(), k2.orientation(), s);

		}

	}
	//=============================================================================================

	//=============================================================================================
	private int findIndex(float time) {
		int idx = -1;
		for (int i=0; i<keyframes.size(); i++) {
			Keyframe k = keyframes.get(i);
			float cmp = k.time();
			if (cmp > time) break;
			idx++;
		}
		return idx;
	}
	//=============================================================================================	
	
}
//*************************************************************************************************
