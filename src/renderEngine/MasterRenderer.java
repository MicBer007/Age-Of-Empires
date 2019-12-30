package renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Unit;
import models.TexturedModel;
import shaders.EntityShader;
import shaders.TerrainShader;
import shaders.UnitShader;
import terrains.Terrain;

public class MasterRenderer {
	
    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000;
    
    private static final float RED = 0.6f;
    private static final float GREEN = 0.8f;
    private static final float BLUE = 1.0f;
    
    private Matrix4f projectionMatrix;
	
	private EntityShader entityShader = new EntityShader();
	private EntityRenderer entityRenderer;
	
	private TerrainShader terrainShader = new TerrainShader();
	private TerrainRenderer terrainRenderer;
	
	private UnitShader unitShader = new UnitShader();
	private UnitRenderer unitRenderer;
	
	private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	private Map<TexturedModel, List<Unit>> units = new HashMap<TexturedModel, List<Unit>>();
	
	private List<Terrain> terrains = new ArrayList<Terrain>();
	private List<Light> lights = new ArrayList<Light>();
	
	public MasterRenderer() {
		enableCulling();
        createProjectionMatrix();
        entityRenderer = new EntityRenderer(entityShader, projectionMatrix);
        terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
        unitRenderer = new UnitRenderer(unitShader, projectionMatrix);
	}
	
	public static void enableCulling() {
		GL11.glEnable(GL11.GL_CULL_FACE);
    	GL11.glCullFace(GL11.GL_BACK);
	}
	
	public static void disableCulling() {
		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	
	public void render(Camera camera) {
		
		prepare();
		entityShader.start();
		entityShader.loadSkyColour(RED, GREEN, BLUE);
		for(Light light:lights) {
			entityShader.loadLight(light);
		}
		entityShader.loadViewMatrix(camera);
		entityRenderer.render(entities);
		entityShader.stop();
		terrainShader.start();
		terrainShader.loadSkyColour(RED, GREEN, BLUE);
		for(Light light:lights) {
			terrainShader.loadLight(light);
		}
		terrainShader.loadViewMatrix(camera);
		terrainRenderer.render(terrains);
		terrainShader.stop();
		unitShader.start();
		unitShader.loadSkyColour(RED, GREEN, BLUE);
		for(Light light:lights) {
			unitShader.loadLight(light);
		}
		unitShader.loadViewMatrix(camera);
		unitRenderer.render(units);
		unitShader.stop();
		terrains.clear();
		entities.clear();
		units.clear();
		
	}
	
	public void processLight(Light light){
		lights.add(light);
	}
	
	public void processTerrain(Terrain terrain) {
		terrains.add(terrain);
	}
	
	public void processEntity(Entity entity) {
		
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if(batch != null) {
			batch.add(entity);
		}else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}
		
	}
	
	public void processUnit(Unit unit) {
		
		TexturedModel unitModel = unit.getModel();
		List<Unit> batch = units.get(unitModel);
		if(batch != null) {
			batch.add(unit);
		}else {
			List<Unit> newBatch = new ArrayList<Unit>();
			newBatch.add(unit);
			units.put(unitModel, newBatch);
		}
		
	}
	
	public void cleanUp() {
		entityShader.cleanUp();
		terrainShader.cleanUp();
	}
	
	public void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(RED, GREEN, BLUE, 1);
	}
    
    private void createProjectionMatrix(){
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;
 
        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
    }

	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
	
}
