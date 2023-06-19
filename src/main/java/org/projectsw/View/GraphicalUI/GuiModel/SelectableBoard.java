package org.projectsw.View.GraphicalUI.GuiModel;

import org.projectsw.Model.Board;
import org.projectsw.Model.Enums.TilesEnum;
import org.projectsw.Model.SerializableGame;
import org.projectsw.Model.Tile;
import org.projectsw.Util.Config;
import org.projectsw.View.GraphicalUI.GameMainFrame;
import org.projectsw.View.GraphicalUI.GuiManager;
import javax.swing.*;
import java.awt.*;

public class SelectableBoard extends JPanel {

    GuiManager guiManager;
    GameMainFrame gameMainFrame;

    public SelectableBoard(SerializableGame model, GuiManager guiManager, GameMainFrame gameMainFrame){
        super();
        setLayout(new GridLayout(9,9));
        this.gameMainFrame = gameMainFrame;
        this.guiManager = guiManager;
        Board board = createBoard(model);
        Tile[][] boardGrid = board.getBoard();

        for(int i=0;i<Config.boardHeight;i++) {
            for(int j=0;j<Config.boardLength;j++) {
                Tile tile = boardGrid[i][j];
                SelectableTile selectableTile = new SelectableTile(tile,new Point(i,j),board.getSelectablePoints(),board.getTemporaryPoints());
                add(selectableTile);
                selectableTile.addActionListener(e -> {
                    guiManager.sendTileSelectionFromBoard(selectableTile.getPosition(), this.gameMainFrame);
                });
            }
        }
    }

    private Board createBoard(SerializableGame model){
        Board board = new Board(model.getSelectablePoints(), model.getTemporaryPoints());
        board.setBoard(model.getGameBoard());
        return board;
    }
}
