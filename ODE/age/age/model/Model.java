//*************************************************************************************************
package age.model;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import age.mesh.Material;

//*************************************************************************************************
public class Model {

	//=============================================================================================
	private Skin skin;
	private List<Material> materials = new ArrayList<>();
	private List<Material> materials_ro = Collections.unmodifiableList(materials);
	//=============================================================================================

	//=============================================================================================
	public Skin skin() {
		return skin;
	}
	//=============================================================================================

	//=============================================================================================
	public void skin(Skin skin) {
		this.skin = skin;
	}
	//=============================================================================================
	
	//=============================================================================================
	public List<Material> materials() {
		return materials_ro;
	}
	//=============================================================================================

	//=============================================================================================
	public void material(Material material) {
		materials.add(material);
	}
	//=============================================================================================
	
}
//*************************************************************************************************
