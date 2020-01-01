package engineTester;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import toolbox.Maths;
import toolbox.MousePicker;
import entities.Building;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.MobileUnit;
import entities.Unit;
 
public class MainGameLoop {
 
    public static void main(String[] args) {
 
        DisplayManager.createDisplay("");
        Loader loader = new Loader();
        
        Random random = new Random();
        
        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassTerrainTile"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirtTerrainTile"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("flowerTerrainTile"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("pathTerrainTile"));
        
        TerrainTexturePack texturePack = new TerrainTexturePack(
        		backgroundTexture, rTexture, gTexture, bTexture);
        
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap2"));
        
        RawModel unitRawModel = OBJLoader.loadObjModel("unit");
        TexturedModel unitModel = new TexturedModel(unitRawModel, new ModelTexture(loader.loadTexture("unit")));
        
        ModelTexture healthBar = new ModelTexture(loader.loadTexture("healthBar"));

        List<MobileUnit> units = new ArrayList<MobileUnit>();
        
        units.add(new MobileUnit(unitModel, new Location(new Vector3f(0, 0, 0)), 0, 0, 0, 10, 20000, 10, 1, 1));
        
        for(MobileUnit unit: units) {
        	unit.setBeingUsed(true);
        }
        
        List<Building> buildings = new ArrayList<Building>();
        
        buildings.add(new Building(unitModel, new Location(new Vector3f(0, 0, 0)), 0, 0, 0, 4, 10000, 1, 0));
        
        for(Building building: buildings) {
        	building.setBeingUsed(true);
        }
        
        Terrain terrain = new Terrain(0,0,loader, texturePack, blendMap);
        Terrain terrain2 = new Terrain(1,0,loader, texturePack, blendMap);
        Terrain terrain3 = new Terrain(1,1,loader, texturePack, blendMap);
        Terrain terrain4 = new Terrain(0,1,loader, texturePack, blendMap);
        Light light = new Light(new Vector3f(0, 250, 0), new Vector3f(1, 1, 1));
        Camera camera = new Camera(7);
        MasterRenderer renderer = new MasterRenderer();
        
        camera.setPosition(new Vector3f(0, 50, 0));
        camera.setPitch(65);
        camera.setYaw(45);
        
        MobileUnit selectedUnit = units.get(0);
        
        MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrain);
        
        while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_X)){
            camera.move();
            for(Building building: buildings) {
            	if(building.isBeingUsed()) {
            		if(Keyboard.isKeyDown(Keyboard.KEY_M)) {
                		building.createUnit(unitModel, units);
                	}
                	building.attackTarget();
                	renderer.processUnit(building);
            	}
            	if(building.getTarget() == null || !building.getTarget().isBeingUsed()) {
            		if(building.NewUnitTarget(units)){
            			building.NewBuildingTarget(buildings);
            		}
            	}
            }
            if(selectedUnit != null) {
            	if(Mouse.isButtonDown(1)) {
            		try {
            			picker.update();
            			selectedUnit.setDestination(picker.getCurrentTerrainPoint());
            		} catch(NullPointerException e) {
            			e.printStackTrace();
            		}
            	}
            }

            picker.update();
            for(MobileUnit unit: units) {
            	if(unit.isBeingUsed()) {
            		renderer.processUnit(unit);
            		unit.move();
            		unit.attackTarget();
            		if(unit.getTarget() == null || !unit.getTarget().isBeingUsed()) {
            			if(!unit.NewUnitTarget(units)) {
            				unit.NewBuildingTarget(buildings);
            			}
            		}
            		if(Mouse.isButtonDown(0)) {
            			if(unit.getLocation().getDistance(picker.getCurrentTerrainPoint()) <= 100) {
            				selectedUnit = unit;
            			}
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
        for(MobileUnit unit: units) {
        	System.out.println(unit.getHealth());
        	System.out.println(unit.getTarget());
        	System.out.println(unit.isBeingUsed());
        	System.out.println(unit.isReachedDestination());
        	System.out.println(unit.getDestination());
        }
        for(Building building: buildings) {
        	System.out.println(building.getHealth());
        	System.out.println(building.getTarget());
        	System.out.println(building.isBeingUsed());
        }
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
 
    }
 
}