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

/**
 * This class represents the main frame of the game.
 * It extends the JFrame class.
 */
public class GameMainFrame extends JFrame {

    /**
     * Enumeration representing the different states of the game's application.
     */
    public enum AppState {
        WAITING_PLAYER,
        WAITING_APP
    }
    private final GuiManager guiManager;
    private final Object lock = new Object();
    private UITurnState turnState = UITurnState.OPPONENT_TURN;
    private  AppState appState = GameMainFrame.AppState.WAITING_PLAYER;
    private final JPanel turnInformationNorthPanel = new JPanel();
    private final JTabbedPane centralTabbedPane = new JTabbedPane();
    private final JPanel selectedTilesSouthPanel = new JPanel();
    private final JLabel turnInformationLabel = new JLabel();
    private ArrayList<Tile> takenTiles;
    private boolean stillPlaying = true;

    /**
     * Constructs a GameMainFrame object with a specified GuiManager.
     * @param guiManager The GuiManager object mainly used to receive and send back messages to the server.
     */

    public GameMainFrame(GuiManager guiManager){
        this.guiManager = guiManager;
    }

    /**
     * Creates the frame and sets up its components and layout.
     */
    public void createFrame(){

        // Frame configuration
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 900);
        setResizable(false);
        setTitle("My Shelfie");
        setBackground(Config.defaultGuiBackgroundColor);
        ImageIcon imageIcon = new ImageIcon(PathSolverGui.icon());
        setIconImage(imageIcon.getImage());

        // Set dimensions and add components to the frame
        turnInformationNorthPanel.setPreferredSize(new Dimension(1200,75));
        selectedTilesSouthPanel.setPreferredSize(new Dimension(50,150));
        add(turnInformationNorthPanel,BorderLayout.NORTH);
        add(centralTabbedPane,BorderLayout.CENTER);
        add(selectedTilesSouthPanel,BorderLayout.SOUTH);

        // Configure turn information label
        turnInformationNorthPanel.add(turnInformationLabel);
        turnInformationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        turnInformationLabel.setVerticalAlignment(SwingConstants.CENTER);

    }

    /**
     * Refreshes the frame by updating its components based on the current game state.
     */
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


    /**
     * Updates the chat.
     */
    public void chatUpdate() {
        centralTabbedPane.add("Chat", askChat());
        centralTabbedPane.remove(4);
    }

    /**
     * Refreshes the frame in the phase of ending game.
     */
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
            centralTabbedPane.add("Chat", askChat());
        });
    }

    /**
     * Refreshes the frame when the player is not the current player.
     */
    private void refreshNoCurrentPlayer() {
        JLabel noCurrentPlayerLabel = new JLabel("You are not the current player, wait your turn");
        selectedTilesSouthPanel.add(noCurrentPlayerLabel);
        noCurrentPlayerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        noCurrentPlayerLabel.setVerticalAlignment(SwingConstants.CENTER);
        centralTabbedPane.add("Board", askForBoard());
        centralTabbedPane.add("Your Shelf", askForNsShelf());
        centralTabbedPane.add("Personal Goal", returnPersonalGoal());
        centralTabbedPane.add("Common Goals", returnCommonGoalImage());
        centralTabbedPane.add("Chat", askChat());
    }


    /**
     * Refreshes the frame when it is the current player's turn.
     */
    private void refreshCurrentPlayer() {
        switch (turnState) {
            case YOUR_TURN_SELECTION -> selectionRefresh();
            case YOUR_TURN_COLUMN -> columnRefresh();
            case YOUR_TURN_INSERTION -> insertionRefresh();
        }
    }

    /**
     * Refreshes the frame during the selection phase of the current player's turn.
     */
    private void selectionRefresh() {
        SelectableBoard selectableBoard = askForBoard();
        centralTabbedPane.add("Board",selectableBoard);
        centralTabbedPane.add("Your Shelf", askForNsShelf());
        centralTabbedPane.add("Personal Goal", returnPersonalGoal());
        centralTabbedPane.add("Common Goals", returnCommonGoalImage());
        centralTabbedPane.add("Chat", askChat());
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

    /**
     * Refreshes the frame during the column phase of the current player's turn.
     */
    private void columnRefresh() {
        centralTabbedPane.add("Your Shelf", askForScShelf());
        centralTabbedPane.add("Board", askForBoard());
        centralTabbedPane.add("Personal Goal", returnPersonalGoal());
        centralTabbedPane.add("Common Goals", returnCommonGoalImage());
        centralTabbedPane.add("Chat", askChat());
        selectedTilesSouthPanel.setLayout(new BoxLayout(selectedTilesSouthPanel,BoxLayout.X_AXIS));
        JLabel selectedTilesLabel = new JLabel("You have selected these tiles:  ");
        selectedTilesSouthPanel.add(selectedTilesLabel);
        for(Tile tile : takenTiles) {
            selectedTilesSouthPanel.add(new NoSelectableTile(tile));
        }
    }

    /**
     * Refreshes the frame during the insertion phase of the current player's turn.
     */
    private void insertionRefresh() {
        centralTabbedPane.add("Your Shelf", askForNsShelf());
        centralTabbedPane.add("Board", askForBoard());
        centralTabbedPane.add("Personal Goal", returnPersonalGoal());
        centralTabbedPane.add("Common Goals", returnCommonGoalImage());
        centralTabbedPane.add("Chat", askChat());
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


    /**
     * Disposes the frame.
     */
    public void disposeFrame() {
        dispose();
    }

    /**
     * Returns the current turn state of the game.
     * @return The current UITurnState representing the turn state.
     */
    public UITurnState getTurnState() {
        return turnState;
    }

    /**
     * Sets the turn state of the game and triggers a refresh of the frame.
     * @param turnState The UITurnState to set as the current turn state.
     */
    public void setTurnState(UITurnState turnState) {
        this.turnState = turnState;
        refresh();
    }

    /**
     * Sets the list of taken tiles.
     * @param takenTiles The ArrayList of Tile objects representing the taken tiles.
     */
    public void setTakenTiles(ArrayList<Tile> takenTiles) {
        this.takenTiles = takenTiles;
    }

    /**
     *  Sets the stillPlaying flag.
     * @param stillPlaying the value to set the flag.
     */
    public void setStillPlaying(boolean stillPlaying) {
        this.stillPlaying = stillPlaying;
    }

    /**
     * Asks the GuiManager for the SelectableBoard.
     * @return The SelectableBoard object obtained from the GuiManager.
     */
    private SelectableBoard askForBoard() {
        return guiManager.askBoard();
    }

    /**
     * Asks the GuiManager for the ChatGui panel.
     * @return The ChatGui object obtained from the GuiManager.
     */
    private ChatGui askChat() {
        return guiManager.askChat();
    }

    /**
     * Asks the GuiManager for the NoSelectableShelf.
     * @return The NoSelectableShelf object obtained from the GuiManager.
     */
    private  NoSelectableShelf askForNsShelf() {
        return guiManager.askNsShelf();
    }

    /**
     * Asks the GuiManager for the SelectableColumnShelf.
     * @return The SelectableColumnShelf object obtained from the GuiManager.
     */
    private SelectableColumnShelf askForScShelf() {
        return guiManager.askScShelf();
    }

    /**
     * Asks the GuiManager for the NoSelectableShelf representing the personal goal.
     * @return The NoSelectableShelf object representing the personal goal obtained from the GuiManager.
     */
    private NoSelectableShelf returnPersonalGoal() {
        return guiManager.askPersonalGoal();
    }

    /**
     * Asks the GuiManager for the CommonGoalImage.
     * @return The CommonGoalImage object obtained from the GuiManager.
     */
    private CommonGoalImage returnCommonGoalImage(){
        return guiManager.askCommonGoal();
    }

    /**
     * Sets the appState.
     * @param appState The state to set as current state.
     */
    public void setAppState(AppState appState) {
        synchronized (lock) {
            this.appState = appState;
            if(appState.equals(AppState.WAITING_PLAYER)) refresh();
        }
    }
}
