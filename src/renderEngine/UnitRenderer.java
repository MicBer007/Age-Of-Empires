package renderEngine;

import models.RawModel;
import models.TexturedModel;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import shaders.EntityShader;
import shaders.UnitShader;
import textures.ModelTexture;
import toolbox.Maths;

import entities.Entity;
import entities.Unit;

public class UnitRenderer {
	
    private UnitShader shader;
    
    public UnitRenderer(UnitShader shader, Matrix4f projectionMatrix) {
    	this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }
    
	public void render(Map<TexturedModel, List<Unit>> units) {
    	
    	for(TexturedModel model: units.keySet()) {
    		
    		List<Unit> batch = units.get(model);
    		
    		Vector3f UnitColour = batch.get(0).getTeamColour();
    		
    		prepareTexturedModel(model);
    		
    		for(Unit unit : batch) {
    			prepareInstance(unit);
    			GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
    		}
    		unbindTexturedModel();
    		
    	}
    	
    }
    
    private void prepareTexturedModel(TexturedModel model) {
    	RawModel rawModel = model.getRawModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        ModelTexture texture = model.getTexture();
        if (texture.isHasTransparency()) {
        	MasterRenderer.disableCulling();
        }
        shader.loadFakeLightingVariable(texture.isUseFakeLighting());
        shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
    }
    
    private void unbindTexturedModel() {
    	
    	MasterRenderer.enableCulling();
    	GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    	
    }
    
    private void prepareInstance(Unit unit) {
        shader.loadUnitColour(unit.getTeamColour());
    	Matrix4f transformationMatrix = Maths.createTransformationMatrix(unit.getLocation(),
    			unit.getRotX(), unit.getRotY(), unit.getRotZ(), unit.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
    }
    
}