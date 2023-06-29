package org.projectsw.View.GraphicalUI.MessagesGUI;

import javax.swing.*;

/**
 * This class represents a custom message frame that notifies the user about a successful game load.
 * It extends the MessageFrame class and provides a specific success message.
 */
public class LoadGameSuccessMessage extends MessageFrame{

    /**
     * Constructs a LoadGameSuccessMessage object.
     * It invokes the constructor of the parent class, sets the appropriate success message, and displays it.
     */
    public LoadGameSuccessMessage(){
        super();
        JOptionPane.showMessageDialog(LoadGameSuccessMessage.this,"Game successfully loaded!");
    }
}
