/*
 * Commit: 7407dc59dacb80fc02cd2de06a33b6b78cc37f8e
 * Date: 2023-10-12 13:32:56+02:00
 * Author: pre7618
 * Comment: Test
 *
 * Commit: d6d00fe1efe23635ab62afe64c0d23e79e28f470
 * Date: 2023-10-05 12:01:59+02:00
 * Author: pre7618
 * Comment: ...
 *
 * Commit: eb306b1e6a0957eb9ec0dc0accbc1747e9f08d0f
 * Date: 2023-10-04 01:48:00+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 */

//*************************************************************************************************
package age.model;
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
