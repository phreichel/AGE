//*************************************************************************************************
package age.gui;
//*************************************************************************************************

//*************************************************************************************************
public class Window extends Widget {

	//=============================================================================================
	private Widget title = new Widget();
	private Widget size  = new Widget();
	private Widget close = new Widget();
	private Widget page  = new Widget();
	//=============================================================================================

	//=============================================================================================
	public Window(Flag ... flags) {
		super(flags);
		flag(Flag.FRAME);
		construct();
	}
	//=============================================================================================
	
	//=============================================================================================
	private void construct() {
		
		final float W = 800;
		final float H = 600;
		final float V = 20;
		final float G = 5;

		title("AGE Window");
		
		dimension(W, H);
		
		title.flag(Flag.TITLE);
		title.position(G, G);
		title.dimension(W-V-3*G, V);
		title.dock(0, 1, 0, 0);
		
		close.flag(Flag.BUTTON);
		close.image("close");
		close.position(W-V-G, G);
		close.dimension(V, V);
		close.dock(1, 1, 0, 0);
		
		page.flag(Flag.CANVAS);
		page.position(G, V+2*G);
		page.dimension(W-2*G, H-V-3*G);
		page.dock(0, 1, 0, 1);

		size.flag(Flag.BUTTON);
		size.image("size");
		size.position(W-V-G, H-V-G);
		size.dimension(V, V);
		size.dock(1, 1, 1, 1);
		
		this.add(title);
		this.add(close);
		this.add(page);
		this.add(size);
		
	}
	//=============================================================================================

	//=============================================================================================
	public String title() {
		return this.title.text();
	}
	//=============================================================================================
	
	//=============================================================================================
	public void title(String title) {
		this.title.text(title);
	}
	//=============================================================================================

	//=============================================================================================
	public Widget getPage() {
		return page;
	}
	//=============================================================================================
	
}
//*************************************************************************************************
