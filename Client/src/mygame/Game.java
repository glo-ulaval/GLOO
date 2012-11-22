/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.font.BitmapText;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Vincent Séguin
 */
public class Game {
    
    // Timer
    private final static ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
    private int timerCount = 4;
    
    private Team currentTeam;
    private Map map;
    private BitmapText bitmapText;
    
    public Game(Map map, BitmapText text) {
        this.map = map;
        this.bitmapText = text;
    }
    
    public void setTimerCount(int count) {
        timerCount = count;
    }
     
    public void shutdown() {
        timer.shutdown();
    }
    
    public void start(Team currentTeam) {
        this.currentTeam = currentTeam;
        timerCount = 4;
        timer.scheduleWithFixedDelay(counterRunnable, 1, 1, TimeUnit.SECONDS);
    }
    
    final Runnable counterRunnable = new Runnable() {
        public void run() {
            timerCount--;
            if (timerCount > 0) {
                setTimerText(String.valueOf(timerCount));
            } else if (timerCount == 0) {
                setTimerText("GO!!!");
                currentTeam.getCurrentPlayer().setCanShoot(true);
            } else if (timerCount == -1) {
                map.shootTarget(); // Shoot target is when the timer reaches -1 to give time to the player to shoot
                setTimerText("");
            }
        }
    };
    
    private void setTimerText(String value) {
        bitmapText.setText(value);
    }
}
