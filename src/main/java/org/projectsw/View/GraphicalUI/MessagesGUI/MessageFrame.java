package org.projectsw.View.GraphicalUI.MessagesGUI;

import org.projectsw.Util.Config;
import org.projectsw.Util.PathSolverGui;

import javax.swing.*;
import java.awt.*;

public class MessageFrame extends JFrame {
    ImageIcon imageIcon;
    public MessageFrame() {
        imageIcon = new ImageIcon(PathSolverGui.icon());
        setIconImage(imageIcon.getImage());
        setLocationRelativeTo(null);
        getContentPane().setBackground(Config.defaultGuiBackgroundColor);
    }
}
