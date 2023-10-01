//*************************************************************************************************
package age.mesh;
//*************************************************************************************************

import java.util.EnumSet;
import java.util.Set;
import javax.vecmath.Color3f;

//*************************************************************************************************
public final class Material {

	//=============================================================================================
	public final Set<MaterialFlag> flags = EnumSet.of(MaterialFlag.LIGHTING);
	//=============================================================================================
	
	//=============================================================================================
	public final Color3f ambience = new Color3f();
	public final Color3f diffuse = new Color3f();
	public final Color3f specular = new Color3f();
	public final Color3f emission = new Color3f();
	public float shininess = 0f;
	public float dissolve = 0f;
	public float refraction = 0f;
	public int illumination = -1;
	//=============================================================================================

	//=============================================================================================
	public String specular_map = null;
	public String ambient_map = null;
	public String diffuse_map = null;
	public String bump_map = null;
	//=============================================================================================
	
}
//*************************************************************************************************
