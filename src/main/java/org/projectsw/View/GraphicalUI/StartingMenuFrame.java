package org.projectsw.View.GraphicalUI;

import org.projectsw.Util.Config;
import org.projectsw.Util.PathSolverGui;

import javax.swing.*;
import java.awt.*;

/*
 * This class is the father-class of all the lunching menu frames.
 */
public class StartingMenuFrame extends JFrame {
    GuiManager guiManager;
    ImageIcon imageIcon;

    /**
     * Constructs a StartingMenuFrame with a pre-set icon, size (with no possibility of resize), location and
     * operation on close and background color (exit).
     * @param guiManager The GuiManager object used to send back the messages.
     */
    public StartingMenuFrame(GuiManager guiManager) {
        this.guiManager = guiManager;
        imageIcon = new ImageIcon(PathSolverGui.icon());
        setIconImage(imageIcon.getImage());
        setSize(400,300);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Config.defaultGuiBackgroundColor);
    }
}
