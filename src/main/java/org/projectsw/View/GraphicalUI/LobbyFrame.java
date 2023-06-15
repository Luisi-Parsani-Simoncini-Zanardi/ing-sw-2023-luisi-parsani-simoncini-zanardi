package org.projectsw.View.GraphicalUI;

import javax.swing.*;
import java.awt.*;

public class LobbyFrame extends StartingMenuFrame {

    public LobbyFrame(GuiManager guiManager){
        super(guiManager);

        //debug
        System.out.println("\nLobby frame recreated:\n AskNickname: " + guiManager.isAskNickname() + "\nFistPlayer: " + guiManager.isFirstPlayer() + "\nLoadGame: " + guiManager.isGameSavedExist());


        JPanel menuButtonsPanel = new JPanel();
        menuButtonsPanel.setLayout(new GridLayout(3,1));
        add(menuButtonsPanel,BorderLayout.CENTER);

        if(guiManager.isAskNickname()){
            JButton buttonNickname = new JButton("Insert nickname");
            buttonNickname.addActionListener(e -> {
                new NicknameFrame(guiManager);
                dispose();
            });
            menuButtonsPanel.add(buttonNickname);
        }

        if(guiManager.isFirstPlayer()) {
            JButton buttonNumberPlayers = new JButton("Insert number of players");
            buttonNumberPlayers.addActionListener(e -> {
                new AskNumberFrame(guiManager);
                dispose();
            });
            menuButtonsPanel.add(buttonNumberPlayers);
        }

        if(guiManager.isGameSavedExist() && guiManager.isFirstPlayer()) {
            JButton buttonSavedGame = new JButton("Load previous game\n(Old save found)");
            buttonSavedGame.addActionListener(e -> {
                guiManager.sendLoadGameSelection();
                dispose();
            });
            menuButtonsPanel.add(buttonSavedGame);
        }

        setVisible(true);
    }
}