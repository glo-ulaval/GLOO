package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.network.Network;
import com.jme3.renderer.RenderManager;
import com.jme3.system.JmeContext;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * test
 * @author normenhansen
 */
public class Client extends SimpleApplication {
    
    com.jme3.network.Client client;
    private BulletAppState bulletAppState;

    public static void main(String[] args) {
        Client app = new Client();
        app.start(JmeContext.Type.Display);
    }

    @Override
    public void simpleInitApp(){
         client = null;
        try {
            client = Network.connectToServer("localhost", 4444);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        client.start();
        initializeClient();
    }
    
    @Override
    public void destroy() {
        client.close();
        super.destroy();
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    private void initializeClient() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        inputManager.addMapping("shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(actionListener, "shoot");
    }
    
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            if(name.equals("shoot") && !isPressed) {
                // instantiate the ball here
            }
        }
    };
}
