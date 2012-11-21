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

public class TargetGeometry extends GeometryObject {

    private Material targetMat;
    private RigidBodyControl targetPhy;
    private static final Sphere sphere;
    private FlyingObject target;

    static {
        sphere = new Sphere(32, 32, 5f, true, false);
        sphere.setTextureMode(TextureMode.Projected);
    }

    public TargetGeometry(AssetManager assetManager, Node rootNode, BulletAppState state, FlyingObject target) {
        super(assetManager, rootNode, state);
        this.target = target;
        instantiateObject();
    }

    @Override
    public void instantiateObject() {
        targetMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        TextureKey key2 = new TextureKey("Textures/target-texture.jpg");
        key2.setGenerateMips(true);
        Texture tex2 = assetManager.loadTexture(key2);
        targetMat.setTexture("ColorMap", tex2);
        Geometry geo = new Geometry("Target", sphere);
        geo.setMaterial(targetMat);
        rootNode.attachChild(geo);
        targetPhy = new RigidBodyControl(1f);
        geo.addControl(targetPhy);
        appState.getPhysicsSpace().add(targetPhy);
        targetPhy.setPhysicsLocation(target.getPosition());
        targetPhy.setLinearVelocity(target.getVelocity());
        targetPhy.setMass(0.1f); 
        targetPhy.setKinematic(false);
    }
}
