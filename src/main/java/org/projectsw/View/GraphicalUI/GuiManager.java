package org.projectsw.View.GraphicalUI;

import org.projectsw.Distributed.Client;
import org.projectsw.Distributed.Messages.InputMessages.*;
import org.projectsw.Distributed.Messages.ResponseMessages.ResponseMessage;
import org.projectsw.Model.SerializableGame;
import org.projectsw.Util.Config;
import org.projectsw.Util.Observable;
import org.projectsw.Util.RandomAlphanumericGen;
import org.projectsw.View.Enums.UIEndState;
import org.projectsw.View.Enums.UITurnState;
import org.projectsw.View.GraphicalUI.MessagesGUI.*;
import org.projectsw.View.SerializableInput;

import java.awt.*;
import java.rmi.RemoteException;
import java.sql.Statement;

public class GuiManager extends Observable<InputMessage> implements Runnable {

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();
    private UITurnState turnState = UITurnState.OPPONENT_TURN;
    private UIEndState endState = UIEndState.LOBBY;
    private final Client client;
    private final String alphanumericKey;
    private String nickname;
    private boolean firstPlayer = false;
    private boolean gameSavedExist = false;
    private boolean askNickname = true;
    private boolean logInCompleted = false;
    private boolean noMoreTemporaryTiles = true ;
    private boolean noMoreSelectableTiles = false ;
    private SerializableGame game;


    public GuiManager(Client client) {
        RandomAlphanumericGen gen = new RandomAlphanumericGen();
        alphanumericKey = gen.generateRandomString(100);
        this.client = client;
    }

    public UITurnState getTurnState() {
        return turnState;
    }

    public UIEndState getEndState() {
        return endState;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isFirstPlayer() {
        return firstPlayer;
    }

    public boolean isGameSavedExist() {
        return gameSavedExist;
    }

    public boolean isAskNickname() {
        return askNickname;
    }

    public boolean isNoMoreTemporaryTiles() {
        return noMoreTemporaryTiles;
    }

    public boolean isNoMoreSelectableTiles() {
        return noMoreSelectableTiles;
    }

    public void setTurnState(UITurnState turnState) {
        this.turnState = turnState;
    }

    public void setEndState(UIEndState endState) {
        this.endState = endState;
    }

    public void setFirstPlayer(boolean firstPlayer) {
        this.firstPlayer = firstPlayer;
        setLogInCompleted(checkLogInCompleted());
    }

    public void setGameSavedExist(boolean gameSavedExist) {
        this.gameSavedExist = gameSavedExist;
        setLogInCompleted(checkLogInCompleted());
    }

    public void setAskNickname(boolean askNickname) {
        this.askNickname = askNickname;
        setLogInCompleted(checkLogInCompleted());
    }

    public void setLogInCompleted(boolean logInCompleted) {
        this.logInCompleted = logInCompleted;
    }

    public void setNoMoreTemporaryTiles(boolean noMoreTemporaryTiles) {
        this.noMoreTemporaryTiles = noMoreTemporaryTiles;
    }

    public void setNoMoreSelectableTiles(boolean noMoreSelectableTiles) {
        this.noMoreSelectableTiles = noMoreSelectableTiles;
    }

    public void updateModel(SerializableGame game){
        this.game = game;
    }

    private boolean checkLogInCompleted(){
        return !askNickname && !firstPlayer;
    }

    public void update(ResponseMessage response) {
        if(response.getModel().getAlphanumericID().equals(this.alphanumericKey)||response.getModel().getAlphanumericID().equals(Config.broadcastID))
            response.execute(this);
    }

    @Override
    public void run() {
        try {
            setChangedAndNotifyObservers(new Connect(new SerializableInput(alphanumericKey, client)));
        } catch (RemoteException e) {
            throw new RuntimeException("A network error occurred connecting to the server: "+e.getMessage());
        }
        waitForResponse1();
        printConnectionStatus();
        lobby();
    }

    private void waitForResponse1() {
        synchronized (lock1) {
            try {
                lock1.wait();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
        System.out.println("Sono uscito");
    }

    public void notifyResponse1() {
        synchronized (lock1) {
            lock1.notify();
        }
    }

    private void waitForResponse2() {
        synchronized (lock2) {
            try {
                lock2.wait();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void notifyResponse2() {
        synchronized (lock2) {
            lock2.notify();
        }
    }

    public void kill(){
        try {
            client.kill();
        } catch (RemoteException e) {
            System.err.println("Error while closing the process, please manually close the client");
            System.exit(0);
        }
        System.exit(0);
    }

    public void kill(int option){
        if(option == 0) new LobbyFullKillMessageFrame();
        if(option == 1) new JoinCancelledMessageFrame();
        kill();
    }

    /**
     * Debug function
     */
    private void printConnectionStatus() {
        System.out.println("\nConnection successfully established");
        if(firstPlayer) System.out.println("You are the first player");
        else System.out.println("You are NOT the first player");
        if(gameSavedExist) System.out.println("Game saved found");
        else System.out.println("Game saved NOT found");
    }

    private void lobby() {
        do {
            new LobbyFrame(this);
            waitForResponse1();
            System.out.println("Risposta ricevuta, LogInCompleted = " + logInCompleted);
        } while (!logInCompleted);
        while (endState.equals(UIEndState.LOBBY)) {
            new WaitingMessageFrame();
            waitForResponse1();
        }
        if (endState.equals(UIEndState.RUNNING)) {
            new GameStartedMessageFrame();
            launchGame();
        }

    }

    private void launchGame(){
        System.out.println("Game has fckkking started!!");
        new GameMainFrame(this);
    }

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
            new NicknameAcceptedFrame();
            this.nickname = nickname;
            notifyResponse1();
        }
    }

    public void sendNumberOfPlayers(int numberOfPlayers) {
        try {
            setChangedAndNotifyObservers(new ConfirmNumberOfPlayers(new SerializableInput(alphanumericKey, numberOfPlayers, client)));
        } catch (RemoteException e) {
            throw new RuntimeException("Network error" + e.getMessage());
        }
        waitForResponse2();
        new GameCreatedMessageFrame();
        setFirstPlayer(false);
        setAskNickname(true);
        notifyResponse1();
    }

    public void sendLoadGameSelection(){
        try {
            setChangedAndNotifyObservers(new LoadGameSelection(new SerializableInput(alphanumericKey, client)));
        } catch (RemoteException e) {
            throw new RuntimeException("Network error" + e.getMessage());
        }
        waitForResponse2();
        new LoadGameSuccessMessage();
        setFirstPlayer(false);
        setAskNickname(true);
        notifyResponse1();
    }

    public void sendTileSelectionFromBoard(Point point) {
        try {
            setChangedAndNotifyObservers(new ConfirmTileSelection(new SerializableInput(alphanumericKey, getNickname(), point, client)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while choosing the tiles: "+e.getCause());
        }
        waitForResponse2();
    }

    public void askBoard(GameMainFrame gameMainFrame){
        try {
            setChangedAndNotifyObservers(new AskForShelf(new SerializableInput(alphanumericKey, getNickname(), client)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while asking for the shelf: "+e.getMessage());
        }
        waitForResponse2();
        setAndDisplayBoard(gameMainFrame);
    }

    private void setAndDisplayBoard(GameMainFrame gameMainFrame){
        gameMainFrame.updateBoard(game);
    }
}
