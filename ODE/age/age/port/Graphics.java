/*
 * Commit: 7407dc59dacb80fc02cd2de06a33b6b78cc37f8e
 * Date: 2023-10-12 13:32:56+02:00
 * Author: pre7618
 * Comment: Test
 *
 * Commit: 00a610434f6fec6c19360cdcbe9949c18fea5a3b
 * Date: 2023-10-10 10:10:29+02:00
 * Author: pre7618
 * Comment: ..
 *
 * Commit: 989a153341f09c0168f323a44e0ffec01fbc07b7
 * Date: 2023-10-01 22:26:41+02:00
 * Author: Philip Reichel
 * Comment: VBO babay
 *
 * Commit: 921fe26242fec8aea55ed85b831bf841b11304e4
 * Date: 2023-09-30 17:42:43+02:00
 * Author: Philip Reichel
 * Comment: Added Material Parsing
Added Models
Remove Bigfile
Added Models
Revert "Added Models"

This reverts commit cd0e7c6f40612113283cf9300c40af491cd1bc2f.

 *
 * Commit: 729ac252a97c3fd77734b2d841dfdd0ec3de55fb
 * Date: 2023-09-27 19:14:06+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 * Commit: 79126a233e35010f5ee69ea22119499909797737
 * Date: 2023-09-27 16:31:28+02:00
 * Author: pre7618
 * Comment: Added Camview and Rig
 *
 * Commit: aa20ad72b29161d58217d659b23accf2664ef3cf
 * Date: 2023-09-26 23:19:19+02:00
 * Author: Philip Reichel
 * Comment: ...
 *
 * Commit: 39b8f74293b119c48ba58cce6da3104541cab12a
 * Date: 2023-09-26 00:54:32+02:00
 * Author: Philip Reichel
 * Comment: Added Walker Animation
 *
 * Commit: fedc4377dc9249932eb67a8eb9e9a46dbec18ff0
 * Date: 2023-09-25 18:34:56+02:00
 * Author: pre7618
 * Comment: ...
 *
 * Commit: 01f6edb422e32c691886b08481b74af734d2419d
 * Date: 2023-09-25 00:22:26+02:00
 * Author: Philip Reichel
 * Comment: Added Mesh
 *
 * Commit: 1fd12df4cfeb1c4d67818231e6df4905af8afdef
 * Date: 2023-09-23 17:22:11+02:00
 * Author: Philip Reichel
 * Comment: Almost reworked Multiline
 *
 * Commit: 4ba52177b03c84982e184472f4e51a9157e9a67f
 * Date: 2023-09-21 20:15:06+02:00
 * Author: Philip Reichel
 * Comment: Cleaning all up
 *
 * Commit: d458c8f320674895dba3866cdd8fd198a68de073
 * Date: 2023-09-21 16:49:14+02:00
 * Author: pre7618
 * Comment: ..
 *
 * Commit: efba03d014f9f8690fc57eb3949bb36f7ed2e269
 * Date: 2023-09-19 07:28:45+02:00
 * Author: Philip Reichel
 * Comment: todo tasks
 *
 * Commit: 28615d0198d72bb1e1667c0113a594237068a89d
 * Date: 2023-09-18 16:25:09+02:00
 * Author: pre7618
 * Comment: Added Scene Nodes
 *
 * Commit: 059a758405028e46fcdd6cbef87c1c4ed3330712
 * Date: 2023-09-17 17:32:59+02:00
 * Author: Philip Reichel
 * Comment: Multitext
 *
 * Commit: 979912e4583b2e47ff4138caf044ae2005b4a274
 * Date: 2023-09-15 21:18:58+02:00
 * Author: Philip Reichel
 * Comment: Refactoring GUI Window Rendering
 *
 * Commit: 275f07861ea5f95dd90f184d1311a0a5a8cd510d
 * Date: 2023-09-14 17:55:15+02:00
 * Author: pre7618
 * Comment: GUI in progress
 *
 * Commit: 343fe7d3b3a248993aa01b34632a93f7d5c8e09c
 * Date: 2023-09-12 07:49:14+02:00
 * Author: Philip Reichel
 * Comment: Relocation and new Design Merger
 *
 */

//*************************************************************************************************
package age.port;
//*************************************************************************************************

import javax.vecmath.Color4f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector2f;
import age.gui.Multiline;
import age.gui.Scrollable;
import age.model.Material;
import age.model.Model;

//*************************************************************************************************
public interface Graphics {

	//=============================================================================================
	public void mode3D(float fovy, float near, float far);
	public void mode2D();
	//=============================================================================================

	//=============================================================================================
	public void pushTransformation();
	public void popTransformation();
	//=============================================================================================

	public void applyTransformation(Matrix4f matrix);
	public void translate(Vector2f pos);
	public void translate(float x, float y);
	//=============================================================================================

	//=============================================================================================
	public void color(Color4f c);
	public void color(float r, float g, float b);
	public void color(float r, float g, float b, float a);
	//=============================================================================================

	//=============================================================================================
	public void rectangle(Vector2f dim, boolean hollow);
	public void rectangle(Vector2f pos, Vector2f dim, boolean hollow);
	public void rectangle(float x, float y, float w, float h, boolean hollow);
	//=============================================================================================

	//=============================================================================================
	public void text(float x, float y, CharSequence text, String font);
	public void calcMultitext(String text, float width, float height, String font, Scrollable scstate, Multiline mlstate);
	//=============================================================================================

	//=============================================================================================
	public void texture(float x, float y, float w, float h, String texture);
	//=============================================================================================

	//=============================================================================================
	public void applyMaterial(Material m);
	//=============================================================================================
	
	//=============================================================================================
	public void drawBox(float sx, float sy, float sz);
	public void drawRig(age.model.Rig rig);
	public void drawModel(Model model);
	//=============================================================================================
	
}
//*************************************************************************************************
