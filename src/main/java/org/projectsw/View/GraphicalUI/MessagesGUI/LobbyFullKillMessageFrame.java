package org.projectsw.View.GraphicalUI.MessagesGUI;

import javax.swing.*;

public class LobbyFullKillMessageFrame extends MessageFrame {
    public LobbyFullKillMessageFrame(){
        super();
        JOptionPane.showMessageDialog(LobbyFullKillMessageFrame.this, "Unable to join the game, the lobby is full.\nClosing the process...");
    }
}

