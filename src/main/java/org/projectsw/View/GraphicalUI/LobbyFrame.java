package org.projectsw.View.GraphicalUI;

import javax.swing.*;
import java.awt.*;

public class LobbyFrame extends JFrame {

    GuiManager guiManager;
    public LobbyFrame(GuiManager guiManager){
        this.guiManager = guiManager;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        setLayout(new BorderLayout());
        JPanel menuButtonsPanel = new JPanel();
        menuButtonsPanel.setLayout(new GridLayout(3,1));
        add(menuButtonsPanel);

        JButton buttonNickname = new JButton("Insert nickname");
        buttonNickname.addActionListener(e -> {


            guiManager.notifyResponse();
            guiManager.openNumberOfPlayersFrame();
        });
        menuButtonsPanel.add(buttonNickname);

        if(guiManager.isFirstPlayer()) {
            JButton buttonNumberPlayers = new JButton("Insert number of players");
            buttonNumberPlayers.addActionListener(e -> {
                dispose();
                guiManager.notifyResponse();
            });
            menuButtonsPanel.add(buttonNumberPlayers);
        }

        if(guiManager.isGameSavedExist() && guiManager.isFirstPlayer()) {
            JButton buttonSavedGame = new JButton("Select previous game");
            buttonSavedGame.addActionListener(e -> {
                dispose();
                guiManager.notifyResponse();
            });
            menuButtonsPanel.add(buttonSavedGame);
        }

        setVisible(true);
    }
}
