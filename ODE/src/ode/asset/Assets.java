//*************************************************************************************************
package ode.asset;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jogamp.opengl.util.texture.TextureIO;

import ode.util.ODEException;

//*************************************************************************************************

//*************************************************************************************************
public class Assets {

	//=============================================================================================
	private final Map<String, String>  texts = new HashMap<>();
	private final Map<String, Color>   colors = new HashMap<>();
	private final Map<String, Font>    fonts = new HashMap<>();
	private final Map<String, Texture> textures = new HashMap<>();
	private final Map<String, Mesh>    meshes = new HashMap<>();
	//=============================================================================================

	//=============================================================================================
	public void loadTexts(String baseName) {
		ResourceBundle bundle = ResourceBundle.getBundle(baseName);
		for (String key : bundle.keySet()) {
			String value = bundle.getString(key);
			texts.put(key, value);
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	public String getText(String name) {
		String text = texts.get(name);
		if (text == null) {
			text = String.format("<MSG:%s>", name);
		}
		return text;
	}
	//=============================================================================================

	//=============================================================================================
	public String resolveText(String name) {
		Map<String, String> replaceMap = new HashMap<>();
		Pattern pattern = Pattern.compile("\\$\\(([^)]*)\\)");
		String text = getText(name);
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			replaceMap.clear();
			do {
				String replaceName = matcher.group(0);
				String matchName = matcher.group(1);
				String matchText = getText(matchName);
				replaceMap.put(replaceName, matchText);
			} while (matcher.find());
			for (Entry<String, String> entry : replaceMap.entrySet()) {
				String src = entry.getKey();
				String dst = entry.getValue();
				text.replace(src, dst);
			}
			matcher = pattern.matcher(text);
		}
		return text;
	}
	//=============================================================================================
	
	//=============================================================================================
	public String formatText(String name, Object ... objects) {
		String format = getText(name);
		if (format != null) {
			format = String.format(format, objects);
		}
		return format;
	}
	//=============================================================================================

	//=============================================================================================
	public void loadTexture(String name, String path, boolean force) {
		if (textures.containsKey(name) && !force) return;
		try {
			com.jogamp.opengl.util.texture.Texture
				joglTexture = TextureIO.newTexture(new File(path), true);
			textures.put(name, null);
		} catch (Exception e) {
			throw new ODEException(e);
		}
	}
	//=============================================================================================
	
}
//*************************************************************************************************

