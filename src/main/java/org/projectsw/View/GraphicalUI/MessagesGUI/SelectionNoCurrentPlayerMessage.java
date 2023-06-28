package org.projectsw.View.GraphicalUI.MessagesGUI;

import javax.swing.*;

public class SelectionNoCurrentPlayerMessage extends MessageFrame{
    public SelectionNoCurrentPlayerMessage(){
        super();
        JOptionPane.showMessageDialog(SelectionNoCurrentPlayerMessage.this,"You cant select tiles now,\nit's not your turn!");
    }
}
