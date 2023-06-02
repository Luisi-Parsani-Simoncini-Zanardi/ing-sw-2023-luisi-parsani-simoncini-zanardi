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
    void update(InputMessage input) throws RemoteException;
     void startPingThread()throws RemoteException;
}
