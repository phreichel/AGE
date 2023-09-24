//*************************************************************************************************
package age.gui.sys;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import age.gui.WFlag;
import age.gui.Widget;
import age.gui.dat.Scrollable;
import age.gui.dat.WItem;
import age.util.X;

//*************************************************************************************************
public class Layouter {

	//=============================================================================================
	private static final float G =  5f; 
	//=============================================================================================
	
	//=============================================================================================
	private Map<WFlag, List<Widget>> register = new HashMap<>();
	//=============================================================================================

	//=============================================================================================
	public void add(WFlag wFlag, Widget widget) {
		var list = register.get(wFlag);
		if (list == null) {
			list = new ArrayList<>(5);
			register.put(wFlag, list);
		}
		list.add(widget);
	}
	//=============================================================================================

	//=============================================================================================
	public void remove(Widget widget) {
		for (var list : register.values()) {
			list.remove(widget);
		}
	}
	//=============================================================================================

	//=============================================================================================
	public void update() {
		for (var entry : register.entrySet()) {
			var flag = entry.getKey();
			var list = entry.getValue();
			for (var widget : list) {
				if (!widget.match(WFlag.CLEAN)) {
					switch (flag) {
						case VSTACK    -> layoutVStack(widget); 
						case SCROLLBAR -> layoutScrollbar(widget); 
						default -> throw new X("Unsupported Layout Flag: %s", flag);
					}
				}
			}
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void layoutScrollbar(Widget widget) {
		boolean vertical = true;
		Scrollable scstate = widget.component(
				WItem.SCROLLABLE_VERTICAL,
				Scrollable.class);
		if (scstate == null) {
			scstate = widget.component(
					WItem.SCROLLABLE_HORIZONTAL,
					Scrollable.class);
			vertical = false;
		}
		if (scstate != null) {
			Widget slider = widget.children().get(0);
			Widget handle = slider.children().get(0);
			float pageScale = (float) (scstate.page) / (float) (scstate.size + scstate.page - 1);
			float sliderRange = vertical ? slider.dimension().y : slider.dimension().x; 
			float handleRange = sliderRange * pageScale; 
			float indexRange  = scstate.size - 1;
			float indexStep = (sliderRange - handleRange) / indexRange;
			if (vertical) {
				handle.position().y = indexStep * scstate.mark;
				handle.dimension().y = handleRange;
			} else {
				handle.position().x = indexStep * scstate.mark;
				handle.dimension().x = handleRange;
			}
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void layoutVStack(Widget widget) {
		var dx =
			2 * G +
			widget
				.children()
				.stream()
				.map(w -> w.dimension().x)
				.max((a, b) -> Float.compare(a, b))
				.get();
		var dy = G;
		for (var w : widget.children()) {
			var cx = w.dimension().x;
			var cy = w.dimension().y;
			w.position((dx - cx) * .5f, dy);
			dy += cy + G;
		}
		widget.dimension(dx, dy);
		widget.flag(WFlag.CLEAN);
	}
	//=============================================================================================

}
//*************************************************************************************************
