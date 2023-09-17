//*************************************************************************************************
package age;
//*************************************************************************************************

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;

//*************************************************************************************************
public class Util {

	//=============================================================================================
	public static final String readFile(String path) {
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

}
//*************************************************************************************************
