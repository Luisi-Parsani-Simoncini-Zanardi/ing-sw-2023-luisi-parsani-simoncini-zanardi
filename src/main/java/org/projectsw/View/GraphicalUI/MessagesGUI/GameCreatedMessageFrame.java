package org.projectsw.View.GraphicalUI.MessagesGUI;

import javax.swing.*;

public class GameCreatedMessageFrame extends MessageFrame {
    public GameCreatedMessageFrame() {
        super();
        JOptionPane.showMessageDialog(GameCreatedMessageFrame.this, "Number of players successfully selected\nThe game has been created!");
    }
}
