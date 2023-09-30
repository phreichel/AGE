//*************************************************************************************************
package age.mesh;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.vecmath.Color3f;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import age.util.X;

//*************************************************************************************************
public class Builder {

	//=============================================================================================
	private final Set<MeshLayer> layers = EnumSet.noneOf(MeshLayer.class);
	//=============================================================================================

	//=============================================================================================
	private final List<Color3f> colors = new ArrayList<>();
	private final List<Vector2f> textures = new ArrayList<>();
	private final List<Vector3f> normals = new ArrayList<>();
	private final List<Vector3f> vertices = new ArrayList<>();
	//=============================================================================================

	//=============================================================================================
	private final List<ElementType> types = new ArrayList<>();
	private final List<int[]> indices = new ArrayList<>();
	//=============================================================================================

	//=============================================================================================
	private final Map<String, Material> materials = new HashMap<>();
	private final List<Integer> materialIndices = new ArrayList<>();
	private final List<String> materialNames = new ArrayList<>();
	//=============================================================================================
	
	//=============================================================================================
	Builder begin(MeshLayer ... layers) {
		clear();
		this.layers.addAll(List.of(layers));
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder texture(Vector2f t) {
		textures.add(t);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder texture(float x, float y) {
		textures.add(new Vector2f(x, y));
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	public Builder color(Color3f c) {
		colors.add(c);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder color(float r, float g, float b) {
		colors.add(new Color3f(r, g, b));
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder normal(Vector3f n) {
		normals.add(n);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder normal(float nx, float ny, float nz) {
		normals.add(new Vector3f(nx, ny, nz));
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder vertex(Vector3f n) {
		vertices.add(n);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder vertex(float x, float y, float z) {
		vertices.add(new Vector3f(x, y, z));
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder next() {
		int idx = vertices.size();
		if (layers.contains(MeshLayer.TEXTURES) && idx != textures.size()) throw new X("out of sync");
		if (layers.contains(MeshLayer.COLORS)   && idx != colors.size()) throw new X("out of sync"); 
		if (layers.contains(MeshLayer.NORMALS)  && idx != normals.size()) throw new X("out of sync"); 
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder element(ElementType type, int ...indices) {
		this.types.add(type);
		this.indices.add(indices);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder material(String name, Material material) {
		materials.put(name, material);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder material(Map<String, Material> materialmap) {
		materials.putAll(materialmap);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder materialUse(int idx, String name) {
		materialIndices.add(idx);
		materialNames.add(name);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder materialUse(List<Integer> indices, List<String> names) {
		materialIndices.addAll(indices);
		materialNames.addAll(names);
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	public Mesh build() {
		Mesh mesh = new Mesh();
		int size = vertices.size();
		mesh.layers.clear();
		mesh.layers.addAll(layers);
		mesh.colors = new float[size * 3];
		mesh.textures = new float[size * 2];
		mesh.normals = new float[size * 3];
		mesh.vertices = new float[size * 3];
		mesh.materials.putAll(materials);
		for (int i=0; i<materialIndices.size(); i++) {
			mesh.materialMap.put(materialIndices.get(i), materialNames.get(i));
		}
		for (int i=0; i<size; i++) {
			if (layers.contains(MeshLayer.TEXTURES)) copyTextures(textures, mesh.textures, i);
			if (layers.contains(MeshLayer.COLORS)) copyColors(colors, mesh.colors, i);
			if (layers.contains(MeshLayer.NORMALS)) copy(normals, mesh.normals, i);
			copy(vertices, mesh.vertices, i);
		}
		mesh.types = types.toArray(new ElementType[] {});
		mesh.indices = indices.toArray(new int[][] {});
		clear();
		return mesh;
	}
	//=============================================================================================

	//=============================================================================================
	private void copyTextures(List<Vector2f> src, float[] dst, int idx) {
		var v = src.get(idx);
		dst[idx*2+0] = v.x;
		dst[idx*2+1] = v.y;
	}
	//=============================================================================================

	//=============================================================================================
	private void copyColors(List<Color3f> src, float[] dst, int idx) {
		var v = src.get(idx);
		dst[idx*3+0] = v.x;
		dst[idx*3+1] = v.y;
		dst[idx*3+2] = v.z;
	}
	//=============================================================================================
	
	//=============================================================================================
	private void copy(List<Vector3f> src, float[] dst, int idx) {
		var v = src.get(idx);
		dst[idx*3+0] = v.x;
		dst[idx*3+1] = v.y;
		dst[idx*3+2] = v.z;
	}
	//=============================================================================================
	
	//=============================================================================================
	private void clear() {
		layers.clear();
		colors.clear();
		textures.clear();
		normals.clear();
		vertices.clear();
		types.clear();
		indices.clear();		
	}
	//=============================================================================================
	
}
//*************************************************************************************************
