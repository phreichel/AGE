/*
 * Commit: 363b75a18db4122bb21de7cf1091c0bd6434b79b
 * Date: 2023-09-23 18:26:28+02:00
 * Author: Philip Reichel
 * Comment: Finished Switch to new Event Handling
Added Pointer Wheel Input
 *
 * Commit: 8e1aad78b74edd61cd0e139ade57196ed44c219a
 * Date: 2023-09-22 22:14:40+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 */

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
import javax.vecmath.Vector3f;

//*************************************************************************************************
public class Events {

	//=============================================================================================
	private final Queue<Event> cache  = new LinkedList<>();
	private final List<Event>  inbox  = new ArrayList<>();
	private final List<Event>  outbox = new ArrayList<>();
	private final Set<Key>          keyset = EnumSet.noneOf(Key.class);
	private final Map<InputType, List<Handler>> handlers = new EnumMap<>(InputType.class);
	//=============================================================================================

	//=============================================================================================
	public void assign(InputType type, Handler handler) {
		List<Handler> list = handlers.get(type);
		if (list == null) {
			list = new ArrayList<>(10);
			handlers.put(type, list);
		}
		list.add(handler);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void postKeyPressed(Key key, char character) {
		Event event = alloc();
		keyset.add(key);
		event.keyPressed(key, character, keyset);
		post(event);
	}
	//=============================================================================================

	//=============================================================================================
	public void postKeyReleased(Key key, char character) {
		Event event = alloc();
		keyset.remove(key);
		event.keyReleased(key, character, keyset);
		post(event);
	}
	//=============================================================================================

	//=============================================================================================
	public void postKeyTyped(Key key, char character) {
		Event event = alloc();
		event.keyTyped(key, character, keyset);
		post(event);
	}
	//=============================================================================================

	//=============================================================================================
	public void postPointerEntered(float x, float y) {
		Event event = alloc();
		event.pointerEntered(x, y, keyset);
		post(event);
	}
	//=============================================================================================

	//=============================================================================================
	public void postPointerExited(float x, float y) {
		Event event = alloc();
		event.pointerExited(x, y, keyset);
		post(event);
	}
	//=============================================================================================

	//=============================================================================================
	public void postPointerMoved(float x, float y) {
		Event event = alloc();
		event.pointerMoved(x, y, keyset);
		post(event);
	}
	//=============================================================================================

	//=============================================================================================
	public void postPointerWheel(float x, float y, Vector3f wheels) {
		Event event = alloc();
		event.pointerWheel(x, y, keyset, wheels);
		post(event);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void postPointerPressed(Button button, int count, float x, float y) {
		Event event = alloc();
		event.pointerPressed(button, count, x, y, keyset);
		post(event);
	}
	//=============================================================================================

	//=============================================================================================
	public void postPointerReleased(Button button, int count, float x, float y) {
		Event event = alloc();
		event.pointerReleased(button, count, x, y, keyset);
		post(event);
	}
	//=============================================================================================

	//=============================================================================================
	public void postPointerClicked(Button button, int count, float x, float y) {
		Event event = alloc();
		event.pointerClicked(button, count, x, y, keyset);
		post(event);
	}
	//=============================================================================================

	//=============================================================================================
	public void postSurfaceResized(float w, float h) {
		Event event = alloc();
		event.surfaceResized(w, h, keyset);
		post(event);
	}
	//=============================================================================================

	//=============================================================================================
	public void postSurfaceCloseRequest() {
		Event event = alloc();
		event.surfaceCloseRequest();
		post(event);
	}
	//=============================================================================================

	//=============================================================================================
	public void postTaskCommand(String command) {
		Event event = alloc();
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
		for (Event event : outbox) {
			handle(event);
		}
		outbox.clear();
	}
	//=============================================================================================

	//=============================================================================================
	private void handle(Event event) {
		InputType type = event.type();
		List<Handler> list = handlers.get(type);
		if (list != null) {
			for (Handler handler : list) {
				handler.handle(event);
			}
		}
		free(event);
	}
	//=============================================================================================

	//=============================================================================================
	private void post(Event event) {
		synchronized (inbox) {
			inbox.add(event);
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	private Event alloc() {
		Event event = cache.poll();
		if (event == null) {
			event = new Event();
		}
		return event;
	}
	//=============================================================================================

	//=============================================================================================
	private void free(Event event) {
		event.clear();
		cache.offer(event);
	}
	//=============================================================================================
	
}
//*************************************************************************************************
