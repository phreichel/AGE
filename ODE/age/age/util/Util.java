/*
 * Commit: c57f32894333f59b57fe81ea6bcc2d1889088a12
 * Date: 2023-09-22 22:13:31+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 * Commit: 023dbd552821f0d803d760c22a9f1193d1bf58b7
 * Date: 2023-09-22 21:50:52+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 * Commit: 755b11dea54fbe41e8bd4279fa3fa1d07e6fc2ea
 * Date: 2023-09-22 20:09:27+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 */

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
