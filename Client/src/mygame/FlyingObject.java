/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Vector3f;

/**
 *
 * @author Vincent Séguin
 */
public abstract class FlyingObject {
    
    private Vector3f direction;
    private Vector3f position;
    private float speed;
    
    public FlyingObject(Vector3f direction, Vector3f position, float speed) {
        this.direction = direction;
        this.position = position;
        this.speed = speed;
    }
    
    public Vector3f getDirection() {
        return direction;
    }
    
    public Vector3f getPosition() {
        return position;
    }
    
    public float getSpeed() {
        return speed;
    }
    
    public Vector3f getVelocity() {
        return direction.mult(speed);
    }
}
