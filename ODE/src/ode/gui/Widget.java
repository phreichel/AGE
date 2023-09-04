//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import javax.vecmath.Vector2f;

import ode.util.Node;

//*************************************************************************************************
public class Widget extends Node<Widget> {

	//=============================================================================================
	public enum TAG {
		DISPLAY;
	}
	//=============================================================================================
	
	//=============================================================================================
	private String name;
	private Type type;
	private final Set<TAG> tags = EnumSet.noneOf(TAG.class);
	private final Set<TAG> tags_ro = Collections.unmodifiableSet(tags);
	private final Vector2f position = new Vector2f();
	private final Vector2f dimension = new Vector2f();
	private Style style;
	private String text;
	//=============================================================================================

	//=============================================================================================
	public Widget(GUI widgets) {
		widgets.widgets.add(this);
	}
	//=============================================================================================

	//=============================================================================================
	public Type type() {
		return type;
	}
	//=============================================================================================

	//=============================================================================================
	public void type(Type type) {
		this.type = type;
	}
	//=============================================================================================

	//=============================================================================================
	public String name() {
		return name;
	}
	//=============================================================================================

	//=============================================================================================
	public void name(String name) {
		this.name = name;
	}
	//=============================================================================================
	
	//=============================================================================================
	public boolean tagged(TAG tag) {
		return tags.contains(tag);
	}
	//=============================================================================================
	
	//=============================================================================================
	public Set<TAG> tags() {
		return tags_ro;
	}
	//=============================================================================================

	//=============================================================================================
	public boolean match(TAG ... tags) {
		return this.tags.containsAll(List.of(tags));
	}
	//=============================================================================================
	
	//=============================================================================================
	public void set(TAG ... tags) {
		this.tags.addAll(List.of(tags));
	}
	//=============================================================================================

	//=============================================================================================
	public void clear(TAG ... tags) {
		this.tags.removeAll(List.of(tags));
	}
	//=============================================================================================
	
	//=============================================================================================
	public Vector2f position() {
		return position;
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
	public Vector2f dimension() {
		return dimension;
	}
	//=============================================================================================

	//=============================================================================================
	public void dimension(Vector2f dimension) {
		this.dimension.set(dimension);
	}
	//=============================================================================================

	//=============================================================================================
	public void dimension(float x, float y) {
		this.dimension.set(x, y);
	}
	//=============================================================================================

	//=============================================================================================
	public Style style() {
		return this.style;
	}
	//=============================================================================================

	//=============================================================================================
	public void style(Style style) {
		this.style = style;
	}
	//=============================================================================================
	
	//=============================================================================================
	public Style style(Style.KEY key, Object value) {
		this.style.put(key, value);
		return this.style;
	}
	//=============================================================================================

	//=============================================================================================
	public Style deriveStyle() {
		this.style = this.style.derive();
		return this.style;
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
	
}
//*************************************************************************************************
