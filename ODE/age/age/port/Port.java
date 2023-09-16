//*************************************************************************************************
package age.port;
//*************************************************************************************************

import age.event.Events;

//*************************************************************************************************
public interface Port {

	//=============================================================================================
	public void assign(Events events);
	//=============================================================================================
	
	//=============================================================================================
	public String title();
	public void title(String title);
	//=============================================================================================
	
	//=============================================================================================
	public boolean maximized();
	public void maximized(boolean maximized);
	//=============================================================================================
	
	//=============================================================================================
	public boolean fullscreen();
	public void fullscreen(boolean fullscreen);
	//=============================================================================================

	//=============================================================================================
	public boolean visible();
	public void visible(boolean visible);
	//=============================================================================================

	//=============================================================================================
	public float width();
	public float height();
	public void size(float width, float height);
	//=============================================================================================
	
	//=============================================================================================
	public void add(Renderable renderable);
	//=============================================================================================
	
	//=============================================================================================
	public void create();
	public void render();
	//=============================================================================================
	
}
//*************************************************************************************************
