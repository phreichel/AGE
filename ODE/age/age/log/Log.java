//*************************************************************************************************
package age.log;
//*************************************************************************************************

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.Calendar;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;
import age.AGEException;

/**************************************************************************************************
 * Class that provides configurable Logging capabilities
 */
public class Log {

	/**********************************************************************************************
	 * Map that contains named log instances that can be configured independently
	 */
	private static final Map<String, Log> map = new HashMap<>();

	/**********************************************************************************************
	 * This method reads a configuration file to preconfigure the logging system.
	 * @param path The file path to the configuration file
	 */
	public static void configure(String path) {
		try {
			File file = new File(path);
			Reader fileReader = new FileReader(file);
			ResourceBundle bundle = new PropertyResourceBundle(fileReader);
			for (String key : bundle.keySet()) {
				String value = bundle.getString(key);
				String[] parts = key.split("\\.");
				String instance = parts[0];
				String property = parts[1];
				Log log = Log.get(instance);
				if (property.equals("trace")) {
					boolean b = Boolean.parseBoolean(value);
					log.trace(b);
				} else if (property.equals("format")) {
					log.format(value);
				} else if (property.equals("levels")) {
					log.clear();
					String[] levels = value.split("\\s*,\\s*");
					for (String l : levels) {
						Level level = Level.valueOf(l);
						log.enable(level);
					}
				}
			}
			fileReader.close();
		} catch (Exception e) {
			throw new AGEException(e);
		}
	}
	//=============================================================================================
	
	/**********************************************************************************************
	 * This method returns a named log instance and creates one if not existing already.
	 * @param instance the name of the log instance to return/create. a null String returns an instance named "default" 
	 * @return The Log instance
	 */
	public static Log get(String instance) {		
		Log log = map.get(instance);
		if (log == null) {
			if (instance.equals("default")) {
				log = new Log();
				map.put(instance, log);
			} else {
				Log parent = get("default");
				if (parent == null) {
					parent = new Log();
					map.put("default", parent);
				}
				log = new Log(parent);
				map.put(instance, log);
			}
		}
		return log;
	}
	
	/**********************************************************************************************
	 * This method allows to write a log message for the "default" Log instance 
	 * @param level the log level to log to
	 * @param message the log message which may contain String formats
	 * @param params the format parameters for the message String
	 */
	public static void log(Level level, String message, Object ... params) {
		log("default", level, message, params);
	}

	/**********************************************************************************************
	 * This method allows to write an info message for the "default" Log instance 
	 * @param message the log message which may contain String formats
	 * @param params the format parameters for the message String
	 */
	public static void info(String message, Object ... params) {
		log("default", Level.INFO, message, params);
	}

	/**********************************************************************************************
	 * This method allows to write a warning message for the "default" Log instance 
	 * @param message the log message which may contain String formats
	 * @param params the format parameters for the message String
	 */
	public static void warn(String message, Object ... params) {
		log("default", Level.WARNING, message, params);
	}

	/**********************************************************************************************
	 * This method allows to write an error message for the "default" Log instance 
	 * @param message the log message which may contain String formats
	 * @param params the format parameters for the message String
	 */
	public static void error(String message, Object ... params) {
		log("default", Level.ERROR, message, params);
	}

	/**********************************************************************************************
	 * This method allows to write a debug message for the "default" Log instance 
	 * @param message the log message which may contain String formats
	 * @param params the format parameters for the message String
	 */
	public static void debug(String message, Object ... params) {
		log("default", Level.DEBUG, message, params);
	}
	
	/**********************************************************************************************
	 * This method allows to write a log message for a named Log instance 
	 * @param instance the name of the log instance 
	 * @param level the log level to log to
	 * @param message the log message which may contain String formats
	 * @param params the format parameters for the message String
	 */
	public static void log(String instance, Level level, String message, Object ... params) {
		Log log = get(instance);
		if (log != null) {
			log.write(level, message, params);
		}
	}

	/**********************************************************************************************
	 * This method allows to write an info message for a named Log instance 
	 * @param instance the name of the log instance 
	 * @param message the log message which may contain String formats
	 * @param params the format parameters for the message String
	 */
	public static void info(String instance, String message, Object ... params) {
		Log log = get(instance);
		if (log != null) {
			log.write(Level.INFO, message, params);
		}
	}

	/**********************************************************************************************
	 * This method allows to write a warning message for a named Log instance 
	 * @param instance the name of the log instance 
	 * @param message the log message which may contain String formats
	 * @param params the format parameters for the message String
	 */
	public static void warn(String instance, String message, Object ... params) {
		Log log = get(instance);
		if (log != null) {
			log.write(Level.WARNING, message, params);
		}
	}

	/**********************************************************************************************
	 * This method allows to write an error message for a named Log instance 
	 * @param instance the name of the log instance 
	 * @param message the log message which may contain String formats
	 * @param params the format parameters for the message String
	 */
	public static void error(String instance, String message, Object ... params) {
		Log log = get(instance);
		if (log != null) {
			log.write(Level.ERROR, message, params);
		}
	}

	/**********************************************************************************************
	 * This method allows to write a debug message for a named Log instance 
	 * @param instance the name of the log instance 
	 * @param message the log message which may contain String formats
	 * @param params the format parameters for the message String
	 */
	public static void debug(String instance, String message, Object ... params) {
		Log log = get(instance);
		if (log != null) {
			log.write(Level.DEBUG, message, params);
		}
	}
	
	/**********************************************************************************************
	 * flag if stack trace output is enabled or not for this Log instance 
	 */
	private boolean trace = false;
	
	/**********************************************************************************************
	 * a set of Level literals that are enabled to print out log messages
	 */
	private Set<Level> levels = null;

	/**********************************************************************************************
	 * the overall log entry format String
	 */
	private String format = null;

	
	/**********************************************************************************************
	 * Constructor that sets all configurable Log settings to default. 
	 */
	private Log() {
		this.trace = false;
		this.levels = EnumSet.allOf(Level.class);
		this.format = "%1$td.%1$tm.%1$ty %1$tH:%1$tM:%1$tS.%1$tN - %2$s: %3$s";
	}

	/**********************************************************************************************
	 * Class internal private copy constructor
	 * @param parent the parent Log from which the settings will be copied
	 */
	private Log(Log parent) {
		this.trace = parent.trace;
		this.format = parent.format;
		this.levels = EnumSet.copyOf(parent.levels);
	}
	
	/**********************************************************************************************
	 * internal method to write a log message considering all configuration settings
	 * @param level the log level
	 * @param message the log message with possible format entries
	 * @param params the parameters for the formatted message String
	 */
	private void write(Level level, String message, Object ... params) {
		if (!levels.contains(level)) return;
		Calendar now = Calendar.getInstance();
		String output = String.format(message, params);
		if (trace) {
			StackTraceElement e = Thread.currentThread().getStackTrace()[4];
			String location = String.format(
				"%s (%s): %s.%s%n	",
				e.getFileName(),
				e.getLineNumber(),
				e.getClassName(),
				e.getMethodName());
			output = location + output;
		}
		String tagged = String.format(format, now, level, output);
		System.out.println(tagged);
	}

	/**********************************************************************************************
	 * method to clear all set log levels 
	 */
	public void clear() {
		this.levels.clear();
	}
	
	/**********************************************************************************************
	 * method to enable all log levels passed in 
	 * @param levels the log levels passed in
	 */
	public void enable(Level ... levels) {
		this.levels.addAll(List.of(levels));
	}

	/**********************************************************************************************
	 * method to disable all log levels passed in 
	 * @param levels the log levels passed in
	 */
	public void disable(Level ... levels) {
		this.levels.removeAll(List.of(levels));
	}

	/**********************************************************************************************
	 * property method to return the current state of the trace flag 
	 * @return the current state of the trace flag
	 */
	public boolean trace() {
		return trace;
	}

	/**********************************************************************************************
	 * property method to set the current state of the trace flag 
	 * @param trace the state to set
	 */
	public void trace(boolean trace) {
		this.trace = trace;
	}
	
	/**********************************************************************************************
	 * property method to return the current format String for log entries 
	 * @return the current format String
	 */
	public String format() {
		return format;
	}
	
	/**********************************************************************************************
	 * property method to set the current format String for log entries 
	 * @param format the future format String
	 */
	public void format(String format) {
		this.format = format;
	}
	//=============================================================================================
	
}
//*************************************************************************************************
