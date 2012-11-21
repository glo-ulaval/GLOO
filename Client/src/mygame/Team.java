package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.scene.Node;

public class Team {
    
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    
    private int teamNumber;
    
    public Team(int teamNumber, BulletAppState state, AssetManager assetManager, Node node) {
        player1 = new Player(state, assetManager, node);
        player2 = new Player(state, assetManager, node);
        currentPlayer = player1;
        this.teamNumber = teamNumber;
        
        if (teamNumber == 1) {
            player1.setPlayerNumber(1);
            player2.setPlayerNumber(3);
        } else {
            player1.setPlayerNumber(2);
            player2.setPlayerNumber(4);
        }
    }
    
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    
    public int getTeamNumber() {
        return teamNumber;
    }
 
    public int getTeamScore() {
        return player1.getScore() + player2.getScore();
    }
}
