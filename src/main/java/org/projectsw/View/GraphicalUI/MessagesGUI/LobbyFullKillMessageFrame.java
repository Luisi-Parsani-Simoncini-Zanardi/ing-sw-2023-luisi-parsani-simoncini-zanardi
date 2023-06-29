package org.projectsw.View.GraphicalUI.MessagesGUI;

import javax.swing.*;

/**
 * This class represents a message frame indicating that joining the game lobby is not possible due to it being full.
 * It extends the MessageFrame class.
 */
public class LobbyFullKillMessageFrame extends MessageFrame {

    /**
     * Constructs a LobbyFullKillMessageFrame object.
     * It displays a message dialog indicating that joining the game lobby is not possible due to it being full.
     * The process is then closed.
     */
    public LobbyFullKillMessageFrame(){
        super();
        JOptionPane.showMessageDialog(LobbyFullKillMessageFrame.this, "Unable to join the game, the lobby is full.\nClosing the process...");
    }
}

