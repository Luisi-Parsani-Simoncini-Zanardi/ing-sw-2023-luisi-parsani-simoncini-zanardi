package org.projectsw.View.GraphicalUI.GuiModel;

import org.projectsw.Util.PathSolverGui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/*
 * This class represents the CommonGoals, putting them on a panel.
 */
public class CommonGoalImage extends JPanel {

    /*
     * Constructs the CommonGoalImage form the descriptions of CommonGoals available for the players
     */
    public CommonGoalImage (String description1, String description2) {
        super();
        setLayout(new BorderLayout());

        String path;

        String path2;

        ImageIcon image1;
        switch (description1) {
            case "Five tiles of the same type forming an X." -> path = "/ImagesGui/CommonGoals/10.jpg"; //10
            case "Five tiles of the same type forming a\ndiagonal." -> path = "/ImagesGui/CommonGoals/11.jpg"; //11
            case "Four tiles of the same type in the four\ncorners of the bookshelf. " -> path = "/ImagesGui/CommonGoals/8.jpg"; //8
            case """
                Eight tiles of the same type. There’s no
                restriction about the position of these
                tiles.""" -> path = "/ImagesGui/CommonGoals/9.jpg"; //9
            case """
                    Four groups each containing at least
                    4 tiles of the same type.
                    The tiles of one group can be different
                    from those of another group.""" -> path = "/ImagesGui/CommonGoals/3.jpg"; //3
            case """
                    Six groups each containing at least
                    2 tiles of the same type.
                    The tiles of one group can be different
                    from those of another group.""" -> path = "/ImagesGui/CommonGoals/4.jpg"; //4
            case "Two columns each formed by 6\ndifferent types of tiles." -> path = "/ImagesGui/CommonGoals/2.jpg"; //2
            case """
                        Three columns each formed by 6 tiles
                        of maximum three different types. One
                        column can show the same or a different
                        combination of another column.""" -> path = "/ImagesGui/CommonGoals/5.jpg"; //5
            case """
                        Two lines each formed by 5 different
                        types of tiles. One line can show the
                        same or a different combination of the
                        other line.""" -> path = "/ImagesGui/CommonGoals/6.jpg"; //6
            case """
                        Four lines each formed by 5 tiles of
                        maximum three different types. One
                        line can show the same or a different
                        combination of another line.""" ->  path = "/ImagesGui/CommonGoals/7.jpg"; //7
            case """
                Two groups each containing 4 tiles of
                the same type in a 2x2 square. The tiles
                of one square can be different from
                those of the other square.""" ->  path = "/ImagesGui/CommonGoals/1.jpg"; //1
            case """
                Five columns of increasing or decreasing
                height. Starting from the first column on
                the left or on the right, each next column
                must be made of exactly one more tile.
                Tiles can be of any type.""" ->  path = "/ImagesGui/CommonGoals/12.jpg"; //12
            default -> path = "/ImagesGui/CommonGoals/back.jpg";
        }

        InputStream inputStream1 = CommonGoalImage.class.getResourceAsStream(path);

        BufferedImage imageF = null;

        try {
            imageF = ImageIO.read(inputStream1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        image1 = new ImageIcon(imageF);

        ImageIcon image2;
        switch (description2) {
            case "Five tiles of the same type forming an X." -> path2 = "/ImagesGui/CommonGoals/10.jpg"; //10
            case "Five tiles of the same type forming a\ndiagonal." -> path2 = "/ImagesGui/CommonGoals/11.jpg"; //11
            case "Four tiles of the same type in the four\ncorners of the bookshelf. " -> path2 = "/ImagesGui/CommonGoals/8.jpg"; //8
            case """
                Eight tiles of the same type. There’s no
                restriction about the position of these
                tiles.""" -> path2 = "/ImagesGui/CommonGoals/9.jpg"; //9
            case """
                    Four groups each containing at least
                    4 tiles of the same type.
                    The tiles of one group can be different
                    from those of another group.""" -> path2 = "/ImagesGui/CommonGoals/3.jpg"; //3
            case """
                    Six groups each containing at least
                    2 tiles of the same type.
                    The tiles of one group can be different
                    from those of another group.""" -> path2 = "/ImagesGui/CommonGoals/4.jpg"; //4
            case "Two columns each formed by 6\ndifferent types of tiles." -> path2 = "/ImagesGui/CommonGoals/2.jpg"; //2
            case """
                        Three columns each formed by 6 tiles
                        of maximum three different types. One
                        column can show the same or a different
                        combination of another column.""" -> path2 = "/ImagesGui/CommonGoals/5.jpg"; //5
            case """
                        Two lines each formed by 5 different
                        types of tiles. One line can show the
                        same or a different combination of the
                        other line.""" -> path2 = "/ImagesGui/CommonGoals/6.jpg"; //6
            case """
                        Four lines each formed by 5 tiles of
                        maximum three different types. One
                        line can show the same or a different
                        combination of another line.""" ->  path2 = "/ImagesGui/CommonGoals/7.jpg"; //7
            case """
                Two groups each containing 4 tiles of
                the same type in a 2x2 square. The tiles
                of one square can be different from
                those of the other square.""" ->  path2 = "/ImagesGui/CommonGoals/1.jpg"; //1
            case """
                Five columns of increasing or decreasing
                height. Starting from the first column on
                the left or on the right, each next column
                must be made of exactly one more tile.
                Tiles can be of any type.""" ->  path2 = "/ImagesGui/CommonGoals/12.jpg"; //12
            default -> path2 = "/ImagesGui/CommonGoals/back.jpg";
        }

        InputStream inputStream2 = CommonGoalImage.class.getResourceAsStream(path2);

        BufferedImage imageS = null;

        try {
            imageS = ImageIO.read(inputStream2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        image2 = new ImageIcon(imageS);

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(1,2,4,4));

        Image originalImage = image1.getImage();

        Image resizedImage = originalImage.getScaledInstance(400, 400, Image.SCALE_SMOOTH);

        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        Image originalImage2 = image2.getImage();

        Image resizedImage2 = originalImage2.getScaledInstance(400, 400, Image.SCALE_SMOOTH);

        ImageIcon resizedIcon2 = new ImageIcon(resizedImage2);

        gridPanel.add(new JLabel(resizedIcon));
        gridPanel.add(new JLabel(resizedIcon2));
        gridPanel.setPreferredSize(new Dimension(15,15));
        add(gridPanel,BorderLayout.CENTER);
    }
}
