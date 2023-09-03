//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import javax.vecmath.Vector2f;

import ode.gui.ODEWidget.TAG;

//*************************************************************************************************
public class ODEWidgetBuilder {

	//=============================================================================================
	private ODEWidget widget;
	private ODEWidgets widgets;
	private ODEWidgetBuilder parentBuilder;
	//=============================================================================================
	
	//=============================================================================================
	public ODEWidgetBuilder(ODEWidgets widgets) {
		this.widgets = widgets; 
		widget = new ODEWidget(widgets);
	}
	//=============================================================================================

	//=============================================================================================
	public ODEWidgetBuilder root(ODEWidgets widgets) {
		widgets.root(this.widget);
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	public ODEWidgetBuilder name(String name) {
		widget.name(name);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public ODEWidgetBuilder tag(TAG ... tags) {
		widget.set(tags);
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	public ODEWidgetBuilder position(Vector2f position) {
		widget.position(position);
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	public ODEWidgetBuilder position(float x, float y) {
		widget.position(x, y);
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public ODEWidgetBuilder dimension(Vector2f dimension) {
		widget.dimension(dimension);
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	public ODEWidgetBuilder dimension(float x, float y) {
		widget.dimension(x, y);
		return this;
	}
	//=============================================================================================
	
	//=============================================================================================
	public ODEWidgetBuilder child(ODEWidget ... children) {
		for (ODEWidget child : children) {
			widget.attach(child);
		}
		return this;
	}
	//=============================================================================================

	//=============================================================================================
	public ODEWidgetBuilder push() {
		ODEWidgetBuilder childBuilder = new ODEWidgetBuilder(widgets);
		childBuilder.parentBuilder = this;
		this.widget.attach(childBuilder.widget);
		return childBuilder;
	}
	//=============================================================================================
	
	//=============================================================================================
	public ODEWidgetBuilder pop() {
		return parentBuilder;
	}
	//=============================================================================================

	//=============================================================================================
	public ODEWidget build() {
		return widget;
	}
	//=============================================================================================
	
}
//*************************************************************************************************