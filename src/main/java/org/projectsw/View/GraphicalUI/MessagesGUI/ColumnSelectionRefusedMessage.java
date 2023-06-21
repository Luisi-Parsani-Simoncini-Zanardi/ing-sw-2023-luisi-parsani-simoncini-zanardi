package org.projectsw.View.GraphicalUI.MessagesGUI;

import javax.swing.*;

public class ColumnSelectionRefusedMessage extends MessageFrame{
    public ColumnSelectionRefusedMessage(){
        super();
        JOptionPane.showMessageDialog(ColumnSelectionRefusedMessage.this, "You can't select this column");
    }
}
