package org.projectsw.View.GraphicalUI.MessagesGUI;

import javax.swing.*;

public class YouAreAloneMessage extends MessageFrame{
    public YouAreAloneMessage(){
        super();
        JOptionPane.showMessageDialog(YouAreAloneMessage.this, "You are alone!\nIf no one comebacks you will win (in 10 seconds)");
    }
}
