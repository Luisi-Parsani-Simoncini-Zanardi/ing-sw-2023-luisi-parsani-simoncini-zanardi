package org.projectsw.Distributed.SocketMiddleware;

import org.projectsw.Distributed.Client;
import org.projectsw.Distributed.Messages.InputMessages.InputMessage;
import org.projectsw.Distributed.Messages.ResponseMessages.ResponseMessage;
import org.projectsw.Distributed.Server;
import org.projectsw.Model.*;
import org.projectsw.Util.Observer;
import org.projectsw.View.GraphicalUI;
import org.projectsw.View.TextualUI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;
import java.rmi.RemoteException;

public class ClientSkeleton implements Client {
    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;

    public ClientSkeleton(Socket socket) throws RemoteException{
        try {
            this.oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RemoteException("Cannot create output stream: "+e.getMessage());
        }
        try {
            this.ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RemoteException("Cannot create input stream: "+e.getMessage());
        }
    }

    @Override
    public String getNickname() throws RemoteException {
        return null;
    }

    @Override
    public void setCorrectResponse(boolean response) throws RemoteException {

    }

    @Override
    public Observer<TextualUI, InputMessage> getTuiObserver() throws RemoteException {
        return null;
    }

    @Override
    public Observer<GraphicalUI, InputMessage> getGuiObserver() throws RemoteException {
        return null;
    }

    @Override
    public void update(ResponseMessage response) throws RemoteException {
        try {
            oos.writeObject(response);
        } catch (IOException e) {
            throw new RemoteException("Cannot send event", e);
        }
    }

    @Override
    public void kill(SerializableGame game) throws RemoteException {

    }

    public void receive(Server server) throws RemoteException{
        InputMessage input;
        try {
            input = (InputMessage) ois.readObject();
        } catch (IOException e) {
            throw new RemoteException("Cannot receive choice from client", e);
        } catch (ClassNotFoundException e) {
            throw new RemoteException("Cannot deserialize choice from client", e);
        }
        server.update(this, input);
    }
}