/*
 * Commit: 01f6edb422e32c691886b08481b74af734d2419d
 * Date: 2023-09-25 00:22:26+02:00
 * Author: Philip Reichel
 * Comment: Added Mesh
 *
 * Commit: 0c072ef17032c66ffdd34170f7a2c79345c34eae
 * Date: 2023-09-23 10:27:57+02:00
 * Author: Philip Reichel
 * Comment: Updated GUI Handling - Halfway done
 *
 * Commit: 755b11dea54fbe41e8bd4279fa3fa1d07e6fc2ea
 * Date: 2023-09-22 20:09:27+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 * Commit: 4ba52177b03c84982e184472f4e51a9157e9a67f
 * Date: 2023-09-21 20:15:06+02:00
 * Author: Philip Reichel
 * Comment: Cleaning all up
 *
 * Commit: ea80018edeb513be67842adc81cba9128971f0c1
 * Date: 2023-09-21 11:36:51+02:00
 * Author: pre7618
 * Comment: Renamed the Exception
 *
 * Commit: dd56eccbb9b27eab45e556efde8d3bb7d0f4ce97
 * Date: 2023-09-20 17:16:57+02:00
 * Author: pre7618
 * Comment: Renamings
 *
 * Commit: d6736fdafe10ab47eab0c1516c6ab53db2c127da
 * Date: 2023-09-20 14:40:20+02:00
 * Author: pre7618
 * Comment: .
 *
 * Commit: efba03d014f9f8690fc57eb3949bb36f7ed2e269
 * Date: 2023-09-19 07:28:45+02:00
 * Author: Philip Reichel
 * Comment: todo tasks
 *
 * Commit: c9fba54298823be83ab349a7f2e0eb3b2c607a44
 * Date: 2023-09-18 22:02:46+02:00
 * Author: Philip Reichel
 * Comment: upd
 *
 * Commit: 28615d0198d72bb1e1667c0113a594237068a89d
 * Date: 2023-09-18 16:25:09+02:00
 * Author: pre7618
 * Comment: Added Scene Nodes
 *
 * Commit: f3bbe83125092b4a8cf308c82455a7fd16ccf366
 * Date: 2023-09-18 03:05:37+02:00
 * Author: Philip Reichel
 * Comment: Stuff
 *
 */

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
import age.util.X;

//*************************************************************************************************
public class Node {

	//=============================================================================================
	private Node parent;
	private final List<Node> children = new ArrayList<>(5);
	private final List<Node> children_ro = Collections.unmodifiableList(children);
	private Set<NFlag> nFlags = EnumSet.noneOf(NFlag.class);
	private Set<NFlag> flags_ro = Collections.unmodifiableSet(nFlags);
	private final Map<NItem, Object> components = new EnumMap<>(NItem.class);
	private final Map<NItem, Object> components_ro = Collections.unmodifiableMap(components);
	//=============================================================================================

	//=============================================================================================
	public Node(NFlag ... flags) {
		flag(flags);
	}
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
		if (node.parent != null) throw new X("Node already attached");
		node.parent = this;
		children.add(idx, node);
	}
	//=============================================================================================

	//=============================================================================================
	public void detach() {
		if (parent == null) throw new X("Node not attached");
		parent.children.remove(this);
		parent = null;
	}
	//=============================================================================================

	//=============================================================================================
	public Set<NFlag> nFlags() {
		return flags_ro;
	}
	//=============================================================================================

	//=============================================================================================
	public void flag(NFlag ... flags) {
		this.nFlags.addAll(List.of(flags));
	}
	//=============================================================================================

	//=============================================================================================
	public void clear(NFlag ... flags) {
		this.nFlags.removeAll(List.of(flags));
	}
	//=============================================================================================

	//=============================================================================================
	public boolean match(NFlag ... flags) {
		return this.nFlags.containsAll(List.of(flags));
	}
	//=============================================================================================
	
	//=============================================================================================
	public Object component(NItem part) {
		return components.get(part);
	}
	//=============================================================================================

	//=============================================================================================
	@SuppressWarnings("unchecked")
	public <C> C component(NItem part, Class<C> cls) {
		return (C) component(part);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void component(NItem part, Object component) {
		part.check(component);
		components.put(part, component);
	}
	//=============================================================================================
	
	//=============================================================================================
	public Map<NItem, Object> components() {
		return components_ro;
	}
	//=============================================================================================
	
}
//*************************************************************************************************
