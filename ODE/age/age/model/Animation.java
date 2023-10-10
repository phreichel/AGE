//*************************************************************************************************
package age.model;

import java.util.HashMap;
import java.util.Map;

//*************************************************************************************************

//*************************************************************************************************
public class Animation {

	//=============================================================================================
	public final Skeleton skeleton;
	//=============================================================================================

	//=============================================================================================
	public int steps;
	public float steptime;
	//=============================================================================================

	//=============================================================================================
	public final Map<Integer, Keyframes> keyframes = new HashMap<>();
	public final Map<String, Sequence> sequences = new HashMap<>();
	//=============================================================================================

	//=============================================================================================
	public Animation(Skeleton skeleton) {
		this.skeleton = skeleton;
	}
	//=============================================================================================

}
//*************************************************************************************************
