//*************************************************************************************************
package age.scene;
//*************************************************************************************************

import javax.vecmath.Matrix4f;

import age.util.X;

//*************************************************************************************************
public enum NodeFlag {

	//=============================================================================================
	TRANSFORM(Matrix4f.class),
	CAMERA(Camera.class);
	//=============================================================================================

	//=============================================================================================
	private Class<?> cls;
	//=============================================================================================
	
	//=============================================================================================
	private NodeFlag(Class<?> cls) {
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
