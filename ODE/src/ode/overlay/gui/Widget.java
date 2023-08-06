//*************************************************************************************************
package ode.overlay.gui;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import javax.vecmath.Vector2f;

import lombok.Getter;

//*************************************************************************************************
public class Widget {

	//=============================================================================================
	@Getter
	private Widget parent = null;
	//=============================================================================================

	//=============================================================================================
	private List<Widget> _children = new ArrayList<>(10);
	@Getter
	private List<Widget> children = Collections.unmodifiableList(_children);
	//=============================================================================================

	//=============================================================================================
	private Vector2f position = new Vector2f();
	private Vector2f size = new Vector2f();
	//=============================================================================================

	//=============================================================================================
	private Set<WidgetFlag> _flags = EnumSet.noneOf(WidgetFlag.class);
	@Getter
	private Set<WidgetFlag> flags = Collections.unmodifiableSet(_flags);
	//=============================================================================================
	
	//=============================================================================================
	public void addChild(Widget child) {
		Widget current = child.parent;
		if (current != this) {
			if (current != null) {
				current.removeChild(child);
			}
			child.parent = this;
			_children.add(child);
		}
	}
	//=============================================================================================

	//=============================================================================================
	public void removeChild(Widget child) {
		if (_children.remove(child)) {
			child.parent = null;
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	public float getX() {
		return position.x;
	}
	//=============================================================================================

	//=============================================================================================
	public float getY() {
		return position.y;
	}
	//=============================================================================================

	//=============================================================================================
	public void getPosition(Vector2f target) {
		target.set(position);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void setPosition(Vector2f position) {
		position.set(position);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void setPosition(float x, float y) {
		position.set(x, y);
	}
	//=============================================================================================
	
	//=============================================================================================
	public float getWidth() {
		return size.x;
	}
	//=============================================================================================

	//=============================================================================================
	public float getHeight() {
		return size.y;
	}
	//=============================================================================================

	//=============================================================================================
	public void getSize(Vector2f target) {
		target.set(position);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void setSize(Vector2f size) {
		size.set(size);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void setSize(float width, float height) {
		position.set(width, height);
	}
	//=============================================================================================

	//=============================================================================================
	public boolean hasFlags(WidgetFlag ... flags) {
		return _flags.containsAll(List.of(flags));
	}
	//=============================================================================================
	
	//=============================================================================================
	public void addFlags(WidgetFlag ... flags) {
		_flags.addAll(List.of(flags));
	}
	//=============================================================================================

	//=============================================================================================
	public void removeFlags(WidgetFlag ... flags) {
		_flags.removeAll(List.of(flags));
	}
	//=============================================================================================
	
}
//*************************************************************************************************
