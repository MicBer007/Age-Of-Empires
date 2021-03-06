package engineTester;
 
import java.util.ArrayList;
import java.util.List;
 
import models.RawModel;
import models.TexturedModel;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
 
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Location;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolbox.MousePicker;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Unit;
 
public class MainGameLoop {
 
    public static void main(String[] args) {
 
        DisplayManager.createDisplay("");
        Loader loader = new Loader();
        
        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassTerrainTile"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirtTerrainTile"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("flowerTerrainTile"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("pathTerrainTile"));
        
        TerrainTexturePack texturePack = new TerrainTexturePack(
        		backgroundTexture, rTexture, gTexture, bTexture);
        
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap2"));
        
        RawModel unitRawModel = OBJLoader.loadObjModel("unit", loader);
        TexturedModel unitModel = new TexturedModel(unitRawModel, new ModelTexture(loader.loadTexture("unit")));
        

        Entity unit = new Entity(unitModel, new Vector3f(0, 0, 0), 0, 0, 0, 1);
        Location unitLocation = new Location(unit.getPosition());
        
        List<Entity> units = new ArrayList<Entity>();
        units.add(unit);
        
        Terrain terrain = new Terrain(0,0,loader, texturePack, blendMap);
        Terrain terrain2 = new Terrain(1,0,loader, texturePack, blendMap);
        Terrain terrain3 = new Terrain(1,1,loader, texturePack, blendMap);
        Terrain terrain4 = new Terrain(0,1,loader, texturePack, blendMap);
        Light light = new Light(new Vector3f(0, 250, 0), new Vector3f(1, 1, 1));
        Camera camera = new Camera(2.6f);   
        MasterRenderer renderer = new MasterRenderer();
        
        camera.setPosition(new Vector3f(0, 150, 0));
        camera.setPitch(65);
        camera.setYaw(45);
        
        MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrain);
        
        Entity selectedUnit = null;
        
        while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_X)){
            camera.move();
            if(Mouse.isButtonDown(0)) {
            	try {
            		picker.update();
                	if(unitLocation.getDistance(picker.getCurrentTerrainPoint()) < 100) {
                		System.out.println("unit selected");
                		selectedUnit = unit;
                	} else {
                		System.out.println("unit not selected");
                		selectedUnit = null;
                	}
            	} catch(NullPointerException e) {
            		System.out.println("you clicked too far away.");
            		e.printStackTrace();
            	}
            }
            if(Mouse.isButtonDown(1)) {
            	try {
            	picker.update();
            	selectedUnit.setPosition(picker.getCurrentTerrainPoint());
            	} catch(NullPointerException e) {
            		System.out.println("there was no selected entity.");
            		e.printStackTrace();
            	}
            }
            renderer.processEntity(unit);
            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);
            renderer.processTerrain(terrain3);
            renderer.processTerrain(terrain4);
            renderer.processLight(light);
            renderer.render(camera);
            DisplayManager.updateDisplay();
        }
 
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
 
    }
 
}