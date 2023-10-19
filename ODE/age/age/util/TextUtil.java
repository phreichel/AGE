/*
 * Commit: a6cfd8a75f5b4f72e889e58a8fbcb41ac4c94136
 * Date: 2023-09-29 14:56:21+02:00
 * Author: Philip Reichel
 * Comment: Added Simple Obj Loader
 *
 */

//*************************************************************************************************
package age.util;
//*************************************************************************************************

//*************************************************************************************************
public final class TextUtil {

	//=============================================================================================
	public static final boolean isAlphaUc(char c) {
		return (c >= 'A') && (c <= 'Z');
	}
	//=============================================================================================	

	//=============================================================================================
	public static final boolean isAlphaLc(char c) {
		return (c >= 'a') && (c <= 'z');
	}
	//=============================================================================================	

	//=============================================================================================
	public static final boolean isAlphaSpecial(char c) {
		return c == '_';
	}
	//=============================================================================================	
	
	//=============================================================================================
	public static final boolean isAlpha(char c) {
		return isAlphaUc(c) || isAlphaLc(c);
	}
	//=============================================================================================	

	//=============================================================================================
	public static final boolean isAlphaExt(char c) {
		return isAlpha(c) || isAlphaSpecial(c);
	}
	//=============================================================================================	
	
	//=============================================================================================
	public static final boolean isWhitespace(char c) {
		return (c == ' ') || (c == '\t');
	}
	//=============================================================================================	

	//=============================================================================================
	public static final boolean isLinebreak(char c) {
		return (c == '\r') || (c == '\n');
	}
	//=============================================================================================	
	
	//=============================================================================================
	public static final boolean isBinary(char c) {
		return (c >= '0') && (c <= '1');
	}
	//=============================================================================================	

	//=============================================================================================
	public static final boolean isOctal(char c) {
		return (c >= '0') && (c <= '7');
	}
	//=============================================================================================	
	
	//=============================================================================================
	public static final boolean isDecimal(char c) {
		return (c >= '0') && (c <= '9');
	}
	//=============================================================================================	

	//=============================================================================================
	public static final boolean isHexLc(char c) {
		return
			isDecimal(c) ||
			((c >= 'a') && (c <= 'f'));
	}
	//=============================================================================================	

	//=============================================================================================
	public static final boolean isHexUc(char c) {
		return
			isDecimal(c) ||
			((c >= 'A') && (c <= 'F'));
	}
	//=============================================================================================	

	//=============================================================================================
	public static final boolean isHex(char c) {
		return
			isDecimal(c) ||
			((c >= 'a') && (c <= 'f')) ||
			((c >= 'A') && (c <= 'F'));
	}
	//=============================================================================================	

	//=============================================================================================
	public static final boolean charMatch(char c , char cmp) {
		return (c == cmp);
	}
	//=============================================================================================	

}
//*************************************************************************************************
