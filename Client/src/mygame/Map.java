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
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.jme3.util.SkyFactory;

/**
 *
 * @author Vincent Séguin
 */
public class Map extends GeometryObject {

    private Material floorMat;
    private RigidBodyControl floorPhy;
    private static final Box floor;
    
    private Hut hut1;
    private Hut hut2;

    static {
        floor = new Box(Vector3f.ZERO, 350f, 0.1f, 200f);
        floor.scaleTextureCoordinates(new Vector2f(9, 18));
    }

    public Map(AssetManager assetManager, Node rootNode, BulletAppState state) {
        super(assetManager, rootNode, state);
        instantiateObject();
        hut1 = new Hut(assetManager, rootNode, appState, new Vector3f(50f, 5f, 0));
        hut2 = new Hut(assetManager, rootNode, appState, new Vector3f(0, 5f, 50f));
    }

    @Override
    protected void instantiateObject() {
        floorMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        TextureKey texture = new TextureKey("Textures/grass-texture.jpg");
        texture.setGenerateMips(true);
        Texture tex3 = assetManager.loadTexture(texture);
        tex3.setWrap(WrapMode.Repeat);
        floorMat.setTexture("ColorMap", tex3);
        Geometry floorGeo = new Geometry("Floor", floor);
        floorGeo.setMaterial(floorMat);
        floorGeo.setLocalTranslation(0, -0.1f, 0);
        this.rootNode.attachChild(floorGeo);
        floorPhy = new RigidBodyControl(0.0f);
        floorGeo.addControl(floorPhy);
        appState.getPhysicsSpace().add(floorPhy);
        rootNode.attachChild(SkyFactory.createSky(
            assetManager, "Textures/Sky/Bright/BrightSky.dds", false));
    }
}
