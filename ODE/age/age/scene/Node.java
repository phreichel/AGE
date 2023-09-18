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

//*************************************************************************************************
public class Node {

	//=============================================================================================
	private Node parent;
	//=============================================================================================

	//=============================================================================================
	private final List<Node> children = new ArrayList<>(5);
	private final List<Node> children_ro = Collections.unmodifiableList(children);
	//=============================================================================================

	//=============================================================================================
	private Set<Flag> flags = EnumSet.noneOf(Flag.class);
	private Set<Flag> flags_ro = Collections.unmodifiableSet(flags);
	//=============================================================================================
	
	//=============================================================================================
	private final Map<Part, Object> components = new EnumMap<>(Part.class);
	private final Map<Part, Object> components_ro = Collections.unmodifiableMap(components);
	//=============================================================================================
	
	//=============================================================================================
	public Node parent() {
		return this.parent;
	}
	//=============================================================================================

	//=============================================================================================
	public List<Node> children() {
		return this.children_ro;
	}
	//=============================================================================================

	//=============================================================================================
	public void attach(Node node) {
		attach(children.size(), node);
	}
	//=============================================================================================

	//=============================================================================================
	public void attach(int idx, Node node) {
		if (node.parent != null) throw new AGEException("Node already attached");
		node.parent = this;
		children.add(idx, node);
	}
	//=============================================================================================

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
	public Object component(Part part) {
		return components.get(part);
	}
	//=============================================================================================

	//=============================================================================================
	@SuppressWarnings("unchecked")
	public <C> C component(Part part, Class<C> cls) {
		return (C) component(part);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void component(Part part, Object component) {
		part.check(component);
		components.put(part, component);
	}
	//=============================================================================================
	
	//=============================================================================================
	public Map<Part, Object> components() {
		return components_ro;
	}
	//=============================================================================================
	
}
//*************************************************************************************************
