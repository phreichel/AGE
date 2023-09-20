//*************************************************************************************************
package age.scene;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import age.AGEException;

/**************************************************************************************************
 * The Scene Node container class.
 * Its main purpose is like an Entity in an ECS to contain and combine components.
 * Unlike a typical ECS a hierarchical Node structure is built in.
 */
public class Node {

	/**********************************************************************************************
	 * The parent Node for the hierarchical structure
	 */
	private Node parent;

	/**********************************************************************************************
	 * The (modifiable) list of child Nodes for the hierarchical structure
	 */
	private final List<Node> children = new ArrayList<>(5);

	/**********************************************************************************************
	 * The (unmodifiable) list wrapper of child Nodes for the hierarchical structure.
	 * It wraps the children list and provides a read only representation for class external use.
	 */
	private final List<Node> children_ro = Collections.unmodifiableList(children);

	/**********************************************************************************************
	 * The (modifiable) set of state flags
	 */
	private Set<Flag> flags = EnumSet.noneOf(Flag.class);

	/**********************************************************************************************
	 * The (unmodifiable) set of state flags
	 * It wraps the flags set and provides a read only representation for class external use
	 */
	private Set<Flag> flags_ro = Collections.unmodifiableSet(flags);
	
	/**********************************************************************************************
	 * The (modifiable) map of Part access keys and Node components.
	 */
	private final Map<NodeFlag, Object> components = new EnumMap<>(NodeFlag.class);

	/**********************************************************************************************
	 * The (unmodifiable) map of Part access keys and Node components.
	 * It wraps the components map and provides a read only representation for clas external use
	 */
	private final Map<NodeFlag, Object> components_ro = Collections.unmodifiableMap(components);
	
	/**********************************************************************************************
	 * The parent property getter
	 * @return the parent Node or null if there is none
	 */
	public Node parent() {
		return this.parent;
	}

	/**********************************************************************************************
	 * The children list property getter
	 * @return the unmodifiable children list 
	 */
	public List<Node> children() {
		return this.children_ro;
	}

	/**********************************************************************************************
	 * Method to attach a Node as child at the end of the children list
	 * @param node The node to attach
	 */
	public void attach(Node node) {
		attach(children.size(), node);
	}

	/**********************************************************************************************
	 * Method to attach a Node as child at a specific index of the children list
	 * @param idx The index of the node location in the children list
	 * @param node The node to attach
	 */
	public void attach(int idx, Node node) {
		if (node.parent != null) throw new AGEException("Node already attached");
		node.parent = this;
		children.add(idx, node);
	}

	//=============================================================================================
	public void detach() {
		if (parent == null) throw new AGEException("Node not attached");
		parent.children.remove(this);
		parent = null;
	}
	//=============================================================================================

	//=============================================================================================
	public Set<Flag> flags() {
		return flags_ro;
	}
	//=============================================================================================

	//=============================================================================================
	public void flag(Flag ... flags) {
		this.flags.addAll(List.of(flags));
	}
	//=============================================================================================

	//=============================================================================================
	public void clear(Flag ... flags) {
		this.flags.removeAll(List.of(flags));
	}
	//=============================================================================================

	//=============================================================================================
	public boolean match(Flag ... flags) {
		return this.flags.containsAll(List.of(flags));
	}
	//=============================================================================================
	
	//=============================================================================================
	public Object component(NodeFlag part) {
		return components.get(part);
	}
	//=============================================================================================

	//=============================================================================================
	@SuppressWarnings("unchecked")
	public <C> C component(NodeFlag part, Class<C> cls) {
		return (C) component(part);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void component(NodeFlag part, Object component) {
		part.check(component);
		components.put(part, component);
	}
	//=============================================================================================
	
	//=============================================================================================
	public Map<NodeFlag, Object> components() {
		return components_ro;
	}
	//=============================================================================================
	
}
//*************************************************************************************************
