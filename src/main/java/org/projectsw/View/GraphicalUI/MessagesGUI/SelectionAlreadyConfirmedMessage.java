package org.projectsw.View.GraphicalUI.MessagesGUI;

import javax.swing.*;

public class SelectionAlreadyConfirmedMessage extends MessageFrame{
    public SelectionAlreadyConfirmedMessage(){
        super();
        JOptionPane.showMessageDialog(SelectionAlreadyConfirmedMessage.this,"You have already confirmed your selection,\nyou can't select or deselect any tile now");
    }
}
