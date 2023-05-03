package org.projectsw.Distributed;

import org.projectsw.Model.InputController;
import org.projectsw.View.UIEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
    /**
     * Register a client to the server
     * @param client the client to register
     */
    void register(Client client) throws RemoteException;

    /**
     * Notify the server that a client has made a choice
     * @param client  the client that generated the event
     * @param arg     the choice made by the client
     */
    void update(Client client, UIEvent arg, InputController input) throws RemoteException;
}
