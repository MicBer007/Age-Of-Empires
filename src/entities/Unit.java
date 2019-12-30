package entities;

import java.util.List;

import models.TexturedModel;
import toolbox.Location;

public class Unit extends Entity {
	
	private float health;
	private float attack;
	
	private Unit target;
	
	private boolean beingUsed = false;
	
	private int team;
	
	public Unit(TexturedModel model, Location location, float rotX, float rotY, float rotZ, float scale, float health, float attack, int team) {
		super(model, location, rotX, rotY, rotZ, scale);
		this.health = health;
		this.attack = attack;
		this.team = team;
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

	public void attack(Unit target) {
		if(this.target != null) {
			if(this.target.isBeingUsed() && this.beingUsed) {
				target.setHealth(target.getHealth()-this.attack);
				target.setTarget(this);
			}
		}
	}
	
	public boolean getNewTarget(List<Unit> units) {
		for(Unit unit: units) {
			if(unit.team != this.team && unit.beingUsed) {
				this.target = unit;
				return true;
			}
		}
		return false;
	}

}
