package org.projectsw.View.GraphicalUI.MessagesGUI;

import javax.swing.*;

/**
 * This class represents a message frame indicating that the game has been successfully created
 * after selecting the number of players. It extends the MessageFrame class.
 */
public class GameCreatedMessageFrame extends MessageFrame {

    /**
     * Constructs a GameCreatedMessageFrame object.
     * It displays a message dialog indicating that the number of players has been successfully selected
     * and the game has been created.
     */
    public GameCreatedMessageFrame() {
        super();
        JOptionPane.showMessageDialog(GameCreatedMessageFrame.this, "Number of players successfully selected\nThe game has been created!");
    }
}
