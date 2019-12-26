package entities;
 
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
 
public class Camera {
     
    private Vector3f position = new Vector3f(0,0,0);
    private float pitch;
    private float yaw;
    private float roll;
    
    private float speed;
     
    public Camera(float speed){
    	this.speed = speed;
    }
     
    public void move(){
    	float distance = speed; // * DisplayManager.getFrameTimeSeconds()
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            position.x -= (float) (distance * -Math.sin(Math.toRadians(yaw)));
            position.z -= (float) (distance * Math.cos(Math.toRadians(yaw)));
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){
        	position.x += (float) (distance * -Math.sin(Math.toRadians(yaw)));
            position.z += (float) (distance * Math.cos(Math.toRadians(yaw)));
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
        	position.x += (float) (distance * Math.cos(Math.toRadians(yaw)));
            position.z += (float) (distance * Math.sin(Math.toRadians(yaw)));
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
        	position.x -= (float) (distance * Math.cos(Math.toRadians(yaw)));
            position.z -= (float) (distance * Math.sin(Math.toRadians(yaw)));
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
            position.y+=speed/2;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
            position.y-=speed/2;
        }
        pitch += getMouseYDifference()*0.1f;
        yaw += getMouseXDifference()*0.1f;
        if(pitch < -90) {
        	pitch = -90;
        } else if(pitch > 90) {
        	pitch = 90;
        }
        Mouse.setCursorPosition(DisplayManager.getWidth()/2, DisplayManager.getHeight()/2);
    }
    
//  private void logMouseX() {
//	System.out.println(Mouse.getX());
//}
//private void logMouseY() {
//	System.out.println(Mouse.getY());
//}
    
    public float getMouseXDifference() {
    	return -(DisplayManager.getWidth()/2 - Mouse.getX());
    }
    
    public float getMouseYDifference() {
    	return DisplayManager.getHeight()/2 - Mouse.getY();
    }
 
    public Vector3f getPosition() {
        return position;
    }
 
    public float getPitch() {
        return pitch;
    }
 
    public float getYaw() {
        return yaw;
    }
 
    public float getRoll() {
        return roll;
    }

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public void setRoll(float roll) {
		this.roll = roll;
	}
 
}