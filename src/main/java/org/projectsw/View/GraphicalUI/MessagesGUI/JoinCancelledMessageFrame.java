package org.projectsw.View.GraphicalUI.MessagesGUI;

import javax.swing.*;

public class JoinCancelledMessageFrame extends MessageFrame {
    public JoinCancelledMessageFrame(){
        super();
        JOptionPane.showMessageDialog(JoinCancelledMessageFrame.this, "The join to the game has been cancelled\nClosing the process...");
    }
}
