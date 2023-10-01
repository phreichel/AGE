//*************************************************************************************************
package age.mesh.obj;
//*************************************************************************************************

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import age.mesh.Material;
import age.mesh.mtl.MaterialMapBuilder;
import age.mesh.mtl.MaterialParser;
import age.util.Scanner;
import age.util.Symbol;
import age.util.X;

import static age.util.Symbol.*;

//*************************************************************************************************
public class ObjectParser {

	//=============================================================================================
	private final MaterialMapBuilder materialBuilder = new MaterialMapBuilder();
	private final MaterialParser  materialParser  = new MaterialParser(materialBuilder);
	private final Map<String, Material> materialLibrary = new HashMap<>();
	//=============================================================================================
	
	//=============================================================================================
	private final Scanner scanner = new ObjectScanner();
	private final ObjectBuilder objectBuilder;
	//=============================================================================================

	//=============================================================================================
	public ObjectParser(ObjectBuilder objectBuilder) {
		this.objectBuilder = objectBuilder;
	}
	//=============================================================================================
	
	//=============================================================================================
	private File base = null;
	//=============================================================================================
	
	//=============================================================================================
	public void init(File base, Reader reader) {
		this.base = base;
		scanner.init(reader);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void parse() {
		objectBuilder.startFile();
		while (!scanner.symbol().equals(ENDOFSTREAM)) {
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
		else if (parseVertexobject());
		else parseEmptyLine();
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseVertexgroup() {
		if (parseGroup()) {
			while (!scanner.symbol().equals(ENDOFSTREAM)) {
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
	private boolean parseVertexobject() {
		if (parseObject()) {
			while (!scanner.symbol().equals(ENDOFSTREAM)) {
				if (parseObjectEnd()) break;
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
		if (tokenMatch(KEYWORD, "g")) {
			scanner.scan();

			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();

			String name = "";
			while (
				!tokenMatch(WHITESPACE) &&
				!tokenMatch(COMMENT) &&
				!tokenMatch(LINEBREAK) &&
				!tokenMatch(ENDOFSTREAM)
			) {
				name += scanner.token();
				scanner.scan();
			}

			objectBuilder.startGroup(name);
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseGroupEnd() {
		if (tokenMatch(KEYWORD, "g")) {
			scanner.scan();

			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();

			if (tokenMatch(KEYWORD, "off")) {
				scanner.scan();
				objectBuilder.endGroup();
				parseEmptyLine();
				return true;
			} else {
				String name = "";
				while (
					!tokenMatch(WHITESPACE) &&
					!tokenMatch(COMMENT) &&
					!tokenMatch(LINEBREAK) &&
					!tokenMatch(ENDOFSTREAM)
				) {
					name += scanner.token();
					scanner.scan();
				}
				objectBuilder.endGroup();
				objectBuilder.startGroup(name);
				parseEmptyLine();
				return false;
			}

		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseObject() {
		if (tokenMatch(KEYWORD, "o")) {
			scanner.scan();

			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();

			String name = "";
			while (
				!tokenMatch(WHITESPACE) &&
				!tokenMatch(COMMENT) &&
				!tokenMatch(LINEBREAK) &&
				!tokenMatch(ENDOFSTREAM)
			) {
				name += scanner.token();
				scanner.scan();
			}

			objectBuilder.startObject(name);
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseObjectEnd() {
		if (tokenMatch(KEYWORD, "o")) {
			scanner.scan();

			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();

			if (tokenMatch(KEYWORD, "off")) {
				scanner.scan();
				objectBuilder.endGroup();
				parseEmptyLine();
				return true;
			} else {
				String name = "";
				while (
					!tokenMatch(WHITESPACE) &&
					!tokenMatch(COMMENT) &&
					!tokenMatch(LINEBREAK) &&
					!tokenMatch(ENDOFSTREAM)
				) {
					name += scanner.token();
					scanner.scan();
				}
				objectBuilder.endObject();
				objectBuilder.startObject(name);
				parseEmptyLine();
				return false;
			}
		}
		return false;
	}
	//=============================================================================================
	
	//=============================================================================================
	private boolean parseVertex() {
		if (tokenMatch(KEYWORD, "v")) {
			scanner.scan();
			
			float vx = 0f;
			float vy = 0f;
			float vz = 0f;
			float vw = 0f;
			
			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();
			vx = parseDecimalNumber();

			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();
			vy = parseDecimalNumber();

			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();
			vz = parseDecimalNumber();

			if (tokenMatch(WHITESPACE)) {
				scanner.scan();
				if (
					!tokenMatch(COMMENT) &&
					!tokenMatch(LINEBREAK) &&
					!tokenMatch(ENDOFSTREAM)
				) {
					vw = parseDecimalNumber();
				}
			}

			objectBuilder.writeVertex(vx, vy, vz, vw);
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseTexture() {
		if (tokenMatch(KEYWORD, "vt")) {
			scanner.scan();

			float tu = 0f;
			float tv = 0f;
			float tw = 0f;
			
			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();

			tu = parseDecimalNumber();

			if (tokenMatch(WHITESPACE)) {
				scanner.scan();
				if (
					!tokenMatch(COMMENT) &&
					!tokenMatch(LINEBREAK) &&
					!tokenMatch(ENDOFSTREAM)
				) {
					tv = parseDecimalNumber();
				}
			}

			if (tokenMatch(WHITESPACE)) {
				scanner.scan();
				if (
					!tokenMatch(COMMENT) &&
					!tokenMatch(LINEBREAK) &&
					!tokenMatch(ENDOFSTREAM)
				) {
					tw = parseDecimalNumber();
				}
			}

			objectBuilder.writeTexture(tu, tv, tw);
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseNormal() {
		if (tokenMatch(KEYWORD, "vn")) {
			scanner.scan();

			float nx = 0f;
			float ny = 0f;
			float nz = 0f;
			
			
			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();
			nx = parseDecimalNumber();

			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();
			ny = parseDecimalNumber();

			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();
			nz =parseDecimalNumber();

			objectBuilder.writeNormal(nx, ny, nz);
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseParameter() {
		if (tokenMatch(KEYWORD, "vp")) {
			scanner.scan();

			float pu = 0f;
			float pv = 0f;
			float pw = 0f;
			
			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();
			pu = parseDecimalNumber();

			if (tokenMatch(WHITESPACE)) {
				scanner.scan();
				if (
					!tokenMatch(COMMENT) &&
					!tokenMatch(LINEBREAK) &&
					!tokenMatch(ENDOFSTREAM)
				) {
					pv = parseDecimalNumber();
				}
			}

			if (tokenMatch(WHITESPACE)) {
				scanner.scan();
				if (
					!tokenMatch(COMMENT) &&
					!tokenMatch(LINEBREAK) &&
					!tokenMatch(ENDOFSTREAM)
				) {
					pw = parseDecimalNumber();
				}
			}

			objectBuilder.writeParameter(pu, pv, pw);
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================
	
	//=============================================================================================
	private boolean parseSmoothing() {
		if (tokenMatch(KEYWORD, "s")) {
			scanner.scan();

			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();

			if (tokenMatch(KEYWORD, "off") || tokenMatch(NUMBER)) {
				scanner.scan();
			} else {
				parseError("Smoothing Parameter expected ('off' or number)");
			}

			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================
	
	//=============================================================================================
	private boolean parseFace() {
		if (tokenMatch(KEYWORD, "f")) {
			scanner.scan();
			
			objectBuilder.startFace();

			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();

			parseFaceIndex();

			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();

			parseFaceIndex();

			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();

			parseFaceIndex();

			if (tokenMatch(WHITESPACE)) {
				scanner.scan();
				if (
					!tokenMatch(COMMENT) &&
					!tokenMatch(LINEBREAK) &&
					!tokenMatch(ENDOFSTREAM)
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
		
		int iv = 0;
		int it = 0;
		int in = 0;		
		
		if (!tokenMatch(NUMBER)) parseError("Number expected");
		iv = Integer.parseInt(scanner.token());
		scanner.scan();
		
		if (tokenMatch(SYMBOL, "/")) {
			scanner.scan();
			int anyOption = 0;
			if (tokenMatch(NUMBER)) {
				it = Integer.parseInt(scanner.token());
				scanner.scan();
				anyOption++;
			} 
			if (tokenMatch(SYMBOL, "/")) {
				scanner.scan();
				if (!tokenMatch(NUMBER)) parseError("Number expected");
				in = Integer.parseInt(scanner.token());
				scanner.scan();
				anyOption++;
			}
			if (anyOption == 0) {
				parseError("Invalid Face Index Format");
			}
		}

		objectBuilder.writeFaceIndex(iv, it, in);
		
	}
	//=============================================================================================
	
	//=============================================================================================
	private boolean parsePolyline() {
		if (tokenMatch(KEYWORD, "l")) {
			scanner.scan();

			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();

			if (!tokenMatch(NUMBER)) parseError("Number expected");
			while (tokenMatch(NUMBER)) {
				scanner.scan();
				if (tokenMatch(WHITESPACE)) {
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
		if (tokenMatch(KEYWORD, "mtllib")) {
			scanner.scan();

			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();

			String path = "";
			while (
				!tokenMatch(WHITESPACE) &&
				!tokenMatch(COMMENT) &&
				!tokenMatch(LINEBREAK) &&
				!tokenMatch(ENDOFSTREAM)
			) {
				path += scanner.token();
				scanner.scan();
			}
			
			try {
				File mtlFile = new File(base, path);
				Reader reader = new FileReader(mtlFile);
				materialParser.init(mtlFile.getParentFile(), reader);
				materialParser.parse();
				materialBuilder.build(materialLibrary);
				reader.close();
				objectBuilder.materialLib(path, materialLibrary);
				materialLibrary.clear();
			} catch (Exception e) {
				throw new X(e);
			}
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseUsemtl() {
		if (tokenMatch(KEYWORD, "usemtl")) {
			scanner.scan();

			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();

			String mtlname = "";
			while (
				!tokenMatch(WHITESPACE) &&
				!tokenMatch(COMMENT) &&
				!tokenMatch(LINEBREAK) &&
				!tokenMatch(ENDOFSTREAM)
			) {
				mtlname += scanner.token();
				scanner.scan();
			}
			
			objectBuilder.materialUse(mtlname);
			
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
		if (tokenMatch(SYMBOL, "-") || tokenMatch(SYMBOL, "+")) {
			sNumber += scanner.token();
			scanner.scan();
		}
		if (tokenMatch(NUMBER)) {
			anyNumber++;
			sNumber += scanner.token();
			scanner.scan();
		}
		if (tokenMatch(SYMBOL, ".")) {
			sNumber += scanner.token();
			scanner.scan();
		}
		if (tokenMatch(NUMBER)) {
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
		while (
			tokenMatch(COMMENT) ||
			tokenMatch(WHITESPACE)
		) {
			scanner.scan();
		}
		parseLinebreak();
	}
	//=============================================================================================
	
	//=============================================================================================
	private void parseLinebreak() {
		if (
			tokenMatch(LINEBREAK) ||
			tokenMatch(ENDOFSTREAM)
		) {
			if (tokenMatch(LINEBREAK)) {
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

}
//*************************************************************************************************
