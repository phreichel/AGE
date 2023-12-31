//*************************************************************************************************
package age.port.jogl;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.jogamp.nativewindow.WindowClosingProtocol.WindowClosingMode;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import age.input.Events;
import age.log.Log;
import age.port.Port;
import age.port.Renderable;

//*************************************************************************************************
public class JOGLPort implements Port {

	//=============================================================================================
	private GLWindow window = null;
	private JOGLEventListener eventListener = null;
	private JOGLRenderListener renderListener = null;
	//=============================================================================================

	//=============================================================================================
	private final List<Renderable> renderables = new ArrayList<>();
	private final List<Renderable> renderables_ro = Collections.unmodifiableList(renderables);
	//=============================================================================================

	//=============================================================================================
	public void assign(Events events) {
		eventListener.assign(events);
	}
	//=============================================================================================

	//=============================================================================================
	public void create() {
		Log.info("Creating JOGL Port");
		GLProfile glProfile = GLProfile.getDefault();
		GLCapabilities glCapabilities = new GLCapabilities(glProfile);
		window = GLWindow.create(glCapabilities);
		window.setDefaultCloseOperation(WindowClosingMode.DO_NOTHING_ON_CLOSE);
		window.setTitle("AGE - A Game Engine - 0.01");
		window.setTopLevelPosition(0, 0);
		window.setTopLevelSize(800, 600);
		eventListener = new JOGLEventListener();
		window.addKeyListener(eventListener);
		window.addMouseListener(eventListener);
		window.addWindowListener(eventListener);
		renderListener = new JOGLRenderListener(this);
		window.addGLEventListener(renderListener);
	}
	//=============================================================================================
	
	//=============================================================================================
	public String title() {
		return window.getTitle();
	}
	//=============================================================================================

	//=============================================================================================
	public void title(String title) {
		window.setTitle(title);
	}
	//=============================================================================================
	
	//=============================================================================================
	public boolean maximized() {
		return window.isMaximizedHorz() && window.isMaximizedVert();
	}
	//=============================================================================================

	//=============================================================================================
	public void maximized(boolean maximized) {
		window.setMaximized(maximized, maximized);
	}
	//=============================================================================================
	
	//=============================================================================================
	public boolean fullscreen() {
		return window.isFullscreen();
	}
	//=============================================================================================

	//=============================================================================================
	public void fullscreen(boolean fullscreen) {
		window.setFullscreen(fullscreen);
		/*
		 * This did fix some problems that happen when the window is made visible in FullScreen initially.
		 * So, do not do that. It messes up things. Always only go to fullscreen when window is visible.
		if (!fullscreen) {
			if (window.getY() < 30) {
				window.setPosition(window.getX(), 30);
			}
		}
		*/
	}
	//=============================================================================================

	//=============================================================================================
	public boolean visible() {
		return window.isVisible();
	}
	//=============================================================================================

	//=============================================================================================
	public void visible(boolean visible) {
		window.setVisible(visible);
		// adjustment hack
		if (!window.isFullscreen()) {
			int x = window.getX();
			int y = window.getY();
			window.setTopLevelPosition(x, y);
		}
	}
	//=============================================================================================

	//=============================================================================================
	public float x() {
		return window.getX();
	}
	//=============================================================================================

	//=============================================================================================
	public float y() {
		return window.getY();
	}
	//=============================================================================================

	//=============================================================================================
	public void position(float x, float y) {
		window.setTopLevelPosition(
				(int) x,
				(int) y);
	}
	//=============================================================================================
	
	//=============================================================================================
	public float width() {
		return window.getWidth();
	}
	//=============================================================================================

	//=============================================================================================
	public float height() {
		return window.getHeight();
	}
	//=============================================================================================

	//=============================================================================================
	public void size(float width, float height) {
		window.setTopLevelSize(
			(int) width,
			(int) height);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void add(Renderable renderable) {
		renderables.add(renderable);
	}
	//=============================================================================================

	//=============================================================================================
	public List<Renderable> get() {
		return renderables_ro;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void render() {
		window.display();
	}
	//=============================================================================================
	
}
//*************************************************************************************************
