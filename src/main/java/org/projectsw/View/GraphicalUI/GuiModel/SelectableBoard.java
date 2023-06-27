package org.projectsw.View.GraphicalUI.GuiModel;

import org.projectsw.Model.Tile;
import org.projectsw.Util.Config;
import org.projectsw.View.GraphicalUI.GameMainFrame;
import org.projectsw.View.GraphicalUI.GuiManager;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SelectableBoard extends JPanel {

    GuiManager guiManager;
    GameMainFrame gameMainFrame;
    ArrayList<Point> temporaryPoints;
    ArrayList<Point> selectablePoints;
    Tile[][] gameBoard;

    public SelectableBoard(Tile[][] gameBoard, ArrayList<Point> selectablePoints, ArrayList<Point> temporaryPoints, GuiManager guiManager, GameMainFrame gameMainFrame){
        super();
        this.gameMainFrame = gameMainFrame;
        this.guiManager = guiManager;
        this.temporaryPoints = temporaryPoints;
        this.selectablePoints = selectablePoints;
        this.gameBoard = gameBoard;
        setSize(200,200);
        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(9,9,3,3));
        for(int i=0;i<Config.boardHeight;i++) {
            for(int j=0;j<Config.boardLength;j++) {
                Tile tile = gameBoard[i][j];
                SelectableTile selectableTile = new SelectableTile(tile,new Point(i,j),selectablePoints,temporaryPoints);
                gridPanel.add(selectableTile);
                selectableTile.addActionListener(e -> {
                    if(e.getSource() instanceof SelectableTile selectedTile){
                        guiManager.sendTileSelectionFromBoard(selectedTile.getPosition());
                        gameMainFrame.setAppState(GameMainFrame.AppState.WAITING_APP);
                    }
                });
            }
        }
        gridPanel.setPreferredSize(new Dimension(15,15));
        add(gridPanel,BorderLayout.CENTER);

    }

    public ArrayList<Point> getTemporaryPoints() {
        return temporaryPoints;
    }

    public NoSelectableTile getLabelFromPoint(Point point) {
        return new NoSelectableTile(gameBoard[point.x][point.y]);
    }
}
