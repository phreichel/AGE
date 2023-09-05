//*************************************************************************************************
package ode.asset;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import ode.util.ODEException;

//*************************************************************************************************

//*************************************************************************************************
public class Assets {

	//=============================================================================================
	private final Map<String, String> texts = new HashMap<>();
	private final Map<String, Texture> textures = new HashMap<>();
	private final Map<String, TextRenderer> fonts = new HashMap<>();
	private final Map<String, Color> colors = new HashMap<>();
	private final Map<String, Mesh> meshes = new HashMap<>();
	//=============================================================================================

	//=============================================================================================
	private final List<String> pendingFontFiles = new ArrayList<>();
	private final List<String> pendingTextureFiles = new ArrayList<>();
	//=============================================================================================
	
	//=============================================================================================
	public void loadTexts(String path) {
		try {
			File file = new File(path);
			Reader fileReader = new FileReader(file);
			ResourceBundle bundle = new PropertyResourceBundle(fileReader);
			for (String key : bundle.keySet()) {
				String value = bundle.getString(key);
				texts.put(key, value);
			}
			fileReader.close();
		} catch (Exception e) {
			throw new ODEException(e);
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
	public int getInt(String name) {
		String text = texts.get(name);
		return Integer.parseInt(text);
	}
	//=============================================================================================

	//=============================================================================================
	public boolean getBoolean(String name) {
		String text = texts.get(name);
		return Boolean.parseBoolean(text);
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
				text = text.replace(src, dst);
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
	public void loadTextures(String path) {
		pendingTextureFiles.add(path);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void loadPendingTextures() {
		try {
			for (String path : pendingTextureFiles) {
				File file = new File(path);
				Reader fileReader = new FileReader(file);
				ResourceBundle bundle = new PropertyResourceBundle(fileReader);
				for (String key : bundle.keySet()) {
					String value = bundle.getString(key);
					loadTexture(key, value, true);
				}
				fileReader.close();
			}
		} catch (Exception e) {
			throw new ODEException(e);
		}
		pendingTextureFiles.clear();
	}
	//=============================================================================================
	
	//=============================================================================================
	public void loadTexture(String name, String path, boolean force) {
		if (textures.containsKey(name) && !force) return;
		try {
			Texture texture = TextureIO.newTexture(new File(path), true);
			textures.put(name, texture);
		} catch (Exception e) {
			throw new ODEException(e);
		}
	}
	//=============================================================================================

	//=============================================================================================
	public Texture getTexture(String name) {
		return textures.get(name);
	}
	//=============================================================================================

	//=============================================================================================
	public void loadFonts(String path) {
		pendingFontFiles.add(path);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void loadPendingFonts() {
		try {
			for (String path : pendingFontFiles) {
				File file = new File(path);
				Reader fileReader = new FileReader(file);
				ResourceBundle bundle = new PropertyResourceBundle(fileReader);
				for (String key : bundle.keySet()) {
					String value = bundle.getString(key);
					loadFont(key, value, true);
				}
				fileReader.close();
			}
		} catch (Exception e) {
			throw new ODEException(e);
		}
		pendingFontFiles.clear();
	}
	//=============================================================================================
	
	//=============================================================================================
	public void loadFont(String name, String fontspec, boolean force) {
		if (fonts.containsKey(name) && !force) return;
		java.awt.Font font = java.awt.Font.decode(fontspec);
		TextRenderer renderer = new TextRenderer(font, true);
		fonts.put(name, renderer);
	}
	//=============================================================================================

	//=============================================================================================
	public TextRenderer getFont(String name) {
		return fonts.get(name);
	}
	//=============================================================================================
	
}
//*************************************************************************************************

