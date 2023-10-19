/*
 * Commit: abb79765d6c7fa32d18352f742be87ce2b7688c6
 * Date: 2023-10-12 05:01:43+02:00
 * Author: Philip Reichel
 * Comment: ,,
 *
 * Commit: 18d2f76ceedb6003ef53119b6e00bf0da3c4ff1d
 * Date: 2023-10-11 17:52:14+02:00
 * Author: pre7618
 * Comment: First Animated Rigged Mesh!
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

import java.util.List;

import javax.vecmath.Vector3f;

//*************************************************************************************************
public class Influence {

	//=============================================================================================
	public final Skeleton skeleton;
	public final Mesh mesh;
	public final float[][] influences;
	//=============================================================================================

	//=============================================================================================
	public Influence(
		Skeleton skeleton,
		Mesh mesh
	) {
		this.skeleton = skeleton;
		this.mesh = mesh;
		this.influences = new float[mesh.positions.limit()/3][skeleton.count()];
		for (int i=0; i<mesh.positions.limit()/3; i++) {
			Vector3f p = new Vector3f();
			p.x = mesh.positions.get(i*3+0);
			p.y = mesh.positions.get(i*3+1);
			p.z = mesh.positions.get(i*3+2);
			calc(i, new Vector3f(), p, skeleton.roots);
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void calc(int vidx, Vector3f ofs, Vector3f vpos, List<Bone> bones) {
		for (Bone b : bones) {
			Vector3f bpos = new Vector3f(b.position);
			bpos.add(ofs);
			Vector3f delta = new Vector3f();
			delta.sub(vpos, bpos);
			float dst = delta.length();
			float scale = (dst < 1f) ? .5f : (1f - Math.min(1f, dst-1f)) * .5f;
			influences[vidx][b.index] = scale;
			Vector3f cofs = new Vector3f(ofs);
			cofs.add(b.position);
			calc(vidx, cofs, vpos, b.children);
		}
	}
	//=============================================================================================
	
}
//*************************************************************************************************
