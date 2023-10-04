//*************************************************************************************************
package age.model;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import age.mesh.Material;
import age.mesh.obj.ObjectBuilder;
import age.util.X;

//*************************************************************************************************
public class OBJModelBuilder implements ObjectBuilder{

	//=============================================================================================
	private Mesh mesh;
	private Skin skin;
	private Model model;
	//=============================================================================================

	//=============================================================================================
	private final List<Float> positions = new ArrayList<>();
	private final List<Float> textures = new ArrayList<>();
	private final List<Float> normals = new ArrayList<>();
	private final List<Integer> indices = new ArrayList<>();
	//=============================================================================================

	//=============================================================================================
	private int mark;
	private int count;
	//=============================================================================================

	//=============================================================================================
	private String currentGroup = "off";
	private String currentMaterial = "off";
	//=============================================================================================

	//=============================================================================================
	private Map<String, String>        elementNames     = new HashMap<>();
	private Map<String, ElementType>   elementTypes     = new HashMap<>();
	private Map<String, String>        elementMaterials = new HashMap<>();
	private Map<String, List<Integer>> elementIndices   = new HashMap<>();
	//=============================================================================================

	//=============================================================================================
	private String key(String name, ElementType type, String material) {
		return name + "_" + type.toString() + "_" + material;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void startFile() {
		currentGroup = "off";
		currentMaterial = "off";
	}
	//=============================================================================================

	//=============================================================================================
	public void endFile() {
	}
	//=============================================================================================

	//=============================================================================================
	public void startGroup(String name) {
		currentGroup = name;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void endGroup() {
		currentGroup = "off";
	}
	//=============================================================================================

	//=============================================================================================
	public void startObject(String name) {
		currentGroup = name;
	}
	//=============================================================================================

	//=============================================================================================
	public void endObject() {
		currentGroup = "off";
	}
	//=============================================================================================

	//=============================================================================================
	public void writeVertex(float vx, float vy, float vz, float vw) {
		positions.add(vx);
		positions.add(vy);
		positions.add(vz);
	}
	//=============================================================================================

	//=============================================================================================
	public void writeNormal(float nx, float ny, float nz) {
		normals.add(nx);
		normals.add(ny);
		normals.add(nz);
	}
	//=============================================================================================

	//=============================================================================================
	public void writeTexture(float tu, float tv, float tw) {
		textures.add(tu);
		textures.add(tv);
	}
	//=============================================================================================

	//=============================================================================================
	public void writeParameter(float pu, float pv, float pw) {
		throw new X("Not supported");
	}
	//=============================================================================================

	//=============================================================================================
	public void startFace() {
		mark = indices.size() / 3;
		count = 0;
	}
	//=============================================================================================

	//=============================================================================================
	public void endFace() {
		
		String key = key(currentGroup, ElementType.TRIANGLES, currentMaterial);
		
		elementNames.put(key, currentGroup);
		elementTypes.put(key, ElementType.TRIANGLES);
		elementMaterials.put(key, currentMaterial);

		if ((count < 3) || (count > 4)) {
			throw new X("Unsipported Face Index Count: $s", count);
		}
		
		List<Integer> idxList = elementIndices.get(key);
		if (idxList == null) {
			idxList = new ArrayList<Integer>();
			elementIndices.put(key, idxList);
		}
		
		idxList.add(mark+0);
		idxList.add(mark+1);
		idxList.add(mark+2);
		
		if (count == 4) {
			idxList.add(mark+0);
			idxList.add(mark+2);
			idxList.add(mark+3);
		}
		
	}
	//=============================================================================================

	//=============================================================================================
	public void writeFaceIndex(int iv, int it, int in) {
		indices.add(iv);
		indices.add(it);
		indices.add(in);
		count++;
	}
	//=============================================================================================

	//=============================================================================================
	public void startLine() {
		mark = indices.size() / 3;
		count = 0;
	}
	//=============================================================================================

	//=============================================================================================
	public void endLine() {
		
		String key = key(currentGroup, ElementType.LINES, currentMaterial);
		
		elementNames.put(key, currentGroup);
		elementTypes.put(key, ElementType.LINES);
		elementMaterials.put(key, currentMaterial);

		List<Integer> idxList = elementIndices.get(key);
		if (idxList == null) {
			idxList = new ArrayList<Integer>();
			elementIndices.put(key, idxList);
		}
		
		for (int i=0; i<count-1; i++) {
			idxList.add(mark+i+0);
			idxList.add(mark+i+1);
		}

	}
	//=============================================================================================

	//=============================================================================================
	public void writeLineIndex(int idx) {
		indices.add(idx);
		indices.add(0);
		indices.add(0);
		count++;
	}
	//=============================================================================================

	//=============================================================================================
	public void materialLib(String path, Map<String, Material> materials) {
	}
	//=============================================================================================

	//=============================================================================================
	public void materialUse(String name) {
		currentMaterial = name;
	}
	//=============================================================================================

	//=============================================================================================
	public Mesh build() {
		
		mesh = new Mesh();
		skin = new Skin();
		model = new Model();
		skin.mesh(mesh);
		model.skin(skin);
		
		mesh.fill(indices, positions, textures, normals);
		skin.fill(elementNames, elementTypes, elementMaterials, elementIndices);
		clear();
		return mesh;
		
	}
	//=============================================================================================

	//=============================================================================================
	private void clear() {
		positions.clear();
		textures.clear();
		normals.clear();
		indices.clear();
		elementNames.clear();
		elementTypes.clear();
		elementMaterials.clear();
		elementIndices.clear();
	}
	//=============================================================================================
	
}
//*************************************************************************************************
