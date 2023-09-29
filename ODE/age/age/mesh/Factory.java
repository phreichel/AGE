//*************************************************************************************************
package age.mesh;
//*************************************************************************************************

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import age.mesh.obj.MeshObjectBuilder;
import age.mesh.obj.ObjectParser;
import age.util.X;

//*************************************************************************************************
public class Factory {

	//=============================================================================================
	private final MeshObjectBuilder meshObjectBuilder;
	private final ObjectParser objectParser;
	//=============================================================================================

	//=============================================================================================
	public Factory() {
		meshObjectBuilder = new MeshObjectBuilder();
		objectParser = new ObjectParser();
		objectParser.assign(meshObjectBuilder);
	}
	//=============================================================================================
	
	//=============================================================================================
	public Mesh model(String path) {
		try {
			File file = new File(path);
			Reader reader = new FileReader(file);
			objectParser.init(reader);
			objectParser.parse();
			Mesh mesh = meshObjectBuilder.build();
			return mesh;
		} catch (Exception e) {
			throw new X(e);
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	public Mesh siglet(int edges, int rows) {
		Builder b = Mesh.builder();
		double d = (Math.PI * 2) / edges;
		for (int i=0; i<edges; i++) {
			double a = d * i;
			double s = Math.sin(a);
			double c = Math.cos(a);
			for (int j=0; j<rows; j++) {
				float x = (float) s * (1+j);
				float z = (float) c * (1+j);
				b.vertex(x, 0f, z);
				b.next();
			}
		}
		for (int i=0; i<edges; i++) {
			int idx[] = new int[rows];
			for (int j=0; j<rows; j++) {
				idx[j] = i * rows + j;
			}
			b.element(ElementType.LINE_STRIP, idx);
		}
		for (int j=0; j<rows; j++) {
			int idx[] = new int[edges];
			for (int i=0; i<edges; i++) {
				idx[i] = i * rows + j;
			}
			b.element(ElementType.LINE_LOOP, idx);
		}
		return b.build();
	}
	//=============================================================================================

}
//*************************************************************************************************
