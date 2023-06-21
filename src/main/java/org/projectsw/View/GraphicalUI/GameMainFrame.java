package org.projectsw.View.GraphicalUI;

import org.projectsw.Model.Tile;
import org.projectsw.View.Enums.UIEndState;
import org.projectsw.View.GraphicalUI.GuiModel.NoSelectableShelf;
import org.projectsw.View.GraphicalUI.GuiModel.SelectableBoard;
import org.projectsw.View.GraphicalUI.GuiModel.SelectableColumnShelf;
import org.projectsw.View.GraphicalUI.GuiModel.SelectableTile;
import org.projectsw.View.GraphicalUI.MessagesGUI.TemporaryTilesConfirmedMessage;
import org.projectsw.View.GraphicalUI.MessagesGUI.UnselectableTileMessage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameMainFrame extends JFrame {

    private final GuiManager guiManager;
    private final Object lock = new Object();
    private final Object finalLock = new Object();
    private boolean tileSelectionAccepted = true;
    private boolean tileSelectionConfirmed = false;
    private boolean columnSelectionConfirmed = false;
    private int selectedColumn;
    private ArrayList<Tile> takenTiles;

    public GameMainFrame(GuiManager guiManager){

        this.guiManager = guiManager;

        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle bounds = env.getMaximumWindowBounds();
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setSize(bounds.width, bounds.height);

        JPanel panelLeft = new JPanel();
        panelLeft.setBackground(Color.blue);
        panelLeft.setPreferredSize(new Dimension(100,100));
        add(panelLeft,BorderLayout.WEST);

        JPanel panelRight = new JPanel();
        panelRight.setBackground(Color.yellow);
        panelRight.setPreferredSize(new Dimension(100,100));
        add(panelRight,BorderLayout.EAST);

        JPanel playCentralPanel = new JPanel();
        playCentralPanel.setLayout(new BorderLayout());
        add(playCentralPanel,BorderLayout.CENTER);


        JPanel turnInformationNorthPanel = new JPanel();
        //TODO davide, ogni tanto la scritta qui sotto è buggata
        JLabel currentPlayerLabel = new JLabel(guiManager.askForCurrentPlayerString());
        turnInformationNorthPanel.add(currentPlayerLabel);
        turnInformationNorthPanel.setPreferredSize(new Dimension(100,100));
        playCentralPanel.add(turnInformationNorthPanel,BorderLayout.NORTH);

        JTabbedPane boardAndShelfTabbedPane = new JTabbedPane();
        playCentralPanel.add(boardAndShelfTabbedPane);

        setVisible(true);

        do {

            do {

                if (tileSelectionAccepted) {

                    SelectableBoard selectableBoard = askForBoard();
                    boardAndShelfTabbedPane.add("Board", selectableBoard);
                    if(tileSelectionConfirmed) {
                        SelectableColumnShelf selectableColumnShelf = askForScShelf();
                        boardAndShelfTabbedPane.add("Your Shelf", selectableColumnShelf);
                    } else {
                        NoSelectableShelf noSelectableShelf = askForNsShelf();
                        boardAndShelfTabbedPane.add("Your Shelf", noSelectableShelf);
                    }

                    JPanel selectionSouthPanel = new JPanel();
                    selectionSouthPanel.setLayout(new FlowLayout());
                    selectionSouthPanel.setPreferredSize(new Dimension(100, 100));
                    playCentralPanel.add(selectionSouthPanel, BorderLayout.SOUTH);
                    if (selectableBoard.getTemporaryPoints().isEmpty() && !tileSelectionConfirmed) {
                        JLabel noSelectedLabel = new JLabel("You haven't selected any tile yet");
                        selectionSouthPanel.add(noSelectedLabel);
                    } else if (!tileSelectionConfirmed){
                        JLabel selectedLabel = new JLabel("You have selected these tiles:  ");
                        selectionSouthPanel.add(selectedLabel);
                        for (Point point : selectableBoard.getTemporaryPoints()) {
                            JLabel selectedTile = selectableBoard.getLabelFromPoint(point);
                            selectionSouthPanel.add(selectedTile);
                        }
                        JButton confirmButton = new JButton("Confirm selection");
                        confirmButton.addActionListener(e -> {
                            guiManager.confirmTilesSelection(this);
                            new TemporaryTilesConfirmedMessage();
                        });
                        selectionSouthPanel.add(confirmButton);
                    } else {
                        if(columnSelectionConfirmed) {
                            JLabel selectedLabel2 = new JLabel("Choose one tile from these:  ");
                            selectionSouthPanel.add(selectedLabel2);
                        } else {
                            JLabel selectedLabel1 = new JLabel("You have confirmed these tiles:  ");
                            selectionSouthPanel.add(selectedLabel1);
                        }
                        for(Tile tile : takenTiles) {
                            SelectableTile selectableTile = new SelectableTile(tile);
                            selectableTile.addActionListener(e -> {

                            });
                            selectionSouthPanel.add(selectableTile);
                        }
                        if(columnSelectionConfirmed){
                            JLabel columnLabel = new JLabel(", you are putting them in column " + selectedColumn);
                            selectionSouthPanel.add(columnLabel);
                        }
                    }

                    revalidate();
                    repaint();

                    waitResponse();

                    boardAndShelfTabbedPane.remove(0);
                    boardAndShelfTabbedPane.remove(0);
                    playCentralPanel.remove(selectionSouthPanel);

                } else {
                    new UnselectableTileMessage();
                    tileSelectionAccepted = true;
                }
            } while (!tileSelectionConfirmed);

        } while(!guiManager.getEndState().equals(UIEndState.ENDING));

        waitFinalLock();
    }

    public boolean isTileSelectionConfirmed() {
        return tileSelectionConfirmed;
    }

    public ArrayList<Tile> getTakenTiles() {
        return takenTiles;
    }

    public void setTileSelectionAccepted(boolean tileSelectionAccepted) {
        this.tileSelectionAccepted = tileSelectionAccepted;
    }

    public void setTileSelectionConfirmed(boolean tileSelectionConfirmed) {
        this.tileSelectionConfirmed = tileSelectionConfirmed;
    }

    public void setTakenTiles(ArrayList<Tile> takenTiles) {
        this.takenTiles = takenTiles;
    }

    public void setColumnSelectionConfirmed(boolean columnSelectionConfirmed) {
        this.columnSelectionConfirmed = columnSelectionConfirmed;
    }

    public void setSelectedColumn(int selectedColumn) {
        this.selectedColumn = selectedColumn;
    }

    private SelectableBoard askForBoard() {
        return guiManager.askBoard(this);
    }

    private  NoSelectableShelf askForNsShelf() {
        return guiManager.askNsShelf();
    }

    private SelectableColumnShelf askForScShelf() {
        return guiManager.askScShelf(this);
    }

    private void waitResponse(){
        synchronized (lock){
            try{
                lock.wait();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void notifyResponse(){
        synchronized (lock){
            lock.notify();
        }
    }

    private void waitFinalLock(){
        synchronized (finalLock){
            try{
                finalLock.wait();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void notifyFinalLock(){
        synchronized (finalLock){
            finalLock.notify();
        }
    }
}
