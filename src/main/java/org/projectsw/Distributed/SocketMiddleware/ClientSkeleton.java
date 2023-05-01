package org.projectsw.Distributed.SocketMiddleware;

import org.projectsw.Distributed.Client;
import org.projectsw.Distributed.Server;
import org.projectsw.Model.Game;
import org.projectsw.Model.GameView;
import org.projectsw.Model.InputController;
import org.projectsw.View.GraphicalUI;
import org.projectsw.View.TextualUI;
import org.projectsw.View.UIEvent;

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
    public void update(GameView o, Game.Event arg) throws RemoteException {
        try{
            oos.writeObject(o);
            oos.writeObject(arg);
        }catch(IOException e){
            throw new RemoteException("Cannot send event: " + e.getMessage());
        }
    }

    public void receive(Server server) throws RemoteException{
        UIEvent arg;
        try{
            arg = (UIEvent) ois.readObject();
        }catch(IOException e){
            throw new RemoteException("Cannot receive event: "+e.getMessage());
        }catch(ClassNotFoundException e){
            throw new RemoteException("Cannot cast event: "+e.getMessage());
        }

        InputController input;
        try{
            input = (InputController) ois.readObject();
        }catch(IOException e){
            throw new RemoteException("Cannot receive event: "+e.getMessage());
        }catch(ClassNotFoundException e){
            throw new RemoteException("Cannot cast event: "+e.getMessage());
        }
        server.update(this, arg, input);
    }


    //TODO: non so se va bene ritornarli con ois e oos o semplicemente fare return nickname
    @Override
    public String getNickname() throws RemoteException {
        return null;
    }

    @Override
    public int getNumOfPLayer() throws RemoteException {
        return 0;
    }

    @Override
    public GraphicalUI getGui() throws RemoteException {
        return null;
    }

    @Override
    public TextualUI getTui() throws RemoteException {
        return null;
    }
}