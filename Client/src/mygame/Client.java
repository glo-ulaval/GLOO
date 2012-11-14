package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.system.JmeContext;

/**
 * test
 *
 * @author normenhansen
 */
public class Client extends SimpleApplication {

    private static final String SHOOT_BUTTON = "shoot";
    com.jme3.network.Client client;
    private BulletAppState bulletAppState;
    private Map map;
    private Hut hut1;
    private Hut hut2;

    public static void main(String[] args) {
        Client app = new Client();
        app.start(JmeContext.Type.Display);
    }

    @Override
    public void simpleInitApp() {
        /*
         client = null;
         try {
         client = Network.connectToServer("localhost", 4444);
         } catch (IOException ex) {
         Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
         }
         client.start();*/
        initializeCamera();
        initializeClient();
        instantiateObjects();
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    private void initializeCamera() {
        cam.setLocation(new Vector3f(10f, 8f, 15f));
        cam.lookAt(new Vector3f(2, 2, 0), Vector3f.UNIT_Y);
    }

    private void initializeClient() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        inputManager.addMapping(SHOOT_BUTTON, new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(actionListener, SHOOT_BUTTON);
    }

    private void instantiateObjects() {
        map = new Map(assetManager, rootNode, bulletAppState);
        hut1 = new Hut(assetManager, rootNode, bulletAppState, new Vector3f(50f, 5f, 0));
        hut2 = new Hut(assetManager, rootNode, bulletAppState, new Vector3f(0, 5f, 50f));
    }
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals(SHOOT_BUTTON) && !isPressed) {
                FlyingObject bullet = FlyingObjectFactory.createBullet(25, cam.getDirection(), cam.getLocation());
                new BulletGeometry(assetManager, rootNode, bulletAppState, bullet);
            }
        }
    };
}
