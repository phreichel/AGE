/*
 * Commit: 1e9f0cb1a10963887f772e1af01d5b9101dd076d
 * Date: 2023-10-05 04:52:55+02:00
 * Author: Philip Reichel
 * Comment: ..
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

import java.nio.IntBuffer;

//*************************************************************************************************
public class Element {

	//=============================================================================================
	public final ElementType type;
	public final IntBuffer indices;
	//=============================================================================================

	//=============================================================================================
	public Element(ElementType type, int ... indices) {
		this.type = type;
		this.indices = IntBuffer.wrap(indices);
	}
	//=============================================================================================
	
}
//*************************************************************************************************
