//*************************************************************************************************
package age.util;
//*************************************************************************************************

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import javax.vecmath.Matrix3f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
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

	//=============================================================================================
	public static float[] toGLMatrix(Matrix4f m, float[] buffer) {
		buffer[ 0] = m.m00;
		buffer[ 1] = m.m10;
		buffer[ 2] = m.m20;
		buffer[ 3] = m.m30;
		buffer[ 4] = m.m01;
		buffer[ 5] = m.m11;
		buffer[ 6] = m.m21;
		buffer[ 7] = m.m31;
		buffer[ 8] = m.m02;
		buffer[ 9] = m.m12;
		buffer[10] = m.m22;
		buffer[11] = m.m32;
		buffer[12] = m.m03;
		buffer[13] = m.m13;
		buffer[14] = m.m23;
		buffer[15] = m.m33;
		return buffer;
	}
	//=============================================================================================

	//=============================================================================================
	public static Matrix4f fromGLMatrix(Matrix4f m, float[] buffer) {
		m.m00 = buffer[ 0];
		m.m10 = buffer[ 1];
		m.m20 = buffer[ 2];
		m.m30 = buffer[ 3];
		m.m01 = buffer[ 4];
		m.m11 = buffer[ 5];
		m.m21 = buffer[ 6];
		m.m31 = buffer[ 7];
		m.m02 = buffer[ 8];
		m.m12 = buffer[ 9];
		m.m22 = buffer[10];
		m.m32 = buffer[11];
		m.m03 = buffer[12];
		m.m13 = buffer[13];
		m.m23 = buffer[14];
		m.m33 = buffer[15];
		return m;
	}
	//=============================================================================================

	//=============================================================================================
	public static Matrix4f acamReverse(Matrix4f src, Matrix4f dst) {
		Matrix3f R = new Matrix3f();
		Vector3f r = new Vector3f();
		src.get(R);
		src.get(r);
		r.scale(-1);		
		R.transpose();
		R.transform(r);
		dst.setRotation(R);
		dst.setTranslation(r);
		return dst;
	}
	//=============================================================================================
	
}
//*************************************************************************************************
