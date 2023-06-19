package org.projectsw.View.GraphicalUI.GuiModel;

import org.projectsw.Model.Tile;
import org.projectsw.Util.PathSolverGui;

import javax.swing.*;

public class NoSelectableTile extends JLabel {
    public NoSelectableTile(Tile tile) {
        super();
        switch (tile.getTile()) {
            case CATS -> setIcon(new ImageIcon(PathSolverGui.cats(tile.getImageNumber())));
            case GAMES -> setIcon(new ImageIcon(PathSolverGui.games(tile.getImageNumber())));
            case FRAMES -> setIcon(new ImageIcon(PathSolverGui.frames(tile.getImageNumber())));
            case PLANTS -> setIcon(new ImageIcon(PathSolverGui.plants(tile.getImageNumber())));
            case TROPHIES -> setIcon(new ImageIcon(PathSolverGui.trophies(tile.getImageNumber())));
            case BOOKS -> setIcon(new ImageIcon(PathSolverGui.books(tile.getImageNumber())));
            case EMPTY -> setText("E");
            case UNUSED -> setText("U");
        }
    }
}
