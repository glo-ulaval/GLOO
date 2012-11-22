/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.audio.AudioNode;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Vincent Séguin
 */
public class Map extends GeometryObject {

    private AudioNode audio;
    private AudioNode pigeon;
    private Material floorMat;
    private RigidBodyControl floorPhy;
    private static final Box floor;
    private Hut pullHut;
    private Hut markHut;
    private List<ShootingSpot> shootingSpots = new ArrayList<ShootingSpot>();

    static {
        floor = new Box(Vector3f.ZERO, 350f, 0.1f, 200f);
        floor.scaleTextureCoordinates(new Vector2f(9, 18));
    }

    public Map(AssetManager assetManager, Node rootNode, BulletAppState state) {
        super(assetManager, rootNode, state);
        instantiateObject();
        initializeAudio();
        pullHut = new Hut(assetManager, rootNode, appState, new Vector3f(-80f, 5f, 30f));
        markHut = new Hut(assetManager, rootNode, appState, new Vector3f(80f, 5f, 30f));
        shootingSpots.add(new ShootingSpot(assetManager, rootNode, appState, new Vector3f(-50f, 2f, -50f)));
        shootingSpots.add(new ShootingSpot(assetManager, rootNode, appState, new Vector3f(-30f, 2f, -75f)));
        shootingSpots.add(new ShootingSpot(assetManager, rootNode, appState, new Vector3f(-15f, 2f, -90f)));
        shootingSpots.add(new ShootingSpot(assetManager, rootNode, appState, new Vector3f(0f, 2f, -100f)));
        shootingSpots.add(new ShootingSpot(assetManager, rootNode, appState, new Vector3f(15f, 2f, -90f)));
        shootingSpots.add(new ShootingSpot(assetManager, rootNode, appState, new Vector3f(30f, 2f, -75f)));
        shootingSpots.add(new ShootingSpot(assetManager, rootNode, appState, new Vector3f(50f, 2f, -50f)));
        shootingSpots.add(new ShootingSpot(assetManager, rootNode, appState, new Vector3f(0f, 2f, 0f)));
    }

    public void shootTarget() {
        int random = new Random().nextInt(3);
        if (random == 0) {
            pullHut.shootTarget(false);
        } else if (random == 1) {
            markHut.shootTarget(true);
        } else {
            pullHut.shootTarget(false);
            markHut.shootTarget(true);
        }
        pigeon.playInstance();
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
        rootNode.attachChild(SkyFactory.createSky(assetManager, "Textures/Sky/Bright/BrightSky.dds", false));
    }

    public ShootingSpot getShootingSpot(int index) {
        return this.shootingSpots.get(index);
    }

    private void initializeAudio() {
        audio = new AudioNode(assetManager, "Sound/Environment/Nature.ogg", false);
        audio.setLooping(true);
        audio.setPositional(true);
        audio.setLocalTranslation(Vector3f.ZERO.clone());
        audio.setVolume(3);
        rootNode.attachChild(audio);
        pigeon = new AudioNode(assetManager, "Sounds/Pigeon.wav", false);
        pigeon.setLooping(false);
        pigeon.setVolume(3);
        rootNode.attachChild(pigeon);
        audio.play();
    }
}
