package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * test
 * @author normenhansen
 */
public class AngryPidgeServerMain extends SimpleApplication {
    public static final int PORT = 4444;
    private float counter = 0;
    private BitmapText text;

    public static void main(String[] args) {
        AngryPidgeServerMain server = new AngryPidgeServerMain();
        server.start();
    }

    @Override
    public void simpleInitApp() {                    
        /** Write text on the screen (HUD) */
        guiNode.detachAllChildren();
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        text = new BitmapText(guiFont, false);
        text.setSize(guiFont.getCharSet().getRenderedSize());
        text.setText("Hello World");
        text.setLocalTranslation(300, text.getLineHeight(), 0);
        guiNode.attachChild(text);
        
        Server myServer;
        try {
            myServer = Network.createServer(PORT);
            myServer.start();
        } catch (IOException ex) {
            Logger.getLogger(AngryPidgeServerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void simpleUpdate(float tpf) {
        counter += tpf;
        if(counter<=3.0f ){
            text.setText("");
        }else if(counter<=5.0f){
            text.setText("working!!!");
        }else{
            counter = 0;
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
