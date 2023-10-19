/*
 * Commit: 5ca6d434e838bc3e560853f9915b1356e492c2fd
 * Date: 2023-10-12 14:59:48+02:00
 * Author: pre7618
 * Comment: Fixed The Signet
 *
 * Commit: 7407dc59dacb80fc02cd2de06a33b6b78cc37f8e
 * Date: 2023-10-12 13:32:56+02:00
 * Author: pre7618
 * Comment: Test
 *
 * Commit: beb06c5be3d51bd9db17f304dc0b8a891b88218b
 * Date: 2023-10-11 17:16:39+02:00
 * Author: pre7618
 * Comment: Made Rigging possible?
 *
 * Commit: e0cca43ebaf9325829b5493e1850af81809f2a4a
 * Date: 2023-10-10 11:04:22+02:00
 * Author: pre7618
 * Comment: Clean Version to Bring to HEAD
 *
 * Commit: 202d17c6d32f4a166bf665865bb5ff76a95cfbd8
 * Date: 2023-10-10 10:22:06+02:00
 * Author: pre7618
 * Comment: ...
 *
 * Commit: 543df522f819ea4a67e616d2be4c08edf6003716
 * Date: 2023-10-10 02:33:39+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 */

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
		int cp = 0;
		int ct = 0;
		int cn = 0;
		for (int i=0; i<edges; i++) {
			double a = d * i;
			double s = Math.sin(a);
			double c = Math.cos(a);
			for (int j=0; j<rows; j++) {
				float x = (float) s * (1+j);
				float z = (float) c * (1+j);
				positions[cp++] = x;
				positions[cp++] = 0f;
				positions[cp++] = z;
				textures[ct++] = 0f;
				textures[ct++] = 0f;
				normals[cn++] = 0f;
				normals[cn++] = 1f;
				normals[cn++] = 0f;
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
