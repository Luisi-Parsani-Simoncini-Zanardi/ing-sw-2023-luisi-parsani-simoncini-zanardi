package org.projectsw.View.GraphicalUI.GuiModel;

import org.projectsw.Util.PathSolverGui;

import javax.swing.*;
import java.awt.*;

public class CommonGoalImage extends JPanel {
    public CommonGoalImage (String description1, String description2) {
        super();
        setLayout(new BorderLayout());
        JPanel commonGoal1 = new JPanel();
        JPanel commonGoal2 = new JPanel();
        commonGoal1.setLayout(new BorderLayout());
        commonGoal2.setLayout(new BorderLayout());
        add(commonGoal1,BorderLayout.EAST);
        add(commonGoal2,BorderLayout.WEST);

        JLabel imageLabel1 = new JLabel();
        switch (description1) {
            case "Five tiles of the same type forming an X." -> imageLabel1.setIcon(new ImageIcon(PathSolverGui.commonGoalPath(10)));
            case "Five tiles of the same type forming a\ndiagonal." -> imageLabel1.setIcon(new ImageIcon(PathSolverGui.commonGoalPath(11)));
            case "Four tiles of the same type in the four\ncorners of the bookshelf. " -> imageLabel1.setIcon(new ImageIcon(PathSolverGui.commonGoalPath(8)));
            case """
                Eight tiles of the same type. There’s no
                restriction about the position of these
                tiles.""" -> imageLabel1.setIcon(new ImageIcon(PathSolverGui.commonGoalPath(9)));
            case """
                    Four groups each containing at least
                    4 tiles of the same type.
                    The tiles of one group can be different
                    from those of another group.""" -> imageLabel1.setIcon(new ImageIcon(PathSolverGui.commonGoalPath(3)));
            case """
                    Six groups each containing at least
                    2 tiles of the same type.
                    The tiles of one group can be different
                    from those of another group.""" -> imageLabel1.setIcon(new ImageIcon(PathSolverGui.commonGoalPath(4)));
            case "Two columns each formed by 6\ndifferent types of tiles." -> imageLabel1.setIcon(new ImageIcon(PathSolverGui.commonGoalPath(2)));
            case """
                        Three columns each formed by 6 tiles
                        of maximum three different types. One
                        column can show the same or a different
                        combination of another column.""" -> imageLabel1.setIcon(new ImageIcon(PathSolverGui.commonGoalPath(5)));
            case """
                        Two lines each formed by 5 different
                        types of tiles. One line can show the
                        same or a different combination of the
                        other line.""" -> imageLabel1.setIcon(new ImageIcon(PathSolverGui.commonGoalPath(6)));
            case """
                        Four lines each formed by 5 tiles of
                        maximum three different types. One
                        line can show the same or a different
                        combination of another line.""" -> imageLabel1.setIcon(new ImageIcon(PathSolverGui.commonGoalPath(7)));
            case """
                Two groups each containing 4 tiles of
                the same type in a 2x2 square. The tiles
                of one square can be different from
                those of the other square.""" -> imageLabel1.setIcon(new ImageIcon(PathSolverGui.commonGoalPath(1)));
            case """
                Five columns of increasing or decreasing
                height. Starting from the first column on
                the left or on the right, each next column
                must be made of exactly one more tile.
                Tiles can be of any type.""" -> imageLabel1.setIcon(new ImageIcon(PathSolverGui.commonGoalPath(12)));
        }
        commonGoal1.add(imageLabel1,BorderLayout.NORTH);
        commonGoal1.add(new JLabel(description1),BorderLayout.SOUTH);

        JLabel imageLabel2 = new JLabel();
        switch (description2) {
            case "Five tiles of the same type forming an X." -> imageLabel2.setIcon(new ImageIcon(PathSolverGui.commonGoalPath(10)));
            case "Five tiles of the same type forming a\ndiagonal." -> imageLabel2.setIcon(new ImageIcon(PathSolverGui.commonGoalPath(11)));
            case "Four tiles of the same type in the four\ncorners of the bookshelf. " -> imageLabel2.setIcon(new ImageIcon(PathSolverGui.commonGoalPath(8)));
            case """
                Eight tiles of the same type. There’s no
                restriction about the position of these
                tiles.""" -> imageLabel2.setIcon(new ImageIcon(PathSolverGui.commonGoalPath(9)));
            case """
                    Four groups each containing at least
                    4 tiles of the same type.
                    The tiles of one group can be different
                    from those of another group.""" -> imageLabel2.setIcon(new ImageIcon(PathSolverGui.commonGoalPath(3)));
            case """
                    Six groups each containing at least
                    2 tiles of the same type.
                    The tiles of one group can be different
                    from those of another group.""" -> imageLabel2.setIcon(new ImageIcon(PathSolverGui.commonGoalPath(4)));
            case "Two columns each formed by 6\ndifferent types of tiles." -> imageLabel2.setIcon(new ImageIcon(PathSolverGui.commonGoalPath(2)));
            case """
                        Three columns each formed by 6 tiles
                        of maximum three different types. One
                        column can show the same or a different
                        combination of another column.""" -> imageLabel2.setIcon(new ImageIcon(PathSolverGui.commonGoalPath(5)));
            case """
                        Two lines each formed by 5 different
                        types of tiles. One line can show the
                        same or a different combination of the
                        other line.""" -> imageLabel2.setIcon(new ImageIcon(PathSolverGui.commonGoalPath(6)));
            case """
                        Four lines each formed by 5 tiles of
                        maximum three different types. One
                        line can show the same or a different
                        combination of another line.""" -> imageLabel2.setIcon(new ImageIcon(PathSolverGui.commonGoalPath(7)));
            case """
                Two groups each containing 4 tiles of
                the same type in a 2x2 square. The tiles
                of one square can be different from
                those of the other square.""" -> imageLabel2.setIcon(new ImageIcon(PathSolverGui.commonGoalPath(1)));
            case """
                Five columns of increasing or decreasing
                height. Starting from the first column on
                the left or on the right, each next column
                must be made of exactly one more tile.
                Tiles can be of any type.""" -> imageLabel2.setIcon(new ImageIcon(PathSolverGui.commonGoalPath(12)));
        }
        commonGoal2.add(imageLabel2,BorderLayout.NORTH);
        commonGoal2.add(new JLabel(description2),BorderLayout.SOUTH);
    }
}
