package org.projectsw.View.GraphicalUI.GuiModel;

import org.projectsw.Model.Board;
import org.projectsw.Model.Enums.TilesEnum;
import org.projectsw.Model.SerializableGame;
import org.projectsw.Model.Tile;
import org.projectsw.Util.Config;
import org.projectsw.View.GraphicalUI.GuiManager;

import javax.swing.*;
import java.awt.*;

public class SelectableBoard extends JPanel {

    GuiManager guiManager;

    public SelectableBoard(SerializableGame model, GuiManager guiManager){
        super();
        setLayout(new GridLayout(9,9));

        this.guiManager = guiManager;
        Board board = createBoard(model);
        Tile[][] boardGrid = board.getBoard();

        for(int i=0;i<Config.boardLength;i++) {
            for(int j=0;j<Config.boardHeight;j++) {
                Tile tile = boardGrid[i][j];
                SelectableTileGui selectableTileGui = new SelectableTileGui(tile,new Point(i,j));
                add(selectableTileGui);
                selectableTileGui.addActionListener(e -> {
                    guiManager.sendTileSelectionFromBoard(selectableTileGui.getPosition());
                });
            }
        }
    }

    public SelectableBoard(){
        super();
        setLayout(new GridLayout(9,9));
        for(int i=0;i<Config.boardLength;i++) {
            for (int j = 0; j < Config.boardHeight; j++) {
                add(new SelectableTileGui(new Tile(TilesEnum.UNUSED,0),new Point(i,j)));
            }
        }
    }

    private Board createBoard(SerializableGame model){
        Board board = new Board(model.getSelectablePoints(), model.getTemporaryPoints());
        board.setBoard(model.getGameBoard());
        return board;
    }
}
