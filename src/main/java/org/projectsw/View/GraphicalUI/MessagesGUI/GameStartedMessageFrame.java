package org.projectsw.View.GraphicalUI.MessagesGUI;

import javax.swing.*;

public class GameStartedMessageFrame extends MessageFrame {
    public GameStartedMessageFrame(){
        super();
        JOptionPane.showMessageDialog(GameStartedMessageFrame.this, "Game is started!");
    }
}
