package org.projectsw.View.GraphicalUI.GuiModel;

import org.projectsw.Util.PathSolverGui;

import javax.swing.*;
import java.awt.*;

public class CommonGoalImage extends JPanel {
    public CommonGoalImage (String description1, String description2) {
        super();
        setLayout(new GridLayout(1,2));


        int imageDimensionX = 13;
        int imageDimensionY = 9;

        ImageIcon image1;
        switch (description1) {
            case "Five tiles of the same type forming an X." -> image1 = new ImageIcon(PathSolverGui.commonGoalPath(10)); //10
            case "Five tiles of the same type forming a\ndiagonal." -> image1 = new ImageIcon(PathSolverGui.commonGoalPath(11)); //11
            case "Four tiles of the same type in the four\ncorners of the bookshelf. " -> image1 = new ImageIcon(PathSolverGui.commonGoalPath(8)); //8
            case """
                Eight tiles of the same type. There’s no
                restriction about the position of these
                tiles.""" -> image1 = new ImageIcon(PathSolverGui.commonGoalPath(9)); //9
            case """
                    Four groups each containing at least
                    4 tiles of the same type.
                    The tiles of one group can be different
                    from those of another group.""" -> image1 = new ImageIcon(PathSolverGui.commonGoalPath(3)); //3
            case """
                    Six groups each containing at least
                    2 tiles of the same type.
                    The tiles of one group can be different
                    from those of another group.""" -> image1 = new ImageIcon(PathSolverGui.commonGoalPath(4)); //4
            case "Two columns each formed by 6\ndifferent types of tiles." -> image1 = new ImageIcon(PathSolverGui.commonGoalPath(2)); //2
            case """
                        Three columns each formed by 6 tiles
                        of maximum three different types. One
                        column can show the same or a different
                        combination of another column.""" -> image1 = new ImageIcon(PathSolverGui.commonGoalPath(5)); //5
            case """
                        Two lines each formed by 5 different
                        types of tiles. One line can show the
                        same or a different combination of the
                        other line.""" -> image1 = new ImageIcon(PathSolverGui.commonGoalPath(6)); //6
            case """
                        Four lines each formed by 5 tiles of
                        maximum three different types. One
                        line can show the same or a different
                        combination of another line.""" ->  image1 = new ImageIcon(PathSolverGui.commonGoalPath(7)); //7
            case """
                Two groups each containing 4 tiles of
                the same type in a 2x2 square. The tiles
                of one square can be different from
                those of the other square.""" ->  image1 = new ImageIcon(PathSolverGui.commonGoalPath(1)); //1
            case """
                Five columns of increasing or decreasing
                height. Starting from the first column on
                the left or on the right, each next column
                must be made of exactly one more tile.
                Tiles can be of any type.""" ->  image1 = new ImageIcon(PathSolverGui.commonGoalPath(12)); //12
            default -> image1 = new ImageIcon(PathSolverGui.commonGoalBack());
        }
        image1.getImage().getScaledInstance(imageDimensionX,imageDimensionY,Image.SCALE_SMOOTH);
        image1.setDescription(description1);
        JLabel imageLabel1 = new JLabel(image1);
        add(imageLabel1);

        ImageIcon image2;
        switch (description2) {
            case "Five tiles of the same type forming an X." -> image2 = new ImageIcon(PathSolverGui.commonGoalPath(10)); //10
            case "Five tiles of the same type forming a\ndiagonal." -> image2 = new ImageIcon(PathSolverGui.commonGoalPath(11)); //11
            case "Four tiles of the same type in the four\ncorners of the bookshelf. " -> image2 = new ImageIcon(PathSolverGui.commonGoalPath(8)); //8
            case """
                Eight tiles of the same type. There’s no
                restriction about the position of these
                tiles.""" -> image2 = new ImageIcon(PathSolverGui.commonGoalPath(9)); //9
            case """
                    Four groups each containing at least
                    4 tiles of the same type.
                    The tiles of one group can be different
                    from those of another group.""" -> image2 = new ImageIcon(PathSolverGui.commonGoalPath(3)); //3
            case """
                    Six groups each containing at least
                    2 tiles of the same type.
                    The tiles of one group can be different
                    from those of another group.""" -> image2 = new ImageIcon(PathSolverGui.commonGoalPath(4)); //4
            case "Two columns each formed by 6\ndifferent types of tiles." -> image2 = new ImageIcon(PathSolverGui.commonGoalPath(2)); //2
            case """
                        Three columns each formed by 6 tiles
                        of maximum three different types. One
                        column can show the same or a different
                        combination of another column.""" -> image2 = new ImageIcon(PathSolverGui.commonGoalPath(5)); //5
            case """
                        Two lines each formed by 5 different
                        types of tiles. One line can show the
                        same or a different combination of the
                        other line.""" -> image2 = new ImageIcon(PathSolverGui.commonGoalPath(6)); //6
            case """
                        Four lines each formed by 5 tiles of
                        maximum three different types. One
                        line can show the same or a different
                        combination of another line.""" ->  image2 = new ImageIcon(PathSolverGui.commonGoalPath(7)); //7
            case """
                Two groups each containing 4 tiles of
                the same type in a 2x2 square. The tiles
                of one square can be different from
                those of the other square.""" ->  image2 = new ImageIcon(PathSolverGui.commonGoalPath(1)); //1
            case """
                Five columns of increasing or decreasing
                height. Starting from the first column on
                the left or on the right, each next column
                must be made of exactly one more tile.
                Tiles can be of any type.""" ->  image2 = new ImageIcon(PathSolverGui.commonGoalPath(12)); //12
            default -> image2 = new ImageIcon(PathSolverGui.commonGoalBack());
        }
        image2.getImage().getScaledInstance(imageDimensionX,imageDimensionY,Image.SCALE_SMOOTH);
        image2.setDescription(description2);
        JLabel imageLabel2 = new JLabel(image2);
        add(imageLabel2);
    }
}
