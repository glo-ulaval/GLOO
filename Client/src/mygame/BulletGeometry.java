/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
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
        mesh.rotate(0.0f, 60.0f, 0.0f);
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
        ballPhy.setLinearVelocity(bullet.getVelocity());
    }
}
