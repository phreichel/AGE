//*************************************************************************************************
package age.mesh.mtl;
//*************************************************************************************************

import java.io.Reader;
import age.util.Scanner;
import age.util.Symbol;
import age.util.X;
import static age.util.Symbol.*;

//*************************************************************************************************
public class MaterialParser {

	//=============================================================================================
	private Scanner scanner = new MaterialScanner();
	//=============================================================================================

	//=============================================================================================
	private MaterialBuilder materialBuilder = null;
	//=============================================================================================

	//=============================================================================================
	public MaterialParser(MaterialBuilder materialBuilder) {
		this.materialBuilder = materialBuilder;
	}
	//=============================================================================================

	//=============================================================================================
	public void init(Reader reader) {
		scanner.init(reader);		
	}
	//=============================================================================================
	
	//=============================================================================================
	public void parse() {
		materialBuilder.startFile();
		while (!scanner.symbol().equals(ENDOFSTREAM)) {
			parseLine();
		}
		materialBuilder.endFile();
	}
	//=============================================================================================

	//=============================================================================================
	private void parseLine() {
		skipWhite();
		if (tokenMatch(KEYWORD, "newmtl")) parseMaterial();
		else if (tokenMatch(KEYWORD, "Ns")) parseNs();
		else if (tokenMatch(KEYWORD, "Ka")) parseKa();
		else if (tokenMatch(KEYWORD, "Kd")) parseKd();
		else if (tokenMatch(KEYWORD, "Ks")) parseKs();
		else if (tokenMatch(KEYWORD, "Ke")) parseKe();
		else if (tokenMatch(KEYWORD, "Tf")) parseTf();
		else if (tokenMatch(KEYWORD, "Ni")) parseNi();
		else if (tokenMatch(KEYWORD, "d"))  parseD();
		else if (tokenMatch(KEYWORD, "Tr")) parseTr();
		else if (tokenMatch(KEYWORD, "map_Ns")) parseMapNs();
		else if (tokenMatch(KEYWORD, "map_Ka")) parseMapKa();
		else if (tokenMatch(KEYWORD, "map_Kd")) parseMapKd();
		else if (tokenMatch(KEYWORD, "map_Bump")) parseMapBump();
		else if (tokenMatch(KEYWORD, "illum")) parseIllum();
		parseLineEnd();
	}
	//=============================================================================================

	//=============================================================================================
	private void parseMaterial() {
		
		if (!tokenMatch(KEYWORD, "newmtl")) parseError("'newmtl' expected");
		scanner.scan();

		skipWhite();
		
		String name = parseAnyName();

		materialBuilder.startNewMaterial(name);
			
	}
	//=============================================================================================

	//=============================================================================================
	private void parseNs() {

		if (!tokenMatch(KEYWORD, "Ns")) parseError("'Ns' expected");
		scanner.scan();

		skipWhite();
		
		float Ns = parseDecimalNumber();

		materialBuilder.writeNs(Ns);
			
	}
	//=============================================================================================

	//=============================================================================================
	private void parseNi() {
		
		if (!tokenMatch(KEYWORD, "Ni")) parseError("'Ni' expected");
		scanner.scan();

		skipWhite();
		
		float Ni = parseDecimalNumber();

		materialBuilder.writeNi(Ni);
			
	}
	//=============================================================================================

	//=============================================================================================
	private void parseD() {
		
		if (!tokenMatch(KEYWORD, "d")) parseError("'d' expected");
		scanner.scan();

		skipWhite();
		
		float d = parseDecimalNumber();

		materialBuilder.writeD(d);
			
	}
	//=============================================================================================

	//=============================================================================================
	private void parseTr() {
		
		if (!tokenMatch(KEYWORD, "Tr")) parseError("'Tr' expected");
		scanner.scan();
		
		skipWhite();

		float Tr = parseDecimalNumber();

		materialBuilder.writeTr(Tr);
			
	}
	//=============================================================================================
	
	//=============================================================================================
	private void parseKa() {
		
		if (!tokenMatch(KEYWORD, "Ka")) parseError("'Ka' expected");
		scanner.scan();
		
		float cr = 0f;
		float cg = 0f;
		float cb = 0f;
		
		skipWhite();

		cr = parseDecimalNumber();

		skipWhite();

		cg = parseDecimalNumber();

		skipWhite();

		cb = parseDecimalNumber();

		materialBuilder.writeKa(cr, cg, cb);
		
	}
	//=============================================================================================

	//=============================================================================================
	private void parseKd() {
		
		if (!tokenMatch(KEYWORD, "Kd")) parseError("'Kd' expected");
		scanner.scan();
		
		float cr = 0f;
		float cg = 0f;
		float cb = 0f;
		
		skipWhite();

		cr = parseDecimalNumber();

		skipWhite();

		cg = parseDecimalNumber();

		skipWhite();

		cb = parseDecimalNumber();

		materialBuilder.writeKd(cr, cg, cb);

	}
	//=============================================================================================

	//=============================================================================================
	private void parseKs() {
		
		if (!tokenMatch(KEYWORD, "Ks")) parseError("'Ks' expected");
		scanner.scan();
		
		float cr = 0f;
		float cg = 0f;
		float cb = 0f;
		
		skipWhite();

		cr = parseDecimalNumber();

		skipWhite();

		cg = parseDecimalNumber();

		skipWhite();

		cb = parseDecimalNumber();

		materialBuilder.writeKs(cr, cg, cb);

	}
	//=============================================================================================

	//=============================================================================================
	private void parseKe() {
		
		if (!tokenMatch(KEYWORD, "Ke")) parseError("'Ke' expected");
		scanner.scan();
		
		float cr = 0f;
		float cg = 0f;
		float cb = 0f;
		
		skipWhite();

		cr = parseDecimalNumber();

		skipWhite();

		cg = parseDecimalNumber();

		skipWhite();

		cb = parseDecimalNumber();

		materialBuilder.writeKe(cr, cg, cb);

	}
	//=============================================================================================

	//=============================================================================================
	private void parseTf() {
		
		if (!tokenMatch(KEYWORD, "Tf")) parseError("'Tf' expected");
		scanner.scan();
		
		skipWhite();
		parseDecimalNumber();

		skipWhite();
		parseDecimalNumber();

		skipWhite();
		parseDecimalNumber();

		// materialBuilder.writeTf(cr, cg, cb);

	}
	//=============================================================================================
	
	//=============================================================================================
	private void parseIllum() {
		
		if (!tokenMatch(KEYWORD, "illum")) parseError("'illum' expected");
		scanner.scan();

		skipWhite();
		
		int illum = parsePositiveNumber();

		materialBuilder.writeIllum(illum);
		
	}
	//=============================================================================================
	
	//=============================================================================================
	private void parseMapNs() {
		
		if (!tokenMatch(KEYWORD, "map_Ns")) parseError("'map_Ns' expected");
		scanner.scan();

		skipWhite();

		String name = parseAnyName();
		
		materialBuilder.writeSpecularMap(name);
	
	}
	//=============================================================================================

	//=============================================================================================
	private void parseMapKa() {
		
		if (!tokenMatch(KEYWORD, "map_Ka")) parseError("'map_Ka' expected");
		scanner.scan();

		skipWhite();

		String name = parseAnyName();
		
		materialBuilder.writeAmbientMap(name);

	}
	//=============================================================================================
	
	//=============================================================================================
	private void parseMapKd() {
		
		if (!tokenMatch(KEYWORD, "map_Kd")) parseError("'map_Kd' expected");
		scanner.scan();

		skipWhite();

		String name = parseAnyName();
		
		materialBuilder.writeDiffuseMap(name);

	}
	//=============================================================================================

	//=============================================================================================
	private void parseMapBump() {
		
		if (!tokenMatch(KEYWORD, "map_Bump")) parseError("'map_Bump' expected");
		scanner.scan();

		skipWhite();

		String name = parseAnyName();
		
		materialBuilder.writeBumpMap(name);

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
	private boolean tokenMatch(Symbol Symbol) {
		return
			scanner.symbol().equals(Symbol);
	}
	//=============================================================================================
	
	//=============================================================================================
	private boolean tokenMatch(Symbol Symbol, String token) {
		return
			scanner.symbol().equals(Symbol) &&
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
