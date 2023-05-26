package org.projectsw.Distributed;

import org.projectsw.Distributed.Messages.InputMessages.InputMessage;
import org.projectsw.Distributed.Messages.ResponseMessages.ResponseMessage;
import org.projectsw.Model.SerializableGame;
import org.projectsw.Util.Observer;
import org.projectsw.View.GraphicalUI;
import org.projectsw.View.TextualUI;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {
    /**
     * Notify the client of a model change
     * @param response is the server response to the client
     */
    void update(ResponseMessage response) throws RemoteException;
    void kill(SerializableGame game)throws RemoteException;
    public String getNickname() throws RemoteException;
    public Observer<TextualUI, InputMessage> getTuiObserver() throws  RemoteException;
    public Observer<GraphicalUI, InputMessage> getGuiObserver() throws  RemoteException;
}
