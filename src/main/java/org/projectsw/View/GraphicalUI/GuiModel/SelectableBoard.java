package org.projectsw.View.GraphicalUI.GuiModel;

import org.projectsw.Model.SerializableGame;
import org.projectsw.Model.Tile;
import org.projectsw.Util.Config;
import org.projectsw.View.GraphicalUI.GameMainFrame;
import org.projectsw.View.GraphicalUI.GuiManager;
import org.projectsw.View.GraphicalUI.MessagesGUI.SelectionAlreadyConfirmedMessage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SelectableBoard extends JPanel {

    GuiManager guiManager;
    GameMainFrame gameMainFrame;
    ArrayList<Point> temporaryPoints;
    ArrayList<Point> selectablePoints;
    Tile[][] gameBoard;

    public SelectableBoard(SerializableGame model, GuiManager guiManager, GameMainFrame gameMainFrame){
        super();
        setLayout(new GridLayout(9,9));
        this.gameMainFrame = gameMainFrame;
        this.guiManager = guiManager;
        this.temporaryPoints = model.getTemporaryPoints();
        this.selectablePoints = model.getSelectablePoints();
        this.gameBoard = model.getGameBoard();

        for(int i=0;i<Config.boardHeight;i++) {
            for(int j=0;j<Config.boardLength;j++) {
                Tile tile = gameBoard[i][j];
                SelectableTile selectableTile = new SelectableTile(tile,new Point(i,j),selectablePoints,temporaryPoints);
                add(selectableTile);
                selectableTile.addActionListener(e -> {
                    if(gameMainFrame.isTileSelectionConfirmed()) new SelectionAlreadyConfirmedMessage();
                    else guiManager.sendTileSelectionFromBoard(selectableTile.getPosition(), this.gameMainFrame);
                });
            }
        }
    }

    public ArrayList<Point> getSelectablePoints() {
        return selectablePoints;
    }

    public ArrayList<Point> getTemporaryPoints() {
        return temporaryPoints;
    }

    public NoSelectableTile getLabelFromPoint(Point point) {
        return new NoSelectableTile(gameBoard[point.x][point.y]);
    }
}
