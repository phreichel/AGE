//*************************************************************************************************
package age.model;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

//*************************************************************************************************
public class Skin {

	//=============================================================================================
	private Mesh mesh;
	private List<Element> elements = new ArrayList<>();
	private List<Element> elements_ro = Collections.unmodifiableList(elements);
	//=============================================================================================

	//=============================================================================================
	public Mesh mesh() {
		return mesh;
	}
	//=============================================================================================

	//=============================================================================================
	public void mesh(Mesh mesh) {
		this.mesh = mesh;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void fill(
		Map<String, String> elementNames,
		Map<String, ElementType> elementTypes,
		Map<String, String> elementMaterials, 
		Map<String, List<Integer>> elementIndices) {
		for (String key : elementNames.keySet()) {
			String name = elementNames.get(key);
			ElementType type = elementTypes.get(key);
			String material = elementMaterials.get(key);
			List<Integer> indices = elementIndices.get(key);
			Element element = new Element();
			element.fill(name, type, material, indices);
			elements.add(element);
		}
	}
	//=============================================================================================	
	
	//=============================================================================================
	public List<Element> elements() {
		return elements_ro;
	}
	//=============================================================================================
	
}
//*************************************************************************************************
