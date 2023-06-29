package org.projectsw.View.GraphicalUI.MessagesGUI;

import javax.swing.*;


/**
 * This class represents a custom message frame that notifies the user about a cancelled game join.
 * It extends the MessageFrame class and provides a specific message for the cancellation.
 */
public class JoinCancelledMessageFrame extends MessageFrame {

    /**
     * Constructs a JoinCancelledMessageFrame object.
     * It invokes the constructor of the parent class, sets the appropriate message, and displays it.
     */
    public JoinCancelledMessageFrame(){
        super();
        JOptionPane.showMessageDialog(JoinCancelledMessageFrame.this, "The join to the game has been cancelled\nClosing the process...");
    }
}
