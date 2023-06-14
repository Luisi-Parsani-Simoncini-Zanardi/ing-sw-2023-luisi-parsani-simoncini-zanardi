package org.projectsw.View.GraphicalUI.MessagesGUI;

import javax.swing.*;

public class GameCreatedMessageFrame extends JFrame {
    public GameCreatedMessageFrame() {
        JOptionPane.showMessageDialog(GameCreatedMessageFrame.this, "Number of players successfully selected\nThe game has been created!");
    }
}
