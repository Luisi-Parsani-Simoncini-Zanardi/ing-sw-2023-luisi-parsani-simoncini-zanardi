package org.projectsw.View.GraphicalUI.MessagesGUI;

import javax.swing.*;

public class LobbyFullKillMessageFrame extends JFrame {
    public LobbyFullKillMessageFrame(){
        JOptionPane.showMessageDialog(LobbyFullKillMessageFrame.this, "Unable to join the game, the lobby is full.\nClosing the process...");
    }
}

