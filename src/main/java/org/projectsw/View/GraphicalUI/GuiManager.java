package org.projectsw.View.GraphicalUI;

import org.projectsw.Distributed.Client;
import org.projectsw.Distributed.Messages.InputMessages.*;
import org.projectsw.Distributed.Messages.ResponseMessages.ResponseMessage;
import org.projectsw.Model.Message;
import org.projectsw.View.GraphicalUI.GuiModel.CommonGoalImage;
import org.projectsw.View.GraphicalUI.GuiModel.NoSelectableShelf;
import org.projectsw.View.GraphicalUI.GuiModel.SelectableBoard;
import org.projectsw.View.GraphicalUI.GuiModel.SelectableColumnShelf;
import org.projectsw.Model.SerializableGame;
import org.projectsw.Util.Config;
import org.projectsw.Util.Observable;
import org.projectsw.Util.RandomAlphanumericGen;
import org.projectsw.View.Enums.UIEndState;
import org.projectsw.View.Enums.UITurnState;
import org.projectsw.View.GraphicalUI.GuiModel.*;
import org.projectsw.View.GraphicalUI.MessagesGUI.*;
import org.projectsw.View.SerializableInput;
import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;

import java.util.ArrayList;

import java.util.LinkedHashMap;

/**
 * The GuiManager class handles the user interface interactions, manages the game state and the communication between client and server.
 */
public class GuiManager extends Observable<InputMessage> {

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();
    private final Object lock3 = new Object();
    private UIEndState endState = UIEndState.LOBBY;
    private final Client client;
    private final String alphanumericKey;
    private String nickname;
    private String lastPlayerNick;
    private boolean firstPlayer = false;
    private boolean gameSavedExist = false;
    private boolean askNickname = true;
    private boolean logInCompleted = false;
    private boolean tileSelectionPossible = true ;
    private boolean tileSelectionAccepted = true;
    private boolean columnSelectionAccepted = true;
    private boolean stillChoosing = true;
    private boolean loadFromFile = false;
    private final GameMainFrame gameMainFrame = new GameMainFrame(this);
    private SerializableGame game;
    private LinkedHashMap<String, Integer> results;

    /**
     * Creates a new GuiManager instance.
     * @param client The client object for network communication.
     */
    public GuiManager(Client client) {
        RandomAlphanumericGen gen = new RandomAlphanumericGen();
        alphanumericKey = gen.generateRandomString(100);
        this.client = client;
    }

    /**
     * Retrieves the current end state of the user interface.
     * @return The current end state.
     */
    public UIEndState getEndState() {
        return endState;
    }

    /**
     * Retrieves the nickname of the user.
     * @return The user's nickname.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Checks if the user is the first player.
     * @return True if the user is the first player, false otherwise.
     */
    public boolean isFirstPlayer() {
        return firstPlayer;
    }

    /**
     * Checks if a game is saved.
     * @return True if a game is saved, false otherwise.
     */
    public boolean isGameSavedExist() {
        return gameSavedExist;
    }

    /**
     * Checks if the nickname is required.
     * @return True if the nickname is required, false otherwise.
     */
    public boolean isAskNickname() {
        return askNickname;
    }

    /**
     * Retrieves the nickname of the last player.
     * @return The nickname of the last player.
     */
    public String getLastPlayerNick() {
        return lastPlayerNick;
    }

    /**
     * Retrieves the GameMainFrame object.
     * @return The GameMainFrame object.
     */
    public GameMainFrame getGameMainFrame() {
        return gameMainFrame;
    }

    /**
     * Sets the end state of the user interface.
     * @param endState The end state to set.
     */
    public void setEndState(UIEndState endState) {
        this.endState = endState;
    }

    /**
     * Sets whether the user is the first player.
     * @param firstPlayer True if the user is the first player, false otherwise.
     */
    public void setFirstPlayer(boolean firstPlayer) {
        this.firstPlayer = firstPlayer;
        setLogInCompleted(checkLogInCompleted());
    }

    /**
     * Sets the value of the savedGameExist flag.
     * @param gameSavedExist True if the save game exist.
     */
    public void setGameSavedExist(boolean gameSavedExist) {
        this.gameSavedExist = gameSavedExist;
        setLogInCompleted(checkLogInCompleted());
    }

    /**
     * Sets whether the nickname is required.
     * @param askNickname True if the nickname is required, false otherwise.
     */
    public void setAskNickname(boolean askNickname) {
        this.askNickname = askNickname;
        setLogInCompleted(checkLogInCompleted());
    }

    /**
     * Sets whether the login process is completed.
     * @param logInCompleted True if the login process is completed, false otherwise.
     */
    public void setLogInCompleted(boolean logInCompleted) {
        this.logInCompleted = logInCompleted;
    }

    /**
     * Sets whether tile selection is possible.
     * @param tileSelectionPossible True if tile selection is possible, false otherwise.
     */
    public void setTileSelectionPossible(boolean tileSelectionPossible) {
        this.tileSelectionPossible = tileSelectionPossible;
    }

    /**
     * Sets whether tile selection is accepted.
     * @param tileSelectionAccepted True if tile selection is accepted, false otherwise.
     */
    public void setTileSelectionAccepted(boolean tileSelectionAccepted) {
        this.tileSelectionAccepted = tileSelectionAccepted;
    }

    /**
     * Sets whether column selection is accepted.
     * @param columnSelectionAccepted True if column selection is accepted, false otherwise.
     */
    public void setColumnSelectionAccepted(boolean columnSelectionAccepted) {
        this.columnSelectionAccepted = columnSelectionAccepted;
    }

    /**
     * Sets the nickname of the last player.
     * @param lastPlayerNick The nickname of the last player.
     */
    public void setLastPlayerNick(String lastPlayerNick) {
        this.lastPlayerNick = lastPlayerNick;
    }

    /**
     * Sets the results and lunch the function to display them, closing the GameMainFrame window, killing the process.
     * @param results the LinkedHashMap containing the results of every player.
     */
    public void setResults(LinkedHashMap<String, Integer> results) {
        this.results = results;
        SwingUtilities.invokeLater(this::closeAndShowResults);
    }

    /**
     * Sets whether the player is still choosing.
     * @param stillChoosing True if the player is still choosing, false otherwise.
     */
    public void setStillChoosing(boolean stillChoosing) {
        this.stillChoosing = stillChoosing;
    }

    /**
     * Sets whether to load the game from a file.
     * @param loadFromFile True to load the game from a file, false otherwise.
     */
    public void setLoadFromFile(boolean loadFromFile) {
        this.loadFromFile = loadFromFile;
    }

    /*
     * Lunch the frame to display the results, close the GameMainFrame window and kills the process.
     */
    private void closeAndShowResults() {
        gameMainFrame.disposeFrame();
        new ResultsFrame(results);
        kill();
    }

    /**
     * Updates the serializable game attribute.
     * @param game the SerializableGame object to update the attribute.
     */
    public void updateModel(SerializableGame game){
        this.game = game;
    }

    /**
     * Checks if the log in flags are right set and so if the login is completed.
     * @return true if the login is completed.
     */
    private boolean checkLogInCompleted(){
        return !askNickname && !firstPlayer;
    }

    /**
     * Updates the client based on the received response message.
     * Executes the response if the alphanumeric ID matches the client's key or the broadcast ID.
     * @param response the response message to process and execute
     */
    public void update(ResponseMessage response) {
        if(response.getModel().getAlphanumericID().equals(this.alphanumericKey)||response.getModel().getAlphanumericID().equals(Config.broadcastID))
            response.execute(this);
    }

    /**
     * Implements the connection of the player, his login (with thw correct opening-frames operations)
     * and the lunch of the game main frame.
     */
    public void run() {
        try {
            setChangedAndNotifyObservers(new Connect(new SerializableInput(alphanumericKey, client)));
        } catch (RemoteException e) {
            throw new RuntimeException("A network error occurred connecting to the server: "+e.getMessage());
        }
        waitForResponse1();
        do {
            SwingUtilities.invokeLater( () -> new LobbyFrame(this));
            waitForResponse3();
        } while (!logInCompleted);
        if(stillChoosing && !firstPlayer){
            waitForResponse1();
            if(loadFromFile) {
                askNickname = true;
                new ReinsertNicknameMessage();
                SwingUtilities.invokeLater( () -> new LobbyFrame(this));
                waitForResponse3();
            }
        }
        new WaitingMessageFrame();
        while (endState.equals(UIEndState.LOBBY)) {
            waitForResponse1();
        }
        if (endState.equals(UIEndState.RUNNING)) {
            new GameStartedMessageFrame();
            SwingUtilities.invokeLater(gameMainFrame::createFrame);
            waitForResponse1();
        }

    }

    /**
     * Sends to the server the nickname chosen by the player and waits until the response arrives, if the response is correct
     * it notifies the right lock, if it isn't it opens the nickname frame again.
     * @param nickname the String to send to the server as the player's nickname.
     */
    public void sendNickname(String nickname){
        try {
            setChangedAndNotifyObservers(new SendNickname(new SerializableInput(alphanumericKey, nickname, client)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred: " + e.getCause());
        }
        waitForResponse2();
        if(askNickname) {
            new NicknameDeniedFrame();
            new NicknameFrame(this);
        } else {
            this.nickname = nickname;
            notifyResponse3();
        }
    }

    /**
     * Sends to the server the number of players chosen by the player and waits until the response arrives,
     * triggering the right lock.
     * @param numberOfPlayers the int to send to the server as the number of players.
     */
    public void sendNumberOfPlayers(int numberOfPlayers) {
        try {
            setChangedAndNotifyObservers(new ConfirmNumberOfPlayers(new SerializableInput(alphanumericKey, numberOfPlayers, client)));
        } catch (RemoteException e) {
            throw new RuntimeException("Network error" + e.getMessage());
        }
        waitForResponse2();
        new GameCreatedMessageFrame();
        stillChoosing = false;
        setFirstPlayer(false);
        setAskNickname(true);
        notifyResponse3();
    }

    /**
     * Communicates to the server that the player has chosen to load a previous game and waits until
     * the response arrives, triggering the right lock.
     */
    public void sendLoadGameSelection(){
        try {
            setChangedAndNotifyObservers(new LoadGameSelection(new SerializableInput(alphanumericKey, client)));
        } catch (RemoteException e) {
            throw new RuntimeException("Network error" + e.getMessage());
        }
        waitForResponse2();
        new LoadGameSuccessMessage();
        stillChoosing = false;
        setFirstPlayer(false);
        setAskNickname(true);
        notifyResponse3();
    }

    /**
     * Asks the server for the current board, creating and returning the corresponding SelectableBoard.
     * @return the SelectableBoard corresponding to the actual one.
     */
    public SelectableBoard askBoard(){
        try {
            setChangedAndNotifyObservers(new AskForBoard(new SerializableInput(alphanumericKey, getNickname(), client)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while asking for the shelf: "+e.getMessage());
        }
        waitForResponse2();
        return new SelectableBoard(game.getGameBoard(), game.getSelectablePoints(), game.getTemporaryPoints(), this, gameMainFrame);
    }

    /**
     * Updates the chat sending it to the EDT.
     */
    public void updateChat(){
        SwingUtilities.invokeLater( () -> gameMainFrame.chatUpdate());
    }

    /**
     * Asks the server for the current chat, creating and returning the corresponding ChatGui.
     * @return the ChatGui corresponding to the actual one.
     */
    public ChatGui askChat() {
        try {
            setChangedAndNotifyObservers(new AskForChat(new SerializableInput(alphanumericKey, getNickname(), Config.everyone, client)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while asking for the shelf: "+e.getMessage());
        }
        waitForResponse2();
        return new ChatGui(this, game.getChat(), client, alphanumericKey, nickname);
    }

    /**
     * Asks the server for the current player shelf, creating and returning the corresponding NoSelectableShelf.
     * @return the NoSelectableShelf corresponding to the actual current player shelf.
     */
    public NoSelectableShelf askNsShelf(){
        try {
            setChangedAndNotifyObservers(new AskForShelf(new SerializableInput(alphanumericKey, getNickname(), client)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while asking for the shelf: "+e.getMessage());
        }
        waitForResponse2();
        return new NoSelectableShelf(game.getPlayerShelf());
    }

    /**
     * Asks the server for the current player shelf, creating and returning the corresponding SelectableColumnShelf.
     * @return the SelectableColumnShelf corresponding to the actual current player shelf.
     */
    public SelectableColumnShelf askScShelf(){
        try {
            setChangedAndNotifyObservers(new AskForShelf(new SerializableInput(alphanumericKey, getNickname(), client)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while asking for the shelf: "+e.getMessage());
        }
        waitForResponse2();
        return new SelectableColumnShelf(game.getPlayerShelf(),this);
    }

    /**
     * Asks the server for the current player, returning a String to display on the interface.
     * @return the String to display on the interface.
     */
    public String askForCurrentPlayerString() {
        try {
            setChangedAndNotifyObservers(new AskForCurrentPlayer(new SerializableInput(alphanumericKey, client)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while asking for the current player: "+e.getMessage());
        }
        waitForResponse2();
        if(game.getPlayerName().equals(nickname)) {
            return "You are the current player, please make your decisions!";
        } else {
            return "The current player is: " + game.getPlayerName() + ", please wait your turn.";
        }
    }

    /**
     * Sends to the server the coordinates of the selected point on the board.
     * @param point the coordinates of the selected point on the board
     */
    public void sendTileSelectionFromBoard(Point point) {
        try {
            setChangedAndNotifyObservers(new ConfirmTileSelection(new SerializableInput(alphanumericKey, getNickname(), point, client)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while choosing the tiles: "+e.getCause());
        }
        waitForResponse2();
        if(!tileSelectionAccepted) new UnselectableTileMessage();
        if(!tileSelectionPossible) {
            new SelectionNotPossibleAnymoreMessage();
        }
        tileSelectionAccepted = true;
        gameMainFrame.setAppState(GameMainFrame.AppState.WAITING_PLAYER);
    }

    /**
     * Sends to the server the conformation of the selected tiles.
     */
    public void confirmTilesSelection() {
        try {
            setChangedAndNotifyObservers(new ConfirmSelectedTiles(new SerializableInput(alphanumericKey, getNickname(), client)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while confirming the tile selection: "+e.getCause());
        }
        waitForResponse2();
        gameMainFrame.setTakenTiles(game.getTemporaryTiles());
        gameMainFrame.setTurnState(UITurnState.YOUR_TURN_COLUMN);
        gameMainFrame.setAppState(GameMainFrame.AppState.WAITING_PLAYER);
    }

    /**
     * Sends to the server the number of the selected column.
     * @param number the number of the selected column.
     */
    public void sendColumnSelection(int number) {
        try {
            setChangedAndNotifyObservers(new ConfirmColumnSelection(new SerializableInput(alphanumericKey, number, client)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while confirming the column: "+e.getCause());
        }
        waitForResponse2();
        if(columnSelectionAccepted) {
            gameMainFrame.setTurnState(UITurnState.YOUR_TURN_INSERTION);
        }
        else {
            new ColumnSelectionRefusedMessage();
        }
        setColumnSelectionAccepted(true);
    }

    /**
     * Sends to the server the index of the temporary tile selected by the player.
     * @param index the index of the tile chosen by the player.
     */
    public void sendTemporaryTilesSelection(int index){
        try {
            setChangedAndNotifyObservers(new ConfirmTilePlacement(new SerializableInput(alphanumericKey, getNickname(), index, client)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while inserting the tiles: "+e.getCause());
        }
        waitForResponse2();
        gameMainFrame.setAppState(GameMainFrame.AppState.WAITING_PLAYER);
    }

    /**
     * Sets the GameMainFrame turn state.
     * @param state the state to set as new one.
     */
    public void setGameMainFrameState(UITurnState state) {
        gameMainFrame.setTurnState(state);
    }

    /**
     * Asks the server for the player personal goal, waiting for the response and returning
     * the corresponding NoSelectableShelf.
     * @return the NoSelectableShelf that corresponds to the player personal goal.
     */
    public NoSelectableShelf askPersonalGoal() {
        try {
            setChangedAndNotifyObservers(new AskForPersonalGoal(new SerializableInput(alphanumericKey, getNickname(), client)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while asking for all shelves: "+e.getMessage());
        }
        waitForResponse2();
        return new NoSelectableShelf(game.getPlayerPersonalGoal());
    }

    /**
     * Asks the server for the actual common goal returning the corresponding CommonGoalImage panel.
     * @return the CommonGoalImage corresponding to the actual common goals.
     */
    public CommonGoalImage askCommonGoal() {
        try {
            setChangedAndNotifyObservers(new AskForCommonGoals(new SerializableInput(alphanumericKey, getNickname(), client)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while asking for all shelves: "+e.getMessage());
        }
        waitForResponse2();
        return new CommonGoalImage(game.getCommonGoalDesc().get(0), game.getCommonGoalDesc().get(1));
    }

    /**
     * Sends the end turn notification to the server.
     */
    public void sendEndTurn() {
        try {
            setChangedAndNotifyObservers(new EndTurn(new SerializableInput(alphanumericKey, getNickname(), client)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while ending the turn: " + e);
        }
        boolean bool = endState.equals(UIEndState.ENDING) && lastPlayerNick.equals(nickname);
        System.out.println("UIEndState = " + endState + ", lastPlayerNick = " + lastPlayerNick + ", my nick = " + nickname + "\nResult of if = " + bool);
        if(endState.equals(UIEndState.ENDING) && lastPlayerNick.equals(nickname)) {
            sendAskForResults();
        }
    }

    /**
     * Sends the ask for result notification to the server.
     */
    private void sendAskForResults() {
        try {
            setChangedAndNotifyObservers(new AskForResults(new SerializableInput(alphanumericKey, getNickname(), client)));
        } catch (RemoteException e) {
            throw new RuntimeException("A network error occurred while asking for results: "+e.getMessage());
        }
        waitForResponse2();
    }

    /**
     * Puts in wait the thread on the lock 1.
     */
    private void waitForResponse1() {
        synchronized (lock1) {
            try {
                lock1.wait();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * Notify the thread waiting on the lock 1.
     */
    public void notifyResponse1() {
        synchronized (lock1) {
            lock1.notify();
        }
    }

    /**
     * Puts in wait the thread on the lock 2.
     */
    private void waitForResponse2() {
        synchronized (lock2) {
            try {
                lock2.wait();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * Notify the thread waiting on the lock 2.
     */
    public void notifyResponse2() {
        synchronized (lock2) {
            lock2.notify();
        }
    }

    /**
     * Puts in wait the thread on the lock 3.
     */
    private void waitForResponse3() {
        synchronized (lock3) {
            try {
                lock3.wait();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * Notify the thread waiting on the lock 3.
     */
    public void notifyResponse3() {
        synchronized (lock3) {
            lock3.notify();
        }
    }

    /**
     * Kills the process in the right way.
     */
    public void kill(){
        try {
            client.kill();
        } catch (RemoteException e) {
            System.err.println("Error while closing the process, please manually close the client");
            System.exit(0);
        }
        System.exit(0);
    }

    /**
     * Kills the process opening some messages based on the option passed.
     * @param option represents the message that you want to open while killing the process
     */
    public void kill(int option){
        if(option == 0) new LobbyFullKillMessageFrame();
        if(option == 1) new JoinCancelledMessageFrame();
        kill();
    }

}
