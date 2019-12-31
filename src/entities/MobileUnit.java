package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import toolbox.Location;

public class MobileUnit extends Unit {
	
	private float speed = 0;
	
	private Location destination;
	
	private boolean reachedDestination = true;

	public MobileUnit(TexturedModel model, Location location, float rotX, float rotY, float rotZ, float scale,
			float health, float attack, int team, float speed) {
		super(model, location, rotX, rotY, rotZ, scale, health, attack, team);
		this.speed = speed;
		this.destination = location;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Location getDestination() {
		return destination;
	}

	public void setDestination(Location destination) {
		this.destination = destination;
		float angle = (float) (Math.toDegrees(Math.atan2(destination.getPosition().x - this.getLocation().getPosition().x, destination.getPosition().x - this.getLocation().getPosition().z) * 180 / Math.PI));
		this.setRotY(angle);
	}
	
	public boolean isReachedDestination() {
		return reachedDestination;
	}

	public void setReachedDestination(boolean reachedDestination) {
		this.reachedDestination = reachedDestination;
	}

	public void move() {
		if(!this.reachedDestination) {
			getLocation().getPosition().x += (float) (speed * Math.sin(Math.toRadians(this.getRotY())));
			getLocation().getPosition().z += (float) (speed * Math.cos(Math.toRadians(this.getRotY())));
			this.reachedDestination = true;
		} else {
			if(this.getLocation().getDistance(destination) >= 5) {
				this.reachedDestination = false;
			}
		}
	}
	
}
