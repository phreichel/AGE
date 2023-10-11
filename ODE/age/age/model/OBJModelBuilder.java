//*************************************************************************************************
package age.model;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import age.mesh.Material;
import age.mesh.obj.ObjectBuilder;
import age.util.X;

//*************************************************************************************************
public class OBJModelBuilder implements ObjectBuilder{

	//=============================================================================================
	private final List<Vector3f> lstpos = new ArrayList<>();
	private final List<Vector2f> lsttex = new ArrayList<>();
	private final List<Vector3f> lstnorm = new ArrayList<>();
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
		lstpos.add(new Vector3f(vx, vy, vz));
	}
	//=============================================================================================

	//=============================================================================================
	public void writeNormal(float nx, float ny, float nz) {
		lstnorm.add(new Vector3f(nx, ny, nz));
	}
	//=============================================================================================

	//=============================================================================================
	public void writeTexture(float tu, float tv, float tw) {
		lsttex.add(new Vector2f(tu, tv));
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
	private final List<Integer> indices = new ArrayList<>();
	//=============================================================================================
	public void startFace() {
		if ((face == null) || !face) {
			face = true;
			current_type = ElementType.TRIANGLES;
			mark.add(idxpos.size());
			type.add(current_type);
			material.add(current_material);
		}
		indices.clear();
	}
	public void endFace() {
		switch (indices.size()) {
			case 9  -> {
				idxpos.add( Math.max(0, indices.get(0)-1));
				idxtex.add( Math.max(0, indices.get(1)-1));
				idxnorm.add(Math.max(0, indices.get(2)-1));
				idxpos.add( Math.max(0, indices.get(3)-1));
				idxtex.add( Math.max(0, indices.get(4)-1));
				idxnorm.add(Math.max(0, indices.get(5)-1));
				idxpos.add( Math.max(0, indices.get(6)-1));
				idxtex.add( Math.max(0, indices.get(7)-1));
				idxnorm.add(Math.max(0, indices.get(8)-1));
			}
			case 12 -> {
				idxpos.add( Math.max(0, indices.get(0)-1));
				idxtex.add( Math.max(0, indices.get(1)-1));
				idxnorm.add(Math.max(0, indices.get(2)-1));
				idxpos.add( Math.max(0, indices.get(3)-1));
				idxtex.add( Math.max(0, indices.get(4)-1));
				idxnorm.add(Math.max(0, indices.get(5)-1));
				idxpos.add( Math.max(0, indices.get(6)-1));
				idxtex.add( Math.max(0, indices.get(7)-1));
				idxnorm.add(Math.max(0, indices.get(8)-1));
				idxpos.add( Math.max(0, indices.get(0)-1));
				idxtex.add( Math.max(0, indices.get(1)-1));
				idxnorm.add(Math.max(0, indices.get(2)-1));
				idxpos.add( Math.max(0, indices.get(6)-1));
				idxtex.add( Math.max(0, indices.get(7)-1));
				idxnorm.add(Math.max(0, indices.get(8)-1));
				idxpos.add( Math.max(0, indices.get(9)-1));
				idxtex.add( Math.max(0, indices.get(10)-1));
				idxnorm.add(Math.max(0, indices.get(11)-1));
			}
			default -> {
				throw new X("invalid index count");
			}
		}
	}
	//=============================================================================================

	//=============================================================================================
	public void writeFaceIndex(int iv, int it, int in) {
		indices.add(iv);
		indices.add(it);
		indices.add(in);
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
		float[] pos  = new float[idxpos.size()*3];
		float[] tex  = new float[idxpos.size()*2];
		float[] norm = new float[idxpos.size()*3];
		for (int i=0; i<idxpos.size(); i++) {
			Vector3f p = lstpos.get(idxpos.get(i));
			pos[i*3+0] = p.x;
			pos[i*3+1] = p.y;
			pos[i*3+2] = p.z;
			Vector2f t = lsttex.get(idxtex.get(i));
			tex[i*2+0] = t.x;
			tex[i*2+1] = t.y;
			Vector3f n = lstnorm.get(idxnorm.get(i));
			norm[i*3+0] = n.x;
			norm[i*3+1] = n.y;
			norm[i*3+2] = n.z;
		}
		Mesh mesh = new Mesh(pos, tex, norm);
		Element[] elements = new Element[mark.size()];
		Material[] materials = new Material[mark.size()];
		for (int i=0; i<mark.size(); i++) {
			ElementType t = type.get(i);
			int m1 = mark.get(i);
			int m2 = (i < mark.size()-1) ? mark.get(i+1)-1 : mesh.size-1;
			int[] range = new int[m2-m1+1];
			for (int j=0; j<m2-m1+1; j++) {
				range[j] = m1+j;
			}
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
