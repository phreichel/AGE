/*
 * Commit: 01f6edb422e32c691886b08481b74af734d2419d
 * Date: 2023-09-25 00:22:26+02:00
 * Author: Philip Reichel
 * Comment: Added Mesh
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

//*************************************************************************************************
public class X extends RuntimeException {

	//=============================================================================================
	private static final long serialVersionUID = 1L;
	//=============================================================================================

	//=============================================================================================
	public X() {
		super();
	}
	//=============================================================================================

	//=============================================================================================
	public X(Throwable cause) {
		super(cause);
	}
	//=============================================================================================

	//=============================================================================================
	public X(String message) {
		super(message);
	}
	//=============================================================================================

	//=============================================================================================
	public X(String message, Throwable cause) {
		super(message, cause);
	}
	//=============================================================================================

	//=============================================================================================
	public X(String message, Object ... params) {
		super(String.format(message, params));
	}
	//=============================================================================================
	
}
//*************************************************************************************************
