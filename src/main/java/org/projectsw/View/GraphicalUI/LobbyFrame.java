package org.projectsw.View.GraphicalUI;

import org.projectsw.Util.Config;

import javax.swing.*;
import java.awt.*;


/**
 * This class represents the launching menu of the game.
 * It extends the StartingMenuFrame class.
 */
public class LobbyFrame extends StartingMenuFrame {

    /**
     * Constructs the launching menu of the game, created with a grid of buttons.
     * @param guiManager The GuiManager object used to send back the messages.
     */
    public LobbyFrame(GuiManager guiManager){
        super(guiManager);
        setTitle("My Shelfie Launcher");

        // Create and configure the menu buttons panel
        JPanel menuButtonsPanel = new JPanel();
        menuButtonsPanel.setLayout(new GridLayout(3,1));
        menuButtonsPanel.setBackground(Config.defaultGuiBackgroundColor);
        add(menuButtonsPanel,BorderLayout.CENTER);

        // Add the "Insert nickname" button if necessary
        if(guiManager.isAskNickname()){
            JButton buttonNickname = new JButton("Insert nickname");
            buttonNickname.addActionListener(e -> {
                new NicknameFrame(guiManager);
                dispose();
            });
            menuButtonsPanel.add(buttonNickname);
        }

        // Add the "Insert number of players" button if necessary
        if(guiManager.isFirstPlayer()) {
            JButton buttonNumberPlayers = new JButton("Insert number of players");
            buttonNumberPlayers.addActionListener(e -> {
                new AskNumberFrame(guiManager);
                dispose();
            });
            menuButtonsPanel.add(buttonNumberPlayers);
        }

        // Add the "Load previous game" button if a saved game exists, and it's the first player
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
