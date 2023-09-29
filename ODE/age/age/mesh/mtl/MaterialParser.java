//*************************************************************************************************
package age.mesh.mtl;
//*************************************************************************************************

import java.io.Reader;
import age.util.X;

//*************************************************************************************************
public class MaterialParser {

	//=============================================================================================
	private MaterialScanner materialScanner = new MaterialScanner();
	//=============================================================================================

	//=============================================================================================
	private MaterialBuilder materialBuilder = null;
	//=============================================================================================

	//=============================================================================================
	public void assign(MaterialBuilder materialBuilder) {
		this.materialBuilder = materialBuilder;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void init(Reader reader) {
		materialScanner.init(reader);		
	}
	//=============================================================================================
	
	//=============================================================================================
	public void parse() {
		materialBuilder.startFile();
		while (!materialScanner.symbol().equals(MaterialSymbol.ENDOFSTREAM)) {
			parseLine();
		}
		materialBuilder.endFile();
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
			while (!materialScanner.symbol().equals(MaterialSymbol.ENDOFSTREAM)) {
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
		if (tokenMatch(MaterialSymbol.KEYWORD, "g")) {
			materialScanner.scan();

			materialBuilder.startGroup();
			
			if (!tokenMatch(MaterialSymbol.WHITESPACE)) parseError("Whitespace expected");
			materialScanner.scan();

			if (!tokenMatch(MaterialSymbol.NAME)) parseError("Name expected");
			String name = materialScanner.token();
			materialScanner.scan();
			while (
				!tokenMatch(MaterialSymbol.WHITESPACE) &&
				!tokenMatch(MaterialSymbol.COMMENT) &&
				!tokenMatch(MaterialSymbol.LINEBREAK) &&
				!tokenMatch(MaterialSymbol.ENDOFSTREAM)
			) {
				name += materialScanner.token();
				materialScanner.scan();
			}

			materialBuilder.nameGroup(name);
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseGroupEnd() {
		if (tokenMatch(MaterialSymbol.KEYWORD, "g")) {
			materialScanner.scan();

			if (!tokenMatch(MaterialSymbol.WHITESPACE)) parseError("Whitespace expected");
			materialScanner.scan();

			if (!tokenMatch(MaterialSymbol.KEYWORD, "off")) parseError("Group End expected");
			materialScanner.scan();
			
			materialBuilder.endGroup();
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================
	
	//=============================================================================================
	private boolean parseVertex() {
		if (tokenMatch(MaterialSymbol.KEYWORD, "v")) {
			materialScanner.scan();
			
			float vx = 0f;
			float vy = 0f;
			float vz = 0f;
			float vw = 0f;
			
			if (!tokenMatch(MaterialSymbol.WHITESPACE)) parseError("Whitespace expected");
			materialScanner.scan();
			vx = parseDecimalNumber();

			if (!tokenMatch(MaterialSymbol.WHITESPACE)) parseError("Whitespace expected");
			materialScanner.scan();
			vy = parseDecimalNumber();

			if (!tokenMatch(MaterialSymbol.WHITESPACE)) parseError("Whitespace expected");
			materialScanner.scan();
			vz = parseDecimalNumber();

			if (tokenMatch(MaterialSymbol.WHITESPACE)) {
				materialScanner.scan();
				if (
					!tokenMatch(MaterialSymbol.COMMENT) &&
					!tokenMatch(MaterialSymbol.LINEBREAK) &&
					!tokenMatch(MaterialSymbol.ENDOFSTREAM)
				) {
					vw = parseDecimalNumber();
				}
			}

			materialBuilder.startVertex();
			materialBuilder.writeVertexCoord(vx);
			materialBuilder.writeVertexCoord(vy);
			materialBuilder.writeVertexCoord(vz);
			materialBuilder.writeVertexCoord(vw);
			materialBuilder.endVertex();
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseTexture() {
		if (tokenMatch(MaterialSymbol.KEYWORD, "vt")) {
			materialScanner.scan();

			float tu = 0f;
			float tv = 0f;
			float tw = 0f;
			
			if (!tokenMatch(MaterialSymbol.WHITESPACE)) parseError("Whitespace expected");
			materialScanner.scan();

			tu = parseDecimalNumber();

			if (tokenMatch(MaterialSymbol.WHITESPACE)) {
				materialScanner.scan();
				if (
					!tokenMatch(MaterialSymbol.COMMENT) &&
					!tokenMatch(MaterialSymbol.LINEBREAK) &&
					!tokenMatch(MaterialSymbol.ENDOFSTREAM)
				) {
					tv = parseDecimalNumber();
				}
			}

			if (tokenMatch(MaterialSymbol.WHITESPACE)) {
				materialScanner.scan();
				if (
					!tokenMatch(MaterialSymbol.COMMENT) &&
					!tokenMatch(MaterialSymbol.LINEBREAK) &&
					!tokenMatch(MaterialSymbol.ENDOFSTREAM)
				) {
					tw = parseDecimalNumber();
				}
			}

			materialBuilder.startTexture();
			materialBuilder.writeTextureCoord(tu);
			materialBuilder.writeTextureCoord(tv);
			materialBuilder.writeTextureCoord(tw);
			materialBuilder.endTexture();
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseNormal() {
		if (tokenMatch(MaterialSymbol.KEYWORD, "vn")) {
			materialScanner.scan();

			float nx = 0f;
			float ny = 0f;
			float nz = 0f;
			
			
			if (!tokenMatch(MaterialSymbol.WHITESPACE)) parseError("Whitespace expected");
			materialScanner.scan();
			nx = parseDecimalNumber();

			if (!tokenMatch(MaterialSymbol.WHITESPACE)) parseError("Whitespace expected");
			materialScanner.scan();
			ny = parseDecimalNumber();

			if (!tokenMatch(MaterialSymbol.WHITESPACE)) parseError("Whitespace expected");
			materialScanner.scan();
			nz =parseDecimalNumber();

			materialBuilder.startNormal();
			materialBuilder.writeNormalCoord(nx);
			materialBuilder.writeNormalCoord(ny);
			materialBuilder.writeNormalCoord(nz);
			materialBuilder.endNormal();
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseParameter() {
		if (tokenMatch(MaterialSymbol.KEYWORD, "vp")) {
			materialScanner.scan();

			float pu = 0f;
			float pv = 0f;
			float pw = 0f;
			
			if (!tokenMatch(MaterialSymbol.WHITESPACE)) parseError("Whitespace expected");
			materialScanner.scan();
			pu = parseDecimalNumber();

			if (tokenMatch(MaterialSymbol.WHITESPACE)) {
				materialScanner.scan();
				if (
					!tokenMatch(MaterialSymbol.COMMENT) &&
					!tokenMatch(MaterialSymbol.LINEBREAK) &&
					!tokenMatch(MaterialSymbol.ENDOFSTREAM)
				) {
					pv = parseDecimalNumber();
				}
			}

			if (tokenMatch(MaterialSymbol.WHITESPACE)) {
				materialScanner.scan();
				if (
					!tokenMatch(MaterialSymbol.COMMENT) &&
					!tokenMatch(MaterialSymbol.LINEBREAK) &&
					!tokenMatch(MaterialSymbol.ENDOFSTREAM)
				) {
					pw = parseDecimalNumber();
				}
			}

			materialBuilder.startParam();
			materialBuilder.writeParamCoord(pu);
			materialBuilder.writeParamCoord(pv);
			materialBuilder.writeParamCoord(pw);
			materialBuilder.endParam();
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================
	
	//=============================================================================================
	private boolean parseSmoothing() {
		if (tokenMatch(MaterialSymbol.KEYWORD, "s")) {
			materialScanner.scan();

			if (!tokenMatch(MaterialSymbol.WHITESPACE)) parseError("Whitespace expected");
			materialScanner.scan();

			if (tokenMatch(MaterialSymbol.KEYWORD, "off") || tokenMatch(MaterialSymbol.NUMBER, "0") || tokenMatch(MaterialSymbol.NUMBER, "1")) {
				materialScanner.scan();
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
		if (tokenMatch(MaterialSymbol.KEYWORD, "f")) {
			materialScanner.scan();
			
			materialBuilder.startFace();

			if (!tokenMatch(MaterialSymbol.WHITESPACE)) parseError("Whitespace expected");
			materialScanner.scan();

			parseFaceIndex();

			if (!tokenMatch(MaterialSymbol.WHITESPACE)) parseError("Whitespace expected");
			materialScanner.scan();

			parseFaceIndex();

			if (!tokenMatch(MaterialSymbol.WHITESPACE)) parseError("Whitespace expected");
			materialScanner.scan();

			parseFaceIndex();

			if (tokenMatch(MaterialSymbol.WHITESPACE)) {
				materialScanner.scan();
				if (
					!tokenMatch(MaterialSymbol.COMMENT) &&
					!tokenMatch(MaterialSymbol.LINEBREAK) &&
					!tokenMatch(MaterialSymbol.ENDOFSTREAM)
				) {
					parseFaceIndex();
				}
			}

			materialBuilder.endFace();
			
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
		
		if (!tokenMatch(MaterialSymbol.NUMBER)) parseError("Number expected");
		idx1 = Integer.parseInt(materialScanner.token());
		materialScanner.scan();
		
		if (tokenMatch(MaterialSymbol.SPECIAL, "/")) {
			materialScanner.scan();
			int anyOption = 0;
			if (tokenMatch(MaterialSymbol.NUMBER)) {
				idx2 = Integer.parseInt(materialScanner.token());
				materialScanner.scan();
				anyOption++;
			} 
			if (tokenMatch(MaterialSymbol.SPECIAL, "/")) {
				materialScanner.scan();
				if (!tokenMatch(MaterialSymbol.NUMBER)) parseError("Number expected");
				idx3 = Integer.parseInt(materialScanner.token());
				materialScanner.scan();
				anyOption++;
			}
			if (anyOption == 0) {
				parseError("Invalid Face Index Format");
			}
		}

		materialBuilder.startFaceIndex();
		materialBuilder.writeFaceIndex(idx1);
		materialBuilder.writeFaceIndex(idx2);
		materialBuilder.writeFaceIndex(idx3);
		materialBuilder.endFaceIndex();
		
	}
	//=============================================================================================
	
	//=============================================================================================
	private boolean parsePolyline() {
		if (tokenMatch(MaterialSymbol.KEYWORD, "l")) {

			if (!tokenMatch(MaterialSymbol.WHITESPACE)) parseError("Whitespace expected");
			materialScanner.scan();

			if (!tokenMatch(MaterialSymbol.NUMBER)) parseError("Number expected");
			while (tokenMatch(MaterialSymbol.NUMBER)) {
				materialScanner.scan();
				if (tokenMatch(MaterialSymbol.WHITESPACE)) {
					materialScanner.scan();
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
		if (tokenMatch(MaterialSymbol.KEYWORD, "mtllib")) {
			materialScanner.scan();

			if (!tokenMatch(MaterialSymbol.WHITESPACE)) parseError("Whitespace expected");
			materialScanner.scan();

			if (!tokenMatch(MaterialSymbol.NAME)) parseError("Name expected");
			materialScanner.scan();
			while (
				!tokenMatch(MaterialSymbol.WHITESPACE) &&
				!tokenMatch(MaterialSymbol.COMMENT) &&
				!tokenMatch(MaterialSymbol.LINEBREAK) &&
				!tokenMatch(MaterialSymbol.ENDOFSTREAM)
			) {
				materialScanner.scan();
			}
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseUsemtl() {
		if (tokenMatch(MaterialSymbol.KEYWORD, "usemtl")) {
			materialScanner.scan();

			if (!tokenMatch(MaterialSymbol.WHITESPACE)) parseError("Whitespace expected");
			materialScanner.scan();

			if (!tokenMatch(MaterialSymbol.NAME)) parseError("Name expected");
			materialScanner.scan();
			while (
				!tokenMatch(MaterialSymbol.WHITESPACE) &&
				!tokenMatch(MaterialSymbol.COMMENT) &&
				!tokenMatch(MaterialSymbol.LINEBREAK) &&
				!tokenMatch(MaterialSymbol.ENDOFSTREAM)
			) {
				materialScanner.scan();
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
		if (tokenMatch(MaterialSymbol.SYMBOL, "-") || tokenMatch(MaterialSymbol.SYMBOL, "+")) {
			sNumber += materialScanner.token();
			materialScanner.scan();
		}
		if (tokenMatch(MaterialSymbol.NUMBER)) {
			anyNumber++;
			sNumber += materialScanner.token();
			materialScanner.scan();
		}
		if (tokenMatch(MaterialSymbol.SPECIAL, ".")) {
			sNumber += materialScanner.token();
			materialScanner.scan();
		}
		if (tokenMatch(MaterialSymbol.NUMBER)) {
			anyNumber++;
			sNumber += materialScanner.token();
			materialScanner.scan();
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
			tokenMatch(MaterialSymbol.COMMENT) ||
			tokenMatch(MaterialSymbol.WHITESPACE)
		) {
			materialScanner.scan();
		}
		parseLinebreak();
	}
	//=============================================================================================
	
	//=============================================================================================
	private void parseLinebreak() {
		if (
			tokenMatch(MaterialSymbol.LINEBREAK) ||
			tokenMatch(MaterialSymbol.ENDOFSTREAM)
		) {
			if (tokenMatch(MaterialSymbol.LINEBREAK)) {
				materialScanner.scan();
			}
		} else {
			parseError("Linebreak or End of Strem expected");
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	private boolean tokenMatch(MaterialSymbol MaterialSymbol) {
		return
			materialScanner.symbol().equals(MaterialSymbol);
	}
	//=============================================================================================
	
	//=============================================================================================
	private boolean tokenMatch(MaterialSymbol MaterialSymbol, String token) {
		return
			materialScanner.symbol().equals(MaterialSymbol) &&
			materialScanner.token().equals(token);
	}
	//=============================================================================================
	
	//=============================================================================================
	private void parseError(String error) {
		throw new X(
			"Parse Error at:(%s:%s), token:'%s': %s",
			materialScanner.column(),
			materialScanner.line(),
			materialScanner.token(),
			error);
	}
	//=============================================================================================

}
//*************************************************************************************************
