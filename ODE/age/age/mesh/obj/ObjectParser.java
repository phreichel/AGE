//*************************************************************************************************
package age.mesh.obj;
//*************************************************************************************************

import java.io.Reader;

import age.mesh.mtl.MaterialParser;
import age.util.X;

//*************************************************************************************************
public class ObjectParser {

	//=============================================================================================
	private final ObjectScanner  objectScanner = new ObjectScanner();
	//=============================================================================================

	//=============================================================================================
	private ObjectBuilder objectBuilder = new MockupObjectBuilder();
	//=============================================================================================

	//=============================================================================================
	public void assign(ObjectBuilder objectBuilder) {
		this.objectBuilder = objectBuilder;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void init(Reader reader) {
		objectScanner.init(reader);		
	}
	//=============================================================================================
	
	//=============================================================================================
	public void parse() {
		objectBuilder.startFile();
		while (!objectScanner.objectSymbol().equals(ObjectSymbol.ENDOFSTREAM)) {
			parseLine();
		}
		objectBuilder.endFile();
	}
	//=============================================================================================

	//=============================================================================================
	private void parseLine() {
		if (parseVertex());
		else if (parseTexture());
		else if (parseNormal());
		else if (parseParameter());
		else if (parseSmoothing());
		else if (parseFace());
		else if (parsePolyline());
		else if (parseMtllib());
		else if (parseUsemtl());
		else if (parseVertexgroup());
		else parseEmptyLine();
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseVertexgroup() {
		if (parseGroup()) {
			while (!objectScanner.objectSymbol().equals(ObjectSymbol.ENDOFSTREAM)) {
				if (parseGroupEnd()) break;
				else if (parseVertex());
				else if (parseTexture());
				else if (parseNormal());
				else if (parseParameter());
				else if (parseSmoothing());
				else if (parseFace());
				else if (parsePolyline());
				else if (parseUsemtl());
				else parseEmptyLine();
			}
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseGroup() {
		if (tokenMatch(ObjectSymbol.KEYWORD, "g")) {
			objectScanner.scan();

			objectBuilder.startGroup();
			
			if (!tokenMatch(ObjectSymbol.WHITESPACE)) parseError("Whitespace expected");
			objectScanner.scan();

			if (!tokenMatch(ObjectSymbol.NAME)) parseError("Name expected");
			String name = objectScanner.token();
			objectScanner.scan();
			while (
				!tokenMatch(ObjectSymbol.WHITESPACE) &&
				!tokenMatch(ObjectSymbol.COMMENT) &&
				!tokenMatch(ObjectSymbol.LINEBREAK) &&
				!tokenMatch(ObjectSymbol.ENDOFSTREAM)
			) {
				name += objectScanner.token();
				objectScanner.scan();
			}

			objectBuilder.nameGroup(name);
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseGroupEnd() {
		if (tokenMatch(ObjectSymbol.KEYWORD, "g")) {
			objectScanner.scan();

			if (!tokenMatch(ObjectSymbol.WHITESPACE)) parseError("Whitespace expected");
			objectScanner.scan();

			if (!tokenMatch(ObjectSymbol.KEYWORD, "off")) parseError("Group End expected");
			objectScanner.scan();
			
			objectBuilder.endGroup();
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================
	
	//=============================================================================================
	private boolean parseVertex() {
		if (tokenMatch(ObjectSymbol.KEYWORD, "v")) {
			objectScanner.scan();
			
			float vx = 0f;
			float vy = 0f;
			float vz = 0f;
			float vw = 0f;
			
			if (!tokenMatch(ObjectSymbol.WHITESPACE)) parseError("Whitespace expected");
			objectScanner.scan();
			vx = parseDecimalNumber();

			if (!tokenMatch(ObjectSymbol.WHITESPACE)) parseError("Whitespace expected");
			objectScanner.scan();
			vy = parseDecimalNumber();

			if (!tokenMatch(ObjectSymbol.WHITESPACE)) parseError("Whitespace expected");
			objectScanner.scan();
			vz = parseDecimalNumber();

			if (tokenMatch(ObjectSymbol.WHITESPACE)) {
				objectScanner.scan();
				if (
					!tokenMatch(ObjectSymbol.COMMENT) &&
					!tokenMatch(ObjectSymbol.LINEBREAK) &&
					!tokenMatch(ObjectSymbol.ENDOFSTREAM)
				) {
					vw = parseDecimalNumber();
				}
			}

			objectBuilder.startVertex();
			objectBuilder.writeVertexCoord(vx);
			objectBuilder.writeVertexCoord(vy);
			objectBuilder.writeVertexCoord(vz);
			objectBuilder.writeVertexCoord(vw);
			objectBuilder.endVertex();
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseTexture() {
		if (tokenMatch(ObjectSymbol.KEYWORD, "vt")) {
			objectScanner.scan();

			float tu = 0f;
			float tv = 0f;
			float tw = 0f;
			
			if (!tokenMatch(ObjectSymbol.WHITESPACE)) parseError("Whitespace expected");
			objectScanner.scan();

			tu = parseDecimalNumber();

			if (tokenMatch(ObjectSymbol.WHITESPACE)) {
				objectScanner.scan();
				if (
					!tokenMatch(ObjectSymbol.COMMENT) &&
					!tokenMatch(ObjectSymbol.LINEBREAK) &&
					!tokenMatch(ObjectSymbol.ENDOFSTREAM)
				) {
					tv = parseDecimalNumber();
				}
			}

			if (tokenMatch(ObjectSymbol.WHITESPACE)) {
				objectScanner.scan();
				if (
					!tokenMatch(ObjectSymbol.COMMENT) &&
					!tokenMatch(ObjectSymbol.LINEBREAK) &&
					!tokenMatch(ObjectSymbol.ENDOFSTREAM)
				) {
					tw = parseDecimalNumber();
				}
			}

			objectBuilder.startTexture();
			objectBuilder.writeTextureCoord(tu);
			objectBuilder.writeTextureCoord(tv);
			objectBuilder.writeTextureCoord(tw);
			objectBuilder.endTexture();
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseNormal() {
		if (tokenMatch(ObjectSymbol.KEYWORD, "vn")) {
			objectScanner.scan();

			float nx = 0f;
			float ny = 0f;
			float nz = 0f;
			
			
			if (!tokenMatch(ObjectSymbol.WHITESPACE)) parseError("Whitespace expected");
			objectScanner.scan();
			nx = parseDecimalNumber();

			if (!tokenMatch(ObjectSymbol.WHITESPACE)) parseError("Whitespace expected");
			objectScanner.scan();
			ny = parseDecimalNumber();

			if (!tokenMatch(ObjectSymbol.WHITESPACE)) parseError("Whitespace expected");
			objectScanner.scan();
			nz =parseDecimalNumber();

			objectBuilder.startNormal();
			objectBuilder.writeNormalCoord(nx);
			objectBuilder.writeNormalCoord(ny);
			objectBuilder.writeNormalCoord(nz);
			objectBuilder.endNormal();
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseParameter() {
		if (tokenMatch(ObjectSymbol.KEYWORD, "vp")) {
			objectScanner.scan();

			float pu = 0f;
			float pv = 0f;
			float pw = 0f;
			
			if (!tokenMatch(ObjectSymbol.WHITESPACE)) parseError("Whitespace expected");
			objectScanner.scan();
			pu = parseDecimalNumber();

			if (tokenMatch(ObjectSymbol.WHITESPACE)) {
				objectScanner.scan();
				if (
					!tokenMatch(ObjectSymbol.COMMENT) &&
					!tokenMatch(ObjectSymbol.LINEBREAK) &&
					!tokenMatch(ObjectSymbol.ENDOFSTREAM)
				) {
					pv = parseDecimalNumber();
				}
			}

			if (tokenMatch(ObjectSymbol.WHITESPACE)) {
				objectScanner.scan();
				if (
					!tokenMatch(ObjectSymbol.COMMENT) &&
					!tokenMatch(ObjectSymbol.LINEBREAK) &&
					!tokenMatch(ObjectSymbol.ENDOFSTREAM)
				) {
					pw = parseDecimalNumber();
				}
			}

			objectBuilder.startParam();
			objectBuilder.writeParamCoord(pu);
			objectBuilder.writeParamCoord(pv);
			objectBuilder.writeParamCoord(pw);
			objectBuilder.endParam();
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================
	
	//=============================================================================================
	private boolean parseSmoothing() {
		if (tokenMatch(ObjectSymbol.KEYWORD, "s")) {
			objectScanner.scan();

			if (!tokenMatch(ObjectSymbol.WHITESPACE)) parseError("Whitespace expected");
			objectScanner.scan();

			if (tokenMatch(ObjectSymbol.KEYWORD, "off") || tokenMatch(ObjectSymbol.NUMBER, "0") || tokenMatch(ObjectSymbol.NUMBER, "1")) {
				objectScanner.scan();
			} else {
				parseError("Smoothing Parameter expected (0, 1, off)");
			}

			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================
	
	//=============================================================================================
	private boolean parseFace() {
		if (tokenMatch(ObjectSymbol.KEYWORD, "f")) {
			objectScanner.scan();
			
			objectBuilder.startFace();

			if (!tokenMatch(ObjectSymbol.WHITESPACE)) parseError("Whitespace expected");
			objectScanner.scan();

			parseFaceIndex();

			if (!tokenMatch(ObjectSymbol.WHITESPACE)) parseError("Whitespace expected");
			objectScanner.scan();

			parseFaceIndex();

			if (!tokenMatch(ObjectSymbol.WHITESPACE)) parseError("Whitespace expected");
			objectScanner.scan();

			parseFaceIndex();

			if (tokenMatch(ObjectSymbol.WHITESPACE)) {
				objectScanner.scan();
				if (
					!tokenMatch(ObjectSymbol.COMMENT) &&
					!tokenMatch(ObjectSymbol.LINEBREAK) &&
					!tokenMatch(ObjectSymbol.ENDOFSTREAM)
				) {
					parseFaceIndex();
				}
			}

			objectBuilder.endFace();
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private void parseFaceIndex() {
		
		int idx1 = 0;
		int idx2 = 0;
		int idx3 = 0;
		
		if (!tokenMatch(ObjectSymbol.NUMBER)) parseError("Number expected");
		idx1 = Integer.parseInt(objectScanner.token());
		objectScanner.scan();
		
		if (tokenMatch(ObjectSymbol.SPECIAL, "/")) {
			objectScanner.scan();
			int anyOption = 0;
			if (tokenMatch(ObjectSymbol.NUMBER)) {
				idx2 = Integer.parseInt(objectScanner.token());
				objectScanner.scan();
				anyOption++;
			} 
			if (tokenMatch(ObjectSymbol.SPECIAL, "/")) {
				objectScanner.scan();
				if (!tokenMatch(ObjectSymbol.NUMBER)) parseError("Number expected");
				idx3 = Integer.parseInt(objectScanner.token());
				objectScanner.scan();
				anyOption++;
			}
			if (anyOption == 0) {
				parseError("Invalid Face Index Format");
			}
		}

		objectBuilder.startFaceIndex();
		objectBuilder.writeFaceIndex(idx1);
		objectBuilder.writeFaceIndex(idx2);
		objectBuilder.writeFaceIndex(idx3);
		objectBuilder.endFaceIndex();
		
	}
	//=============================================================================================
	
	//=============================================================================================
	private boolean parsePolyline() {
		if (tokenMatch(ObjectSymbol.KEYWORD, "l")) {

			if (!tokenMatch(ObjectSymbol.WHITESPACE)) parseError("Whitespace expected");
			objectScanner.scan();

			if (!tokenMatch(ObjectSymbol.NUMBER)) parseError("Number expected");
			while (tokenMatch(ObjectSymbol.NUMBER)) {
				objectScanner.scan();
				if (tokenMatch(ObjectSymbol.WHITESPACE)) {
					objectScanner.scan();
				}
			}
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseMtllib() {
		if (tokenMatch(ObjectSymbol.KEYWORD, "mtllib")) {
			objectScanner.scan();

			if (!tokenMatch(ObjectSymbol.WHITESPACE)) parseError("Whitespace expected");
			objectScanner.scan();

			if (!tokenMatch(ObjectSymbol.NAME)) parseError("Name expected");
			objectScanner.scan();
			while (
				!tokenMatch(ObjectSymbol.WHITESPACE) &&
				!tokenMatch(ObjectSymbol.COMMENT) &&
				!tokenMatch(ObjectSymbol.LINEBREAK) &&
				!tokenMatch(ObjectSymbol.ENDOFSTREAM)
			) {
				objectScanner.scan();
			}
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseUsemtl() {
		if (tokenMatch(ObjectSymbol.KEYWORD, "usemtl")) {
			objectScanner.scan();

			if (!tokenMatch(ObjectSymbol.WHITESPACE)) parseError("Whitespace expected");
			objectScanner.scan();

			if (!tokenMatch(ObjectSymbol.NAME)) parseError("Name expected");
			objectScanner.scan();
			while (
				!tokenMatch(ObjectSymbol.WHITESPACE) &&
				!tokenMatch(ObjectSymbol.COMMENT) &&
				!tokenMatch(ObjectSymbol.LINEBREAK) &&
				!tokenMatch(ObjectSymbol.ENDOFSTREAM)
			) {
				objectScanner.scan();
			}
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================
	
	//=============================================================================================
	private float parseDecimalNumber() {
		String sNumber = "";
		int anyNumber = 0;
		if (tokenMatch(ObjectSymbol.SYMBOL, "-") || tokenMatch(ObjectSymbol.SYMBOL, "+")) {
			sNumber += objectScanner.token();
			objectScanner.scan();
		}
		if (tokenMatch(ObjectSymbol.NUMBER)) {
			anyNumber++;
			sNumber += objectScanner.token();
			objectScanner.scan();
		}
		if (tokenMatch(ObjectSymbol.SPECIAL, ".")) {
			sNumber += objectScanner.token();
			objectScanner.scan();
		}
		if (tokenMatch(ObjectSymbol.NUMBER)) {
			anyNumber++;
			sNumber += objectScanner.token();
			objectScanner.scan();
		}
		if (anyNumber == 0) {
			 parseError("Decimal Number expected");
		}
		return Float.parseFloat(sNumber);
	}
	//=============================================================================================

	//=============================================================================================
	private void parseEmptyLine() {
		while (
			tokenMatch(ObjectSymbol.COMMENT) ||
			tokenMatch(ObjectSymbol.WHITESPACE)
		) {
			objectScanner.scan();
		}
		parseLinebreak();
	}
	//=============================================================================================
	
	//=============================================================================================
	private void parseLinebreak() {
		if (
			tokenMatch(ObjectSymbol.LINEBREAK) ||
			tokenMatch(ObjectSymbol.ENDOFSTREAM)
		) {
			if (tokenMatch(ObjectSymbol.LINEBREAK)) {
				objectScanner.scan();
			}
		} else {
			parseError("Linebreak or End of Strem expected");
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	private boolean tokenMatch(ObjectSymbol objectSymbol) {
		return
			objectScanner.objectSymbol().equals(objectSymbol);
	}
	//=============================================================================================
	
	//=============================================================================================
	private boolean tokenMatch(ObjectSymbol objectSymbol, String token) {
		return
			objectScanner.objectSymbol().equals(objectSymbol) &&
			objectScanner.token().equals(token);
	}
	//=============================================================================================
	
	//=============================================================================================
	private void parseError(String error) {
		throw new X(
			"Parse Error at:(%s:%s), token:'%s': %s",
			objectScanner.column(),
			objectScanner.line(),
			objectScanner.token(),
			error);
	}
	//=============================================================================================

}
//*************************************************************************************************
