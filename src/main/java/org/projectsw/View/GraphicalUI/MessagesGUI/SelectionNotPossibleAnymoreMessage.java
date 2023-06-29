package org.projectsw.View.GraphicalUI.MessagesGUI;

import javax.swing.*;

/**
 * This class represents a message frame indicating that tile selection is no longer possible.
 * It extends the MessageFrame class.
 */
public class SelectionNotPossibleAnymoreMessage extends MessageFrame{


    /**
     * Constructs a SelectionNotPossibleAnymoreMessage object.
     * It displays a message dialog indicating that tile selection is no longer possible.
     */
    public SelectionNotPossibleAnymoreMessage(){
        super();
        JOptionPane.showMessageDialog(SelectionNotPossibleAnymoreMessage.this,"This is the last tile you can choose");
    }
}
