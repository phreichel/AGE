//*************************************************************************************************
package age;
//*************************************************************************************************

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import com.fasterxml.jackson.databind.ObjectMapper;

//*************************************************************************************************
/**
 * The AGE Utility class.
 */
public class Util {

	//=============================================================================================
	private static final ObjectMapper objectMapper = new ObjectMapper();
	//=============================================================================================
	
	//=============================================================================================
	/**
	 * Static utility method to load and return text read from a file located by path
	 * @param path The file path String of the file to be read
	 * @return The file contents as a text String
	 */
	public static final String readTextFile(String path) {
		try {
			File file = new File(path);
			StringBuffer buffer = new StringBuffer((int) file.length());
			Reader reader = new BufferedReader(new FileReader(file));
			int res = reader.read();
			while (res != -1) {
				buffer.append((char) res);
				res = reader.read();
			}
			reader.close();
			return buffer.toString();
		} catch (Exception e) {
			throw new AGEException(e);
		}
	}
	//=============================================================================================

	//=============================================================================================
	public static final <C> C readJSonFile(String path, Class<C> cls) {
		try {
			File file = new File(path);
			C object = objectMapper.readValue(file, cls);
			return object;
		} catch (Exception e) {
			throw new AGEException(e);
		}
	}
	//=============================================================================================

}
//*************************************************************************************************
