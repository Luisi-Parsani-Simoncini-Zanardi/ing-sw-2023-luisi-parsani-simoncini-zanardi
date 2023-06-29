package org.projectsw.View.GraphicalUI.MessagesGUI;

import org.projectsw.Util.Config;
import org.projectsw.Util.PathSolverGui;
import javax.swing.*;

/**
 * This class represents a custom message frame that extends the JFrame class.
 * It provides a base class for creating message frames with common functionality.
 */
public class MessageFrame extends JFrame {
    ImageIcon imageIcon;

    /**
     * Constructs a MessageFrame object.
     * It sets the icon image, centers the frame on the screen, and sets the background color.
     */
    public MessageFrame() {
        imageIcon = new ImageIcon(PathSolverGui.icon());
        setIconImage(imageIcon.getImage());
        setLocationRelativeTo(null);
        getContentPane().setBackground(Config.defaultGuiBackgroundColor);
    }
}
