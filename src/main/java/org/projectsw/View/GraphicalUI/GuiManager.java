package org.projectsw.View.GraphicalUI;

import org.projectsw.Distributed.Client;
import org.projectsw.Distributed.Messages.InputMessages.Connect;
import org.projectsw.Distributed.Messages.InputMessages.InputMessage;
import org.projectsw.Distributed.Messages.ResponseMessages.ResponseMessage;
import org.projectsw.Util.Config;
import org.projectsw.Util.Observable;
import org.projectsw.Util.Observer;
import org.projectsw.Util.RandomAlphanumericGen;
import org.projectsw.View.Enums.UIEndState;
import org.projectsw.View.Enums.UITurnState;
import org.projectsw.View.SerializableInput;

import java.rmi.RemoteException;

public class GuiManager extends Observable<InputMessage> {

    private final Object lock = new Object();
    private UITurnState turnState = UITurnState.OPPONENT_TURN;
    private UIEndState endState = UIEndState.LOBBY;
    private final Client client;
    private final String alphanumericKey;
    private boolean firstPlayer = false;
    private boolean gameSavedExist = false;


    public GuiManager(Client client) {
        RandomAlphanumericGen gen = new RandomAlphanumericGen();
        alphanumericKey = gen.generateRandomString(100);
        this.client = client;
    }

    public void setState(UITurnState turnState) {
        this.turnState = turnState;
    }

    public void setEndState(UIEndState endState) {
        this.endState = endState;
    }

    public void setFirstPlayer(boolean firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public void setGameSavedExist(boolean gameSavedExist) {
        this.gameSavedExist = gameSavedExist;
    }

    public void update(ResponseMessage response) {
        if(response.getModel().getAlphanumericID().equals(this.alphanumericKey)||response.getModel().getAlphanumericID().equals(Config.broadcastID))
            response.execute(this);
    }

    public void connect() {
        try {
            setChangedAndNotifyObservers(new Connect(new SerializableInput(alphanumericKey, client)));
        } catch (RemoteException e) {
            throw new RuntimeException("A network error occurred connecting to the server: "+e.getMessage());
        }
        waitForConnection();
    }

    private void waitForConnection() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void notifyConnection() {
        synchronized (lock) {
            lock.notify();
        }
        printConnectionStatus();
    }

    private void printConnectionStatus() {
        System.out.println("Connection successfully established");
        if(firstPlayer) System.out.println("You are the first player");
        else System.out.println("You are NOT the first player");
        if(gameSavedExist) System.out.println("Gave saved found");
        else System.out.println("Game saved NOT found");
    }
}
