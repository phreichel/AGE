//*************************************************************************************************
package ode.platform;
//*************************************************************************************************

import java.util.HashMap;
import java.util.Map;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import ode.gui.GUI;

//*************************************************************************************************
public class Platform implements GLEventListener {

	//=============================================================================================
	private Graphics graphics = new Graphics();
	private GLWindow window;
	private String title = "ODE Test Window";
	private boolean maximized = true;
	private boolean fullscreen = false;
	private boolean visible = true;
	//=============================================================================================

	//=============================================================================================
	private GUI gui;
	//=============================================================================================

	//=============================================================================================
	private Map<java.awt.Font, Font> fontMap = new HashMap<>();
	//=============================================================================================
	
	//=============================================================================================
	public void init() {
		GLProfile glProfile = GLProfile.getDefault();
		GLCapabilities glCaps = new GLCapabilities(glProfile);
		window = GLWindow.create(glCaps);
		window.addGLEventListener(this);
		window.setSize(800, 600);
		window.setTitle(title);
		window.setMaximized(maximized, maximized);
		window.setFullscreen(fullscreen);
		window.setVisible(visible);
	}
	//=============================================================================================

	//=============================================================================================
	public String getTitle() {
		return this.title;
	}
	//=============================================================================================	
	
	//=============================================================================================
	public void setTitle(String title) {
		this.title = title;
		if (this.window != null) {
			this.window.setTitle(title);
		}
	}
	//=============================================================================================

	//=============================================================================================
	public boolean isMaximized() {
		return this.maximized;
	}
	//=============================================================================================	
	
	//=============================================================================================
	public void setMaximized(boolean maximized) {
		this.maximized = maximized;
		if (this.window != null) {
			this.window.setMaximized(maximized, maximized);
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	public boolean isFullscreen() {
		return this.fullscreen;
	}
	//=============================================================================================	
	
	//=============================================================================================
	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
		if (this.window != null) {
			this.window.setFullscreen(fullscreen);
		}
	}
	//=============================================================================================

	//=============================================================================================
	public boolean isVisible() {
		return this.visible;
	}
	//=============================================================================================	
	
	//=============================================================================================
	public void setVisible(boolean visible) {
		this.visible = visible;
		if (this.window != null) {
			this.window.setVisible(visible);
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	public void register(GUI gui) {
		this.gui = gui;
	}
	//=============================================================================================

	//=============================================================================================
	public Font allocateFont(String name, float size, FontDecoration decoration) {
		String fontText = String.format("%s-%s-%.0f", name, decoration, size);
		java.awt.Font awtFont = java.awt.Font.decode(fontText);
		Font font = fontMap.get(awtFont);
		if (font == null) {
			font = new Font(awtFont);
			fontMap.put(awtFont, font);
		}
		return font;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void update() {
		window.display();
	}
	//=============================================================================================

	//=============================================================================================
	@Override
	public void init(GLAutoDrawable drawable) {
	}
	//=============================================================================================

	//=============================================================================================
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		gui.resize(width, height);
	}
	//=============================================================================================
	
	//=============================================================================================
	@Override
	public void display(GLAutoDrawable drawable) {
		graphics.init(drawable);
		gui.render(graphics);
	}
	//=============================================================================================

	//=============================================================================================
	@Override
	public void dispose(GLAutoDrawable drawable) {
	}
	//=============================================================================================
	
}
//*************************************************************************************************
