package entities;

import java.util.List;

import models.TexturedModel;
import toolbox.Location;

public class Building extends Unit {
	
	private Location SpawnLocation;

	public Building(TexturedModel model, Location location, float rotX, float rotY, float rotZ, float scale,
			float health, float attack, int team) {
		super(model, location, rotX, rotY, rotZ, scale, health, attack, team);
		this.SpawnLocation = this.getLocation();
		this.SpawnLocation.getPosition().x += 100;
	}
	
	public void createUnit(TexturedModel model, List<MobileUnit> units) {
		MobileUnit unit = new MobileUnit(model, SpawnLocation, 0, 0, 0, 5, 1, 1, getTeam(), 5);
		unit.setBeingUsed(true);
		units.add(unit);
	}
	
}
