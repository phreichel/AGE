//*************************************************************************************************
package age.input;
//*************************************************************************************************

import java.util.EnumSet;
import java.util.Set;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

//*************************************************************************************************
public class Event {

	//=============================================================================================
	private InputType type = InputType.NONE;
	private Key key = Key.NONE;
	private char character = '\0';
	private final Set<Key> keyset = EnumSet.noneOf(Key.class);
	private Button button = Button.NONE;
	private int count = -1;
	private final Vector2f position = new Vector2f();
	private final Vector3f wheels = new Vector3f();
	private final Vector2f dimension = new Vector2f();
	private String command = null;
	//=============================================================================================
	
	//=============================================================================================
	public Event() {
		clear();
	}
	//=============================================================================================
	
	//=============================================================================================
	public void clear() {
		type = InputType.NONE;
		key = Key.NONE;
		character = '\0';
		keyset.clear();
		button = Button.NONE;
		count = 0;
		position.set(0, 0);
		wheels.set(0, 0, 0);
		dimension.set(0, 0);
		command = null;
	}
	//=============================================================================================

	//=============================================================================================
	public void set(Event e) {
		type = e.type;
		key = e.key;
		character = e.character;
		keyset.clear();
		keyset.addAll(e.keyset);
		button = e.button;
		count = e.count;
		position.set(e.position);
		wheels.set(e.wheels);
		dimension.set(e.dimension);
		command = e.command;
	}
	//=============================================================================================
	
	//=============================================================================================
	public InputType type() {
		return type;
	}
	//=============================================================================================

	//=============================================================================================
	public Key key() {
		return key;
	}
	//=============================================================================================

	//=============================================================================================
	public char character() {
		return character;
	}
	//=============================================================================================

	//=============================================================================================
	public Set<Key> keyset() {
		return keyset;
	}
	//=============================================================================================
	
	//=============================================================================================
	public Button button() {
		return button;
	}
	//=============================================================================================

	//=============================================================================================
	public int count() {
		return count;
	}
	//=============================================================================================

	//=============================================================================================
	public float x() {
		return position.x;
	}
	//=============================================================================================

	//=============================================================================================
	public float y() {
		return position.y;
	}
	//=============================================================================================
	
	//=============================================================================================
	public Vector2f position() {
		return position;
	}
	//=============================================================================================

	//=============================================================================================
	public float wheelX() {
		return wheels.x;
	}
	//=============================================================================================

	//=============================================================================================
	public float wheelY() {
		return wheels.y;
	}
	//=============================================================================================

	//=============================================================================================
	public float wheelZ() {
		return wheels.z;
	}
	//=============================================================================================
	
	//=============================================================================================
	public Vector3f wheels() {
		return wheels;
	}
	//=============================================================================================
	
	//=============================================================================================
	public float width() {
		return dimension.x;
	}
	//=============================================================================================

	//=============================================================================================
	public float height() {
		return dimension.y;
	}
	//=============================================================================================
	
	//=============================================================================================
	public Vector2f dimension() {
		return dimension;
	}
	//=============================================================================================

	//=============================================================================================
	public String command() {
		return this.command;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void keyPressed(Key key, char character, Set<Key> keyset) {
		keyEvent(InputType.KEY_PRESSED, key, character, keyset);
	}
	//=============================================================================================

	//=============================================================================================
	public void keyReleased(Key key, char character, Set<Key> keyset) {
		keyEvent(InputType.KEY_RELEASED, key, character, keyset);
	}
	//=============================================================================================

	//=============================================================================================
	public void keyTyped(Key key, char character, Set<Key> keyset) {
		keyEvent(InputType.KEY_TYPED, key, character, keyset);
	}
	//=============================================================================================

	//=============================================================================================
	public void pointerEntered(float x, float y, Set<Key> keyset) {
		pointerEvent(InputType.POINTER_ENTERED, Button.NONE, -1, x, y, keyset);
	}
	//=============================================================================================

	//=============================================================================================
	public void pointerExited(float x, float y, Set<Key> keyset) {
		pointerEvent(InputType.POINTER_EXITED, Button.NONE, -1, x, y, keyset);
	}
	//=============================================================================================

	//=============================================================================================
	public void pointerMoved(float x, float y, Set<Key> keyset) {
		pointerEvent(InputType.POINTER_MOVED, Button.NONE, -1, x, y, keyset);
	}
	//=============================================================================================

	//=============================================================================================
	public void pointerPressed(Button button, int count, float x, float y, Set<Key> keyset) {
		pointerEvent(InputType.POINTER_PRESSED, button, count, x, y, keyset);
	}
	//=============================================================================================

	//=============================================================================================
	public void pointerClicked(Button button, int count, float x, float y, Set<Key> keyset) {
		pointerEvent(InputType.POINTER_CLICKED, button, count, x, y, keyset);
	}
	//=============================================================================================

	//=============================================================================================
	public void pointerReleased(Button button, int count, float x, float y, Set<Key> keyset) {
		pointerEvent(InputType.POINTER_RELEASED, button, count, x, y, keyset);
	}
	//=============================================================================================

	//=============================================================================================
	public void surfaceResized(float w, float h, Set<Key> keyset) {
		clear();
		type = InputType.SURFACE_RESIZED;
		dimension.set(w, h);
	}
	//=============================================================================================

	//=============================================================================================
	public void surfaceCloseRequest() {
		clear();
		type = InputType.SURFACE_CLOSE_REQUEST;
	}
	//=============================================================================================

	//=============================================================================================
	public void taskCommand(String command, Set<Key> keyset) {
		clear();
		this.keyset.addAll(keyset);
		type = InputType.TASK_COMMAND;
		this.command = command;
	}
	//=============================================================================================
	
	//=============================================================================================
	private void keyEvent(
			InputType type,
			Key key,
			char character,
			Set<Key> keyset) {
		this.type = type;
		this.key = key;
		this.character = character;
		this.keyset.clear();
		this.keyset.addAll(keyset);
		this.button = Button.NONE;
		this.count = 0;
		this.position.set(0, 0);
		this.wheels.set(0, 0, 0);
		this.dimension.set(0, 0);
		this.command = null;
	}
	//=============================================================================================

	//=============================================================================================
	private void pointerEvent(
			InputType type,
			Button button,
			int count,
			float x,
			float y,
			Set<Key> keyset) {
		this.key = Key.NONE;
		this.character = '\0';
		this.type = type;
		this.keyset.clear();
		this.keyset.addAll(keyset);
		this.button = button;
		this.count = count;
		this.position.set(x, y);
		this.wheels.set(0, 0, 0);
		this.dimension.set(0, 0);
		this.command = null;
	}
	//=============================================================================================

	//=============================================================================================
	public void pointerWheel(
			float x,
			float y,
			Set<Key> keyset,
			Vector3f wheels) {
		this.key = Key.NONE;
		this.character = '\0';
		this.type = InputType.POINTER_WHEEL;
		this.keyset.clear();
		this.keyset.addAll(keyset);
		this.button = Button.NONE;
		this.count = 0;
		this.position.set(x, y);
		this.wheels.set(wheels);
		this.dimension.set(0, 0);
		this.command = null;
	}
	//=============================================================================================
	
}
//*************************************************************************************************
