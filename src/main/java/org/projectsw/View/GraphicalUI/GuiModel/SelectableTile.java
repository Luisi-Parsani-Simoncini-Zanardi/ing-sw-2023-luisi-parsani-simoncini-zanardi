package org.projectsw.View.GraphicalUI.GuiModel;

import org.projectsw.Model.Enums.TilesEnum;
import org.projectsw.Model.Tile;
import org.projectsw.Util.PathSolverGui;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public class SelectableTile extends JButton {

    private final Point position;

    public SelectableTile(Tile tile, Point point, ArrayList<Point> selectablePoints, ArrayList<Point> temporaryPoints) {
        super();
        position = point;
        ImageIcon icon = null;
        Border normalBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK);
        Border selectableBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GREEN);
        Border selectedBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.RED);
        switch (tile.getTile()) {
            case CATS -> icon = new ImageIcon(PathSolverGui.cats(tile.getImageNumber()));
            case GAMES -> icon = new ImageIcon(PathSolverGui.games(tile.getImageNumber()));
            case FRAMES -> icon = new ImageIcon(PathSolverGui.frames(tile.getImageNumber()));
            case PLANTS -> icon = new ImageIcon(PathSolverGui.plants(tile.getImageNumber()));
            case TROPHIES -> icon = new ImageIcon(PathSolverGui.trophies(tile.getImageNumber()));
            case BOOKS -> icon = new ImageIcon(PathSolverGui.books(tile.getImageNumber()));
        }
        setContentAreaFilled(false);
        if(!tile.getTile().equals(TilesEnum.EMPTY) && !tile.getTile().equals(TilesEnum.UNUSED)){
            assert icon != null;
            Image image = icon.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(image));
        }
        if(selectablePoints.contains(point)){
            setBorder(selectableBorder);
        } else if (temporaryPoints.contains(point)) {
            setBorder(selectedBorder);
        } else {
            setBorder(normalBorder);
        }
    }

    public SelectableTile(Tile tile) {
        super();
        position = null;
        ImageIcon icon = null;
        int buttonSize = 50;
        Border normalBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK);
        switch (tile.getTile()) {
            case CATS -> icon = new ImageIcon(PathSolverGui.cats(tile.getImageNumber()));
            case GAMES -> icon = new ImageIcon(PathSolverGui.games(tile.getImageNumber()));
            case FRAMES -> icon = new ImageIcon(PathSolverGui.frames(tile.getImageNumber()));
            case PLANTS -> icon = new ImageIcon(PathSolverGui.plants(tile.getImageNumber()));
            case TROPHIES -> icon = new ImageIcon(PathSolverGui.trophies(tile.getImageNumber()));
            case BOOKS -> icon = new ImageIcon(PathSolverGui.books(tile.getImageNumber()));
        }
        setPreferredSize(new Dimension(buttonSize,buttonSize));
        setContentAreaFilled(false);
        if(!tile.getTile().equals(TilesEnum.EMPTY) && !tile.getTile().equals(TilesEnum.UNUSED)){
            assert icon != null;
            Image image = icon.getImage().getScaledInstance(buttonSize,buttonSize,Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(image));
        }
        setBorder(normalBorder);
    }

    public Point getPosition() {
        return position;
    }
}
