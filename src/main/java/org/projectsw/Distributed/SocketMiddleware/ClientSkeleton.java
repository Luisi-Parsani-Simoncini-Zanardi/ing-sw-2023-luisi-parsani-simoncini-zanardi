package org.projectsw.Distributed.SocketMiddleware;

import org.projectsw.Distributed.Client;
import org.projectsw.Distributed.Messages.ResponseMessages.ResponseMessage;
import org.projectsw.Distributed.Server;
import org.projectsw.Model.*;
import org.projectsw.Model.Enums.GameEvent;
import org.projectsw.View.Enums.UIEvent;

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
    public void setID(GameView serverResponse) throws RemoteException {

    }

    @Override
    public String getNickname() throws RemoteException {
        return null;
    }

    @Override
    public void setCorrectResponse(boolean response) throws RemoteException {

    }

    @Override
    public void kill() throws RemoteException {

    }

    @Override
    public void update(ResponseMessage response) throws RemoteException {

    }
    public void receive(Server server) throws RemoteException{

    }
}
