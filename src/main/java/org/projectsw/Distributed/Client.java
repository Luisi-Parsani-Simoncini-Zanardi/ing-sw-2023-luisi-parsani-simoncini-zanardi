package org.projectsw.Distributed;

import org.projectsw.Distributed.Messages.InputMessages.InputMessage;
import org.projectsw.Distributed.Messages.ResponseMessages.ResponseMessage;
import org.projectsw.Util.Observer;
import org.projectsw.View.GraphicalUI.GuiManager;
import org.projectsw.View.TextualUI;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {
    /**
     * Notify the client of a model change
     * @param response is the server response to the client
     */
    void update(ResponseMessage response) throws RemoteException;
    void kill()throws RemoteException;
    String getNickname() throws RemoteException;
    Observer<TextualUI, InputMessage> getTuiObserver() throws  RemoteException;
    Observer<GuiManager, InputMessage> getGuiObserver() throws  RemoteException;
    void ping() throws  RemoteException;
    TextualUI getTui()throws  RemoteException;
}
