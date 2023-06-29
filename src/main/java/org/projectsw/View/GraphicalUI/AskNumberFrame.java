package org.projectsw.View.GraphicalUI;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents a frame that prompts the user to enter the number of players.
 * It extends the StartingMenuFrame class.
 */
public class AskNumberFrame extends StartingMenuFrame {

    /**
     * Constructs an AskNumberFrame object with a specified GuiManager.
     * It displays a frame with buttons for selecting the number of players.
     * @param guiManager The GuiManager object used for managing the graphical user interface.
     */
    public AskNumberFrame(GuiManager guiManager) {
        super(guiManager);

        // Create and configure the panel
        JPanel panel = new JPanel();
        panel.add(new JLabel("Enter the number of players:"));
        panel.setLayout(new GridLayout(1, 3));

        // Create buttons for selecting the number of players
        JButton button2 = new JButton("2");
        button2.addActionListener(e -> {
            guiManager.sendNumberOfPlayers(2);
            dispose();
        });

        JButton button3 = new JButton("3");
        button3.addActionListener(e -> {
            guiManager.sendNumberOfPlayers(3);
            dispose();
        });

        JButton button4 = new JButton("4");
        button4.addActionListener(e -> {
            guiManager.sendNumberOfPlayers(4);
            dispose();
        });

        // Add buttons to the panel
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);
        getContentPane().add(panel);

        pack();
        setVisible(true);
    }
}
