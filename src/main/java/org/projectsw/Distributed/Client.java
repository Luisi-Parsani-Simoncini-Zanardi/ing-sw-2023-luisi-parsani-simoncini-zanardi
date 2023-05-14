package org.projectsw.Distributed;

import org.projectsw.Model.Game;
import org.projectsw.Model.GameView;
import org.projectsw.View.GraphicalUI;
import org.projectsw.View.TextualUI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {
    /**
     * Notify the client of a model change
     * @param o     The resulting model view
     * @param arg   The causing event
     */
    void update(GameView o, Game.Event arg) throws RemoteException;
    void kill()throws RemoteException;
    public void setID(GameView serverResponse)throws RemoteException;
    public void setNickname(GameView serverResponse)throws RemoteException;
    public String getNickname()throws RemoteException;;
    public void askNumberOfPlayers()throws RemoteException;
    public void askNewNick(GameView nicks)throws RemoteException;
}
