//*************************************************************************************************
package age.mesh;
//*************************************************************************************************

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.nio.IntBuffer;
import java.util.Map;
import age.mesh.mtl.MaterialMapBuilder;
import age.mesh.mtl.MaterialParser;
import age.mesh.obj.MeshBuilder;
import age.mesh.obj.ObjectParser;
import age.util.X;

//*************************************************************************************************
public class Factory {

	//=============================================================================================
	private final MaterialMapBuilder materialBuilder;
	private final MaterialParser  materialParser;
	//=============================================================================================

	//=============================================================================================
	private final MeshBuilder objectBuilder;
	private final ObjectParser objectParser;
	//=============================================================================================
	
	//=============================================================================================
	public Factory() {
		materialBuilder = new MaterialMapBuilder();
		materialParser  = new MaterialParser(materialBuilder);
		objectBuilder   = new MeshBuilder();
		objectParser    = new ObjectParser(objectBuilder);
	}
	//=============================================================================================
	
	//=============================================================================================
	public Mesh model(String path) {
		try {
			File file = new File(path);
			File base = file.getAbsoluteFile().getParentFile();
			Reader reader = new FileReader(file);
			objectParser.init(base, reader);
			objectParser.parse();
			Mesh mesh = objectBuilder.build();
			return mesh;
		} catch (Exception e) {
			throw new X(e);
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	public void material(String path, Map<String, Material> materials) {
		try {
			File file = new File(path);
			Reader reader = new FileReader(file);
			materialParser.init(reader);
			materialParser.parse();
			materialBuilder.build(materials);
		} catch (Exception e) {
			throw new X(e);
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	public Mesh siglet(int edges, int rows) {
		
		Material mat = new Material();
		mat.flags.clear();
		mat.refraction = 1f;
		mat.ambience.set(0f, 1f, 0f);
		mat.diffuse.set(0f, 1f, 0f);
		mat.emission.set(0f, 0f, 0f);
		mat.specular.set(0f, 0f, 0f);
		mat.shininess = 0f;
		mat.illumination = 2;
		mat.dissolve = 1f;

		int vsize = edges * rows;
		int isize = 2 * edges * rows + 2 * edges * (rows-1);
		
		Mesh mesh = new Mesh(vsize);
		Submesh submesh = new Submesh();
		submesh.type = ElementType.LINES;
		submesh.material = mat;
		submesh.indices = IntBuffer.allocate(isize);
		mesh.submeshes.add(submesh);
		
		double d = (Math.PI * 2) / edges;
		for (int i=0; i<edges; i++) {
			double a = d * i;
			double s = Math.sin(a);
			double c = Math.cos(a);
			for (int j=0; j<rows; j++) {
				float x = (float) s * (1+j);
				float z = (float) c * (1+j);
				mesh.positions.put(x);
				mesh.positions.put(0f);
				mesh.positions.put(z);
				mesh.textures.put(0f);
				mesh.textures.put(0f);
				mesh.normals.put(0f);
				mesh.normals.put(1f);
				mesh.normals.put(0f);
			}
		}

		for (int i=0; i<edges; i++) {
			for (int j=0; j<rows; j++) {
				submesh.indices.put((i*rows+j) % vsize);
				submesh.indices.put(((i+1)*rows+j) % vsize);
			}
		}
		
		for (int j=0; j<rows-1; j++) {
			for (int i=0; i<edges; i++) {
				submesh.indices.put((i*rows+j) % vsize);
				submesh.indices.put((i*rows+j+1) % vsize);
			}
		}
		
		return mesh;
	}
	//=============================================================================================
	
}
//*************************************************************************************************
