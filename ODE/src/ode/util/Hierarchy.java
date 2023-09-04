//*************************************************************************************************
package ode.util;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//*************************************************************************************************
public class Hierarchy<Node> {

	//=============================================================================================
	private Map<Node, Node> parentMap = new HashMap<>();
	private Map<Node, List<Node>> childMap = new HashMap<>();
	//=============================================================================================

	//=============================================================================================
	public void attach(Node parent, Node child) {
		parentMap.put(child, parent);
		List<Node> childList = childMap.get(parent);
		if (childList == null) {
			childList = new ArrayList<>();
			childMap.put(parent, childList);
		}
		childList.add(child);
	}
	//=============================================================================================

	//=============================================================================================
	public Node detach(Node node) {
		Node parent = parentMap.get(node);
		List<Node> childList = childMap.get(parent);
		parentMap.remove(node);
		childList.remove(node);
		return node;
	}
	//=============================================================================================
	
}
//*************************************************************************************************
