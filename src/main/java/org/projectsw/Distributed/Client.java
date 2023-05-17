package org.projectsw.Distributed;

import org.projectsw.Distributed.Messages.ResponseMessages.ResponseMessage;
import org.projectsw.Model.Enums.GameEvent;
import org.projectsw.Model.GameView;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {
    /**
     * Notify the client of a model change
     * @param response is the server response to the client
     */
    void update(ResponseMessage response) throws RemoteException;
    void kill()throws RemoteException;
    public void setID(GameView model) throws RemoteException;
    public String getNickname() throws RemoteException;
    public void setCorrectResponse(boolean response) throws RemoteException;
}
