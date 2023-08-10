//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.List;

import ode.platform.Graphics;
import ode.util.Dimension;
import ode.util.Location;
import ode.util.ODEException;

//*************************************************************************************************
public class Widget {

	//=============================================================================================
	protected Widget parent = null;
	protected final List<Widget> children = new ArrayList<>(5);
	//=============================================================================================

	//=============================================================================================
	private final Location location = new Location();
	private final Dimension size = new Dimension();	
	//=============================================================================================

	//=============================================================================================
	private Layout layout = null;
	//=============================================================================================
	
	//=============================================================================================
	public Widget getParent() {
		return parent;
	}
	//=============================================================================================

	//=============================================================================================
	public int getChildCount() {
		return this.children.size();
	}
	//=============================================================================================
	
	//=============================================================================================
	public void attachChild(Widget widget) {
		if (widget.parent != null) throw new ODEException("Widget already attached");
		widget.parent = this;
		children.add(widget);
	}
	//=============================================================================================

	//=============================================================================================
	public void detachChild(Widget widget) {
		if (widget.parent == this) throw new ODEException("Widget not attached to this parent");
		widget.parent = null;
		children.remove(widget);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void detachFromParent() {
		if (parent == null) throw new ODEException("Widget not attached");
		parent.detachChild(this);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void moveUp() {
		if (parent == null) throw new ODEException("Widget not attached");
		int idx = parent.children.indexOf(this);
		if (idx > 0) {
			parent.children.remove(this);
			parent.children.add(idx-1, this);
		}
	}
	//=============================================================================================

	//=============================================================================================
	public void moveDown() {
		if (parent == null) throw new ODEException("Widget not attached");
		int idx = parent.children.indexOf(this);
		if (idx < parent.children.size()-1) {
			parent.children.remove(this);
			parent.children.add(idx+1, this);
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	public void moveToTop() {
		if (parent == null) throw new ODEException("Widget not attached");
		parent.children.remove(this);
		parent.children.add(0, this);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void moveToBottom() {
		if (parent == null) throw new ODEException("Widget not attached");
		parent.children.remove(this);
		parent.children.add(this);
	}
	//=============================================================================================

	//=============================================================================================
	public void moveToIndex(int idx) {
		if (parent == null) throw new ODEException("Widget not attached");
		parent.children.remove(this);
		parent.children.add(idx, this);
	}
	//=============================================================================================

	//=============================================================================================
	public Widget nextSibling(boolean cycle) {
		if (parent == null) throw new ODEException("Widget not attached");
		int idx = parent.children.indexOf(this);
		if (!cycle && idx == parent.children.size()-1) throw new ODEException("No next sibling Widget");
		idx = idx + 1 + parent.children.size() % parent.children.size(); 
		return parent.children.get(idx);
	}
	//=============================================================================================

	//=============================================================================================
	public Widget previousSibling(boolean cycle) {
		if (parent == null) throw new ODEException("Widget not attached");
		int idx = parent.children.indexOf(this);
		if (!cycle && idx == 0) throw new ODEException("No previous sibling Widget");
		idx = idx - 1 + parent.children.size() % parent.children.size(); 
		return parent.children.get(idx);
	}
	//=============================================================================================

	//=============================================================================================
	public Widget getChildAt(int idx) {
		return children.get(idx);
	}
	//=============================================================================================

	//=============================================================================================
	public int getIndex() {
		if (parent == null) throw new ODEException("Widget not attached");
		return parent.getChildIndex(this);
	}
	//=============================================================================================

	//=============================================================================================
	public int getChildIndex(Widget widget) {
		return children.indexOf(widget);	
	}
	//=============================================================================================

	//=============================================================================================
	public List<Widget> getChildren() {
		return children;
	}
	//=============================================================================================
	
	//=============================================================================================
	public float getX() {
		return location.x;
	}
	//=============================================================================================

	//=============================================================================================
	public float getY() {
		return location.y;
	}
	//=============================================================================================

	//=============================================================================================
	public Location getLocation() {
		return location;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void setLocation(float x, float y) {
		location.set(x, y);
	}
	//=============================================================================================

	//=============================================================================================
	public void setLocation(Location location) {
		location.set(location);
	}
	//=============================================================================================

	//=============================================================================================
	public float getWidth() {
		return size.width;
	}
	//=============================================================================================

	//=============================================================================================
	public float getHeight() {
		return size.height;
	}
	//=============================================================================================

	//=============================================================================================
	public Dimension getSize() {
		return size;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void setSize(float width, float height) {
		size.set(width, height);
	}
	//=============================================================================================

	//=============================================================================================
	public void setSize(Dimension size) {
		size.set(size);
	}
	//=============================================================================================

	//=============================================================================================
	public void render(Graphics g) {
		g.pushTransform();
		g.translate(getX(), getY());
		renderWidget(g);
		for (int i=children.size()-1; i>=0; i--) {
			Widget child = children.get(i);
			child.render(g);
		}
		g.popTransform();
	}
	//=============================================================================================

	//=============================================================================================
	protected void renderWidget(Graphics g) {
		g.setColor(.6f, .6f, .6f);
		g.drawRectangle(0f, 0f, getWidth(), getHeight());
	}
	//=============================================================================================

	//=============================================================================================
	public Layout getLayout() {
		return layout;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void setLayout(Layout layout) {
		this.layout = layout;
	}
	//=============================================================================================

	//=============================================================================================
	public void applyLayout() {
		if (layout != null) {
			layout.apply(this);
		}
	}
	//=============================================================================================
	
}
//*************************************************************************************************
