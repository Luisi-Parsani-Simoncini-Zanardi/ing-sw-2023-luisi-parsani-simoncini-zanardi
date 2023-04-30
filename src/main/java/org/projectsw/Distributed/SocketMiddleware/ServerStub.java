package org.projectsw.Distributed.SocketMiddleware;

import org.projectsw.Distributed.Client;
import org.projectsw.Distributed.Server;
import org.projectsw.Model.Game;
import org.projectsw.Model.GameView;
import org.projectsw.Model.InputController;
import org.projectsw.View.UIEvent;

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
    public void update(Client client, UIEvent arg, InputController input) throws RemoteException {
        try{
            oos.writeObject(arg);
        }catch(IOException e){
            throw new RemoteException("Cannot send event: "+ e.getMessage());
        }

        try{
            oos.writeObject(input);
        }catch(IOException e){
            throw new RemoteException("Cannot send event: "+ e.getMessage());
        }
    }

    public void receive(Client client) throws RemoteException{
        GameView model;
        try{
            model = (GameView) ois.readObject();
        }catch(IOException e){
            throw new RemoteException("Cannot receive event: "+e.getMessage());
        }catch(ClassNotFoundException e){
            throw  new RemoteException("Cannot cast event: "+e.getMessage());
        }

        Game.Event arg;
        try{
            arg = (Game.Event) ois.readObject();
        }catch(IOException e){
            throw new RemoteException("Cannot receive event: "+e.getMessage());
        }catch(ClassNotFoundException e){
            throw  new RemoteException("Cannot cast event: "+e.getMessage());
        }

        client.update(model, arg);
    }

    public void close() throws RemoteException {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RemoteException("Cannot close socket", e);
        }
    }

    @Override
    public boolean askNum() {
        return true;
    }
}
