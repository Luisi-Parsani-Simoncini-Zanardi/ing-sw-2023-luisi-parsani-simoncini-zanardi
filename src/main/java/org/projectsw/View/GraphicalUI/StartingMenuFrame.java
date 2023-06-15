package org.projectsw.View.GraphicalUI;

import org.projectsw.Util.PathSolverGui;

import javax.swing.*;

public class StartingMenuFrame extends JFrame {
    GuiManager guiManager;
    ImageIcon imageIcon;
    public StartingMenuFrame(GuiManager guiManager) {
        this.guiManager = guiManager;
        setIconImage(imageIcon.getImage());
        setSize(400,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
