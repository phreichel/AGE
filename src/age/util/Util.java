//*************************************************************************************************
package age.util;
//*************************************************************************************************

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import com.fasterxml.jackson.databind.ObjectMapper;

//*************************************************************************************************
public class Util {

	//=============================================================================================
	private static final ObjectMapper objectMapper = new ObjectMapper();
	//=============================================================================================
	
	//=============================================================================================
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
			throw new X(e);
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
			throw new X(e);
		}
	}
	//=============================================================================================


}
//*************************************************************************************************
