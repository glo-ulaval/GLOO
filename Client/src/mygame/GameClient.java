package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.font.BitmapText;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Vector3f;
import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.renderer.RenderManager;
import com.jme3.system.JmeContext;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mygame.util.NetworkMessages;

/**
 * test
 *
 * @author normenhansen
 */
public class GameClient extends SimpleApplication {

    private static final String SHOOT_BUTTON = "shoot";
    com.jme3.network.Client client;
    private BulletAppState bulletAppState;
    private Map map;
    private BitmapText text;
    private Team team1;
    private Team team2;
    private Team currentTeam;

    public static void main(String[] args) {
        GameClient app = new GameClient();
        app.start(JmeContext.Type.Display);
        app.pauseOnFocus = false;
    }

    @Override
    public void simpleInitApp() {
        client = null;
        initializeClient();
        instantiateObjects();
        initializeCamera();
        try {
            client = Network.connectToServer("localhost", AngryPidgeServerMain.PORT);
            client.start();
            NetworkMessages.initializeSerializables();
            client.addMessageListener(new ClientListener());
            client.send(new NetworkMessages.ConnectionMessage());
        } catch (IOException ex) {
            Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
        }

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
        Vector3f positon = map.getShootingSpot(0).getPosition();
        cam.setLocation(new Vector3f(positon.x, 8f, positon.z));
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
        initializeGui();
        team1 = new Team(bulletAppState, assetManager, rootNode);
        team2 = new Team(bulletAppState, assetManager, rootNode);
        currentTeam = team1;
    }
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals(SHOOT_BUTTON) && !isPressed) {
                currentTeam.getCurrentPlayer().shoot(cam.getDirection(), cam.getLocation());
                map.shootTarget(); // TO REMOVE
            }
        }
    };

    public class ClientListener implements MessageListener<Client> {

        public void messageReceived(Client source, Message message) {
            if (message instanceof NetworkMessages.NetworkMessage) {
                NetworkMessages.NetworkMessage networkMessage = (NetworkMessages.NetworkMessage) message;
                if (text != null) {
                    text.setText("Client received: '" + networkMessage.getMessage() + "'");
                }
            }
            if (message instanceof NetworkMessages.GameUpdateMessage) {
                NetworkMessages.GameUpdateMessage updateMessage = (NetworkMessages.GameUpdateMessage) message;
                if (text != null) {
                    text.setText("Ronde en cours : " + updateMessage.getPosition() + " || Score équipe 1 = " + updateMessage.getTeam1Score() + " | Score équipe 2 = " + updateMessage.getTeam2Score());
                }
                client.send(new NetworkMessages.NetworkMessage("message received"));
            }
        }
    }

    public void setCameraLocation(Vector3f position) {
        this.cam.setLocation(position);
    }
    
    private void initializeGui() {
        guiNode.detachAllChildren();
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        text = new BitmapText(guiFont, false);
        text.setSize(guiFont.getCharSet().getRenderedSize());
        text.setText("Ronde en cours : 1 || Score équipe 1 = 0 | Score équipe 2 = 0");
        text.setLocalTranslation(300, text.getLineHeight(), 0);
        guiNode.attachChild(text);
    }
}