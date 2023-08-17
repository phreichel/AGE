//*************************************************************************************************
package ode.event;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

//*************************************************************************************************
public class Events {

	//=============================================================================================
	private Queue<Event> ibox = new LinkedList<>();
	private Queue<Event> obox = new LinkedList<>();
	//=============================================================================================

	//=============================================================================================
	private Map<Integer, List<Handler>> handlers = new HashMap<>();
	//=============================================================================================

	//=============================================================================================
	private Queue<Event> pool = new LinkedList<>();
	//=============================================================================================
	
	//=============================================================================================
	public void register(int type, Handler handler) {
		List<Handler> list = handlers.get(type);
		if (list == null) {
			list = new ArrayList<>();
			handlers.put(type, list);
		}
		list.add(handler);
	}
	//=============================================================================================

	//=============================================================================================
	public synchronized void post(int type, Object ... elements) {
		Event e = pool.poll();
		if (e == null) e = new Event();
		e.set(type, elements);
		ibox.offer(e);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void update() {
		synchronized (this) {
			Queue<Event> tmp = obox;
			obox = ibox;
			ibox = tmp;
		}
		Event e = obox.poll();
		while (e != null) {
			update(e);
			e = obox.poll();
		}
	}
	//=============================================================================================

	//=============================================================================================
	public void update(long frame) {
		long mark = System.nanoTime();
		synchronized (this) {
			Queue<Event> tmp = obox;
			obox = ibox;
			ibox = tmp;	
		}
		Event e = obox.poll();
		while (e != null) {
			update(e);
			long delta = System.nanoTime() - mark;
			if (delta >= frame) break;
			e = obox.poll();
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	private void update(Event e) {
	
		List<Handler> list = handlers.get(e.type);
		if (list != null) {
			for (Handler handler : list) {
				handler.handle(e);
			}
		}
		e.clear();
		pool.add(e);

	}
	//=============================================================================================
	
}
//*************************************************************************************************