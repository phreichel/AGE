/*
 * Commit: 58407180b4578ab8fe91b005b6bcf51e549abaa7
 * Date: 2023-10-11 00:53:39+02:00
 * Author: Philip Reichel
 * Comment: Rigging revisited.
 *
 * Commit: d7587bbba419eaa855b68a9e0ccc30ec13873658
 * Date: 2023-10-10 17:02:07+02:00
 * Author: pre7618
 * Comment: Update to crappy version
 *
 * Commit: 00a610434f6fec6c19360cdcbe9949c18fea5a3b
 * Date: 2023-10-10 10:10:29+02:00
 * Author: pre7618
 * Comment: ..
 *
 * Commit: e0af20930ce5c29d8c32b48b9731826e17bfa88d
 * Date: 2023-10-09 07:41:02+02:00
 * Author: Philip Reichel
 * Comment: ,,,
 *
 * Commit: eb306b1e6a0957eb9ec0dc0accbc1747e9f08d0f
 * Date: 2023-10-04 01:48:00+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 */

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
