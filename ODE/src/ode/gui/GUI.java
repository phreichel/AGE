//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ode.gui.Widget.TAG;
import ode.npa.Graphics;

//*************************************************************************************************
public class GUI {

	//=============================================================================================
	private final List<Widget> roots = new ArrayList<>();
	private final List<Widget> roots_ro = Collections.unmodifiableList(roots);
	//=============================================================================================

	//=============================================================================================
	final List<Widget> widgets = new ArrayList<Widget>();
	private final List<Widget> widgets_ro = Collections.unmodifiableList(widgets);
	//=============================================================================================

	//=============================================================================================
	private Theme theme = new Theme();
	private final Renderer renderer = new Renderer(roots_ro);
	//=============================================================================================
	
	//=============================================================================================
	public List<Widget> roots() {
		return roots_ro;
	}
	//=============================================================================================

	//=============================================================================================
	public void root(Widget ... widgets) {
		this.roots.addAll(List.of(widgets));
	}
	//=============================================================================================

	//=============================================================================================
	public void unroot(Widget ... widgets) {
		this.roots.removeAll(List.of(widgets));
	}
	//=============================================================================================

	//=============================================================================================
	public void destroy(Widget ... widgets) {
		for (Widget widget : widgets) {
			unroot(widget);
			destroy(widget.children().toArray(new Widget[] {}));
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	public List<Widget> widgets() {
		return widgets_ro;
	}
	//=============================================================================================

	//=============================================================================================
	public List<Widget> unattached(List<Widget> dst) {
		for (Widget widget : widgets) {
			if (widget.parent() == null) {
				dst.add(widget);
			}
		}
		return dst;
	}
	//=============================================================================================
	
	//=============================================================================================
	public List<Widget> named(String name, List<Widget> dst) {
		for (Widget widget : widgets) {
			if (widget.name().equals(name)) {
				dst.add(widget);
			}
		}
		return dst;
	}
	//=============================================================================================

	//=============================================================================================
	public Builder newBuilder() {
		return new Builder(this);
	}
	//=============================================================================================

	//=============================================================================================
	public Builder newLabel(String text) {
		Type type = Type.LABEL;
		return newBuilder()
			.type(type)
			.tag(TAG.DISPLAY)
			.text(text)
			.style(theme.get(type))
			.dimension(150, 20);
	}
	//=============================================================================================
	
	//=============================================================================================
	public Builder newButton(String text) {
		Type type = Type.BUTTON;
		return newBuilder()
			.type(type)
			.tag(TAG.DISPLAY)
			.text(text)
			.style(theme.get(type))
			.dimension(100, 20);
	}
	//=============================================================================================

	//=============================================================================================
	public Builder newTextField(String text) {
		Type type = Type.TEXTFIELD;
		return newBuilder()
			.type(type)
			.tag(TAG.DISPLAY)
			.text(text)
			.style(theme.get(type))
			.dimension(200, 20);
	}
	//=============================================================================================
	
	//=============================================================================================
	public Builder newGroup() {
		Type type = Type.GROUP;
		return newBuilder()
			.type(type)
			.tag(TAG.DISPLAY)
			.style(theme.get(type))
			.dimension(300, 800);
	}
	//=============================================================================================

	//=============================================================================================
	public Theme theme() {
		return theme;
	}
	//=============================================================================================

	//=============================================================================================
	public void theme(Theme theme) {
		this.theme = theme;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void render(Graphics graphics) {
		renderer.render(graphics);
	}
	//=============================================================================================
	
}
//*************************************************************************************************
