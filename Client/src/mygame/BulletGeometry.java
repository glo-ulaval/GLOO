/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import com.jme3.scene.shape.Sphere.TextureMode;
import com.jme3.texture.Texture;

/**
 *
 * @author Vincent Séguin
 */
public class BulletGeometry extends GeometryObject {

    private Material ballMat;
    private RigidBodyControl ballPhy;
    private static final Sphere sphere;
    private FlyingObject bullet;

    static {
        sphere = new Sphere(32, 32, 0.5f, true, false);
        sphere.setTextureMode(TextureMode.Projected);
    }

    public BulletGeometry(AssetManager assetManager, Node rootNode, BulletAppState state, FlyingObject bullet) {
        super(assetManager, rootNode, state);
        this.bullet = bullet;
        instantiateObject();
    }

    @Override
    protected void instantiateObject() {
        ballMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        TextureKey key = new TextureKey("Textures/Terrain/Rock/Rock.PNG");
        key.setGenerateMips(true);
        Texture texture = assetManager.loadTexture(key);
        ballMat.setTexture("ColorMap", texture);

        Geometry ballGeo = new Geometry("Ball", sphere);
        ballGeo.setMaterial(ballMat);
        rootNode.attachChild(ballGeo);
        ballGeo.setLocalTranslation(bullet.getPosition());
        ballPhy = new RigidBodyControl(1f);
        ballGeo.addControl(ballPhy);
        appState.getPhysicsSpace().add(ballPhy);
        ballPhy.setLinearVelocity(bullet.getVelocity());
    }
}
