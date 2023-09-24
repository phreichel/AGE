//*************************************************************************************************
package age.scene;
//*************************************************************************************************

import age.port.Port;

//*************************************************************************************************
public class Scene {

	//=============================================================================================
	private Node root = new Node();
	private Node camera = null;
	//=============================================================================================

	//=============================================================================================
	private Animations animations = new Animations();
	private Rendering rendering = new Rendering(this);
	//=============================================================================================
	
	//=============================================================================================
	public void assign(Port port) {
		port.add(rendering);
	}
	//=============================================================================================

	//=============================================================================================
	public Animations animations() {
		return animations;
	}
	//=============================================================================================
	
	//=============================================================================================
	public Node root() {
		return root;
	}
	//=============================================================================================

	//=============================================================================================
	public Node camera() {
		return camera;
	}
	//=============================================================================================

	//=============================================================================================
	public void camera(Node camera) {
		this.camera = camera;
	}
	//=============================================================================================

	//=============================================================================================
	public void update(float dT) {
		animations.update(dT);
	}
	//=============================================================================================
	
}
//*************************************************************************************************
