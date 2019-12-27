package terrains;

import org.lwjgl.util.vector.Vector3f;

public class Location {
	
	private Vector3f position;
	
	public Location(Vector3f position) {
		this.position = position;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	//distance method taken from https://www.math.usm.edu/lambers/mat169/fall09/lecture17.pdf 27 December 2019
	public float getDistance(Vector3f location) {
		return (float) (Math.sqrt((position.x - location.x) * (position.x - location.x) + (position.y - location.y) * (position.y - location.y) + (position.z - location.z) * (position.z - location.z)));
	}

}
