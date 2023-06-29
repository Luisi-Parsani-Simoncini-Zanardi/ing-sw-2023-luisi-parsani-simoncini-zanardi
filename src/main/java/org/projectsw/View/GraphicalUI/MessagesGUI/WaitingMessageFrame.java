package org.projectsw.View.GraphicalUI.MessagesGUI;

import javax.swing.*;

/**
 * This class represents a message frame indicating that the player is waiting for more players to join the game.
 * It extends the MessageFrame class.
 */
public class WaitingMessageFrame extends MessageFrame {

    /**
     * Constructs a WaitingMessageFrame object.
     * It displays a message dialog indicating that the player is in the game and waiting for more players to join.
     */
    public WaitingMessageFrame(){
        super();
        JOptionPane.showMessageDialog(WaitingMessageFrame.this, "You are in the game!\nWaiting for more players to join");
    }
}



