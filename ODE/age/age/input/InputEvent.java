//*************************************************************************************************
package age.input;
//*************************************************************************************************

import java.util.EnumSet;
import java.util.Set;

import javax.vecmath.Vector2f;

/**************************************************************************************************
 * This class defines an Event along with its descriptive data.
 */
public class InputEvent {

	/**********************************************************************************************
	 * The event type 
	 */
	private InputType type = InputType.NONE;

	/**********************************************************************************************
	 * The key for key event types 
	 */
	private Key key = Key.NONE;

	/**********************************************************************************************
	 * The character typed for key event types 
	 */
	private char character = '\0';

	/**********************************************************************************************
	 * The set of pressed keys 
	 */
	private final Set<Key> keyset = EnumSet.noneOf(Key.class);
	
	/**********************************************************************************************
	 * The button for pointer event types 
	 */
	private Button button = Button.NONE;
	
	/**********************************************************************************************
	 * The click count for pointer event types 
	 */
	private int count = -1;
	
	/**********************************************************************************************
	 * The pointer position for pointer event types 
	 */
	private Vector2f position = new Vector2f();

	/**********************************************************************************************
	 * The window dimension for the window resize event type 
	 */
	private Vector2f dimension  = new Vector2f();

	/**********************************************************************************************
	 * The command detail name for the command event type 
	 */
	private String command = null;
	
	/**********************************************************************************************
	 * Package visible constructor 
	 */
	public InputEvent() {
		clear();
	}
	
	/**********************************************************************************************
	 * Method to clear all event data values to default values 
	 */
	public void clear() {
		type      = InputType.NONE;
		key       = Key.NONE;
		character = '\0';
		keyset.clear();
		button    = Button.NONE;
		count     = -1;
		position.set(-1, -1);
		dimension.set(-1, -1);
		command = null;
	}

	/**********************************************************************************************
	 * Method to set all event data values to the values in Event e
	 * e the event to copy from.
	 */
	public void set(InputEvent e) {
		type      = e.type;
		key       = e.key;
		character = e.character;
		keyset.clear();
		keyset.addAll(e.keyset);
		button    = e.button;
		count     = e.count;
		position.set(e.position);
		dimension.set(e.dimension);
		command = e.command;
	}
	
	/**********************************************************************************************
	 * Method to clear all event data values to default values 
	 */
	public InputType type() {
		return type;
	}

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
	private void keyEvent(InputType type, Key key, char character, Set<Key> keyset) {
		this.type = type;
		this.key = key;
		this.character = character;
		this.keyset.clear();
		this.keyset.addAll(keyset);
		this.button    = Button.NONE;
		this.count = -1;
		this.position.set(-1, -1);
		this.dimension.set(-1, -1);
	}
	//=============================================================================================

	//=============================================================================================
	private void pointerEvent(InputType type, Button button, int count, float x, float y, Set<Key> keyset) {
		this.type = type;
		this.keyset.clear();
		this.keyset.addAll(keyset);
		this.button = button;
		this.count = count;
		this.position.set(x, y);
		this.dimension.set(-1, -1);
	}
	//=============================================================================================
	
}
//*************************************************************************************************
