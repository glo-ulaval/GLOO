/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.system.JmeContext;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Vincent Séguin
 */
public class GameInterface extends JPanel implements ActionListener {

    private JButton createGameButton;
    private JButton joinGameButton;

    public GameInterface() {
        createGameButton = new JButton("Create Game");
        createGameButton.setVerticalTextPosition(AbstractButton.CENTER);
        createGameButton.setHorizontalTextPosition(AbstractButton.LEADING);
        createGameButton.setActionCommand("createGame");
        
        joinGameButton = new JButton("Join Game");
        joinGameButton.setVerticalTextPosition(AbstractButton.CENTER);
        joinGameButton.setHorizontalTextPosition(AbstractButton.LEADING);
        joinGameButton.setActionCommand("joinGame");
        
        createGameButton.addActionListener(this);
        joinGameButton.addActionListener(this);
        add(createGameButton);
        add(joinGameButton);
    }
    
    public void actionPerformed(ActionEvent e) {
        if ("createGame".equals(e.getActionCommand())) {
            GameClient app = new GameClient();
            app.start(JmeContext.Type.Display);
        } 
    }
 
}
