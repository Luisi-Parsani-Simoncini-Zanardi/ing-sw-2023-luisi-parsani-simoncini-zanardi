package org.projectsw.View.GraphicalUI;

import javax.swing.*;
import java.awt.*;

public class AskNumberFrame extends StartingMenuFrame {

    public AskNumberFrame(GuiManager guiManager) {
        super(guiManager);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Enter the number of players:"));
        panel.setLayout(new GridLayout(1, 3));

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
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);
        getContentPane().add(panel);

        setVisible(true);
    }
}
