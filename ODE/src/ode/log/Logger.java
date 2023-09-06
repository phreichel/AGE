//*************************************************************************************************
package ode.log;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

//*************************************************************************************************

//*************************************************************************************************
public class Logger {

	//=============================================================================================
	private static final Map<String, Logger> map = new HashMap<>();
	//=============================================================================================

	//=============================================================================================
	private static Logger get(String instance) {
		Logger logger = map.get(instance);
		if (logger == null) {
			logger = new Logger();
			map.put(instance, logger);
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
	private void write(Level level, String message, Object ... params) {
		Calendar now = Calendar.getInstance();
		String output = String.format(message, params);
		String tagged = String.format("%1$td.%1$tm.%1$ty %1$tH:%1$tM:%1$tS.%1$tN - %2$s: %3$s", now, level, output);
		System.out.println(tagged);
	}
	//=============================================================================================
	
}
//*************************************************************************************************

