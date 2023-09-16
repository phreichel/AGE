//*************************************************************************************************
package age.event;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import age.log.Log;

//*************************************************************************************************
public class Events {

	//=============================================================================================
	private final Queue<Event> cache  = new LinkedList<>();
	private final List<Event>  inbox  = new ArrayList<>();
	private final List<Event>  outbox = new ArrayList<>();
	private final Map<Type, List<Handler>> handlers = new EnumMap<>(Type.class);
	//=============================================================================================

	//=============================================================================================
	public void assign(Type type, Handler handler) {
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
		event.keyPressed(key, character);
		post(event);
	}
	//=============================================================================================

	//=============================================================================================
	public void postKeyReleased(Key key, char character) {
		Event event = alloc();
		event.keyReleased(key, character);
		post(event);
	}
	//=============================================================================================

	//=============================================================================================
	public void postKeyTyped(Key key, char character) {
		Event event = alloc();
		event.keyTyped(key, character);
		post(event);
	}
	//=============================================================================================

	//=============================================================================================
	public void postPointerEntered(float x, float y) {
		Event event = alloc();
		event.pointerEntered(x, y);
		post(event);
	}
	//=============================================================================================

	//=============================================================================================
	public void postPointerExited(float x, float y) {
		Event event = alloc();
		event.pointerExited(x, y);
		post(event);
	}
	//=============================================================================================

	//=============================================================================================
	public void postPointerMoved(float x, float y) {
		Event event = alloc();
		event.pointerMoved(x, y);
		post(event);
	}
	//=============================================================================================

	//=============================================================================================
	public void postPointerPressed(Button button, int count, float x, float y) {
		Event event = alloc();
		event.pointerPressed(button, count, x, y);
		post(event);
	}
	//=============================================================================================

	//=============================================================================================
	public void postPointerReleased(Button button, int count, float x, float y) {
		Event event = alloc();
		event.pointerReleased(button, count, x, y);
		post(event);
	}
	//=============================================================================================

	//=============================================================================================
	public void postPointerClicked(Button button, int count, float x, float y) {
		Event event = alloc();
		event.pointerClicked(button, count, x, y);
		post(event);
	}
	//=============================================================================================

	//=============================================================================================
	public void postSurfaceResized(float w, float h) {
		Event event = alloc();
		event.surfaceResized(w, h);
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
		Type type = event.type();
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
