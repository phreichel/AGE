/*
 * Commit: 7407dc59dacb80fc02cd2de06a33b6b78cc37f8e
 * Date: 2023-10-12 13:32:56+02:00
 * Author: pre7618
 * Comment: Test
 *
 * Commit: 543df522f819ea4a67e616d2be4c08edf6003716
 * Date: 2023-10-10 02:33:39+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 * Commit: 79a5191fc4ca527a12fa7cf477631efa44a9b707
 * Date: 2023-10-02 01:54:44+02:00
 * Author: Philip Reichel
 * Comment: Done some stuff with meshes.
 *
 * Commit: 989a153341f09c0168f323a44e0ffec01fbc07b7
 * Date: 2023-10-01 22:26:41+02:00
 * Author: Philip Reichel
 * Comment: VBO babay
 *
 * Commit: 79126a233e35010f5ee69ea22119499909797737
 * Date: 2023-09-27 16:31:28+02:00
 * Author: pre7618
 * Comment: Added Camview and Rig
 *
 * Commit: fedc4377dc9249932eb67a8eb9e9a46dbec18ff0
 * Date: 2023-09-25 18:34:56+02:00
 * Author: pre7618
 * Comment: ...
 *
 * Commit: 4e914ceff796e6e44fd96c76252c68c7325e64cd
 * Date: 2023-09-25 01:23:31+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 * Commit: 01f6edb422e32c691886b08481b74af734d2419d
 * Date: 2023-09-25 00:22:26+02:00
 * Author: Philip Reichel
 * Comment: Added Mesh
 *
 */

//*************************************************************************************************
package age.scene;
//*************************************************************************************************

import javax.vecmath.Matrix4f;
import age.util.X;

//*************************************************************************************************
public enum NItem {

	//=============================================================================================
	TRANSFORM(Matrix4f.class),
	TRANSFORM_ANIMATION(Matrix4f.class),
	CAMERA(Camera.class),
	RIG(age.model.Rig.class),
	MODEL(age.model.Model.class);
	//=============================================================================================

	//=============================================================================================
	private Class<?> cls;
	//=============================================================================================
	
	//=============================================================================================
	private NItem(Class<?> cls) {
		this.cls = cls;
	}
	//=============================================================================================

	//=============================================================================================
	public void check(Object object) {
		if (!cls.isInstance(object)) {
			throw new X(this.toString() + ": " + cls.getName() + " expected.");
		}
	}
	//=============================================================================================
	
}
//*************************************************************************************************
