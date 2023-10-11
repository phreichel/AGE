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
			float scale = (dst < 1f) ? 1f : 1f - Math.min(1f, (dst-1f) * 1f);
			influences[vidx][b.index] = scale;
			Vector3f cofs = new Vector3f(ofs);
			cofs.add(b.position);
			calc(vidx, cofs, vpos, b.children);
		}
	}
	//=============================================================================================
	
}
//*************************************************************************************************
