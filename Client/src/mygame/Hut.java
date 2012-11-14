package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

public class Hut extends GeometryObject {

    private Material hutMat;
    private RigidBodyControl hutPhy;
    private static final Box hut;
    private static final float x = 8f;
    private static final float y = 12f;
    private static final float z = 10f;
    private Vector3f position;

    static {
        hut = new Box(Vector3f.ZERO, x, y, z);
        hut.scaleTextureCoordinates(new Vector2f(1, 1));
    }

    public Hut(AssetManager assetManager, Node rootNode, BulletAppState state, Vector3f position) {
        super(assetManager, rootNode, state);
        this.position = position;
        instantiateObject();
    }
    
    public void shootTarget(boolean isMark) {
        FlyingObject target;
        if (isMark) {
            target = FlyingObjectFactory.createTarget(new Vector3f(-8f,8f,0), new Vector3f(position.x + 25.0f, position.y + 25.0f, position.z));
        } else {
            target = FlyingObjectFactory.createTarget(new Vector3f(8f,8f,0), new Vector3f(position.x, position.y + 15.0f, position.z));
        }

        new TargetGeometry(assetManager, rootNode, appState, target);
    }

    @Override
    protected void instantiateObject() {
        hutMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        TextureKey texture = new TextureKey("Textures/hut-texture.jpg");
        texture.setGenerateMips(true);
        Texture tex3 = assetManager.loadTexture(texture);
        tex3.setWrap(Texture.WrapMode.Repeat);
        hutMat.setTexture("ColorMap", tex3);
        Geometry hutGeo = new Geometry("Floor", hut);
        hutGeo.setMaterial(hutMat);
        hutGeo.setLocalTranslation(position.x, position.y, position.z);
        this.rootNode.attachChild(hutGeo);
        hutPhy = new RigidBodyControl(0.0f);
        hutGeo.addControl(hutPhy);
        appState.getPhysicsSpace().add(hutPhy);
    }
}
