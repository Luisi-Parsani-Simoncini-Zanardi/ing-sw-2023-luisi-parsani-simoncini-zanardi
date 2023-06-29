package org.projectsw.View.GraphicalUI.GuiModel;

import org.projectsw.Model.Tile;
import org.projectsw.Util.Config;
import org.projectsw.View.GraphicalUI.GameMainFrame;
import org.projectsw.View.GraphicalUI.GuiManager;
import org.projectsw.View.GraphicalUI.MessagesGUI.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SelectableBoard extends JPanel {

    GuiManager guiManager;
    GameMainFrame gameMainFrame;
    ArrayList<Point> temporaryPoints;
    ArrayList<Point> selectablePoints;
    Tile[][] gameBoard;
    private Image backgroundImage;


    public SelectableBoard(Tile[][] gameBoard, ArrayList<Point> selectablePoints, ArrayList<Point> temporaryPoints, GuiManager guiManager, GameMainFrame gameMainFrame){
        super();
        this.gameMainFrame = gameMainFrame;
        this.guiManager = guiManager;
        this.temporaryPoints = temporaryPoints;
        this.selectablePoints = selectablePoints;
        this.gameBoard = gameBoard;
        setSize(200,200);
        setLayout(new BorderLayout());

        ImageIcon backgroundImage = new ImageIcon("src/main/resources/ImagesGui/Boards/Board.png");

        // Crea il pannello con sfondo
        BackgroundPanel gridPanel = new BackgroundPanel(backgroundImage.getImage(), 1200, 600);

        gridPanel.setLayout(new GridLayout(9,9,3,3));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        for(int i=0;i<Config.boardHeight;i++) {
            for(int j=0;j<Config.boardLength;j++) {
                Tile tile = gameBoard[i][j];
                SelectableTile selectableTile = new SelectableTile(tile,new Point(i,j),selectablePoints,temporaryPoints);
                gridPanel.add(selectableTile);
                selectableTile.addActionListener(e -> {
                    if(e.getSource() instanceof SelectableTile selectedTile){
                        switch (gameMainFrame.getTurnState()) {
                            case YOUR_TURN_SELECTION -> {
                                guiManager.sendTileSelectionFromBoard(selectedTile.getPosition());
                                gameMainFrame.setAppState(GameMainFrame.AppState.WAITING_APP);
                            }
                            case OPPONENT_TURN -> new SelectionNoCurrentPlayerMessage();
                        }
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
