//*************************************************************************************************
package age.input;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import age.log.Log;

//*************************************************************************************************
//TODO: add javadoc comments
public class InputEvents {

	//=============================================================================================
	private final Queue<InputEvent> cache  = new LinkedList<>();
	private final List<InputEvent>  inbox  = new ArrayList<>();
	private final List<InputEvent>  outbox = new ArrayList<>();
	private final Set<Key>          keyset = EnumSet.noneOf(Key.class);
	private final Map<InputType, List<InputHandler>> handlers = new EnumMap<>(InputType.class);
	//=============================================================================================

	//=============================================================================================
	public void assign(InputType type, InputHandler handler) {
		List<InputHandler> list = handlers.get(type);
		if (list == null) {
			list = new ArrayList<>(10);
			handlers.put(type, list);
		}
		list.add(handler);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void postKeyPressed(Key key, char character) {
		InputEvent event = alloc();
		keyset.add(key);
		event.keyPressed(key, character, keyset);
		post(event);
	}
	//=============================================================================================

	//=============================================================================================
	public void postKeyReleased(Key key, char character) {
		InputEvent event = alloc();
		keyset.remove(key);
		event.keyReleased(key, character, keyset);
		post(event);
	}
	//=============================================================================================

	//=============================================================================================
	public void postKeyTyped(Key key, char character) {
		InputEvent event = alloc();
		event.keyTyped(key, character, keyset);
		post(event);
	}
	//=============================================================================================

	//=============================================================================================
	public void postPointerEntered(float x, float y) {
		InputEvent event = alloc();
		event.pointerEntered(x, y, keyset);
		post(event);
	}
	//=============================================================================================

	//=============================================================================================
	public void postPointerExited(float x, float y) {
		InputEvent event = alloc();
		event.pointerExited(x, y, keyset);
		post(event);
	}
	//=============================================================================================

	//=============================================================================================
	public void postPointerMoved(float x, float y) {
		InputEvent event = alloc();
		event.pointerMoved(x, y, keyset);
		post(event);
	}
	//=============================================================================================

	//=============================================================================================
	public void postPointerPressed(Button button, int count, float x, float y) {
		InputEvent event = alloc();
		event.pointerPressed(button, count, x, y, keyset);
		post(event);
	}
	//=============================================================================================

	//=============================================================================================
	public void postPointerReleased(Button button, int count, float x, float y) {
		InputEvent event = alloc();
		event.pointerReleased(button, count, x, y, keyset);
		post(event);
	}
	//=============================================================================================

	//=============================================================================================
	public void postPointerClicked(Button button, int count, float x, float y) {
		InputEvent event = alloc();
		event.pointerClicked(button, count, x, y, keyset);
		post(event);
	}
	//=============================================================================================

	//=============================================================================================
	public void postSurfaceResized(float w, float h) {
		InputEvent event = alloc();
		event.surfaceResized(w, h, keyset);
		post(event);
	}
	//=============================================================================================

	//=============================================================================================
	public void postSurfaceCloseRequest() {
		InputEvent event = alloc();
		event.surfaceCloseRequest();
		post(event);
	}
	//=============================================================================================

	//=============================================================================================
	public void postTaskCommand(String command) {
		InputEvent event = alloc();
		event.taskCommand(command, keyset);
		post(event);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void update() {
		synchronized (inbox) {
			outbox.addAll(inbox);
			inbox.clear();
		}
		for (InputEvent event : outbox) {
			handle(event);
		}
		outbox.clear();
	}
	//=============================================================================================

	//=============================================================================================
	private void handle(InputEvent event) {
		InputType type = event.type();
		Log.debug(
			"Handle Event %s, {%s:%s} {%s,%s,%s,%s} {%s:%s}",
			type,
			event.key(),
			event.character(),
			event.x(),
			event.y(),
			event.button(),
			event.count(),
			event.width(),
			event.height());
		List<InputHandler> list = handlers.get(type);
		if (list != null) {
			for (InputHandler handler : list) {
				handler.handle(event);
			}
		}
		free(event);
	}
	//=============================================================================================

	//=============================================================================================
	private void post(InputEvent event) {
		synchronized (inbox) {
			inbox.add(event);
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	private InputEvent alloc() {
		InputEvent event = cache.poll();
		if (event == null) {
			event = new InputEvent();
		}
		return event;
	}
	//=============================================================================================

	//=============================================================================================
	private void free(InputEvent event) {
		event.clear();
		cache.offer(event);
	}
	//=============================================================================================
	
}
//*************************************************************************************************
