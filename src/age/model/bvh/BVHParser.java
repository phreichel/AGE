//*************************************************************************************************
package age.model.bvh;
//*************************************************************************************************

import java.io.Reader;
import age.util.Scanner;
import age.util.Symbol;
import age.util.X;

import static age.util.Symbol.*;

//*************************************************************************************************
public class BVHParser {

	//=============================================================================================
	private final Scanner scanner = new BVHScanner();
	private final BVHBuilder bvhBuilder;
	//=============================================================================================

	//=============================================================================================
	public BVHParser(BVHBuilder bvhBuilder) {
		this.bvhBuilder = bvhBuilder;
	}
	//=============================================================================================
	
	//=============================================================================================
	private int nchannels = 0;
	private int nframes = 0;
	//=============================================================================================
	
	//=============================================================================================
	public void init(Reader reader) {
		nchannels = 0;
		nframes = 0;
		scanner.init(reader);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void parse() {
		bvhBuilder.startFile();
		skipWhiteAndLinebreak();
		parseHierarchy();
		skipWhiteAndLinebreak();
		parseMotion();
		if (!tokenMatch(ENDOFSTREAM))
			parseError("End of Stream expected");
		bvhBuilder.endFile();
	}
	//=============================================================================================

	//=============================================================================================
	private void parseHierarchy() {
		
		nchannels = 0;

		if (!tokenMatch(KEYWORD, "HIERARCHY")) parseError("'HIERARCHY' expected");
		scanner.scan();

		skipWhiteAndLinebreak();

		if (!tokenMatch(KEYWORD, "ROOT")) parseError("'ROOT' expected");
		scanner.scan();

		skipWhite();
		
		String name = parseAnyName();
		
		skipWhiteAndLinebreak();

		parseBoneBody(name);
		
	}
	//=============================================================================================

	//=============================================================================================
	private void parseBoneBody(String name) {

		if (!tokenMatch(SYMBOL, "{")) parseError("'{' expected");
		scanner.scan();

		skipWhiteAndLinebreak();

		if (!tokenMatch(KEYWORD, "OFFSET")) parseError("'OFFSET' expected");
		scanner.scan();

		skipWhite();

		float ox = parseDecimalNumber();

		skipWhite();

		float oy = parseDecimalNumber();

		skipWhite();

		float oz = parseDecimalNumber();
		
		bvhBuilder.startBone(name, ox, oy, oz);

		skipWhiteAndLinebreak();

		if (!tokenMatch(KEYWORD, "CHANNELS")) parseError("'CHANNELS' expected");
		scanner.scan();

		skipWhite();

		int count = parsePositiveNumber();
		nchannels += count;

		bvhBuilder.writeChannelCount(count);
		
		skipWhite();
		
		for (int i=0; i<count; i++) {

			String channelName = parseAnyName();
			
			bvhBuilder.writeChannelName(channelName);
			
			skipWhite();
			
		}
		
		skipWhiteAndLinebreak();

		if (tokenMatch(KEYWORD, "JOINT")) {

			while (tokenMatch(KEYWORD, "JOINT")) {
				scanner.scan();
				
				skipWhite();
				
				String jointName = parseAnyName();
	
				skipWhiteAndLinebreak();
				
				parseBoneBody(jointName);

				skipWhiteAndLinebreak();
			}
			
		} else if (tokenMatch(KEYWORD, "End")) {

			scanner.scan();
			
			skipWhite();
			
			if (!tokenMatch(KEYWORD, "Site")) parseError("'Site' expected");
			scanner.scan();

			skipWhiteAndLinebreak();
			
			parseBoneLeafBody();

			skipWhiteAndLinebreak();

		} else {
			parseError("'JOINT' or 'End' expected");
		}
		
		if (!tokenMatch(SYMBOL, "}")) parseError("'}' expected");
		scanner.scan();

		bvhBuilder.endBone();
		
		skipWhiteAndLinebreak();
		
	}
	//=============================================================================================

	//=============================================================================================
	private void parseBoneLeafBody() {

		if (!tokenMatch(SYMBOL, "{")) parseError("'{' expected");
		scanner.scan();

		skipWhiteAndLinebreak();

		if (!tokenMatch(KEYWORD, "OFFSET")) parseError("'OFFSET' expected");
		scanner.scan();

		skipWhite();

		float ox = parseDecimalNumber();

		skipWhite();

		float oy = parseDecimalNumber();

		skipWhite();

		float oz = parseDecimalNumber();

		bvhBuilder.startBone("End", ox, oy, oz);
		
		skipWhiteAndLinebreak();

		if (!tokenMatch(SYMBOL, "}")) parseError("'}' expected");
		scanner.scan();

		bvhBuilder.endBone();
		
		skipWhiteAndLinebreak();
		
	}
	//=============================================================================================

	//=============================================================================================
	public void parseMotion() {
		
		nframes = 0;

		if (!tokenMatch(KEYWORD, "MOTION")) parseError("'MOTION' expected");
		scanner.scan();
		
		skipWhiteAndLinebreak();

		if (!tokenMatch(KEYWORD, "Frames")) parseError("'Frames' expected");
		scanner.scan();

		if (!tokenMatch(SYMBOL, ":")) parseError("':' expected");
		scanner.scan();

		skipWhite();

		nframes = parsePositiveNumber();

		bvhBuilder.writeFrameCount(nframes);
		
		skipWhiteAndLinebreak();
		
		if (!tokenMatch(KEYWORD, "Frame")) parseError("'Frame' expected");
		scanner.scan();

		skipWhite();
		
		if (!tokenMatch(KEYWORD, "Time")) parseError("'Time' expected");
		scanner.scan();

		if (!tokenMatch(SYMBOL, ":")) parseError("':' expected");
		scanner.scan();

		skipWhite();

		float frameTime = parseDecimalNumber();
		
		bvhBuilder.writeFrameTime(frameTime);

		skipWhiteAndLinebreak();
		
		for (int i=0; i<nframes; i++) {
			
			parseFrame();
			
		}
		
	}
	//=============================================================================================

	//=============================================================================================
	private float[] frameValues;
	//=============================================================================================
	
	//=============================================================================================
	private void parseFrame() {
		
		if (frameValues == null) {
			frameValues = new float[nchannels];
		}
		
		for (int i=0; i<nchannels; i++) {
			
			float value = parseDecimalNumber();
			frameValues[i] = value;
					
			skipWhite();

		}
		
		bvhBuilder.writeFrameData(frameValues);
		
		if (!tokenMatch(LINEBREAK) && !tokenMatch(ENDOFSTREAM))
			parseError("Line break or End of Stream expected");

		skipWhiteAndLinebreak();
		
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
	private void skipWhite() {
		while (
			tokenMatch(WHITESPACE)
		) {
			scanner.scan();
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	private void skipWhiteAndLinebreak() {
		while (
			tokenMatch(WHITESPACE) ||
			tokenMatch(LINEBREAK)
		) {
			scanner.scan();
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
