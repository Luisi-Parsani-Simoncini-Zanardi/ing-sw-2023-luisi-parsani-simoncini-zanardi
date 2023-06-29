package org.projectsw.View.GraphicalUI.MessagesGUI;

import javax.swing.*;
/**
 * This class represents a message frame indicating that the player is alone in the game and will win in 10 seconds.
 * It extends the MessageFrame class.
 */
public class YouAreAloneMessage extends MessageFrame {

    /**
     * Constructs a YouAreAloneMessage object.
     * It displays a message dialog indicating that the player is alone in the game and will win in 10 seconds.
     */
    public YouAreAloneMessage(){
        super();
        JOptionPane.showMessageDialog(YouAreAloneMessage.this, "You are alone in this game \n you will win in 10 seconds");
    }
}