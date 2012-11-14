/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Vincent Séguin
 */
public class Player extends RigidBodyControl implements PhysicsCollisionListener {

    public static final String BALL_NAME = "Ball";
    private BulletAppState appState;
    private AssetManager assetManager;
    private Node rootNode;

    public Player(BulletAppState appState, AssetManager assetManager, Node rootNode) {
        this.appState = appState;
        this.assetManager = assetManager;
        this.rootNode = rootNode;
        appState.getPhysicsSpace().addCollisionListener(this);
    }

    public void collision(PhysicsCollisionEvent event) {
        Spatial firstObject = event.getNodeA();
        Spatial secondObject = event.getNodeB();

        if (firstObject.getName().equals(BALL_NAME)) {
            firstObject.removeFromParent();
            appState.getPhysicsSpace().remove(firstObject);
            instantiateExplosion(firstObject);
        } else if (secondObject.getName().equals(BALL_NAME)) {
            secondObject.removeFromParent();
            appState.getPhysicsSpace().remove(secondObject);
            instantiateExplosion(secondObject);
        }
    }

    private void instantiateExplosion(Spatial spatial) {
        ParticleEmitter fire =
                new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);
        Material mat_red = new Material(assetManager,
                "Common/MatDefs/Misc/Particle.j3md");
        mat_red.setTexture("Texture", assetManager.loadTexture(
                "Effects/Explosion/flame.png"));
        fire.setMaterial(mat_red);
        fire.setImagesX(2);
        fire.setImagesY(2); // 2x2 texture animation
        fire.setEndColor(new ColorRGBA(1f, 0f, 0f, 1f));   // red
        fire.setStartColor(new ColorRGBA(1f, 1f, 0f, 0.5f)); // yellow
        fire.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2, 0));
        fire.setStartSize(1.5f);
        fire.setEndSize(0.1f);
        fire.setGravity(0, 0, 0);
        fire.setLowLife(1f);
        fire.setHighLife(3f);
        fire.setLocalTranslation(spatial.getLocalTranslation());
        fire.getParticleInfluencer().setVelocityVariation(0.3f);
        fire.setParticlesPerSec(0);
        rootNode.attachChild(fire);
        fire.emitAllParticles();
    }
}
