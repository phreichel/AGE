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
		skipWhite();
		if (tokenMatch(KEYWORD, "v")) parseVertex();
		else if (tokenMatch(KEYWORD, "vt")) parseTexture();
		else if (tokenMatch(KEYWORD, "vn")) parseNormal();
		else if (tokenMatch(KEYWORD, "vp")) parseParameter();
		else if (tokenMatch(KEYWORD, "s")) parseSmoothing();
		else if (tokenMatch(KEYWORD, "f")) parseFace();
		else if (tokenMatch(KEYWORD, "l")) parsePolyline();
		else if (tokenMatch(KEYWORD, "mtllib")) parseMtllib();
		else if (tokenMatch(KEYWORD, "usemtl")) parseUsemtl();
		else if (tokenMatch(KEYWORD, "g")) parseVertexgroup();
		else if (tokenMatch(KEYWORD, "o")) parseVertexobject();
		parseLineEnd();
	}
	//=============================================================================================

	//=============================================================================================
	private void parseVertexgroup() {
		parseGroup();
		while (!scanner.symbol().equals(ENDOFSTREAM)) {
			skipWhite();
			if (tokenMatch(KEYWORD, "v")) parseVertex();
			else if (tokenMatch(KEYWORD, "vt")) parseTexture();
			else if (tokenMatch(KEYWORD, "vn")) parseNormal();
			else if (tokenMatch(KEYWORD, "vp")) parseParameter();
			else if (tokenMatch(KEYWORD, "s")) parseSmoothing();
			else if (tokenMatch(KEYWORD, "f")) parseFace();
			else if (tokenMatch(KEYWORD, "l")) parsePolyline();
			else if (tokenMatch(KEYWORD, "usemtl")) parseUsemtl();
			else if (tokenMatch(KEYWORD, "g") && parseGroupEnd()) break;
			parseLineEnd();
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void parseVertexobject() {
		parseObject();
		while (!scanner.symbol().equals(ENDOFSTREAM)) {
			skipWhite();
			if (tokenMatch(KEYWORD, "v")) parseVertex();
			else if (tokenMatch(KEYWORD, "vt")) parseTexture();
			else if (tokenMatch(KEYWORD, "vn")) parseNormal();
			else if (tokenMatch(KEYWORD, "vp")) parseParameter();
			else if (tokenMatch(KEYWORD, "s")) parseSmoothing();
			else if (tokenMatch(KEYWORD, "f")) parseFace();
			else if (tokenMatch(KEYWORD, "l")) parsePolyline();
			else if (tokenMatch(KEYWORD, "usemtl")) parseUsemtl();
			else if (tokenMatch(KEYWORD, "o") && parseObjectEnd()) break;
			parseLineEnd();
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	private void parseGroup() {
		
		if (!tokenMatch(KEYWORD, "g")) parseError("'g' expected");
		scanner.scan();

		skipWhite();
		
		String name = parseAnyName();
		objectBuilder.startGroup(name);
		
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseGroupEnd() {

		if (!tokenMatch(KEYWORD, "g")) parseError("'g' expected");
		scanner.scan();

		skipWhite();

		objectBuilder.endGroup();
		
		String name = parseAnyName();
		boolean leaveGroupParsing = true;
		if (!name.equals("off")) {
			objectBuilder.startGroup(name);
			leaveGroupParsing = false;
		}
			
		return leaveGroupParsing;
		
	}
	//=============================================================================================

	//=============================================================================================
	private void parseObject() {
		
		if (!tokenMatch(KEYWORD, "o")) parseError("'o' expected");
		scanner.scan();

		skipWhite();
		
		String name = parseAnyName();
		objectBuilder.startObject(name);
		
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseObjectEnd() {
		
		if (!tokenMatch(KEYWORD, "o")) parseError("'o' expected");
		scanner.scan();

		skipWhite();

		objectBuilder.endObject();
		
		String name = parseAnyName();
		boolean leaveObjectParsing = true;
		if (!name.equals("off")) {
			objectBuilder.startObject(name);
			leaveObjectParsing = false;
		}
			
		return leaveObjectParsing;

	}
	//=============================================================================================
	
	//=============================================================================================
	private void parseVertex() {
		
		if (!tokenMatch(KEYWORD, "v")) parseError("'v' expected");
		scanner.scan();
			
		float vx = 0f;
		float vy = 0f;
		float vz = 0f;
		float vw = 0f;
		
		skipWhite();

		vx = parseDecimalNumber();

		skipWhite();

		vy = parseDecimalNumber();

		skipWhite();
		
		vz = parseDecimalNumber();

		skipWhite();
		
		if (
			!tokenMatch(COMMENT) &&
			!tokenMatch(LINEBREAK) &&
			!tokenMatch(ENDOFSTREAM)) {
			vw = parseDecimalNumber();
		}

		objectBuilder.writeVertex(vx, vy, vz, vw);
		
		//parseLineEnd();
		
	}
	//=============================================================================================

	//=============================================================================================
	private void parseTexture() {

		if (!tokenMatch(KEYWORD, "vt")) parseError("'vt' expected");
		scanner.scan();

		float tu = 0f;
		float tv = 0f;
		float tw = 0f;
		
		skipWhite();

		tu = parseDecimalNumber();

		skipWhite();
		
		if (
			!tokenMatch(COMMENT) &&
			!tokenMatch(LINEBREAK) &&
			!tokenMatch(ENDOFSTREAM)) {
			tv = parseDecimalNumber();
		}

		skipWhite();

		if (
			!tokenMatch(COMMENT) &&
			!tokenMatch(LINEBREAK) &&
			!tokenMatch(ENDOFSTREAM)) {
			tw = parseDecimalNumber();
		}
		
		objectBuilder.writeTexture(tu, tv, tw);
		
	}
	//=============================================================================================

	//=============================================================================================
	private void parseNormal() {

		if (!tokenMatch(KEYWORD, "vn")) parseError("'vn' expected");
		scanner.scan();

		float nx = 0f;
		float ny = 0f;
		float nz = 0f;
		
		skipWhite();

		nx = parseDecimalNumber();

		skipWhite();

		ny = parseDecimalNumber();

		skipWhite();

		nz =parseDecimalNumber();

		objectBuilder.writeNormal(nx, ny, nz);
		
		//parseLineEnd();

	}
	//=============================================================================================

	//=============================================================================================
	private void parseParameter() {

		if (!tokenMatch(KEYWORD, "vp")) parseError("'vp' expected");
		scanner.scan();

		float pu = 0f;
		float pv = 0f;
		float pw = 0f;
		
		skipWhite();

		pu = parseDecimalNumber();

		skipWhite();
		
		if (
			!tokenMatch(COMMENT) &&
			!tokenMatch(LINEBREAK) &&
			!tokenMatch(ENDOFSTREAM)) {
			pv = parseDecimalNumber();
		}

		skipWhite();

		if (
			!tokenMatch(COMMENT) &&
			!tokenMatch(LINEBREAK) &&
			!tokenMatch(ENDOFSTREAM)) {
			pw = parseDecimalNumber();
		}
		
		objectBuilder.writeParameter(pu, pv, pw);
		
	}
	//=============================================================================================
	
	//=============================================================================================
	private void parseSmoothing() {
		
		if (!tokenMatch(KEYWORD, "s")) parseError("'s' expected");
		scanner.scan();

		skipWhite();

		if (tokenMatch(KEYWORD, "off")) {
			scanner.scan();
		} else {
			parsePositiveNumber();
		}

	}
	//=============================================================================================
	
	//=============================================================================================
	private void parseFace() {
		
		if (!tokenMatch(KEYWORD, "f")) parseError("'f' expected");
		scanner.scan();
			
		objectBuilder.startFace();

		skipWhite();

		parseFaceIndex();

		skipWhite();

		parseFaceIndex();

		skipWhite();

		parseFaceIndex();

		skipWhite();
		
		if (
			!tokenMatch(COMMENT) &&
			!tokenMatch(LINEBREAK) &&
			!tokenMatch(ENDOFSTREAM)
		) {
			parseFaceIndex();
		}

		objectBuilder.endFace();
		
	}
	//=============================================================================================

	//=============================================================================================
	private void parseFaceIndex() {
		
		int iv = 0;
		int it = 0;
		int in = 0;		
		
		iv = parsePositiveNumber();
		
		if (tokenMatch(SYMBOL, "/")) {
			scanner.scan();
			
			if (
				tokenMatch(SYMBOL, "+") ||
				tokenMatch(NUMBER)
			) {
				it = parsePositiveNumber();
				if (tokenMatch(SYMBOL, "/")) {
					scanner.scan();
					in = parsePositiveNumber();
				}
			}
			else if (tokenMatch(SYMBOL, "/")) {
				scanner.scan();
				in = parsePositiveNumber();
			} else {
				parseError("'/' or number expected");
			}
			
		}

		objectBuilder.writeFaceIndex(iv, it, in);
		
	}
	//=============================================================================================
	
	//=============================================================================================
	private void parsePolyline() {

		if (!tokenMatch(KEYWORD, "l")) parseError("'l' expected");
		scanner.scan();

		objectBuilder.startLine();
		
		skipWhite();

		while (
			tokenMatch(SYMBOL, "+") ||
			tokenMatch(NUMBER)
		) {
			
			int in = parsePositiveNumber();

			objectBuilder.writeLineIndex(in);
			
			skipWhite();
			
		}
		
		objectBuilder.endLine();
		
	}
	//=============================================================================================

	//=============================================================================================
	private void parseMtllib() {
		
		if (!tokenMatch(KEYWORD, "mtllib")) parseError("'mtllib' expected");
		scanner.scan();

		skipWhite();
		String path = parseAnyName();
		
		try {
			File mtlFile = new File(base, path);
			Reader reader = new FileReader(mtlFile);
			materialParser.init(reader);
			materialParser.parse();
			materialBuilder.build(materialLibrary);
			reader.close();
			objectBuilder.materialLib(path, materialLibrary);
			materialLibrary.clear();
		} catch (Exception e) {
			throw new X(e);
		}
		
	}
	//=============================================================================================

	//=============================================================================================
	private void parseUsemtl() {
		
		if (!tokenMatch(KEYWORD, "usemtl")) parseError("'usemtl' expected");
		scanner.scan();

		skipWhite();

		String mtlname = parseAnyName();
		
		objectBuilder.materialUse(mtlname);
		
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
	private String parseAnyName() {
		String name = "";
		while (
			!tokenMatch(WHITESPACE) &&
			!tokenMatch(LINEBREAK) &&
			!tokenMatch(ENDOFSTREAM)
		) {
			name += scanner.token();
			scanner.scan();
		}
		if (name.isBlank()) parseError("Any text expected");
		return name;
	}
	//=============================================================================================
	
	//=============================================================================================
	private int parsePositiveNumber() {
		String sNumber = "";
		if (tokenMatch(SYMBOL, "+")) {
			sNumber += scanner.token();
			scanner.scan();
		}
		if (!tokenMatch(NUMBER)) parseError("Number expected");
		sNumber += scanner.token();
		scanner.scan();
		return Integer.parseInt(sNumber);
	}
	//=============================================================================================

	//=============================================================================================
	private void skipWhite() {
		while (
			tokenMatch(WHITESPACE)
		) {
			scanner.scan();
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	private void parseLineEnd() {
		while (tokenMatch(COMMENT) || tokenMatch(WHITESPACE)) {
			scanner.scan();
		}

		if (!tokenMatch(LINEBREAK) && !tokenMatch(ENDOFSTREAM))
			parseError("Linebreak or End of Stream expected");

		if (tokenMatch(LINEBREAK))
			scanner.scan();
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
