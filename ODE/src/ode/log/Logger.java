//*************************************************************************************************
package ode.log;
//*************************************************************************************************

import java.util.Calendar;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

//*************************************************************************************************
public class Logger {

	//=============================================================================================
	private static final Map<String, Logger> map = new HashMap<>();
	//=============================================================================================

	//=============================================================================================
	public static Logger get(String instance) {		
		Logger logger = map.get(instance);
		if (logger == null) {
			if (instance.equals("default")) {
				logger = new Logger();
				map.put(instance, logger);
			} else {
				Logger parent = get("default");
				if (parent == null) {
					parent = new Logger();
					map.put("default", parent);
				}
				logger = new Logger(parent);
				map.put(instance, logger);
			}
		}
		return logger;
	}
	//=============================================================================================
	
	//=============================================================================================
	public static void log(Level level, String message, Object ... params) {
		log("default", level, message, params);
	}
	//=============================================================================================

	//=============================================================================================
	public static void log(String instance, Level level, String message, Object ... params) {
		Logger logger = get(instance);
		if (logger != null) {
			logger.write(level, message, params);
		}
	}
	//=============================================================================================

	//=============================================================================================
	private Logger parent = null;
	private Set<Level> levels = null;
	private String format = null;
	//=============================================================================================

	//=============================================================================================
	private Logger() {
		this.parent = null;
		this.levels = EnumSet.allOf(Level.class);
		this.format = "%1$td.%1$tm.%1$ty %1$tH:%1$tM:%1$tS.%1$tN - %2$s: %3$s";
	}
	//=============================================================================================
	
	//=============================================================================================
	private Logger(Logger parent) {
		this.parent = parent;
		this.format = null;
		this.levels = null;
	}
	//=============================================================================================
	
	//=============================================================================================
	private void write(Level level, String message, Object ... params) {
		if (!levels.contains(level)) return;
		Calendar now = Calendar.getInstance();
		String output = String.format(message, params);
		String tagged = String.format(format, now, level, output);
		System.out.println(tagged);
	}
	//=============================================================================================

	//=============================================================================================
	public void enable(Level ... levels) {
		this.levels.addAll(List.of(levels));
	}
	//=============================================================================================

	//=============================================================================================
	public void disable(Level ... levels) {
		this.levels.removeAll(List.of(levels));
	}
	//=============================================================================================

	//=============================================================================================
	public String format() {
		return format;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void format(String format) {
		this.format = format;
	}
	//=============================================================================================
	
}
//*************************************************************************************************
