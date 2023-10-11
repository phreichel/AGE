//*************************************************************************************************
package age.model;
//*************************************************************************************************

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import age.mesh.obj.ObjectParser;
import age.rig.bvh.BVHParser;
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
	private final BVHRigBuilder2 animationBuilder = new BVHRigBuilder2();
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
