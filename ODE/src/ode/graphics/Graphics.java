//*************************************************************************************************
package ode.graphics;

import javax.vecmath.Color4f;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

//*************************************************************************************************

//*************************************************************************************************
public class Graphics {

	//=============================================================================================
	private GLU glu; 
	private GL2 glAPI;
	//=============================================================================================

	//=============================================================================================
	public void setOrtho(float left, float right, float bottom, float top) {
		glAPI.glMatrixMode(GL2.GL_PROJECTION);
		glAPI.glLoadIdentity();
		glu.gluOrtho2D(left, right, bottom, top);
		glAPI.glMatrixMode(GL2.GL_MODELVIEW);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void setPerspective(float fovy, float aspect, float near, float far) {
		glAPI.glMatrixMode(GL2.GL_PROJECTION);
		glAPI.glLoadIdentity();
		glu.gluPerspective(fovy, aspect, near, far);
		glAPI.glMatrixMode(GL2.GL_MODELVIEW);
	}
	//=============================================================================================

	//=============================================================================================
	public void setLighting() {
		glAPI.glEnable(GL2.GL_CULL_FACE);
		glAPI.glEnable(GL2.GL_LIGHTING);
	}
	//=============================================================================================

	//=============================================================================================
	public void setDrawing() {
		glAPI.glDisable(GL2.GL_CULL_FACE);
		glAPI.glDisable(GL2.GL_LIGHTING);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void pushTransform() {
		glAPI.glPushMatrix();
	}
	//=============================================================================================

	//=============================================================================================
	public void popTransform() {
		glAPI.glPopMatrix();
	}
	//=============================================================================================

	//=============================================================================================
	public void setAmbientMaterialColor(Color4f color) {
		setAmbientMaterialColor(color.x, color.y, color.z, color.w);
	}
	//=============================================================================================

	//=============================================================================================
	public void setDiffuseMaterialColor(Color4f color) {
		setDiffuseMaterialColor(color.x, color.y, color.z, color.w);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void setEmissionMaterialColor(Color4f color) {
		setEmissionMaterialColor(color.x, color.y, color.z, color.w);
	}
	//=============================================================================================

	//=============================================================================================
	public void setSpecularMaterialColor(Color4f color) {
		setSpecularMaterialColor(color.x, color.y, color.z, color.w);
	}
	//=============================================================================================
	
	//=============================================================================================
	public void setAmbientMaterialColor(float r, float g, float b, float a) {
		glAPI.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, new float[] {r, g, b, a}, 0);
	}
	//=============================================================================================

	//=============================================================================================
	public void setDiffuseMaterialColor(float r, float g, float b, float a) {
		glAPI.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, new float[] {r, g, b, a}, 0);
	}
	//=============================================================================================

	//=============================================================================================
	public void setEmissionMaterialColor(float r, float g, float b, float a) {
		glAPI.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, new float[] {r, g, b, a}, 0);
	}
	//=============================================================================================

	//=============================================================================================
	public void setSpecularMaterialColor(float r, float g, float b, float a) {
		glAPI.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, new float[] {r, g, b, a}, 0);
	}
	//=============================================================================================

	//=============================================================================================
	public void setMaterialShininess(float shininess) {
		glAPI.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, shininess);
	}
	//=============================================================================================
	
	
}
//*************************************************************************************************
