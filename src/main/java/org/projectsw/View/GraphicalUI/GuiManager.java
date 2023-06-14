package org.projectsw.View.GraphicalUI;

import org.projectsw.Distributed.Client;
import org.projectsw.Distributed.Messages.InputMessages.*;
import org.projectsw.Distributed.Messages.ResponseMessages.ResponseMessage;
import org.projectsw.Util.Config;
import org.projectsw.Util.Observable;
import org.projectsw.Util.RandomAlphanumericGen;
import org.projectsw.View.Enums.UIEndState;
import org.projectsw.View.Enums.UITurnState;
import org.projectsw.View.GraphicalUI.MessagesGUI.*;
import org.projectsw.View.SerializableInput;

import java.rmi.RemoteException;

public class GuiManager extends Observable<InputMessage> implements Runnable {

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();
    private UITurnState turnState = UITurnState.OPPONENT_TURN;
    private UIEndState endState = UIEndState.LOBBY;
    private final Client client;
    private final String alphanumericKey;
    private boolean firstPlayer = false;
    private boolean gameSavedExist = false;
    private boolean askNickname = true;
    private boolean logInCompleted = false;




    public GuiManager(Client client) {
        RandomAlphanumericGen gen = new RandomAlphanumericGen();
        alphanumericKey = gen.generateRandomString(100);
        this.client = client;
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

    public void setState(UITurnState turnState) {
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

        new WaitingMessageFrame();
        waitForResponse1();
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
        } else new NicknameAcceptedFrame();
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
}
