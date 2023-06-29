package org.projectsw.View.GraphicalUI.MessagesGUI;

import javax.swing.*;

/**
 * This class represents a message frame indicating that column selection is refused.
 * It extends the MessageFrame class.
 */
public class ColumnSelectionRefusedMessage extends MessageFrame{

    /**
     * Constructs a ColumnSelectionRefusedMessage object.
     * It displays a message dialog indicating that column selection is not allowed.
     */
    public ColumnSelectionRefusedMessage(){
        super();
        JOptionPane.showMessageDialog(ColumnSelectionRefusedMessage.this, "You can't select this column");
    }
}
