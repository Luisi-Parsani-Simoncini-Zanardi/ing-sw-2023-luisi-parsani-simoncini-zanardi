package org.projectsw.Distributed;

import org.projectsw.Distributed.Messages.InputMessages.InputMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
    /**
     * Register a client to the server
     * @param client the client to register
     */
    void register(Client client) throws RemoteException;
    public void removeObserver(Client client)throws RemoteException;

    /**
     * Notify the server that a client has made a choice
     * @param client is the client that is sending the data
     * @param input all data taken from the client
     */
    void update(Client client, InputMessage input) throws RemoteException;
}
