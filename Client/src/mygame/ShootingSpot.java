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

public class ShootingSpot extends GeometryObject {

    private Material spotMat;
    private RigidBodyControl spotPhy;
    private static final Box spot;
    private static final float x = 4f;
    private static final float y = 0.1f;
    private static final float z = 4f;
    private Vector3f position;

    static {
        spot = new Box(Vector3f.ZERO, x, y, z);
        spot.scaleTextureCoordinates(new Vector2f(1, 1));
    }

    public ShootingSpot(AssetManager assetManager, Node rootNode, BulletAppState state, Vector3f position) {
        super(assetManager, rootNode, state);
        this.position = position;
        instantiateObject();
    }

    @Override
    protected void instantiateObject() {
        spotMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        TextureKey texture = new TextureKey("Textures/Terrain/Rock2/rock.jpg");
        texture.setGenerateMips(true);
        Texture tex3 = assetManager.loadTexture(texture);
        tex3.setWrap(Texture.WrapMode.Repeat);
        spotMat.setTexture("ColorMap", tex3);
        Geometry hutGeo = new Geometry("Floor", spot);
        hutGeo.setMaterial(spotMat);
        hutGeo.setLocalTranslation(position.x, position.y, position.z);
        this.rootNode.attachChild(hutGeo);
        spotPhy = new RigidBodyControl(0.0f);
        hutGeo.addControl(spotPhy);
        appState.getPhysicsSpace().add(spotPhy);
    }
    
    public Vector3f getPosition(){
        return this.position;
    }
}
