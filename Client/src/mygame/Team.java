package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.scene.Node;

public class Team {
    
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    
    public Team(BulletAppState state, AssetManager assetManager, Node node) {
        player1 = new Player(state, assetManager, node);
        player2 = new Player(state, assetManager, node);
        currentPlayer = player1;
    }
    
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
 
    public int getTeamScore() {
        return player1.getScore() + player2.getScore();
    }
}
