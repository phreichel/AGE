//*************************************************************************************************
package ode.util;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//*************************************************************************************************
@SuppressWarnings("rawtypes")
public class ODENode<N extends ODENode> {

	//=============================================================================================
	protected N parent = null;
	protected final List<N> children = new ArrayList<>(5);
	protected final List<N> children_ro = Collections.unmodifiableList(children);
	//=============================================================================================

	//=============================================================================================
	public N parent() {
		return parent;
	}
	//=============================================================================================

	//=============================================================================================
	public List<N> children() {
		return children_ro;
	}
	//=============================================================================================
	
	//=============================================================================================
	@SuppressWarnings("unchecked")
	public void attach(N child) {
		if (child.parent != null) {
			throw new ODEException("Node already attached.");
		}
		child.parent = this;
		parent.children.add(child);
	}
	//=============================================================================================

	//=============================================================================================
	@SuppressWarnings("unchecked")
	public void attach(int idx, N child) {
		if (child.parent != null) {
			throw new ODEException("Node already attached.");
		}
		child.parent = this;
		parent.children.add(idx, child);
	}
	//=============================================================================================
	
	//=============================================================================================
	@SuppressWarnings("unchecked")
	public void detach(N child) {
		if (child.parent != this) {
			throw new ODEException("Node not attached to parent.");
		}
		children.remove(child);
		child.parent = null;
	}
	//=============================================================================================
	
}
//*************************************************************************************************
