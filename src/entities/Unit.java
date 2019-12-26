package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

public class Unit extends Entity{
	
	private float health;
	private float attack;
	private float defense;
	private Unit target;
	private float attackSpeed;
	private boolean vanishes;
	private int team;

	public Unit(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
		this.health = 100;
		this.attack = 1;
		this.defense = 1;
		this.attackSpeed = 1;
		this.vanishes = true;
		this.team = -1;
	}

	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}

	public float getAttackSpeed() {
		return attackSpeed;
	}

	public void setAttackSpeed(float attackSpeed) {
		this.attackSpeed = attackSpeed;
	}

	public boolean isVanishes() {
		return vanishes;
	}

	public void setVanishes(boolean vanishes) {
		this.vanishes = vanishes;
	}

	public Unit getTarget() {
		return target;
	}

	public void setTarget(Unit target) {
		this.target = target;
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

	public float getDefense() {
		return defense;
	}

	public void setDefense(float defense) {
		this.defense = defense;
	}
	
	public void attackTarget() {
		if(target.team != this.team) {
			target.setHealth(target.getHealth() - attack / target.defense);
		}
	}

}
