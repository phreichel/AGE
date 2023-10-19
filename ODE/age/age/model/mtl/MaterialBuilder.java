/*
 * Commit: 7407dc59dacb80fc02cd2de06a33b6b78cc37f8e
 * Date: 2023-10-12 13:32:56+02:00
 * Author: pre7618
 * Comment: Test
 *
 */

//*************************************************************************************************
package age.model.mtl;
//*************************************************************************************************

//*************************************************************************************************
public interface MaterialBuilder {

	//=============================================================================================
	public void startFile();
	public void endFile();
	//=============================================================================================

	//=============================================================================================
	public void startNewMaterial(String name);
	public void writeIllum(int illum);
	public void writeNs(float Ns);
	public void writeNi(float Ni);
	public void writeD(float d);
	public void writeTr(float Tr);
	public void writeKa(float r, float g, float b);
	public void writeKd(float r, float g, float b);
	public void writeKs(float r, float g, float b);
	public void writeKe(float r, float g, float b);
	//=============================================================================================

	//=============================================================================================
	public void writeSpecularMap(String name);
	public void writeAmbientMap(String name);
	public void writeDiffuseMap(String name);
	public void writeBumpMap(String name);
	//=============================================================================================
	
}
//*************************************************************************************************
