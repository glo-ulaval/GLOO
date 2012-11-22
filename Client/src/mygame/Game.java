/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.font.BitmapText;
import com.jme3.system.Timer;

/**
 *
 * @author Vincent Séguin
 */
public class Game {

    private Map map;
    private BitmapText bitmapText;
    private boolean isCountdownStarted = false;

    public Game(Map map, BitmapText text) {
        this.map = map;
        this.bitmapText = text;
    }

    public void update(com.jme3.system.Timer timer, Team currentTeam) {
        if (isCountdownStarted()) {
            if (timer.getTimeInSeconds() >= 5) {
                currentTeam.getCurrentPlayer().setCanShoot(true);
                map.shootTarget();
                setTimerText("");
                timer.reset();
                setIsCountdownStarted(false);
            } else if (timer.getTimeInSeconds() >= 4) {
                setTimerText("GO!!!");
            } else if (timer.getTimeInSeconds() >= 3) {
                setTimerText("1");
            } else if (timer.getTimeInSeconds() >= 2) {
                setTimerText("2");
            } else if (timer.getTimeInSeconds() >= 1) {
                setTimerText("3");
            }
        } else {
            timer.reset();
        }
    }

    public boolean isCountdownStarted() {
        return isCountdownStarted;
    }

    public void setIsCountdownStarted(boolean isStarted) {
        this.isCountdownStarted = isStarted;
    }
    
    private void setTimerText(String value) {
        bitmapText.setText(value);
    }
}
