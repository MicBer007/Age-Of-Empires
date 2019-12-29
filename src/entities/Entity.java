package entities;
 
import models.TexturedModel;
import toolbox.Location;
 
public class Entity {
 
    private TexturedModel model;
    private Location location;
    private float rotX, rotY, rotZ;
    private float scale;
 
    public Entity(TexturedModel model, Location location, float rotX, float rotY, float rotZ,
            float scale) {
        this.model = model;
        this.location = location;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }
 
    public void increasePosition(float dx, float dy, float dz) {
        this.location.getPosition().x += dx;
        this.location.getPosition().y += dy;
        this.location.getPosition().z += dz;
    }
 
    public void increaseRotation(float dx, float dy, float dz) {
        this.rotX += dx;
        this.rotY += dy;
        this.rotZ += dz;
    }
 
    public TexturedModel getModel() {
        return model;
    }
 
    public void setModel(TexturedModel model) {
        this.model = model;
    }
 
    public Location getLocation() {
        return location;
    }
 
    public void setLocation(Location location) {
        this.location = location;
    }
 
    public float getRotX() {
        return rotX;
    }
 
    public void setRotX(float rotX) {
        this.rotX = rotX;
    }
 
    public float getRotY() {
        return rotY;
    }
 
    public void setRotY(float rotY) {
        this.rotY = rotY;
    }
 
    public float getRotZ() {
        return rotZ;
    }
 
    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
    }
 
    public float getScale() {
        return scale;
    }
 
    public void setScale(float scale) {
        this.scale = scale;
    }
 
}