package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.BulletAppState;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.renderer.RenderManager;
import com.jme3.system.JmeContext;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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
    private Team team1;
    private Team team2;
    private Team currentTeam;
    private int round = 1;
    // GUI
    private BitmapText scoreText;
    private BitmapText playerText;
    private BitmapText timerText;
    // Timer
    private final static ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);
    private int timerCount = 4;
    private AudioNode timerAudio;

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
        initializeAudio();
        initCrossHairs();
        try {
            client = Network.connectToServer("localhost", AngryPidgeServerMain.PORT);
            client.start();
            NetworkMessages.initializeSerializables();
            client.addMessageListener(new ClientListener());
            client.send(new NetworkMessages.ConnectionMessage());
        } catch (IOException ex) {
            Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        startGame();
    }

    @Override
    public void simpleUpdate(float tpf) {
        scoreText.setText(getScoreText());
        playerText.setText(getPlayerText());
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    @Override
    public void destroy() {
        timer.shutdown();
    }

    private void startGame() {
        timer.scheduleWithFixedDelay(counterRunnable, 1, 1, TimeUnit.SECONDS);
    }

    private void initializeCamera() {
        Vector3f positon = map.getShootingSpot(0).getPosition();
        cam.setLocation(new Vector3f(positon.x, 8f, positon.z));
        cam.lookAt(new Vector3f(2, 2, 0), Vector3f.UNIT_Y);
    }

    private void initializeClient() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        inputManager.addMapping(SHOOT_BUTTON, new KeyTrigger(KeyInput.KEY_SPACE));
         inputManager.addMapping("toto", new KeyTrigger(KeyInput.KEY_1));
        inputManager.addListener(actionListener, SHOOT_BUTTON);
        inputManager.addListener(actionListener, "toto");
    }
    
    private void initializeAudio() {
        timerAudio = new AudioNode(assetManager, "Sounds/Explosion.wav", false);
        timerAudio.setLooping(false);
        timerAudio.setVolume(4);
        rootNode.attachChild(timerAudio);
    }

    private void instantiateObjects() {
        map = new Map(assetManager, rootNode, bulletAppState);
        team1 = new Team(1, bulletAppState, assetManager, rootNode);
        team2 = new Team(2, bulletAppState, assetManager, rootNode);
        currentTeam = team1;
        initializeGui();
    }
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            Player currentPlayer = currentTeam.getCurrentPlayer();
            if (name.equals(SHOOT_BUTTON) && !isPressed && currentPlayer.canShoot()) {
                currentTeam.getCurrentPlayer().shoot(cam.getDirection(), cam.getLocation());
            }
        }
    };

    public class ClientListener implements MessageListener<Client> {

        public void messageReceived(Client source, Message message) {
            if (message instanceof NetworkMessages.NetworkMessage) {
                NetworkMessages.NetworkMessage networkMessage = (NetworkMessages.NetworkMessage) message;
                if (scoreText != null) {
                    scoreText.setText("Client received: '" + networkMessage.getMessage() + "'");
                }
            }
            if (message instanceof NetworkMessages.GameUpdateMessage) {
                NetworkMessages.GameUpdateMessage updateMessage = (NetworkMessages.GameUpdateMessage) message;
                if (scoreText != null) {
                    scoreText.setText("Ronde en cours : " + updateMessage.getPosition() + " || Score équipe 1 = " + updateMessage.getTeam1Score() + " | Score équipe 2 = " + updateMessage.getTeam2Score());
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
        scoreText = new BitmapText(guiFont, false);
        scoreText.setSize(guiFont.getCharSet().getRenderedSize());
        scoreText.setText(getScoreText());
        scoreText.setLocalTranslation(200, scoreText.getLineHeight(), 0);
        playerText = new BitmapText(guiFont, false);
        playerText.setSize(guiFont.getCharSet().getRenderedSize());
        playerText.setText(getPlayerText());
        playerText.setLocalTranslation(475, 475, 0);

        timerText = new BitmapText(guiFont, false);
        timerText.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        timerText.setColor(ColorRGBA.Red);
        timerText.setText("");
        timerText.setLocalTranslation(
                settings.getWidth() / 2 - guiFont.getCharSet().getRenderedSize() / 3 * 2,
                settings.getHeight() / 2 + timerText.getLineHeight() / 2 + 200, 0);

        guiNode.attachChild(scoreText);
        guiNode.attachChild(playerText);
        guiNode.attachChild(timerText);
    }

    private void initCrossHairs() {
        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+");
        ch.setLocalTranslation(
                settings.getWidth() / 2 - guiFont.getCharSet().getRenderedSize() / 3 * 2,
                settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
        guiNode.attachChild(ch);
    }

    private String getPlayerText() {
        return "Équipe : " + currentTeam.getTeamNumber() + " || Joueur : " + currentTeam.getCurrentPlayer().getPlayerNumber();
    }

    private String getScoreText() {
        return "Ronde en cours : " + round + " || Score équipe 1 = " + team1.getTeamScore() + " | Score équipe 2 = " + team2.getTeamScore();
    }

    private void setTimerText(String value) {
        timerText.setText(value);
    }
    
    final Runnable counterRunnable = new Runnable() {
        public void run() {
            timerCount--;
            if (timerCount > 0) {
                setTimerText(String.valueOf(timerCount));
            } else if (timerCount == 0) {
                setTimerText("GO!!!");
                currentTeam.getCurrentPlayer().setCanShoot(true);
            } else {
                map.shootTarget(); // Shoot target is when the timer reaches -1 to give time to the player to shoot
                setTimerText("");
                timer.shutdown();
            }
        }
    };
}