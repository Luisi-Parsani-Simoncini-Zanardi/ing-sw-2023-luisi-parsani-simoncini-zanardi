package org.projectsw.Distributed.SocketMiddleware;

import org.projectsw.Distributed.Client;
import org.projectsw.Distributed.Messages.InputMessages.InputMessage;
import org.projectsw.Distributed.Messages.ResponseMessages.ResponseMessage;
import org.projectsw.Distributed.Server;
import org.projectsw.Model.SerializableGame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

public class ServerStub implements Server {
    private final String ip;
    private final int port;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public ServerStub(String ip, int port){
        this.ip=ip;
        this.port=port;
    }

    @Override
    public void register(Client client) throws RemoteException {
        try {
            this.socket = new Socket(ip, port);
            try {
                this.oos = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                throw new RemoteException("Cannot create output stream", e);
            }
            try {
                this.ois = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                throw new RemoteException("Cannot create input stream", e);
            }
        } catch (IOException e) {
            throw new RemoteException("Unable to connect to the server", e);
        }
    }

    @Override
    public void removeObserver(Client client) throws RemoteException {

    }

    @Override
    public void update(Client client, InputMessage input) throws RemoteException {
        try {
            oos.writeObject(input);
        } catch (IOException e) {
            throw new RemoteException("Cannot send event: ", e);
        }
    }

    public void receive(Client client) throws RemoteException{
        ResponseMessage o;
        try {
            o = (ResponseMessage) ois.readObject();
        } catch (IOException e) {
            throw new RemoteException("Cannot receive model view from client: ", e);
        } catch (ClassNotFoundException e) {
            throw new RemoteException("Cannot deserialize model view from client: ", e);
        }
        client.update(o);
    }
    public void close() throws RemoteException {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RemoteException("Cannot close socket: ", e);
        }
    }
}