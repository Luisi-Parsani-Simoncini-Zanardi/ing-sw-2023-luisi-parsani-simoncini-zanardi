package org.projectsw.View.GraphicalUI.MessagesGUI;

import javax.swing.*;

/**
 * This class represents a message frame indicating that tile selection is not allowed because it's not the current player's turn.
 * It extends the MessageFrame class.
 */
public class SelectionNoCurrentPlayerMessage extends MessageFrame{

    /**
     * Constructs a SelectionNoCurrentPlayerMessage object.
     * It displays a message dialog indicating that tile selection is not allowed because it's not the current player's turn.
     */
    public SelectionNoCurrentPlayerMessage(){
        super();
        JOptionPane.showMessageDialog(SelectionNoCurrentPlayerMessage.this,"You cant select tiles now,\nit's not your turn!");
    }
}
