//*************************************************************************************************
package age.model.obj;
//*************************************************************************************************

import java.util.Map;

import age.model.Material;

//*************************************************************************************************
public interface ObjectBuilder {

	//=============================================================================================
	public void startFile();
	public void endFile();
	//=============================================================================================

	//=============================================================================================
	public void startGroup(String name);
	public void endGroup();
	//=============================================================================================

	//=============================================================================================
	public void startObject(String name);
	public void endObject();
	//=============================================================================================
	
	//=============================================================================================
	public void writeVertex(float vx, float vy, float vz, float vw);
	public void writeNormal(float nx, float ny, float nz);
	public void writeTexture(float tu, float tv, float tw);
	public void writeParameter(float pu, float pv, float pw);
	//=============================================================================================

	//=============================================================================================
	public void startFace();
	public void endFace();
	public void writeFaceIndex(int iv, int it, int in);
	//=============================================================================================

	//=============================================================================================
	public void startLine();
	public void endLine();
	public void writeLineIndex(int idx);
	//=============================================================================================

	//=============================================================================================
	public void materialLib(String path, Map<String, Material> materials);
	public void materialUse(String name);
	//=============================================================================================
	
}
//*************************************************************************************************
