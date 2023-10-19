/*
 * Commit: 7a0e67a478c9bc474bec2860dbaccef928526344
 * Date: 2023-10-03 17:21:16+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 * Commit: 85cb5b7a1fbf0119b4e9bcb753640ca543e9e9a8
 * Date: 2023-09-22 22:29:04+02:00
 * Author: Philip Reichel
 * Comment: Adding Logs
 *
 * Commit: 8e1aad78b74edd61cd0e139ade57196ed44c219a
 * Date: 2023-09-22 22:14:40+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 * Commit: 023dbd552821f0d803d760c22a9f1193d1bf58b7
 * Date: 2023-09-22 21:50:52+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 * Commit: b669be6f6568945c41bf44a9085f269152f4a3ae
 * Date: 2023-09-22 20:53:50+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 * Commit: 4ba52177b03c84982e184472f4e51a9157e9a67f
 * Date: 2023-09-21 20:15:06+02:00
 * Author: Philip Reichel
 * Comment: Cleaning all up
 *
 * Commit: dd56eccbb9b27eab45e556efde8d3bb7d0f4ce97
 * Date: 2023-09-20 17:16:57+02:00
 * Author: pre7618
 * Comment: Renamings
 *
 * Commit: efba03d014f9f8690fc57eb3949bb36f7ed2e269
 * Date: 2023-09-19 07:28:45+02:00
 * Author: Philip Reichel
 * Comment: todo tasks
 *
 * Commit: 0a0626956b5c49e78d12757c077c1f9694a9dcc2
 * Date: 2023-09-16 21:35:52+02:00
 * Author: Philip Reichel
 * Comment: System Menu and Improved Messaging
 *
 * Commit: 275f07861ea5f95dd90f184d1311a0a5a8cd510d
 * Date: 2023-09-14 17:55:15+02:00
 * Author: pre7618
 * Comment: GUI in progress
 *
 * Commit: 343fe7d3b3a248993aa01b34632a93f7d5c8e09c
 * Date: 2023-09-12 07:49:14+02:00
 * Author: Philip Reichel
 * Comment: Relocation and new Design Merger
 *
 */

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
