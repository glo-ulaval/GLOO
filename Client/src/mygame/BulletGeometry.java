/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Vincent Séguin
 */
public class BulletGeometry extends GeometryObject {

    private AudioNode audio;
    private Material ballMat;
    private Spatial mesh;
    private RigidBodyControl ballPhy;
    private FlyingObject bullet;

    public BulletGeometry(AssetManager assetManager, Node rootNode, BulletAppState state, FlyingObject bullet) {
        super(assetManager, rootNode, state);
        this.bullet = bullet;
        instantiateObject();
    }

    @Override
    protected void instantiateObject() {
        mesh = assetManager.loadModel("Models/SpaceCraft/Rocket.mesh.xml");
        mesh.setName("Ball");
        mesh.setLocalScale(3, 3, 3);
        mesh.rotate(15f, 0.0f, 5.0f);
        ballMat = new Material(
                assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        ballMat.setTexture("ColorMap",
                assetManager.loadTexture("Models/SpaceCraft/Rocket.png"));
        mesh.setMaterial(ballMat);
        rootNode.attachChild(mesh);
        mesh.setLocalTranslation(bullet.getPosition());
        ballPhy = new RigidBodyControl(1f);
        mesh.addControl(ballPhy);
        appState.getPhysicsSpace().add(ballPhy);
        ballPhy.setPhysicsLocation(bullet.getPosition());
        ballPhy.setLinearVelocity(bullet.getVelocity());
        ballPhy.setMass(0.5f);
        ballPhy.setKinematic(false);
        initializeAudio();
    }

    private void initializeAudio() {
        audio = new AudioNode(assetManager, "Sounds/Gun.wav", false);
        audio.setLooping(false);
        audio.setVolume(3);
        rootNode.attachChild(audio);
        audio.playInstance();
    }
}
