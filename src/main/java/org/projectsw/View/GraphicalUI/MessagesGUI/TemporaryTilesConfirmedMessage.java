package org.projectsw.View.GraphicalUI.MessagesGUI;

import javax.swing.*;


/**
 * This class represents a message frame indicating that the temporary tiles selection has been confirmed.
 * It extends the MessageFrame class.
 */
public class TemporaryTilesConfirmedMessage extends MessageFrame{

    /**
     * Constructs a TemporaryTilesConfirmedMessage object.
     * It displays a message dialog indicating that the temporary tiles selection has been confirmed.
     * It instructs the user to insert the column on the other page.
     */
    public TemporaryTilesConfirmedMessage(){
        super();
        JOptionPane.showMessageDialog(TemporaryTilesConfirmedMessage.this,"You have confirmed your selection,\nnow please insert the column on the other page");
    }
}
