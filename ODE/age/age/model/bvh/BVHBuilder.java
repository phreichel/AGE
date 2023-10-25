/*
 * Commit: 7407dc59dacb80fc02cd2de06a33b6b78cc37f8e
 * Date: 2023-10-12 13:32:56+02:00
 * Author: pre7618
 * Comment: Test
 *
 */

//*************************************************************************************************
package age.model.bvh;
//*************************************************************************************************

//*************************************************************************************************
public interface BVHBuilder {

	//=============================================================================================
	public void startFile();
	public void endFile();
	//=============================================================================================

	//=============================================================================================
	public void startBone(String name, float ofsx, float ofsy, float ofsz);
	public void endBone();
	public void writeChannelCount(int count);
	public void writeChannelName(String channelName);
	//=============================================================================================

	//=============================================================================================
	public void writeFrameCount(int frameCount);
	public void writeFrameTime(float frameTime);
	public void writeFrameData(float ... frameValues);
	//=============================================================================================
	
}
//*************************************************************************************************