package org.projectsw.View.GraphicalUI.MessagesGUI;

import javax.swing.*;

public class SelectionNotPossibleAnymoreMessage extends MessageFrame{
    public SelectionNotPossibleAnymoreMessage(){
        super();
        JOptionPane.showMessageDialog(SelectionNotPossibleAnymoreMessage.this,"You can't select more tiles!");
    }
}
