package entities;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import toolbox.Location;

public class Unit extends Entity {
	
	private float health;
	private float attack;
	
	private Unit target;
	
	private boolean beingUsed = true;
	
	private int team;
	
	private Vector3f teamColour;
	
	public Unit(TexturedModel model, Location location, float rotX, float rotY, float rotZ, float scale, float health, float attack, int team) {
		super(model, location, rotX, rotY, rotZ, scale);
		this.health = health;
		this.attack = attack;
		this.team = team;
		if(this.team == 0) {
			this.teamColour = new Vector3f(0, 0, 1);
		} else if(this.team == 1) {
			this.teamColour = new Vector3f(1, 0, 0);
		} else if(this.team == 2) {
			this.teamColour = new Vector3f(0, 1, 0);
		} else if(this.team == 3) {
			this.teamColour = new Vector3f(1, 1, 1);
		} else {
			this.teamColour = new Vector3f(0, 0, 0);
		}
	}
	
	public float getHealth() {
		return health;
	}
	
	public void setHealth(float health) {
		this.health = health;
	}
	
	public float getAttack() {
		return attack;
	}
	
	public void setAttack(float attack) {
		this.attack = attack;
	}

	public Unit getTarget() {
		return target;
	}

	public boolean setTarget(Unit target) {
		if(target.team != this.team) {
			this.target = target;
			return true;
		}
		return false;
	}
	
	public boolean isBeingUsed() {
		return beingUsed;
	}

	public void setBeingUsed(boolean beingUsed) {
		this.beingUsed = beingUsed;
	}

	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}
	
	public Vector3f getTeamColour() {
		return teamColour;
	}

	public void setTeamColour(Vector3f teamColour) {
		this.teamColour = teamColour;
	}
	
	public void attackTarget() {
		if(this.target != null && this.target.beingUsed) {
			if(beingUsed) {
				target.setHealth(target.getHealth()-attack);
				target.setTarget(this);
				if(target.getHealth() <= 0) {
					target.beingUsed = false;
				}
			}
		}
	}
	
	public boolean NewUnitTarget(List<MobileUnit> units) {
		for(Unit unit: units) {
			if(unit.team != this.team && unit.beingUsed) {
				this.target = unit;
				return true;
			}
		}
		return false;
	}
	
	public boolean NewBuildingTarget(List<Building> buildings) {
		for(Unit unit: buildings) {
			if(unit.team != this.team && unit.beingUsed) {
				this.target = unit;
				return true;
			}
		}
		return false;
	}

}
