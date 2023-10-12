//*************************************************************************************************
package age.model;
//*************************************************************************************************

import java.io.File;
import java.io.FileReader;
import java.io.Reader;

import age.model.bvh.BVHParser;
import age.model.bvh.BVHRigBuilder;
import age.model.obj.OBJModelBuilder;
import age.model.obj.ObjectParser;
import age.util.X;

//*************************************************************************************************
public class Factory {

	//=============================================================================================
	public static final Factory instance = new Factory();
	//=============================================================================================
	
	//=============================================================================================
	private final OBJModelBuilder modelBuilder = new OBJModelBuilder();
	private final ObjectParser    modelParser  = new ObjectParser(modelBuilder);
	//=============================================================================================

	//=============================================================================================
	public Model model(String path) {
		try {
			File file = new File(path);
			Reader reader = new FileReader(file);
			modelParser.init(file.getParentFile(), reader);
			modelParser.parse();
			reader.close();
			return modelBuilder.build();
		} catch (Exception e) {
			throw new X(e);
		}
	}
	//=============================================================================================

	//=============================================================================================
	public age.model.Model siglet(int edges, int rows) {
		
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

		float[] positions = new float[vsize*3];
		float[] textures  = new float[vsize*2];
		float[] normals   = new float[vsize*3];
		double d = (Math.PI * 2) / edges;
		for (int i=0; i<edges; i++) {
			double a = d * i;
			double s = Math.sin(a);
			double c = Math.cos(a);
			for (int j=0; j<rows; j++) {
				float x = (float) s * (1+j);
				float z = (float) c * (1+j);
				positions[i*3+0] = x;
				positions[i*3+1] = 0f;
				positions[i*3+2] = z;
				textures[i*2+0] = 0f;
				textures[i*2+1] = 0f;
				normals[i*3+0] = 0f;
				normals[i*3+1] = 1f;
				normals[i*3+2] = 0f;
			}
		}
		
		int isize = 2 * edges * rows + 2 * edges * (rows-1);
		int[] indices = new int[isize];
		int current = 0;
		for (int i=0; i<edges; i++) {
			for (int j=0; j<rows; j++) {
				indices[current++] = (i*rows+j) % vsize;
				indices[current++] = ((i+1)*rows+j) % vsize;
			}
		}
		for (int j=0; j<rows-1; j++) {
			for (int i=0; i<edges; i++) {
				indices[current++] = (i*rows+j) % vsize;
				indices[current++] = (i*rows+j+1) % vsize;
			}
		}

		age.model.Mesh mesh = new age.model.Mesh(positions, textures, normals);
		Element element = new Element(age.model.ElementType.LINES, indices);
		Element[] elements = new Element[] { element };
		Material[] materials = new Material[] { mat };
		age.model.Skin skin = new Skin(mesh, elements);
		age.model.Model model = new Model(skin, materials); 

		return model;
		
	}
	//=============================================================================================
	
	//=============================================================================================
	private final BVHRigBuilder animationBuilder = new BVHRigBuilder();
	private final BVHParser animationParser = new BVHParser(animationBuilder);
	//=============================================================================================

	//=============================================================================================
	public Animation animation(String path) {
		try {
			File file = new File(path);
			Reader reader = new FileReader(file);
			animationParser.init(reader);
			animationParser.parse();
			reader.close();
			return animationBuilder.build();
		} catch (Exception e) {
			throw new X(e);
		}
	}
	//=============================================================================================
	
}
//*************************************************************************************************
