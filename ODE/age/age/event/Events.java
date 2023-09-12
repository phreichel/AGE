//*************************************************************************************************
package age.event;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

//*************************************************************************************************
public class Events {

	//=============================================================================================
	private final Queue<Event> cache  = new LinkedList<>();
	private final List<Event>  events = new ArrayList<>();
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
	public void update() {
		for (Event event : events) {
			handle(event);
		}
		events.clear();
	}
	//=============================================================================================

	//=============================================================================================
	private void handle(Event event) {
		Type type = event.type();
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
		events.add(event);
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
