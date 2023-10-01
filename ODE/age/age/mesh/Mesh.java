//*************************************************************************************************
package age.mesh;
//*************************************************************************************************

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

//*************************************************************************************************
public class Mesh {

	//=============================================================================================
	private static final Factory factory = new Factory();
	//=============================================================================================

	//=============================================================================================
	public static final Factory factory() {
		return factory;
	}
	//=============================================================================================
	
	//=============================================================================================
	public final UUID uuid = UUID.randomUUID();
	//=============================================================================================
	
	//=============================================================================================
	public final FloatBuffer   positions;
	public final FloatBuffer   textures;
	public final FloatBuffer   normals;
	public final List<Submesh> submeshes = new ArrayList<>();
	//=============================================================================================

	//=============================================================================================
	public Mesh(int count) {
		positions = FloatBuffer.allocate(count * 3);
		textures  = FloatBuffer.allocate(count * 2);
		normals   = FloatBuffer.allocate(count * 3);
		positions.rewind();
		textures.rewind();
		normals.rewind();
	}
	//=============================================================================================

	//=============================================================================================
	public void vertex(
		Vector3f pos,
		Vector2f tex,
		Vector3f nrm
	) {
		positions.put(pos.x);
		positions.put(pos.y);
		positions.put(pos.z);
		textures.put(tex.x);
		textures.put(tex.y);
		normals.put(nrm.x);
		normals.put(nrm.y);
		normals.put(nrm.z);
	}
	//=============================================================================================
	
}
//*************************************************************************************************
