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
	//=============================================================================================

	//=============================================================================================
	private TriggerSystem triggerSystem = new TriggerSystem();
	private HoverSystem   hoverSystem   = new HoverSystem();
	private LayoutSystem  layoutSystem  = new LayoutSystem();
	private RenderSystem  renderSystem  = new RenderSystem();
	//=============================================================================================

	//=============================================================================================
	public GUI(Events events) {
		events.register(Event.MOUSE_CLICKED, triggerSystem);
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
			.register(triggerSystem)
			.register(hoverSystem)
			.register(layoutSystem)
			.register(renderSystem)
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
			.register(triggerSystem)
			.register(hoverSystem)
			.register(renderSystem)
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
			.register(triggerSystem)
			.register(hoverSystem)
			.register(renderSystem)
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
		triggerSystem.update(dT);
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
