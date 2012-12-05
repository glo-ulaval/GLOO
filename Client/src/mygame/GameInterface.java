/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.system.JmeContext;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.AbstractButton;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 *
 * @author Vincent Séguin
 */
public class GameInterface implements ItemListener {

    private JPanel mainJPanel;
    private JButton createGameButton;
    private JButton joinGameButton;
    private JList gameList;

    public void addComponentToPane(Container pane) {
        JLabel title = new JLabel("ANGRY PIDGE!", JLabel.CENTER);
        Font newLabelFont = new Font(title.getFont().getName(), Font.BOLD, 22);
        title.setFont(newLabelFont);
        title.setForeground(Color.RED);
        title.setVerticalTextPosition(AbstractButton.CENTER);
        title.setHorizontalTextPosition(AbstractButton.LEADING);

        JPanel buttons = new JPanel();
        createGameButton = new JButton("Create Game");
        createGameButton.setVerticalTextPosition(AbstractButton.CENTER);
        createGameButton.setHorizontalTextPosition(AbstractButton.LEADING);

        joinGameButton = new JButton("Join Game");
        joinGameButton.setVerticalTextPosition(AbstractButton.CENTER);
        joinGameButton.setHorizontalTextPosition(AbstractButton.LEADING);

        createGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameClient app = new GameClient();
                app.start(JmeContext.Type.Display);
                Frame.getFrames()[0].dispose();
            }
        });

        joinGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameClient app = new GameClient();
                app.start(JmeContext.Type.Display);
                Frame.getFrames()[0].dispose();
            }
        });
        buttons.add(createGameButton);
        buttons.add(joinGameButton);

        mainJPanel = new JPanel(new CardLayout());
        mainJPanel.add(buttons);

        pane.add(title, BorderLayout.PAGE_START);
        pane.add(mainJPanel, BorderLayout.CENTER);
    }

    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout) (mainJPanel.getLayout());
        cl.show(mainJPanel, (String) evt.getItem());
    }
}
