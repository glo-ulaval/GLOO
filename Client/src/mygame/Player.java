/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Spatial;

/**
 *
 * @author Vincent Séguin
 */
public class Player extends RigidBodyControl implements PhysicsCollisionListener {
    
    public Player(BulletAppState appState) {
        appState.getPhysicsSpace().addCollisionListener(this);
    }
    
    public void collision(PhysicsCollisionEvent event) {
        Spatial firstObject = event.getNodeA();
        Spatial secondObject = event.getNodeB();
        
        System.out.println(firstObject.getName());
        System.out.println(secondObject.getName());
    }
}
