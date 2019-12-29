package entities;

import models.TexturedModel;
import toolbox.Location;

public class Unit extends Entity {
	
	private float health;
	private float attack;
	
	private Unit target;
	
	private boolean beingUsed = false;
	
	public Unit(TexturedModel model, Location location, float rotX, float rotY, float rotZ, float scale, float health, float attack) {
		super(model, location, rotX, rotY, rotZ, scale);
		this.health = health;
		this.attack = attack;
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

	public void setTarget(Unit target) {
		this.target = target;
	}
	
	public boolean isBeingUsed() {
		return beingUsed;
	}

	public void setBeingUsed(boolean beingUsed) {
		this.beingUsed = beingUsed;
	}

	public void attack(Unit target) {
		if(this.target != null) {
			if(this.target.isBeingUsed() && this.beingUsed) {
				target.setHealth(target.getHealth()-this.attack);
			}
		}
	}

}
