/*
 * Commit: 1d3937202c635d21dd0780fb7222445393a7b75a
 * Date: 2023-10-02 23:28:58+02:00
 * Author: Philip Reichel
 * Comment: Skeleton Reader hopefully working
 *
 * Commit: 755b11dea54fbe41e8bd4279fa3fa1d07e6fc2ea
 * Date: 2023-09-22 20:09:27+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 * Commit: 0843481a778d3a257ef3379baf70327d16287db0
 * Date: 2023-09-21 20:37:18+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 * Commit: 4ba52177b03c84982e184472f4e51a9157e9a67f
 * Date: 2023-09-21 20:15:06+02:00
 * Author: Philip Reichel
 * Comment: Cleaning all up
 *
 * Commit: ea80018edeb513be67842adc81cba9128971f0c1
 * Date: 2023-09-21 11:36:51+02:00
 * Author: pre7618
 * Comment: Renamed the Exception
 *
 * Commit: a6ff64f7ddf561fda51d80cf514edfa3e03c3fbf
 * Date: 2023-09-19 23:41:44+02:00
 * Author: Philip Reichel
 * Comment: Further comments
 *
 * Commit: efba03d014f9f8690fc57eb3949bb36f7ed2e269
 * Date: 2023-09-19 07:28:45+02:00
 * Author: Philip Reichel
 * Comment: todo tasks
 *
 * Commit: fe16e6ad57d7fe295d39edc19c9c2fc0d70fa0a1
 * Date: 2023-09-16 13:05:47+02:00
 * Author: Philip Reichel
 * Comment: Changed Handling a bit
Extended Logging
 *
 */

//*************************************************************************************************
package age.log;
//*************************************************************************************************

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Calendar;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;

import age.util.X;

//*************************************************************************************************
public class Log {

	//=============================================================================================
	private static final Map<String, Log> map = new HashMap<>();
	//=============================================================================================

	//=============================================================================================
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
			throw new X(e);
		}
	}
	//=============================================================================================
	
	//=============================================================================================
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
	//=============================================================================================
	
	//=============================================================================================
	public static void log(Level level, String message, Object ... params) {
		log("default", level, message, params);
	}
	//=============================================================================================

	//=============================================================================================
	public static void info(String message, Object ... params) {
		log("default", Level.INFO, message, params);
	}
	//=============================================================================================

	//=============================================================================================
	public static void warn(String message, Object ... params) {
		log("default", Level.WARNING, message, params);
	}
	//=============================================================================================

	//=============================================================================================
	public static void error(String message, Object ... params) {
		log("default", Level.ERROR, message, params);
	}
	//=============================================================================================

	//=============================================================================================
	public static void debug(String message, Object ... params) {
		log("default", Level.DEBUG, message, params);
	}
	//=============================================================================================
	
	//=============================================================================================
	public static void log(String instance, Level level, String message, Object ... params) {
		Log log = get(instance);
		if (log != null) {
			log.write(level, message, params);
		}
	}
	//=============================================================================================

	//=============================================================================================
	public static void infoInst(String instance, String message, Object ... params) {
		Log log = get(instance);
		if (log != null) {
			log.write(Level.INFO, message, params);
		}
	}
	//=============================================================================================

	//=============================================================================================
	public static void warnInst(String instance, String message, Object ... params) {
		Log log = get(instance);
		if (log != null) {
			log.write(Level.WARNING, message, params);
		}
	}
	//=============================================================================================

	//=============================================================================================
	public static void errorInst(String instance, String message, Object ... params) {
		Log log = get(instance);
		if (log != null) {
			log.write(Level.ERROR, message, params);
		}
	}
	//=============================================================================================

	//=============================================================================================
	public static void debugInst(String instance, String message, Object ... params) {
		Log log = get(instance);
		if (log != null) {
			log.write(Level.DEBUG, message, params);
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	private boolean trace = false;
	private Set<Level> levels = null;
	private String format = null;
	private Writer writer = null;
	//=============================================================================================

	//=============================================================================================
	private Log() {
		this.trace = false;
		this.levels = EnumSet.allOf(Level.class);
		this.format = "%1$td.%1$tm.%1$ty %1$tH:%1$tM:%1$tS.%1$tN - %2$s: %3$s%n";
		this.writer = new OutputStreamWriter(System.out);
	}
	//=============================================================================================

	//=============================================================================================
	private Log(Log parent) {
		this.trace = parent.trace;
		this.format = parent.format;
		this.levels = EnumSet.copyOf(parent.levels);
		this.writer = parent.writer;
	}
	//=============================================================================================
	
	//=============================================================================================
	private void write(Level level, String message, Object ... params) {
		try {
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
			writer.append(tagged);
			writer.flush();
		} catch (Exception e) {
			throw new X(e);
		}
	}
	//=============================================================================================

	//=============================================================================================
	public void clear() {
		this.levels.clear();
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
	public boolean trace() {
		return trace;
	}
	//=============================================================================================

	//=============================================================================================
	public void trace(boolean trace) {
		this.trace = trace;
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

	//=============================================================================================
	public void output(String path) {
		try {
			File file  = new File(path);
			this.writer = new FileWriter(file, true);
		} catch (Exception e) {
			throw new X(e);
		}
	}
	//=============================================================================================
	
}
//*************************************************************************************************
