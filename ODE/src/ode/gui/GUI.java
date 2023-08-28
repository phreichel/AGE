//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.vecmath.Vector2f;

import ode.event.Event;
import ode.event.Events;
import ode.platform.Graphics;
import ode.util.Inset;

//*************************************************************************************************
public class GUI {

	//=============================================================================================
	public float width = 800;
	public float height = 600;	
	//=============================================================================================
	
	//=============================================================================================
	public final Set<Widget> widgets = new HashSet<>();
	public final Map<Widget, TriggerData> triggerMap = new HashMap<>();
	public final Map<Widget, Vector2f> dimensionMap = new HashMap<>();
	public final Map<Widget, FlagData> flagsMap = new HashMap<>();
	public final Map<Widget, HierarchyData> hierarchyMap = new HashMap<>();
	public final Map<Widget, LayoutEnum> layoutMap = new HashMap<>();
	public final Map<Widget, Inset> paddingMap = new HashMap<>();
	public final Map<Widget, AlignEnum> alignMap = new HashMap<>();
	public final Map<Widget, Vector2f> positionMap = new HashMap<>();
	public final Map<Widget, RenderEnum> renderMap = new HashMap<>();
	public final Map<Widget, String> textMap = new HashMap<>();
	//=============================================================================================

	//=============================================================================================
	private RenderSystem renderSystem = new RenderSystem(renderMap);
	private HoverSystem hoverSystem = new HoverSystem(widgets);
	private TriggerSystem clickSystem = new TriggerSystem(widgets);
	private LayoutSystem layoutSystem = new LayoutSystem(layoutMap);
	//=============================================================================================

	//=============================================================================================
	public GUI(Events events) {
		events.register(Event.MOUSE_CLICKED, clickSystem);
		events.register(Event.MOUSE_MOVED, hoverSystem);
	}
	//=============================================================================================

	//=============================================================================================
	public WidgetBuilder build() {
		return new WidgetBuilder(this);
	}
	//=============================================================================================
	
	//=============================================================================================
	public Widget createBox() {
		return build()
			.withFlagsData(FlagEnum.DISPLAYED)
			.withPositionData(10, 10)
			.withDimensionData(100, 100)
			.withHierarchyData()
			.withRenderData(RenderEnum.BOX)
			.withLayoutData(LayoutEnum.VERTICAL)
			.withAlignData(AlignEnum.CENTER)
			.withPaddingData(5,  5,  5,  5)
			.build();
	}
	//=============================================================================================

	//=============================================================================================
	public Widget createLabel(String label) {
		return build()
			.withFlagsData(FlagEnum.DISPLAYED)
			.withPositionData(0, 0)
			.withDimensionData(200, 20)
			.withHierarchyData()
			.withRenderData(RenderEnum.LABEL)
			.withAlignData(AlignEnum.CENTER)
			.withTextData(label)
			.build();
	}
	//=============================================================================================

	//=============================================================================================
	public Widget createButton(String label, Action action) {
		return build()
			.withFlagsData(FlagEnum.DISPLAYED)
			.withPositionData(0, 0)
			.withDimensionData(120, 20)
			.withHierarchyData()
			.withRenderData(RenderEnum.BUTTON)
			.withAlignData(AlignEnum.CENTER)
			.withTextData(label)
			.withTriggerData(TriggerEnum.LEFT_CLICK, action)
			.build();
	}
	//=============================================================================================
	
	//=============================================================================================
	public void resize(float width, float height) {
		this.width = width;
		this.height = height;
	}
	//=============================================================================================

	//=============================================================================================
	public void update(float dT) {
		clickSystem.update(dT);
		hoverSystem.update();
		layoutSystem.update();
	}
	//=============================================================================================
	
	//=============================================================================================
	public void render(Graphics graphics) {
		graphics.beginGUIMode();
		renderSystem.update(graphics);
	}
	//=============================================================================================

}
//*************************************************************************************************
