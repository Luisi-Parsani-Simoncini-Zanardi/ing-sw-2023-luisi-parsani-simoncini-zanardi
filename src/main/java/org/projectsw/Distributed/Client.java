package org.projectsw.Distributed;

import org.projectsw.Model.Game;
import org.projectsw.Model.GameView;
import org.projectsw.View.GraphicalUI;
import org.projectsw.View.TextualUI;

import java.rmi.Remote;
import java.rmi.RemoteException;

//TODO: come detto da Damiani nelle registrazioni bisogna gestire esplicitamente le RemoteException cioè quando qualcosa fa storto nella connessione 1:20:00 della registrazione
public interface Client extends Remote {
    /**
     * Notify the client of a model change
     * @param o     The resulting model view
     * @param arg   The causing event
     */
    void update(GameView o, Game.Event arg) throws RemoteException;
     GraphicalUI getGui() throws RemoteException;
     TextualUI getTui() throws RemoteException;
}
