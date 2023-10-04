//*************************************************************************************************
package age.model;
//*************************************************************************************************

import java.nio.FloatBuffer;
import java.util.List;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

//*************************************************************************************************
public class Mesh {

	//=============================================================================================
	private int size;
	private FloatBuffer positions;
	private FloatBuffer textures;
	private FloatBuffer normals;
	//=============================================================================================

	//=============================================================================================
	public int size() {
		return size;
	}
	//=============================================================================================

	//=============================================================================================
	public void fill(
		List<Integer> indices,
		List<Float> positions,
		List<Float> textures,
		List<Float> normals) {
		int size = indices.size() / 3;
		this.positions = FloatBuffer.allocate(size*3);
		this.textures  = FloatBuffer.allocate(size*2);
		this.normals   = FloatBuffer.allocate(size*3);
		for(int i=0; i<size; i++) {
			int ipos = indices.get(i+0);
			int itex = indices.get(i+1);
			int inrm = indices.get(i+2);
			this.positions.put(i*3+0, positions.get(ipos*3+0));
			this.positions.put(i*3+1, positions.get(ipos*3+1));
			this.positions.put(i*3+2, positions.get(ipos*3+2));
			this.textures.put(i*2+0, positions.get(itex*3+0));
			this.textures.put(i*2+1, positions.get(itex*3+1));
			this.normals.put(i*3+0, normals.get(inrm*3+0));
			this.normals.put(i*3+1, normals.get(inrm*3+1));
			this.normals.put(i*3+2, normals.get(inrm*3+2));
		}
		this.positions.rewind();
		this.textures.rewind();
		this.normals.rewind();
	}
	//=============================================================================================
	
	//=============================================================================================
	public Vector3f getPosition(int idx, Vector3f dst) {
		float x = positions.get(idx*3+0);
		float y = positions.get(idx*3+1);
		float z = positions.get(idx*3+1);
		dst.set(x, y, z);
		return dst;
	}
	//=============================================================================================

	//=============================================================================================
	public void setPosition(int idx, Vector3f src) {
		positions.put(idx*3+0, src.x);
		positions.put(idx*3+1, src.y);
		positions.put(idx*3+2, src.z);
	}
	//=============================================================================================
	
	//=============================================================================================
	public Vector2f getTexture(int idx, Vector2f dst) {
		float x = positions.get(idx*2+0);
		float y = positions.get(idx*2+1);
		dst.set(x, y);
		return dst;
	}
	//=============================================================================================

	//=============================================================================================
	public void setTexture(int idx, Vector2f src) {
		textures.put(idx*2+0, src.x);
		textures.put(idx*2+1, src.y);
	}
	//=============================================================================================

	//=============================================================================================
	public Vector3f getNormal(int idx, Vector3f dst) {
		float x = normals.get(idx*3+0);
		float y = normals.get(idx*3+1);
		float z = normals.get(idx*3+2);
		dst.set(x, y, z);
		return dst;
	}
	//=============================================================================================

	//=============================================================================================
	public void setNormal(int idx, Vector3f src) {
		normals.put(idx*3+0, src.x);
		normals.put(idx*3+1, src.y);
		normals.put(idx*3+2, src.z);
	}
	//=============================================================================================
	
	//=============================================================================================
	public FloatBuffer positions() {
		return positions;
	}
	//=============================================================================================

	//=============================================================================================
	public FloatBuffer textures() {
		return textures;
	}
	//=============================================================================================

	//=============================================================================================
	public FloatBuffer normals() {
		return normals;
	}
	//=============================================================================================

}
//*************************************************************************************************
