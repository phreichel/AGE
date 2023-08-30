//*************************************************************************************************
package ode.asset;
//*************************************************************************************************

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import ode.util.ODEException;

//*************************************************************************************************
public class JOGLTextureManager implements ODETextureManager {

	//=============================================================================================
	private Map<String, JOGLTexture> textureMap = new HashMap<>();
	//=============================================================================================

	//=============================================================================================
	public ODETexture requestTexture(String path) {
		try {
			JOGLTexture joglTexture = textureMap.get(path);
			if (joglTexture == null) {
				File file = new File(path);
				Texture texture = TextureIO.newTexture(file, true);
				joglTexture = new JOGLTexture(path, texture);
				textureMap.put(path, joglTexture);
			}
			return joglTexture;
		} catch(Exception e) {
			throw new ODEException(e);
		}
	}
	//=============================================================================================

}
//*************************************************************************************************
