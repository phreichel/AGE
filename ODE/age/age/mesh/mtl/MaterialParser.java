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
	public void assign(MaterialBuilder materialBuilder) {
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
		if (parseMaterial());
		else if (parseNs());
		else if (parseKa());
		else if (parseKd());
		else if (parseKs());
		else if (parseKe());
		else if (parseNi());
		else if (parseD());
		else if (parseTr());
		else if (parseMapNs());
		else if (parseMapKd());
		else if (parseMapBump());
		else if (parseIllum());
		else parseEmptyLine();
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseMaterial() {
		if (tokenMatch(KEYWORD, "newmtl")) {
			scanner.scan();

			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();

			if (!tokenMatch(NAME)) parseError("Name expected");
			String name = scanner.token();
			scanner.scan();
			while (
				!tokenMatch(WHITESPACE) &&
				!tokenMatch(COMMENT) &&
				!tokenMatch(LINEBREAK) &&
				!tokenMatch(ENDOFSTREAM)
			) {
				name += scanner.token();
				scanner.scan();
			}

			materialBuilder.startNewMaterial(name);
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseNs() {
		if (tokenMatch(KEYWORD, "Ns")) {
			scanner.scan();
			
			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();
			float Ns = parseDecimalNumber();

			materialBuilder.writeNs(Ns);
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseNi() {
		if (tokenMatch(KEYWORD, "Ni")) {
			scanner.scan();
			
			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();
			float Ni = parseDecimalNumber();

			materialBuilder.writeNi(Ni);
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseD() {
		if (tokenMatch(KEYWORD, "d")) {
			scanner.scan();
			
			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();
			float d = parseDecimalNumber();

			materialBuilder.writeD(d);
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseTr() {
		if (tokenMatch(KEYWORD, "Tr")) {
			scanner.scan();
			
			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();
			float Tr = parseDecimalNumber();

			materialBuilder.writeTr(Tr);
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================
	
	//=============================================================================================
	private boolean parseKa() {
		if (tokenMatch(KEYWORD, "Ka")) {
			scanner.scan();
			
			float cr = 0f;
			float cg = 0f;
			float cb = 0f;
			
			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();
			cr = parseDecimalNumber();

			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();
			cg = parseDecimalNumber();

			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();
			cb = parseDecimalNumber();

			materialBuilder.writeKa(cr, cg, cb);
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseKd() {
		if (tokenMatch(KEYWORD, "Kd")) {
			scanner.scan();
			
			float cr = 0f;
			float cg = 0f;
			float cb = 0f;
			
			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();
			cr = parseDecimalNumber();

			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();
			cg = parseDecimalNumber();

			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();
			cb = parseDecimalNumber();

			materialBuilder.writeKd(cr, cg, cb);
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseKs() {
		if (tokenMatch(KEYWORD, "Ks")) {
			scanner.scan();
			
			float cr = 0f;
			float cg = 0f;
			float cb = 0f;
			
			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();
			cr = parseDecimalNumber();

			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();
			cg = parseDecimalNumber();

			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();
			cb = parseDecimalNumber();

			materialBuilder.writeKs(cr, cg, cb);
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseKe() {
		if (tokenMatch(KEYWORD, "Ke")) {
			scanner.scan();
			
			float cr = 0f;
			float cg = 0f;
			float cb = 0f;
			
			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();
			cr = parseDecimalNumber();

			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();
			cg = parseDecimalNumber();

			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();
			cb = parseDecimalNumber();

			materialBuilder.writeKe(cr, cg, cb);
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================
	
	//=============================================================================================
	private boolean parseIllum() {
		if (tokenMatch(KEYWORD, "illum")) {
			scanner.scan();
			
			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();

			if (!tokenMatch(NUMBER)) parseError("Number expected");
			int illum = Integer.parseInt(scanner.token());
			scanner.scan();

			materialBuilder.writeIllum(illum);
			
			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================
	
	//=============================================================================================
	private boolean parseMapNs() {
		if (tokenMatch(KEYWORD, "map_Ns")) {
			scanner.scan();

			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();

			if (!tokenMatch(NAME)) parseError("Name expected");
			String name = scanner.token();
			scanner.scan();
			while (
				!tokenMatch(WHITESPACE) &&
				!tokenMatch(COMMENT) &&
				!tokenMatch(LINEBREAK) &&
				!tokenMatch(ENDOFSTREAM)
			) {
				name += scanner.token();
				scanner.scan();
			}

			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseMapKd() {
		if (tokenMatch(KEYWORD, "map_Kd")) {
			scanner.scan();

			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();

			if (!tokenMatch(NAME)) parseError("Name expected");
			String name = scanner.token();
			scanner.scan();
			while (
				!tokenMatch(WHITESPACE) &&
				!tokenMatch(COMMENT) &&
				!tokenMatch(LINEBREAK) &&
				!tokenMatch(ENDOFSTREAM)
			) {
				name += scanner.token();
				scanner.scan();
			}

			parseEmptyLine();
			
			return true;
		}
		return false;
	}
	//=============================================================================================

	//=============================================================================================
	private boolean parseMapBump() {
		if (tokenMatch(KEYWORD, "map_Bump")) {
			scanner.scan();

			if (!tokenMatch(WHITESPACE)) parseError("Whitespace expected");
			scanner.scan();

			if (!tokenMatch(NAME)) parseError("Name expected");
			String name = scanner.token();
			scanner.scan();
			while (
				!tokenMatch(WHITESPACE) &&
				!tokenMatch(COMMENT) &&
				!tokenMatch(LINEBREAK) &&
				!tokenMatch(ENDOFSTREAM)
			) {
				name += scanner.token();
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
