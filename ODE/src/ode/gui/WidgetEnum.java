//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import javax.vecmath.Vector2f;
import ode.util.Inset;

//*************************************************************************************************
public enum WidgetEnum {

	//=============================================================================================
	TRIGGER(TriggerData.class),
	DIMENSION(Vector2f.class),
	FLAGS(FlagData.class),
	HIERARCHY(HierarchyData.class),
	LAYOUT(LayoutEnum.class),
	PADDING(Inset.class),
	ALIGN(AlignEnum.class),
	POSITION(Vector2f.class),
	RENDER(RenderEnum.class),
	TEXT(String.class);
	//=============================================================================================
	
	//=============================================================================================
	private Class<?> componentClass = null;
	//=============================================================================================
	
	//=============================================================================================
	private WidgetEnum(Class<?> componentClass) {
		this.componentClass = componentClass;
	}
	//=============================================================================================

	//=============================================================================================
	public Class<?> getComponentClass() {
		return componentClass;
	}
	//=============================================================================================
	
}
//*************************************************************************************************