package org.projectsw.View.GraphicalUI.MessagesGUI;

import javax.swing.*;

public class WaitingMessageFrame extends MessageFrame {
    public WaitingMessageFrame(){
        super();
        JOptionPane.showMessageDialog(WaitingMessageFrame.this, "You are in the game!\nWaiting for more players to join");
    }
}



