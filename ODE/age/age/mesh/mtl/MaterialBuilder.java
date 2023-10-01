//*************************************************************************************************
package age.mesh.mtl;
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
	public void writeDiffuseMap(String name);
	public void writeBumpMap(String name);
	//=============================================================================================
	
}
//*************************************************************************************************
