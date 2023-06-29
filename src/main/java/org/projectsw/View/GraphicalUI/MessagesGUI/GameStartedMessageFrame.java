package org.projectsw.View.GraphicalUI.MessagesGUI;

import javax.swing.*;

/**
 * This class represents a message frame indicating that the game has started.
 * It extends the MessageFrame class.
 */
public class GameStartedMessageFrame extends MessageFrame {

    /**
     * Constructs a GameStartedMessageFrame object.
     * It displays a message dialog indicating that the game has started.
     */
    public GameStartedMessageFrame(){
        super();
        JOptionPane.showMessageDialog(GameStartedMessageFrame.this, "Game is started!");
    }
}
