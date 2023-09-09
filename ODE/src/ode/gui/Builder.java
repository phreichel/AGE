//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import javax.vecmath.Color4f;
import javax.vecmath.Vector2f;

//*************************************************************************************************
public class Builder {

	//=============================================================================================
	private Widget  widget;
	private GUI     widgets;
	private Builder parentBuilder;
	//=============================================================================================
	
	//=============================================================================================
	public Builder(GUI widgets) {
		this.widgets = widgets; 
		widget = new Widget(widgets);
	}
	//=============================================================================================

	//=============================================================================================
	public Builder root(GUI widgets) {
		widgets.root(this.widget);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder name(String name) {
		widget.name(name);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder set(Flag ... flags) {
		widget.set(flags);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder clear(Flag ... flags) {
		widget.clear(flags);
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	public Builder position(Vector2f position) {
		widget.position(position);
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	public Builder position(float x, float y) {
		widget.position(x, y);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder dimension(Vector2f dimension) {
		widget.dimension(dimension);
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	public Builder dimension(float x, float y) {
		widget.dimension(x, y);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder foreground(Color4f foreground) {
		widget.foreground(foreground);
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	public Builder foreground(float r, float g, float b, float a) {
		widget.foreground(r, g, b, a);
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	public Builder background(Color4f background) {
		widget.background(background);
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	public Builder background(float r, float g, float b, float a) {
		widget.background(r, g, b, a);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder font(String font) {
		widget.font(font);
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	public Builder text(String text) {
		widget.text(text);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder icon(String icon) {
		widget.icon(icon);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder onPointerEnter(Action action) {
		widget.onPointerEnter(action);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder onPointerExit(Action action) {
		widget.onPointerExit(action);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder onPointerPress(Action action) {
		widget.onPointerPress(action);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder onPointerRelease(Action action) {
		widget.onPointerPress(action);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder onPointerClick(Action action) {
		widget.onPointerPress(action);
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	public Builder child(Widget ... children) {
		for (Widget child : children) {
			widget.attach(child);
		}
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder push() {
		Builder childBuilder = new Builder(widgets);
		childBuilder.parentBuilder = this;
		this.widget.attach(childBuilder.widget);
		return childBuilder;
	}
	//=============================================================================================
	
	//=============================================================================================
	public Builder pop() {
		return parentBuilder;
	}
	//=============================================================================================

	//=============================================================================================
	public Widget widget() {
		return widget;
	}
	//=============================================================================================
	
}
//*************************************************************************************************