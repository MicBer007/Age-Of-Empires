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
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolbox.Location;
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

        List<Unit> units = new ArrayList<Unit>();
        
        units.add(new Unit(unitModel, new Location(new Vector3f(0, 0, 0)), 0, 0, 0, 1, 100, 10));
        units.add(new Unit(unitModel, new Location(new Vector3f(0, 0, 50)), 0, 0, 0, 1, 100, 5));
        
        units.get(0).setTarget(units.get(1));
        units.get(1).setTarget(units.get(0));
        
        for(Unit unit: units) {
        	unit.setBeingUsed(true);
        }
        
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
        
        while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_X)){
            camera.move();
            for(Unit unit: units) {
            	if(unit.isBeingUsed()) {
            		unit.attack(unit.getTarget());
            		renderer.processEntity(unit);
            		if(unit.getHealth() <= 0) {
            			units.get(units.indexOf(unit)).setBeingUsed(false);
            		}
            	}
            }
            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);
            renderer.processTerrain(terrain3);
            renderer.processTerrain(terrain4);
            renderer.processLight(light);
            renderer.render(camera);
            DisplayManager.updateDisplay();
        }
        for(Unit unit: units) {
        	System.out.println(unit.getHealth());
        	System.out.println(unit.getTarget());
        }
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
 
    }
 
}