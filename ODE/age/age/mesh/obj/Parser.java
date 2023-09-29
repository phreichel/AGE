//*************************************************************************************************
package age.mesh.obj;
//*************************************************************************************************

//import java.io.File;
//import java.io.FileReader;
import java.io.Reader;
//import age.mesh.Mesh;
import age.util.X;

//*************************************************************************************************
public class Parser {

	//=============================================================================================
	private Scanner scanner = new Scanner();
	//=============================================================================================

	//=============================================================================================
	private Builder builder = new MockupBuilder();
	//=============================================================================================

	//=============================================================================================
	public void assign(Builder builder) {
		this.builder = builder;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void init(Reader reader) {
		scanner.init(reader);		
	}
	//=============================================================================================
	
	//=============================================================================================
	public void parse() {
		builder.startFile();
		while (!scanner.symbol().equals(Symbol.ENDOFSTREAM)) {
			parseLine();
		}
		builder.endFile();
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
		else if (parseVertexgroup());
		else parseEmptyLine();
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseVertexgroup() {
		if (parseGroup()) {
			while (!scanner.symbol().equals(Symbol.ENDOFSTREAM)) {
				if (parseGroupEnd()) break;
				else if (parseVertex());
				else if (parseTexture());
				else if (parseNormal());
				else if (parseParameter());
				else if (parseSmoothing());
				else parseEmptyLine();
			}
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseGroup() {
		if (tokenMatch(Symbol.KEYWORD, "g")) {
			scanner.scan();

			builder.startGroup();
			
			if (!tokenMatch(Symbol.WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();

			if (!tokenMatch(Symbol.NAME)) parseError("Name expected");
			String name = scanner.token();
			scanner.scan();
			while (!tokenMatch(Symbol.WHITESPACE) && !tokenMatch(Symbol.COMMENT) && !tokenMatch(Symbol.LINEBREAK)&& !tokenMatch(Symbol.ENDOFSTREAM)) {
				name += scanner.token();
				scanner.scan();
			}

			builder.nameGroup(name);
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseGroupEnd() {
		if (tokenMatch(Symbol.KEYWORD, "g")) {
			scanner.scan();

			if (!tokenMatch(Symbol.WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();

			if (!tokenMatch(Symbol.KEYWORD, "off")) parseError("Group End expected");
			scanner.scan();
			
			builder.endGroup();
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================
	
	//=============================================================================================
	private boolean parseVertex() {
		if (tokenMatch(Symbol.KEYWORD, "v")) {
			scanner.scan();
			
			float vx = 0f;
			float vy = 0f;
			float vz = 0f;
			float vw = 0f;
			
			if (!tokenMatch(Symbol.WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();
			vx = parseDecimalNumber();

			if (!tokenMatch(Symbol.WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();
			vy = parseDecimalNumber();

			if (!tokenMatch(Symbol.WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();
			vz = parseDecimalNumber();

			if (tokenMatch(Symbol.WHITESPACE)) {
				scanner.scan();
				if (
					!tokenMatch(Symbol.COMMENT) &&
					!tokenMatch(Symbol.LINEBREAK) &&
					!tokenMatch(Symbol.ENDOFSTREAM)
				) {
					vw = parseDecimalNumber();
				}
			}

			builder.startVertex();
			builder.writeVertexCoord(vx);
			builder.writeVertexCoord(vy);
			builder.writeVertexCoord(vz);
			builder.writeVertexCoord(vw);
			builder.endVertex();
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseTexture() {
		if (tokenMatch(Symbol.KEYWORD, "vt")) {
			scanner.scan();

			float tu = 0f;
			float tv = 0f;
			float tw = 0f;
			
			if (!tokenMatch(Symbol.WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();

			tu = parseDecimalNumber();

			if (tokenMatch(Symbol.WHITESPACE)) {
				scanner.scan();
				if (
					!tokenMatch(Symbol.COMMENT) &&
					!tokenMatch(Symbol.LINEBREAK) &&
					!tokenMatch(Symbol.ENDOFSTREAM)
				) {
					tv = parseDecimalNumber();
				}
			}

			if (tokenMatch(Symbol.WHITESPACE)) {
				scanner.scan();
				if (
					!tokenMatch(Symbol.COMMENT) &&
					!tokenMatch(Symbol.LINEBREAK) &&
					!tokenMatch(Symbol.ENDOFSTREAM)
				) {
					tw = parseDecimalNumber();
				}
			}

			builder.startTexture();
			builder.writeTextureCoord(tu);
			builder.writeTextureCoord(tv);
			builder.writeTextureCoord(tw);
			builder.endTexture();
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseNormal() {
		if (tokenMatch(Symbol.KEYWORD, "vn")) {
			scanner.scan();

			float nx = 0f;
			float ny = 0f;
			float nz = 0f;
			
			
			if (!tokenMatch(Symbol.WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();
			nx = parseDecimalNumber();

			if (!tokenMatch(Symbol.WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();
			ny = parseDecimalNumber();

			if (!tokenMatch(Symbol.WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();
			nz =parseDecimalNumber();

			builder.startNormal();
			builder.writeNormalCoord(nx);
			builder.writeNormalCoord(ny);
			builder.writeNormalCoord(nz);
			builder.endNormal();
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseParameter() {
		if (tokenMatch(Symbol.KEYWORD, "vp")) {
			scanner.scan();

			float pu = 0f;
			float pv = 0f;
			float pw = 0f;
			
			if (!tokenMatch(Symbol.WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();
			pu = parseDecimalNumber();

			if (tokenMatch(Symbol.WHITESPACE)) {
				scanner.scan();
				if (
					!tokenMatch(Symbol.COMMENT) &&
					!tokenMatch(Symbol.LINEBREAK) &&
					!tokenMatch(Symbol.ENDOFSTREAM)
				) {
					pv = parseDecimalNumber();
				}
			}

			if (tokenMatch(Symbol.WHITESPACE)) {
				scanner.scan();
				if (
					!tokenMatch(Symbol.COMMENT) &&
					!tokenMatch(Symbol.LINEBREAK) &&
					!tokenMatch(Symbol.ENDOFSTREAM)
				) {
					pw = parseDecimalNumber();
				}
			}

			builder.startParam();
			builder.writeParamCoord(pu);
			builder.writeParamCoord(pv);
			builder.writeParamCoord(pw);
			builder.endParam();
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================
	
	//=============================================================================================
	private boolean parseSmoothing() {
		if (tokenMatch(Symbol.KEYWORD, "s")) {
			scanner.scan();

			if (!tokenMatch(Symbol.WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();

			if (tokenMatch(Symbol.KEYWORD, "off") || tokenMatch(Symbol.NUMBER, "0") || tokenMatch(Symbol.NUMBER, "1")) {
				scanner.scan();
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
		if (tokenMatch(Symbol.KEYWORD, "f")) {
			scanner.scan();
			
			builder.startFace();

			if (!tokenMatch(Symbol.WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();

			parseFaceIndex();

			if (!tokenMatch(Symbol.WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();

			parseFaceIndex();

			if (!tokenMatch(Symbol.WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();

			parseFaceIndex();

			if (tokenMatch(Symbol.WHITESPACE)) {
				scanner.scan();
				if (
					!tokenMatch(Symbol.COMMENT) &&
					!tokenMatch(Symbol.LINEBREAK) &&
					!tokenMatch(Symbol.ENDOFSTREAM)
				) {
					parseFaceIndex();
				}
			}

			builder.endFace();
			
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
		
		if (!tokenMatch(Symbol.NUMBER)) parseError("Number expected");
		idx1 = Integer.parseInt(scanner.token());
		scanner.scan();
		
		if (tokenMatch(Symbol.SPECIAL, "/")) {
			scanner.scan();
			int anyOption = 0;
			if (tokenMatch(Symbol.NUMBER)) {
				idx2 = Integer.parseInt(scanner.token());
				scanner.scan();
				anyOption++;
			} 
			if (tokenMatch(Symbol.SPECIAL, "/")) {
				scanner.scan();
				if (!tokenMatch(Symbol.NUMBER)) parseError("Number expected");
				idx3 = Integer.parseInt(scanner.token());
				scanner.scan();
				anyOption++;
			}
			if (anyOption == 0) {
				parseError("Invalid Face Index Format");
			}
		}

		builder.startFaceIndex();
		builder.writeFaceIndex(idx1);
		builder.writeFaceIndex(idx2);
		builder.writeFaceIndex(idx3);
		builder.endFaceIndex();
		
	}
	//=============================================================================================
	
	//=============================================================================================
	private boolean parsePolyline() {
		if (tokenMatch(Symbol.KEYWORD, "l")) {

			if (!tokenMatch(Symbol.WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();

			if (!tokenMatch(Symbol.NUMBER)) parseError("Number expected");
			while (tokenMatch(Symbol.NUMBER)) {
				scanner.scan();
				if (tokenMatch(Symbol.WHITESPACE)) {
					scanner.scan();
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
		if (tokenMatch(Symbol.KEYWORD, "mtllib")) {
			scanner.scan();

			if (!tokenMatch(Symbol.WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();

			if (!tokenMatch(Symbol.NAME)) parseError("Name expected");
			scanner.scan();
			while (
				!tokenMatch(Symbol.WHITESPACE) &&
				!tokenMatch(Symbol.COMMENT) &&
				!tokenMatch(Symbol.LINEBREAK) &&
				!tokenMatch(Symbol.ENDOFSTREAM)
			) {
				scanner.scan();
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
		if (tokenMatch(Symbol.SYMBOL, "-") || tokenMatch(Symbol.SYMBOL, "+")) {
			sNumber += scanner.token();
			scanner.scan();
		}
		if (tokenMatch(Symbol.NUMBER)) {
			anyNumber++;
			sNumber += scanner.token();
			scanner.scan();
		}
		if (tokenMatch(Symbol.SPECIAL, ".")) {
			sNumber += scanner.token();
			scanner.scan();
		}
		if (tokenMatch(Symbol.NUMBER)) {
			anyNumber++;
			sNumber += scanner.token();
			scanner.scan();
		}
		if (anyNumber == 0) {
			 parseError("Decimal Number expected");
		}
		return Float.parseFloat(sNumber);
	}
	//=============================================================================================

	//=============================================================================================
	private void parseEmptyLine() {
		while (tokenMatch(Symbol.COMMENT) || tokenMatch(Symbol.WHITESPACE)) {
			scanner.scan();
		}
		parseLinebreak();
	}
	//=============================================================================================
	
	//=============================================================================================
	private void parseLinebreak() {
		if (tokenMatch(Symbol.LINEBREAK) || tokenMatch(Symbol.ENDOFSTREAM)) {
			if (tokenMatch(Symbol.LINEBREAK)) {
				scanner.scan();
			}
		} else {
			parseError("Linebreak or End of Strem expected");
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	private boolean tokenMatch(Symbol symbol) {
		return
			scanner.symbol().equals(symbol);
	}
	//=============================================================================================
	
	//=============================================================================================
	private boolean tokenMatch(String token) {
		return
			scanner.token().equals(token);
	}
	//=============================================================================================
	
	//=============================================================================================
	private boolean tokenMatch(Symbol symbol, String token) {
		return
			scanner.symbol().equals(symbol) &&
			scanner.token().equals(token);
	}
	//=============================================================================================
	
	//=============================================================================================
	private void parseError(String error) {
		throw new X(
			"Parse Error at:(%s:%s), token:'%s': %s",
			scanner.column(),
			scanner.line(),
			scanner.token(),
			error);
	}
	//=============================================================================================

	//=============================================================================================
	/*
	public static void main(String[] args) throws Exception {
		Reader reader = new FileReader(new File("assets/example.obj"));
		Parser parser = new Parser();
		MeshBuilder builder = new MeshBuilder();
		parser.assign(builder);
		parser.init(reader);
		parser.parse();
		reader.close();
		Mesh mesh = builder.build();
	}
	*/
	//=============================================================================================
	
}
//*************************************************************************************************
