//*************************************************************************************************
package ode.gui;
//*************************************************************************************************

import java.util.UUID;

import javax.vecmath.Vector2f;

//*************************************************************************************************
public class Widget {

	//=============================================================================================
	public final UUID uuid = UUID.randomUUID();
	//=============================================================================================

	//=============================================================================================
	private final GUI gui;
	//=============================================================================================

	//=============================================================================================
	Widget(GUI gui) {
		this.gui = gui; 
	}
	//=============================================================================================

	//=============================================================================================
	public TriggerData getTriggerData() {
		return gui.triggerMap.get(this);
	}
	//=============================================================================================

	//=============================================================================================
	public Vector2f getDimensionData() {
		return gui.dimensionMap.get(this);
	}
	//=============================================================================================

	//=============================================================================================
	public FlagData getFlagsData() {
		return gui.flagsMap.get(this);
	}
	//=============================================================================================

	//=============================================================================================
	public HierarchyData getHierarchyData() {
		return gui.hierarchyMap.get(this);
	}
	//=============================================================================================
	
	//=============================================================================================
	public Vector2f getPositionData() {
		return gui.positionMap.get(this);
	}
	//=============================================================================================

	//=============================================================================================
	public LayoutData getLayoutData() {
		return gui.layoutMap.get(this);
	}
	//=============================================================================================

	//=============================================================================================
	public RenderEnum getRenderData() {
		return gui.renderMap.get(this);
	}
	//=============================================================================================

	//=============================================================================================
	public String getTextData() {
		return gui.textMap.get(this);
	}
	//=============================================================================================

	//=============================================================================================
	public InsetData getPaddingData() {
		return gui.paddingMap.get(this);
	}
	//=============================================================================================
	
	//=============================================================================================
	public int hashCode() {
		return uuid.hashCode();
	}
	//=============================================================================================

	//=============================================================================================
	public boolean equals(Object obj) {
		return uuid.equals(obj);
	}
	//=============================================================================================

}
//*************************************************************************************************
