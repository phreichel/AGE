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
	private final List<Float> lstpos = new ArrayList<>();
	private final List<Float> lsttex = new ArrayList<>();
	private final List<Float> lstnorm = new ArrayList<>();
	//=============================================================================================

	//=============================================================================================
	private final List<Integer> idxpos = new ArrayList<>();
	private final List<Integer> idxtex = new ArrayList<>();
	private final List<Integer> idxnorm = new ArrayList<>();
	//=============================================================================================

	//=============================================================================================
	private final List<Integer> mark = new ArrayList<>();
	private final List<String> material = new ArrayList<>();
	private final List<ElementType> type = new ArrayList<>();
	//=============================================================================================

	//=============================================================================================
	private final Map<String, Material> materialmap = new HashMap<>();
	//=============================================================================================

	//=============================================================================================
	private ElementType current_type     = null;
	private String      current_material = "";
	//=============================================================================================
	
	//=============================================================================================
	public void startFile() {}
	public void endFile() {}
	//=============================================================================================

	//=============================================================================================
	public void startGroup(String name) {}
	public void endGroup() {}
	//=============================================================================================

	//=============================================================================================
	public void startObject(String name) {}
	public void endObject() {}
	//=============================================================================================

	//=============================================================================================
	public void writeVertex(float vx, float vy, float vz, float vw) {
		lstpos.add(vx);
		lstpos.add(vy);
		lstpos.add(vz);
	}
	//=============================================================================================

	//=============================================================================================
	public void writeNormal(float nx, float ny, float nz) {
		lstnorm.add(nx);
		lstnorm.add(ny);
		lstnorm.add(nz);
	}
	//=============================================================================================

	//=============================================================================================
	public void writeTexture(float tu, float tv, float tw) {
		lsttex.add(tu);
		lsttex.add(tv);
	}
	//=============================================================================================

	//=============================================================================================
	public void writeParameter(float pu, float pv, float pw) {
		throw new X("Not supported");
	}
	//=============================================================================================

	//=============================================================================================
	Boolean face = null;
	//=============================================================================================
	
	//=============================================================================================
	public void startFace() {
		if ((face == null) || !face) {
			face = true;
			current_type = ElementType.TRIANGLES;
			mark.add(idxpos.size());
			type.add(current_type);
			material.add(current_material);
		}
	}
	public void endFace() {}
	//=============================================================================================

	//=============================================================================================
	public void writeFaceIndex(int iv, int it, int in) {
		idxpos.add(iv);
		idxtex.add(it);
		idxnorm.add(in);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void startLine() {
		if ((face == null) || face) {
			face = false;
			current_type = ElementType.LINES;
			mark.add(idxpos.size());
			type.add(current_type);
			material.add(current_material);
		}
	}
	public void endLine() {}
	//=============================================================================================

	//=============================================================================================
	public void writeLineIndex(int idx) {
		idxpos.add(idx);
		idxtex.add(0);
		idxnorm.add(0);
	}
	//=============================================================================================

	//=============================================================================================
	public void materialLib(String path, Map<String, Material> materials) {
		materialmap.putAll(materials);
	}
	//=============================================================================================

	//=============================================================================================
	public void materialUse(String name) {
		current_material = name;
		if (face != null) {
			mark.add(idxpos.size());
			type.add(current_type);
			material.add(current_material);
		}
	}	
	//=============================================================================================

	//=============================================================================================
	public Model build() {
		float[] pos = new float[lstpos.size()];
		float[] tex = new float[lsttex.size()];
		float[] norm = new float[lstnorm.size()];
		for (int i=0; i<lstpos.size(); i++) pos[i] = lstpos.get(i);
		for (int i=0; i<lsttex.size(); i++) tex[i] = lsttex.get(i);
		for (int i=0; i<lstnorm.size(); i++) norm[i] = lstnorm.get(i);
		Mesh mesh = new Mesh(pos, tex, norm);
		Element[] elements = new Element[mark.size()];
		Material[] materials = new Material[mark.size()];
		for (int i=0; i<mark.size(); i++) {
			ElementType t = type.get(i);
			int m1 = mark.get(i);
			int m2 = (i < mark.size()-1) ? mark.get(i+1)-1 : mesh.size-1;
			int[] range = new int[m2-m1+1];
			for (int j=0; j<m2-m1+1; j++) range[j] = m1+j;
			elements[i] = new Element(t, range);
			String matname = material.get(i);
			materials[i] = materialmap.get(matname);
		}
		Skin skin = new Skin(mesh, elements);
		Model model = new Model(skin, materials);
		clear();
		return model;
	}
	//=============================================================================================

	//=============================================================================================
	private void clear() {
		lstpos.clear();
		idxpos.clear();
		lsttex.clear();
		idxtex.clear();
		lstnorm.clear();
		idxnorm.clear();
		mark.clear();
		type.clear();
		material.clear();
		materialmap.clear();
	}
	//=============================================================================================
	
}
//*************************************************************************************************
