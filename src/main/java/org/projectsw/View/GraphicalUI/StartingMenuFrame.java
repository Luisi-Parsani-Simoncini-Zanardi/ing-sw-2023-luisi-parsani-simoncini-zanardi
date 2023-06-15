package org.projectsw.View.GraphicalUI;

import org.projectsw.Util.Config;
import org.projectsw.Util.PathSolverGui;

import javax.swing.*;
import java.awt.*;

public class StartingMenuFrame extends JFrame {
    GuiManager guiManager;
    ImageIcon imageIcon;
    public StartingMenuFrame(GuiManager guiManager) {
        this.guiManager = guiManager;
        imageIcon = new ImageIcon(PathSolverGui.icon());
        setIconImage(imageIcon.getImage());
        setSize(400,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Config.defaultGuiBackgroundColor);
    }
}
