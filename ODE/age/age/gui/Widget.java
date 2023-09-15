//*************************************************************************************************
package age.gui;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import javax.vecmath.Vector2f;
import age.AGEException;

//*************************************************************************************************
public class Widget {

	//=============================================================================================
	private final Set<Flag> flags = EnumSet.noneOf(Flag.class);
	private final Set<Flag> flags_ro = Collections.unmodifiableSet(flags);
	//=============================================================================================
	
	//=============================================================================================
	private final Vector2f position = new Vector2f();
	private final Vector2f dimension = new Vector2f();
	private final Dock dock = new Dock();
	//=============================================================================================

	//=============================================================================================
	private Widget parent = null;
	private final List<Widget> children = new ArrayList<>(10);
	private final List<Widget> children_ro = Collections.unmodifiableList(children);
	//=============================================================================================

	//=============================================================================================
	private String text = null;
	private String image = null;
	//=============================================================================================
	
	//=============================================================================================
	public Widget(Flag ... flags) {
		this.flags.addAll(List.of(flags));
	}
	//=============================================================================================
	
	//=============================================================================================
	public float x() {
		return position.x;
	}
	//=============================================================================================

	//=============================================================================================
	public float y() {
		return position.y;
	}
	//=============================================================================================

	//=============================================================================================
	public float width() {
		return dimension.x;
	}
	//=============================================================================================

	//=============================================================================================
	public float height() {
		return dimension.y;
	}
	//=============================================================================================
	
	//=============================================================================================
	public Vector2f position() {
		return position;
	}
	//=============================================================================================

	//=============================================================================================
	public Vector2f dimension() {
		return dimension;
	}
	//=============================================================================================

	//=============================================================================================
	public Dock dock() {
		return dock;
	}
	//=============================================================================================

	//=============================================================================================
	public void dock(Dock dock) {
		this.dock.set(dock);
	}
	//=============================================================================================

	//=============================================================================================
	public void dock(float top, float bottom, float left, float right) {
		this.dock.set(top, bottom, left, right);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void position(Vector2f position) {
		this.position.set(position);
	}
	//=============================================================================================

	//=============================================================================================
	public void position(float x, float y) {
		this.position.set(x, y);
	}
	//=============================================================================================

	//=============================================================================================
	public void positionAdd(Vector2f position) {
		this.position.add(position);
	}
	//=============================================================================================

	//=============================================================================================
	public void positionAdd(float x, float y) {
		this.position.x += x;
		this.position.y += y;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void dimension(Vector2f dimension) {
		float dx = dimension.x - this.dimension.x;
		float dy = dimension.y - this.dimension.y;
		this.dimension.set(dimension);
		resized(dx, dy);
	}
	//=============================================================================================

	//=============================================================================================
	public void dimension(float width, float height) {
		float dx = width - this.dimension.x;
		float dy = height - this.dimension.y;
		this.dimension.set(width, height);
		resized(dx, dy);
	}
	//=============================================================================================

	//=============================================================================================
	public void dimensionAdd(Vector2f dimension) {
		this.dimension.add(dimension);
		resized(dimension.x, dimension.y);
	}
	//=============================================================================================

	//=============================================================================================
	public void dimensionAdd(float width, float height) {
		this.dimension.x += width;
		this.dimension.y += height;
		resized(width, height);
	}
	//=============================================================================================

	//=============================================================================================
	private void resized(float dx, float dy) {
		if (dx != 0f || dy != 0f) {
			for (Widget child : children) {
				child.parentResized(dx, dy);
			}
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void parentResized(float dx, float dy) {
		float dltX = dx * dock.left();
		float dltY = dy * dock.top();
		float dltW = dx * dock.right();
		float dltH = dy * dock.bottom();
		float newX = position.x  + dltX;
		float newY = position.y  + dltY;
		float newW = dimension.x + (dltW-dltX);
		float newH = dimension.y + (dltH-dltY);
		position(newX, newY);
		dimension(newW, newH);
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
	public Widget parent() {
		return parent;
	}
	//=============================================================================================
	
	//=============================================================================================
	public List<Widget> children() {
		return children_ro;
	}
	//=============================================================================================

	//=============================================================================================
	public void add(Widget child) {
		if (child.parent != null) throw new AGEException("Parent not NULL");
		children.add(child);
		child.parent = this;
	}
	//=============================================================================================

	//=============================================================================================
	public void add(int idx, Widget child) {
		if (child.parent != null) throw new AGEException("Parent not NULL");
		children.add(idx, child);
		child.parent = this;
	}
	//=============================================================================================

	//=============================================================================================
	public void remove() {
		if (parent == null) throw new AGEException("Parent is NULL");
		parent.children.remove(this);
		parent = null;
	}
	//=============================================================================================

	//=============================================================================================
	public void toFront() {
		if (parent != null) {
			parent.children.remove(this);
			parent.children.add(this);
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	public String text() {
		return text;
	}
	//=============================================================================================

	//=============================================================================================
	public void text(String text) {
		this.text = text;
	}
	//=============================================================================================

	//=============================================================================================
	public String image() {
		return image;
	}
	//=============================================================================================

	//=============================================================================================
	public void image(String image) {
		this.image = image;
	}
	//=============================================================================================
	
}
//*************************************************************************************************
