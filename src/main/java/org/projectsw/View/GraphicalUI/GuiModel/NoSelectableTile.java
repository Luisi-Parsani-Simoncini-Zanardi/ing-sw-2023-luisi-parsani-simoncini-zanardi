package org.projectsw.View.GraphicalUI.GuiModel;

import org.projectsw.Model.Enums.TilesEnum;
import org.projectsw.Model.Tile;
import org.projectsw.Util.PathSolverGui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class NoSelectableTile extends JLabel {
    public NoSelectableTile(Tile tile) {
        super();
        ImageIcon icon = null;
        int size = 50;
        switch (tile.getTile()) {
            case CATS -> icon = new ImageIcon(PathSolverGui.cats(tile.getImageNumber()));
            case GAMES -> icon = new ImageIcon(PathSolverGui.games(tile.getImageNumber()));
            case FRAMES -> icon = new ImageIcon(PathSolverGui.frames(tile.getImageNumber()));
            case PLANTS -> icon = new ImageIcon(PathSolverGui.plants(tile.getImageNumber()));
            case TROPHIES -> icon = new ImageIcon(PathSolverGui.trophies(tile.getImageNumber()));
            case BOOKS -> icon = new ImageIcon(PathSolverGui.books(tile.getImageNumber()));
        }
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
