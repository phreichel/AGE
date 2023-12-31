/*
 * Commit: aa20ad72b29161d58217d659b23accf2664ef3cf
 * Date: 2023-09-26 23:19:19+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 * Commit: 01f6edb422e32c691886b08481b74af734d2419d
 * Date: 2023-09-25 00:22:26+02:00
 * Author: Philip Reichel
 * Comment: Added Mesh
 *
 * Commit: 54b553def3cb950e911a8df3fd89a9f1818bb7e3
 * Date: 2023-09-23 20:40:18+02:00
 * Author: Philip Reichel
 * Comment: ...
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
 * Commit: efba03d014f9f8690fc57eb3949bb36f7ed2e269
 * Date: 2023-09-19 07:28:45+02:00
 * Author: Philip Reichel
 * Comment: todo tasks
 *
 * Commit: 0a0626956b5c49e78d12757c077c1f9694a9dcc2
 * Date: 2023-09-16 21:35:52+02:00
 * Author: Philip Reichel
 * Comment: System Menu and Improved Messaging
 *
 * Commit: 6989d6c19381cac7970d6f3736fe63e8a5f1af4e
 * Date: 2023-09-15 22:51:45+02:00
 * Author: Philip Reichel
 * Comment: Add Move and Resize to Frames
 *
 * Commit: 979912e4583b2e47ff4138caf044ae2005b4a274
 * Date: 2023-09-15 21:18:58+02:00
 * Author: Philip Reichel
 * Comment: Refactoring GUI Window Rendering
 *
 * Commit: 275f07861ea5f95dd90f184d1311a0a5a8cd510d
 * Date: 2023-09-14 17:55:15+02:00
 * Author: pre7618
 * Comment: GUI in progress
 *
 * Commit: b779630154637a233236d58a5fc0960eb49c2cb1
 * Date: 2023-09-12 16:03:02+02:00
 * Author: pre7618
 * Comment: Added age Widget
 *
 */

//*************************************************************************************************
package age.gui;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.vecmath.Vector2f;

import age.util.X;

//*************************************************************************************************
public class Widget {

	//=============================================================================================
	private final Set<WFlag> wFlags = EnumSet.noneOf(WFlag.class);
	private final Set<WFlag> flags_ro = Collections.unmodifiableSet(wFlags);
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
	private final Map<WItem, Object> components = new EnumMap<>(WItem.class);
	private final Map<WItem, Object> components_ro = Collections.unmodifiableMap(components);
	//=============================================================================================
	
	//=============================================================================================
	public Widget(WFlag ... flags) {
		this.wFlags.addAll(List.of(flags));
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
		clear(WFlag.CLEAN);
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
	public Set<WFlag> wFlags() {
		return flags_ro;
	}
	//=============================================================================================

	//=============================================================================================
	public void flag(WFlag ... flags) {
		this.wFlags.addAll(List.of(flags));
	}
	//=============================================================================================

	//=============================================================================================
	public void clear(WFlag ... flags) {
		this.wFlags.removeAll(List.of(flags));
	}
	//=============================================================================================
	
	//=============================================================================================
	public boolean match(WFlag ... flags) {
		return this.wFlags.containsAll(List.of(flags));
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
		if (child.parent != null) throw new X("Parent not NULL");
		children.add(child);
		child.parent = this;
		clear(WFlag.CLEAN);
	}
	//=============================================================================================

	//=============================================================================================
	public void add(int idx, Widget child) {
		if (child.parent != null) throw new X("Parent not NULL");
		children.add(idx, child);
		child.parent = this;
		clear(WFlag.CLEAN);
	}
	//=============================================================================================

	//=============================================================================================
	public void remove() {
		if (parent == null) throw new X("Parent is NULL");
		parent.children.remove(this);
		parent = null;
		clear(WFlag.CLEAN);
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
	public Object component(WItem c) {
		return components.get(c);
	}
	//=============================================================================================

	//=============================================================================================
	@SuppressWarnings("unchecked")
	public <C> C component(WItem c, Class<C> cls) {
		return (C) component(c);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void component(WItem c, Object component) {
		c.check(component);
		components.put(c, component);
	}
	//=============================================================================================
	
	//=============================================================================================
	public Map<WItem, Object> components() {
		return components_ro;
	}
	//=============================================================================================
	
}
//*************************************************************************************************
