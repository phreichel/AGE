//*************************************************************************************************
package age.mesh.obj;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import age.mesh.Builder;
import age.mesh.ElementType;
import age.mesh.Material;
import age.mesh.Mesh;
import age.mesh.MeshLayer;
import age.util.X;

//*************************************************************************************************
public class MeshBuilder implements ObjectBuilder {

	//=============================================================================================
	private int   index = -1;
	private int[] indices = null;
	//=============================================================================================

	//=============================================================================================
	private final List<Vector3f> vs = new ArrayList<>();
	private final List<Vector2f> ts = new ArrayList<>();
	private final List<Vector3f> ns = new ArrayList<>();
	//=============================================================================================

	//=============================================================================================
	private final Map<String, Material> mm = new HashMap<>();
	private final List<Integer> mx = new ArrayList<>();
	private final List<String> mn = new ArrayList<>();
	//=============================================================================================
	
	//=============================================================================================
	private final List<ElementType> ft = new ArrayList<>();
	private final List<int[]> fx = new ArrayList<>();
	//=============================================================================================

	//=============================================================================================
	private Set<MeshLayer> layers = EnumSet.noneOf(MeshLayer.class);
	//=============================================================================================

	//=============================================================================================
	private final List<Integer> listIndices = new ArrayList<>(); 
	//=============================================================================================
	
	//=============================================================================================
	public Mesh build() {
		Builder builder = Mesh.builder(layers.toArray(new MeshLayer[] {}));
		builder.material(mm);
		builder.materialUse(mx, mn);
		int offset = 0;
		for (int i=0; i<ft.size(); i++) {
			ElementType t = ft.get(i);
			int[] indices = fx.get(i);
			switch (t) {
				case TRIANGLES -> {
					builder.vertex(vs.get(indices[0]));
					builder.vertex(vs.get(indices[3]));
					builder.vertex(vs.get(indices[6]));
					if (layers.contains(MeshLayer.TEXTURES)) {
						builder.texture(ts.get(indices[1]));
						builder.texture(ts.get(indices[4]));
						builder.texture(ts.get(indices[7]));
					}
					if (layers.contains(MeshLayer.NORMALS)) {
						builder.normal(ns.get(indices[2]));
						builder.normal(ns.get(indices[5]));
						builder.normal(ns.get(indices[8]));
					}
					builder.next();
					builder.element(t, offset++, offset++, offset++);
				}
				case QUADS -> {
					builder.vertex(vs.get(indices[0]));
					builder.vertex(vs.get(indices[3]));
					builder.vertex(vs.get(indices[6]));
					builder.vertex(vs.get(indices[9]));
					if (layers.contains(MeshLayer.TEXTURES)) {
						builder.texture(ts.get(indices[1]));
						builder.texture(ts.get(indices[4]));
						builder.texture(ts.get(indices[7]));
						builder.texture(ts.get(indices[10]));
					}
					if (layers.contains(MeshLayer.NORMALS)) {
						builder.normal(ns.get(indices[2]));
						builder.normal(ns.get(indices[5]));
						builder.normal(ns.get(indices[8]));
						builder.normal(ns.get(indices[11]));
					}
					builder.next();
					builder.element(t, offset++, offset++, offset++, offset++);
				}
				default -> throw new X("Unsupported Element Type: %s", t);
			};
		}
		Mesh mesh = builder.build();
		clear();
		return mesh;
	}
	//=============================================================================================

	//=============================================================================================
	private void clear() {
		vs.clear();
		ns.clear();
		ts.clear();
		ft.clear();
		fx.clear();
		mm.clear();
		mx.clear();
		mn.clear();
		layers.clear();
		index = -1;
		indices = null;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void startFile() {
		clear();
	}
	//=============================================================================================

	//=============================================================================================
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
		vs.add(new Vector3f(vx, vy, vz));
	}
	//=============================================================================================

	//=============================================================================================
	public void writeNormal(float nx, float ny, float nz) {
		layers.add(MeshLayer.NORMALS);
		ns.add(new Vector3f(nx, ny, nz));
	}
	//=============================================================================================

	//=============================================================================================
	public void writeTexture(float tu, float tv, float tw) {
		layers.add(MeshLayer.TEXTURES);
		ts.add(new Vector2f(tu, tv));
	}
	//=============================================================================================

	//=============================================================================================
	public void writeParameter(float pu, float pv, float pw) {}
	//=============================================================================================

	//=============================================================================================
	public void startFace() {
		index = 0;
		indices = new int[12];
	}
	//=============================================================================================

	//=============================================================================================
	public void endFace() {
		ElementType type = switch (index) {
			case 9 -> ElementType.TRIANGLES;
			case 12 -> ElementType.QUADS;
			default -> throw new X("Unsupported Index Length: %s", index);
		};
		ft.add(type);
		fx.add(indices);
	}
	//=============================================================================================

	//=============================================================================================
	public void writeFaceIndex(int iv, int it, int in) {
		iv = (iv == 0) ? 0 : (iv > 0) ? iv-1 : vs.size() + iv; 
		it = (it == 0) ? 0 : (it > 0) ? it-1 : ts.size() + it; 
		in = (in == 0) ? 0 : (in > 0) ? in-1 : ns.size() + in; 
		indices[index++] = iv;
		indices[index++] = it;
		indices[index++] = in;
	}
	//=============================================================================================

	//=============================================================================================
	public void startLine() {
		listIndices.clear();
	}
	//=============================================================================================

	//=============================================================================================
	public void endLine() {
		index = 0;
		indices = new int[listIndices.size()];
		for (int idx : listIndices) {
			indices[index++] = idx;
		}
		ft.add(ElementType.LINE_STRIP);
		fx.add(indices);
		index = -1;
		indices = null;
		listIndices.clear();
	}
	//=============================================================================================

	//=============================================================================================
	public void writeLineIndex(int idx) {
		listIndices.add(idx);
	}
	//=============================================================================================

	//=============================================================================================
	public void materialLib(String path) {
		Mesh.factory().material(path, mm);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void materialUse(String material) {
		mx.add(ft.size());
		mn.add(material);
	}
	//=============================================================================================
	
}
//*************************************************************************************************
