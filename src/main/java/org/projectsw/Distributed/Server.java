package org.projectsw.Distributed;

import org.projectsw.Distributed.Messages.InputMessages.InputMessage;
import org.projectsw.Distributed.Messages.ResponseMessages.ResponseMessage;
import org.projectsw.Model.Game;
import org.projectsw.Util.Observer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface Server extends Remote {
    /**
     * Register a client to the server
     * @param client the client to register
     */
    void register(Client client) throws RemoteException;
    void update(Client client,InputMessage input) throws RemoteException;
    void startPingThread()throws RemoteException;
}
