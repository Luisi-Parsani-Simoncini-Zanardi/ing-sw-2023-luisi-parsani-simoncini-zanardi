package org.projectsw.View.GraphicalUI.GuiModel;

import org.projectsw.Util.PathSolverGui;

import javax.swing.*;

public class PersonalGoalImage extends JPanel {
    public PersonalGoalImage (int code) {
        super();
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon(PathSolverGui.personalGoalPath(code)));
        add(imageLabel);
    }
}
