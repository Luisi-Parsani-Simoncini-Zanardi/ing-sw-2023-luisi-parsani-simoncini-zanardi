package org.projectsw.View.GraphicalUI;

import org.projectsw.Model.Tile;
import org.projectsw.View.Enums.UIEndState;
import org.projectsw.View.Enums.UITurnState;
import org.projectsw.View.GraphicalUI.GuiModel.*;
import org.projectsw.View.GraphicalUI.MessagesGUI.TemporaryTilesConfirmedMessage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameMainFrame extends JFrame {

    private final GuiManager guiManager;
    private final Object lock = new Object();
    private final Object turnLock = new Object();
    private UITurnState turnState = UITurnState.OPPONENT_TURN;
    private JPanel turnInformationsNorthPanel;
    private JPanel playPanel;
    private JTabbedPane centralTabbedPane;
    private JPanel selectedTilesSouthPanel;
    private ArrayList<Tile> takenTiles;
    private int selectedColumn;

    public GameMainFrame(GuiManager guiManager){
        this.guiManager = guiManager;
    }

    public void createFrame(){
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle bounds = env.getMaximumWindowBounds();
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setSize(bounds.width, bounds.height);

        turnInformationsNorthPanel = new JPanel();
        turnInformationsNorthPanel.setPreferredSize(new Dimension(1200,150));
        add(turnInformationsNorthPanel);

        setVisible(true);

        do {
            JLabel turnInformationLabel = new JLabel(guiManager.askForCurrentPlayerString());
            turnInformationsNorthPanel.add(turnInformationLabel);
            turnInformationLabel.setHorizontalAlignment(SwingConstants.CENTER);
            turnInformationLabel.setVerticalAlignment(SwingConstants.CENTER);

            centralTabbedPane = new JTabbedPane();
            selectedTilesSouthPanel = new JPanel();
            add(centralTabbedPane,BorderLayout.CENTER);
            add(selectedTilesSouthPanel,BorderLayout.SOUTH);
            selectedTilesSouthPanel.setPreferredSize(new Dimension(50,100));

            if(turnState.equals(UITurnState.OPPONENT_TURN)) {
                refreshNoCurrentPlayer();
                revalidate();
                repaint();
                waitTurnLock();
            } else {
                refreshCurrentPlayer();
                revalidate();
                repaint();
                waitResponse();
            }

            turnInformationsNorthPanel.removeAll();
            centralTabbedPane.removeAll();
            selectedTilesSouthPanel.removeAll();
            System.out.println("Recreated, UIEndState: " + turnState);
        } while (guiManager.getEndState().equals(UIEndState.RUNNING));

        dispose();
    }

    private void refreshNoCurrentPlayer() {
        JLabel noCurrentPlayerLabel = new JLabel("You are not the current player, wait your turn");
        selectedTilesSouthPanel.add(noCurrentPlayerLabel);
        noCurrentPlayerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        noCurrentPlayerLabel.setVerticalAlignment(SwingConstants.CENTER);
        centralTabbedPane.add("Board", askForBoard());
        centralTabbedPane.add("Your Shelf", askForNsShelf());
        centralTabbedPane.add("Personal Goal", returnPersonalGoal());
        centralTabbedPane.add("Common Goals", returnCommonGoalImage());
        centralTabbedPane.add("Chat", new JPanel());
    }

    private void refreshCurrentPlayer() {
        switch (turnState) {
            case YOUR_TURN_SELECTION -> selectionRefresh();
            case YOUR_TURN_COLUMN -> columnRefresh();
            case YOUR_TURN_INSERTION -> insertionRefresh();
        }
    }

    private void selectionRefresh() {
        SelectableBoard selectableBoard = askForBoard();
        centralTabbedPane.add("Board",selectableBoard);
        centralTabbedPane.add("Your Shelf", askForNsShelf());
        centralTabbedPane.add("Personal Goal", returnPersonalGoal());
        centralTabbedPane.add("Common Goals", returnCommonGoalImage());
        centralTabbedPane.add("Chat", new JPanel());
        if (selectableBoard.getTemporaryPoints().isEmpty()) {
            JLabel chooseTilesLabel = new JLabel("Choose your tiles in the board page!");
            selectedTilesSouthPanel.add(chooseTilesLabel);
            chooseTilesLabel.setHorizontalAlignment(SwingConstants.CENTER);
            chooseTilesLabel.setVerticalAlignment(SwingConstants.CENTER);
        } else {
            selectedTilesSouthPanel.setLayout(new FlowLayout());
            JLabel selectedTilesLabel = new JLabel("You have selected these tiles:  ");
            selectedTilesSouthPanel.add(selectedTilesLabel);
            for(Point point : selectableBoard.getTemporaryPoints()) {
                selectedTilesSouthPanel.add(selectableBoard.getLabelFromPoint(point));
            }
            JButton confirmButton = new JButton("Confirm Selection");
            confirmButton.addActionListener( e -> {
                new TemporaryTilesConfirmedMessage();
                guiManager.confirmTilesSelection();
            });
            selectedTilesSouthPanel.add(confirmButton);
        }
    }

    private void columnRefresh() {
        centralTabbedPane.add("Your Shelf", askForScShelf());
        centralTabbedPane.add("Board", askForBoard());
        centralTabbedPane.add("Personal Goal", returnPersonalGoal());
        centralTabbedPane.add("Common Goals", returnCommonGoalImage());
        centralTabbedPane.add("Chat", new JPanel());
        selectedTilesSouthPanel.setLayout(new FlowLayout());
        JLabel selectedTilesLabel = new JLabel("You have selected these tiles:  ");
        selectedTilesSouthPanel.add(selectedTilesLabel);
        for(Tile tile : takenTiles) {
            selectedTilesSouthPanel.add(new NoSelectableTile(tile));
        }
        //TODO sistema di cambio colonna
    }

    private void insertionRefresh() {
        centralTabbedPane.add("Your Shelf", askForNsShelf());
        centralTabbedPane.add("Board", askForBoard());
        centralTabbedPane.add("Personal Goal", returnPersonalGoal());
        centralTabbedPane.add("Common Goals", returnCommonGoalImage());
        centralTabbedPane.add("Chat", new JPanel());
        selectedTilesSouthPanel.setLayout(new FlowLayout());
        JLabel selectedTilesLabel = new JLabel("Which tile do you want to insert?  ");
        selectedTilesSouthPanel.add(selectedTilesLabel);
        JPanel takenTilesButtonGrid = new JPanel(new GridLayout(1,3));
        for(Tile tile : takenTiles) {
            SelectableTile selectableTile = new SelectableTile(tile);
            selectableTile.addActionListener(e -> {
                    guiManager.sendTemporaryTilesSelection(takenTiles.indexOf(tile));
                    takenTiles.remove(tile);
                    if(takenTiles.isEmpty()) {
                        //TODO rimuovi stampa debug
                        System.out.println("TakenTiles is empty!");
                        guiManager.sendEndTurn();
                    }
            });
            takenTilesButtonGrid.add(selectableTile);
        }
        selectedTilesSouthPanel.add(takenTilesButtonGrid);
    }

    public UITurnState getTurnState() {
        return turnState;
    }

    public void setTurnState(UITurnState turnState) {
        this.turnState = turnState;
    }

    public void setTakenTiles(ArrayList<Tile> takenTiles) {
        this.takenTiles = takenTiles;
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
        return guiManager.askScShelf();
    }

    private NoSelectableShelf returnPersonalGoal() {
        return guiManager.askPersonalGoal();
    }

    private CommonGoalImage returnCommonGoalImage(){
        return guiManager.askCommonGoal();
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

    private void waitTurnLock(){
        synchronized (turnLock){
            try{
                turnLock.wait();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void notifyTurnLock(){
        synchronized (turnLock){
            turnLock.notify();
        }
    }
}
