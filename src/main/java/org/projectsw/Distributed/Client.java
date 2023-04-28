package org.projectsw.Distributed;

import org.projectsw.Model.Game;
import org.projectsw.Model.GameView;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {
    /**
     * Notify the client of a model change
     * @param o     The resulting model view
     * @param arg   The causing event
     */
    void update(GameView o, Game.Event arg) throws RemoteException;
}
