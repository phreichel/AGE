//*************************************************************************************************
package age.util;
//*************************************************************************************************

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

//*************************************************************************************************
public final class MathUtil {

	//=============================================================================================
	public static Quat4f identityQuaternion() {
		return new Quat4f(0, 0, 0, 1);
	}
	//=============================================================================================	

	//=============================================================================================
	public static Quat4f lerpQuaternion(Quat4f a, Quat4f b, float s, Quat4f dst) {
		dst.x = (1.0f - s) * a.x + s * b.x;
		dst.y = (1.0f - s) * a.y + s * b.y;
		dst.z = (1.0f - s) * a.z + s * b.z;
		dst.w = (1.0f - s) * a.w + s * b.w;
		return dst;
	}
	//=============================================================================================	
	
	//=============================================================================================
	public static Quat4f slerpQuaternion(Quat4f a, Quat4f b, float s, Quat4f dst) {
		dst.set(a);
		dst.interpolate(b, s);
		return dst;
	}
	//=============================================================================================	
	
	//=============================================================================================
	public static final Matrix4f identityMatrix() {
		Matrix4f m = new Matrix4f();
		m.setIdentity();
		return m;
	}
	//=============================================================================================

	//=============================================================================================
	public static final Matrix4f translateMatrix(float x, float y, float z) {
		Vector3f v = new Vector3f(x, y, z);
		Matrix4f m = new Matrix4f();
		m.setIdentity();
		m.setTranslation(v);
		return m;
	}
	//=============================================================================================
	
	//=============================================================================================
	public static final Matrix4f rotY(float a) {
		Matrix4f m = new Matrix4f();
		m.rotY(a);
		return m;
	}
	//=============================================================================================
	
	//=============================================================================================
	public static final Matrix4f cameraMatrix(
			Matrix4f src,
			Matrix4f dst) {

		dst.set(src);
		
		float r0 = -dst.m03;
		float r1 = -dst.m13;
		float r2 = -dst.m23;

		dst.m03 = 0f;
		dst.m13 = 0f;
		dst.m23 = 0f;

		dst.transpose();
		
		dst.m03 = r0 * dst.m00 + r1 * dst.m01 + r2 * dst.m02;
		dst.m13 = r0 * dst.m10 + r1 * dst.m11 + r2 * dst.m12;
		dst.m23 = r0 * dst.m20 + r1 * dst.m21 + r2 * dst.m22;

		return dst;

	}
	//=============================================================================================

	//=============================================================================================
	public static final Matrix4f lookAtMatrix(
			Vector3f pos,
			Vector3f at,
			Vector3f up,
			Matrix4f dst) {
		throw new X("not yet implemented"); 
	}
	//=============================================================================================

	//=============================================================================================
	public static final Matrix4f perspectiveMatrix(
			float fovy,
			float aspect,
			float near,
			float far) {
		throw new X("not yet implemented"); 
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
	
}
//*************************************************************************************************
