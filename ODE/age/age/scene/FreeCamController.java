//*************************************************************************************************
package age.scene;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.List;
import javax.vecmath.Matrix3f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import age.input.Event;
import age.input.Events;
import age.input.InputType;

//*************************************************************************************************
public class FreeCamController {
	
	//=============================================================================================
	private List<Node> register = new ArrayList<>();
	//=============================================================================================	

	//=============================================================================================
	public void assign(Events events) {
		events.assign(InputType.KEY_TYPED, this::handleEvent);
		events.assign(InputType.KEY_PRESSED, this::handleEvent);
		events.assign(InputType.KEY_RELEASED, this::handleEvent);
		events.assign(InputType.POINTER_MOVED, this::handleEvent);
		events.assign(InputType.POINTER_PRESSED, this::handleEvent);
		events.assign(InputType.POINTER_RELEASED, this::handleEvent);
		events.assign(InputType.POINTER_CLICKED, this::handleEvent);
		events.assign(InputType.POINTER_WHEEL, this::handleEvent);
	}
	//=============================================================================================

	//=============================================================================================
	public void add(Node node) {
		register.add(node);
	}
	//=============================================================================================

	//=============================================================================================
	public void remove(Node node) {
		register.remove(node);
	}
	//=============================================================================================

	//=============================================================================================
	private int forward = 0;
	private int up      = 0;
	private int left    = 0;
	private int pitch   = 0;
	private int yaw     = 0;
	private int roll    = 0;
	//=============================================================================================
	
	//=============================================================================================
	private void handleEvent(Event e) {
		switch (e.type()) {
			case KEY_PRESSED -> {
				switch (e.key()) {
					case W     -> forward++;
					case S     -> forward--;
					case A     -> left++;
					case D     -> left--;
					case R     -> up++;
					case F     -> up--;
					case UP    -> pitch++;
					case DOWN  -> pitch--;
					case LEFT  -> yaw--;
					case RIGHT -> yaw++;
					case Q     -> roll++;
					case E     -> roll--;
					default    -> {}
				}
			}
			case KEY_RELEASED -> {
				switch (e.key()) {
					case W     -> forward--;
					case S     -> forward++;
					case A     -> left--;
					case D     -> left++;
					case R     -> up--;
					case F     -> up++;
					case UP    -> pitch--;
					case DOWN  -> pitch++;
					case LEFT  -> yaw++;
					case RIGHT -> yaw--;
					case Q     -> roll--;
					case E     -> roll++;
					default    -> {}
				}
			}
			default -> {}
		}
	}
	//=============================================================================================

	//=============================================================================================
	public void update(float dT) {
		
		final float VT = 10f;
		final float VR = (float) Math.toRadians( 45f );
		
		for (var node : register) {
			
			Matrix4f matrix = node.component(NItem.TRANSFORM, Matrix4f.class);
			
			Vector3f trn = new Vector3f();
			Matrix3f rot = new Matrix3f();
					
			matrix.get(trn);
			matrix.get(rot);

			Matrix3f mrot   = new Matrix3f();
			Matrix3f mpitch = new Matrix3f();
			Matrix3f myaw   = new Matrix3f();
			Matrix3f mroll  = new Matrix3f();
			mpitch.rotX(dT * VR * pitch);
			myaw.rotY(dT * VR * -yaw);
			mroll.rotZ(dT * VR * roll);
			mrot.mul(mroll, myaw);
			mrot.mul(mrot, mpitch);
			mrot.mul(rot, mrot);
			
			Vector3f dr = new Vector3f(
				dT * -VT * left,
				dT *  VT * up,
				dT * -VT * forward
			);
			rot.transform(dr);
			trn.add(dr);

			matrix.setTranslation(trn);
			matrix.setRotation(mrot);

		}

	}
	//=============================================================================================
	
}
//*************************************************************************************************
