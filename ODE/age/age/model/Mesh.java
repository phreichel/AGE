/*
 * Commit: 1e9f0cb1a10963887f772e1af01d5b9101dd076d
 * Date: 2023-10-05 04:52:55+02:00
 * Author: Philip Reichel
 * Comment: ..
 *
 * Commit: e5bb68c8896f9172d8653bf44223a2c332662b59
 * Date: 2023-10-04 16:55:10+02:00
 * Author: pre7618
 * Comment: ...
 *
 * Commit: eb306b1e6a0957eb9ec0dc0accbc1747e9f08d0f
 * Date: 2023-10-04 01:48:00+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 */

//*************************************************************************************************
package age.model;
//*************************************************************************************************

import java.nio.FloatBuffer;

//*************************************************************************************************
public class Mesh {

	//=============================================================================================
	public final int size;
	public final FloatBuffer positions;
	public final FloatBuffer textures;
	public final FloatBuffer normals;
	//=============================================================================================

	//=============================================================================================
	public Mesh(
			float[] positions,
			float[] textures,
			float[] normals) {
		this.size = positions.length / 3;
		this.positions = FloatBuffer.wrap(positions);
		this.textures = FloatBuffer.wrap(textures);
		this.normals = FloatBuffer.wrap(normals);
	}
	//=============================================================================================

	//=============================================================================================
	public void rewind() {
		this.positions.rewind();
		this.textures.rewind();
		this.normals.rewind();
	}
	//=============================================================================================
	
}
//*************************************************************************************************
