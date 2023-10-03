//*************************************************************************************************
package age.mesh.obj;
//*************************************************************************************************

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import age.mesh.ElementType;
import age.mesh.Material;
import age.mesh.Mesh;
import age.mesh.Submesh;
import age.util.X;

//*************************************************************************************************
public class MeshBuilder implements ObjectBuilder {

	//=============================================================================================
	private static final int TYPE_TRIANGLES = 0;
	private static final int TYPE_LINES     = 1;
	//=============================================================================================
	
	//=============================================================================================
	private static class Triplet {

		public int position = 0;
		public int texture  = 0;
		public int normal   = 0;
		
		public Triplet(
			int position,
			int texture,
			int normal) {
			this.position = position;
			this.texture = texture;
			this.normal = normal;
		}

	}
	//=============================================================================================

	//=============================================================================================
	private static class Segment {
		
		public int         type;
		public String      material = null;
		public List<int[]> indices  = new ArrayList<>();
		
		public Segment(
				int type,
				String material) {
			this.type = type;
			this.material = material;
			this.indices = new ArrayList<>();
		}
		
	}
	//=============================================================================================
	
	//=============================================================================================
	private static Vector3f newPosition(float x, float y, float z) {
		return new Vector3f(x, y, z);
	}
	//=============================================================================================

	//=============================================================================================
	private static Vector3f newNormal(float nx, float ny, float nz) {
		return new Vector3f(nx, ny, nz);
	}
	//=============================================================================================

	//=============================================================================================
	private static Vector2f newTexture(float tu, float tv) {
		return new Vector2f(tu, tv);
	}
	//=============================================================================================
	
	//=============================================================================================
	private Triplet newTriplet(int position, int texture, int normal) {
		position = (position >= 0) ? position : positions.size() + position;
		texture = (texture >= 0) ? texture : textures.size() + texture;
		normal = (normal >= 0) ? normal : normals.size() + normal;
		return new Triplet(position, texture, normal);
	}
	//=============================================================================================

	//=============================================================================================
	private static int[] newIndices(int ... indices) {
		return indices;
	}
	//=============================================================================================
	
	//=============================================================================================
	private Map<String, Material> materials = new HashMap<>();
	private List<Vector3f> positions = new ArrayList<>();
	private List<Vector3f> normals   = new ArrayList<>();
	private List<Vector2f> textures  = new ArrayList<>();
	private List<Triplet>  triplets  = new ArrayList<>();
	private List<Segment>  segments  = new ArrayList<>();
	//=============================================================================================

	//=============================================================================================
	private int     type     = -1;
	private String  material = null;
	private Segment segment  = null;
	//=============================================================================================
	
	//=============================================================================================
	private int base  = 0;
	private int count = 0;
	//=============================================================================================
	
	//=============================================================================================
	public void startFile() {
		reset();
	}
	//=============================================================================================

	//=============================================================================================
	public void endFile() {
	}
	//=============================================================================================

	//=============================================================================================
	public void startGroup(String name) {
	}
	//=============================================================================================

	//=============================================================================================
	public void endGroup() {
	}
	//=============================================================================================

	//=============================================================================================
	public void startObject(String name) {
	}
	//=============================================================================================

	//=============================================================================================
	public void endObject() {
	}
	//=============================================================================================

	//=============================================================================================
	public void writeVertex(float vx, float vy, float vz, float vw) {
		positions.add(newPosition(vx, vy, vz));
	}
	//=============================================================================================

	//=============================================================================================
	public void writeNormal(float nx, float ny, float nz) {
		normals.add(newNormal(nx, ny, nz));
	}
	//=============================================================================================

	//=============================================================================================
	public void writeTexture(float tu, float tv, float tw) {
		textures.add(newTexture(tu, tv));
	}
	//=============================================================================================

	//=============================================================================================
	public void writeParameter(float pu, float pv, float pw) {
		throw new X("Not Supported");
	}
	//=============================================================================================

	//=============================================================================================
	public void startFace() {
		type = TYPE_TRIANGLES;
		checkSegment();
		base  = triplets.size(); 
		count = 0;
	}
	//=============================================================================================

	//=============================================================================================
	public void endFace() {
		switch (count) {
			case 3 -> {
				segment.indices.add(newIndices(base+0, base+1, base+2));
			}
			case 4 -> {
				segment.indices.add(newIndices(base+0, base+1, base+2));
				segment.indices.add(newIndices(base+0, base+2, base+3));
			}
			default -> throw new X("Unsupported Face count: %s", count);
		}
	}
	//=============================================================================================

	//=============================================================================================
	public void writeFaceIndex(int iv, int it, int in) {
		count++;
		triplets.add(newTriplet(iv, it, in));
	}
	//=============================================================================================

	//=============================================================================================
	public void startLine() {
		type = TYPE_LINES;
		checkSegment();
		base  = triplets.size(); 
		count = 0;
	}
	//=============================================================================================

	//=============================================================================================
	public void endLine() {
		for (int i=0; i<count-1; i++) {
			segment.indices.add(newIndices(base+i+0, base+i+1));
		}
	}
	//=============================================================================================

	//=============================================================================================
	public void writeLineIndex(int idx) {
		count++;
		triplets.add(newTriplet(idx, 0, 0));
	}
	//=============================================================================================

	//=============================================================================================
	public void materialLib(String path, Map<String, Material> materials) {
		this.materials.putAll(materials);
	}
	//=============================================================================================

	//=============================================================================================
	public void materialUse(String name) {
		material = name;
	}
	//=============================================================================================

	//=============================================================================================
	public Mesh build() {
		Mesh mesh = new Mesh(triplets.size());
		for (Triplet triplet : triplets) {
			Vector3f pos = positions.get(triplet.position);
			Vector2f tex = textures.get(triplet.texture);
			Vector3f nrm = normals.get(triplet.normal);
			mesh.vertex(pos, tex, nrm);
		}
		for (Segment segment : segments) {
			Submesh submesh = new Submesh();
			mesh.submeshes.add(submesh);
			submesh.type = switch (segment.type) {
				case TYPE_LINES-> ElementType.LINES;
				case TYPE_TRIANGLES -> ElementType.TRIANGLES;
				default -> throw new X("Unsupported type.");
			};
			int idxcount = 0;
			for (int[] buffer : segment.indices) {
				idxcount += buffer.length;
			}
			submesh.indices = IntBuffer.allocate(idxcount);
			submesh.indices.rewind();
			for (int[] buffer : segment.indices) {
				submesh.indices.put(buffer);
			}
			submesh.material = materials.get(segment.material);
		}
		reset();
		return mesh;
	}
	//=============================================================================================
	
	//=============================================================================================
	private void checkSegment() {
		if (
			(segment == null) ||
			(segment.type != type) ||
			((material == null) && (segment.material != null)) ||
			((material != null) && !material.equals(segment.material))
		) {
			segment = new Segment(TYPE_TRIANGLES, material);
			segments.add(segment);
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	private void reset() {
		
		// clear all buffers;
		positions.clear();
		textures.clear();
		normals.clear();
		triplets.clear();
		segments.clear();

		// provide fake elements for index zero
		positions.add(newPosition(0f, 0f, 0f));
		textures.add(newTexture(0f, 0f));
		normals.add(newNormal(0f, 0f, 0f));

	}
	//=============================================================================================
	
}
//*************************************************************************************************
