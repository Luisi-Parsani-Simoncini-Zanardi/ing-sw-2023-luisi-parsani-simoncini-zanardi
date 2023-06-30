package org.projectsw.View.GraphicalUI.GuiModel;

import org.projectsw.Model.Enums.TilesEnum;
import org.projectsw.Model.Tile;
import org.projectsw.Util.PathSolverGui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/*
 * This class represents the JLabel equivalent of the tile.
 */
public class NoSelectableTile extends JLabel {

    /**
     * Constructs the JLabel that corresponds to the passed tile.
     * @param tile the tile to transform in a label.
     */
    public NoSelectableTile(Tile tile) {
        super();
        String path;
        int size = 50;
        switch (tile.getTile()) {
            case CATS -> path = "/ImagesGui/Tiles/Cats" + tile.getImageNumber() + ".png";
            case GAMES -> path = "/ImagesGui/Tiles/Games" + tile.getImageNumber() + ".png";
            case FRAMES -> path = "/ImagesGui/Tiles/Frames" + tile.getImageNumber() + ".png";
            case PLANTS -> path = "/ImagesGui/Tiles/Plants" + tile.getImageNumber() + ".png";
            case TROPHIES -> path = "/ImagesGui/Tiles/Trophies" + tile.getImageNumber() + ".png";
            case BOOKS -> path = "/ImagesGui/Tiles/Books" + tile.getImageNumber() + ".png";
            default -> path = "/ImagesGui/Tiles/Books0.png";
        }

        InputStream inputStream = NoSelectableTile.class.getResourceAsStream(path);

        BufferedImage image1 = null;

        try {
            image1 = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ImageIcon icon = new ImageIcon(image1);

        Border separatorBorder = BorderFactory.createMatteBorder(1, 1, 0, 0, Color.BLACK);
        setPreferredSize(new Dimension(size,size));
        setBorder(separatorBorder);
        if(!tile.getTile().equals(TilesEnum.EMPTY) && !tile.getTile().equals(TilesEnum.UNUSED)){
            assert icon != null;
            Image image = icon.getImage().getScaledInstance(size,size,Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(image);
            setIcon(imageIcon);
        }
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
    }
}
