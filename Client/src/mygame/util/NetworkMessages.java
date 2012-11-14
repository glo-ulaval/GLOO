/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.util;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import com.jme3.network.serializing.Serializer;

/**
 *
 * @author jrochette
 */
public class NetworkMessages {
    
    public static void initializeSerializable(){
        Serializer.registerClass(NetworkMessage.class);
        Serializer.registerClass(GameUpdateMessage.class);
        Serializer.registerClass(ShootingResult.class);
    }
    
    @Serializable
    public static class NetworkMessage extends AbstractMessage{
        private String message;
        
        public NetworkMessage(){
        }
        
        public NetworkMessage(String hasHitTarget){
            this.message = hasHitTarget;
        }

        public String getMessage() {
            return message;
        }
    }
    
    @Serializable
    public static class GameUpdateMessage extends AbstractMessage{
        private int position;
        private int team1Score;
        private int team2Score;
        
        public GameUpdateMessage(){
        }
        
        public GameUpdateMessage(int position, int team1Score, int team2Score){
            this.position = position;
            this.team1Score = team1Score;
            this.team2Score = team2Score;
        }

        public int getPosition() {
            return position;
        }

        public int getTeam1Score() {
            return team1Score;
        }

        public int getTeam2Score() {
            return team2Score;
        }
    }
    
    @Serializable
    public static class ShootingResult extends AbstractMessage{
        private boolean hasHitTarget;
        private int teamNumber;
        
        public ShootingResult(){
        }
        
        public ShootingResult(boolean hasHitTarget, int teamNumber){
            this.hasHitTarget = hasHitTarget;
            this.teamNumber = teamNumber;
        }

        public boolean isHasHitTarget() {
            return hasHitTarget;
        }

        public int getTeamNumber() {
            return teamNumber;
        }
    }
}
