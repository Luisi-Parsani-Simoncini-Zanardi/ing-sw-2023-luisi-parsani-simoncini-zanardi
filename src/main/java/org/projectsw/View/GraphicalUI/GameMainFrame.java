package org.projectsw.View.GraphicalUI;

import org.projectsw.Model.Tile;
import org.projectsw.Util.Config;
import org.projectsw.Util.PathSolverGui;
import org.projectsw.View.Enums.UIEndState;
import org.projectsw.View.Enums.UITurnState;
import org.projectsw.View.GraphicalUI.GuiModel.*;
import org.projectsw.View.GraphicalUI.MessagesGUI.TemporaryTilesConfirmedMessage;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameMainFrame extends JFrame {

    public enum AppState {
        WAITING_PLAYER,
        WAITING_APP
    }
    private final GuiManager guiManager;
    private final Object lock = new Object();
    private UITurnState turnState = UITurnState.OPPONENT_TURN;
    private  AppState appState = GameMainFrame.AppState.WAITING_PLAYER;
    private final JPanel turnInformationsNorthPanel = new JPanel();
    private final JTabbedPane centralTabbedPane = new JTabbedPane();
    private final JPanel selectedTilesSouthPanel = new JPanel();
    private final JLabel turnInformationLabel = new JLabel();
    private ArrayList<Tile> takenTiles;
    private boolean stillPlaying = true;

    public GameMainFrame(GuiManager guiManager){
        this.guiManager = guiManager;
    }

    public void createFrame(){

        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 900);
        setResizable(false);
        setTitle("My Shelfie");
        setBackground(Config.defaultGuiBackgroundColor);
        ImageIcon imageIcon = new ImageIcon(PathSolverGui.icon());
        setIconImage(imageIcon.getImage());

        turnInformationsNorthPanel.setPreferredSize(new Dimension(1200,75));
        selectedTilesSouthPanel.setPreferredSize(new Dimension(50,150));
        add(turnInformationsNorthPanel,BorderLayout.NORTH);
        add(centralTabbedPane,BorderLayout.CENTER);
        add(selectedTilesSouthPanel,BorderLayout.SOUTH);

        turnInformationsNorthPanel.add(turnInformationLabel);
        turnInformationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        turnInformationLabel.setVerticalAlignment(SwingConstants.CENTER);

    }

    public void refresh () {
        SwingUtilities.invokeLater(centralTabbedPane::removeAll);
        SwingUtilities.invokeLater(selectedTilesSouthPanel::removeAll);
        if(guiManager.getEndState().equals(UIEndState.ENDING) && !stillPlaying) SwingUtilities.invokeLater(this::endingRefresh);
        else {
            SwingUtilities.invokeLater( () -> turnInformationLabel.setText(guiManager.askForCurrentPlayerString()));
            if(turnState.equals(UITurnState.OPPONENT_TURN)) SwingUtilities.invokeLater(this::refreshNoCurrentPlayer);
            else SwingUtilities.invokeLater(this::refreshCurrentPlayer);
        }
        revalidate();
        repaint();
        setVisible(true);
    }

    private void endingRefresh(){
        SwingUtilities.invokeLater( () -> turnInformationLabel.setText("The game has ended but some players are still playing."));
        SwingUtilities.invokeLater( () ->{
            JLabel bottomLabel = new JLabel("Results are not available yet, wait until " + guiManager.getLastPlayerNick() + " ends his turn!");
            selectedTilesSouthPanel.add(bottomLabel);
            bottomLabel.setHorizontalAlignment(SwingConstants.CENTER);
            bottomLabel.setVerticalAlignment(SwingConstants.CENTER);
            centralTabbedPane.add("Your Shelf", askForNsShelf());
            centralTabbedPane.add("Personal Goal", returnPersonalGoal());
            centralTabbedPane.add("Common Goals", returnCommonGoalImage());
            centralTabbedPane.add("Chat", new JPanel());
        });
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
                if(e.getSource() instanceof JButton) {
                    new TemporaryTilesConfirmedMessage();
                    setAppState(AppState.WAITING_APP);
                    guiManager.confirmTilesSelection();
                }
            });
            confirmButton.setContentAreaFilled(false);
            selectedTilesSouthPanel.add(confirmButton);
        }
    }

    private void columnRefresh() {
        centralTabbedPane.add("Your Shelf", askForScShelf());
        centralTabbedPane.add("Board", askForBoard());
        centralTabbedPane.add("Personal Goal", returnPersonalGoal());
        centralTabbedPane.add("Common Goals", returnCommonGoalImage());
        centralTabbedPane.add("Chat", new JPanel());
        selectedTilesSouthPanel.setLayout(new BoxLayout(selectedTilesSouthPanel,BoxLayout.X_AXIS));
        JLabel selectedTilesLabel = new JLabel("You have selected these tiles:  ");
        selectedTilesSouthPanel.add(selectedTilesLabel);
        for(Tile tile : takenTiles) {
            selectedTilesSouthPanel.add(new NoSelectableTile(tile));
        }
    }

    private void insertionRefresh() {
        centralTabbedPane.add("Your Shelf", askForNsShelf());
        centralTabbedPane.add("Board", askForBoard());
        centralTabbedPane.add("Personal Goal", returnPersonalGoal());
        centralTabbedPane.add("Common Goals", returnCommonGoalImage());
        centralTabbedPane.add("Chat", new JPanel());
        selectedTilesSouthPanel.setLayout(new BoxLayout(selectedTilesSouthPanel,BoxLayout.X_AXIS));
        JLabel selectedTilesLabel = new JLabel("Which tile do you want to insert?  ");
        selectedTilesSouthPanel.add(selectedTilesLabel);
        JPanel takenTilesButtonGrid = new JPanel(new GridLayout(1,3));
        for(Tile tile : takenTiles) {
            SelectableTile selectableTile = new SelectableTile(tile);
            selectableTile.addActionListener(e -> SwingUtilities.invokeLater(() -> {
                guiManager.sendTemporaryTilesSelection(takenTiles.indexOf(tile));
                appState = AppState.WAITING_APP;
                takenTiles.remove(tile);
                if(takenTiles.isEmpty()) {
                    guiManager.sendEndTurn();
                }
            }));
            selectableTile.setContentAreaFilled(false);
            takenTilesButtonGrid.add(selectableTile);
        }
        selectedTilesSouthPanel.add(takenTilesButtonGrid);
    }

    public void disposeFrame() {
        dispose();
    }

    public UITurnState getTurnState() {
        return turnState;
    }

    public void setTurnState(UITurnState turnState) {
        this.turnState = turnState;
        refresh();
    }

    public void setTakenTiles(ArrayList<Tile> takenTiles) {
        this.takenTiles = takenTiles;
    }


    public void setStillPlaying(boolean stillPlaying) {
        this.stillPlaying = stillPlaying;
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

    public void setAppState(AppState appState) {
        synchronized (lock) {
            this.appState = appState;
            if(appState.equals(AppState.WAITING_PLAYER)) refresh();
        }
    }
}
