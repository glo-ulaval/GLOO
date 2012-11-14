/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Spatial;

/**
 *
 * @author Vincent Séguin
 */
public class Player extends RigidBodyControl implements PhysicsCollisionListener {
    public static final String BALL_NAME = "Ball";
    private BulletAppState appState;

    public Player(BulletAppState appState) {
        this.appState = appState;
        appState.getPhysicsSpace().addCollisionListener(this);
    }
    
    public void collision(PhysicsCollisionEvent event) {
        Spatial firstObject = event.getNodeA();
        Spatial secondObject = event.getNodeB();
        
        if (firstObject.getName().equals(BALL_NAME)) {
            firstObject.removeFromParent();
            appState.getPhysicsSpace().remove(firstObject);
        } else if (secondObject.getName().equals(BALL_NAME)) {
            secondObject.removeFromParent();
            appState.getPhysicsSpace().remove(secondObject);
        }
    }
}
