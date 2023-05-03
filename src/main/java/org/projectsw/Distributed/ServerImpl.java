package org.projectsw.Distributed;

import org.projectsw.Controller.Engine;
import org.projectsw.Model.Game;
import org.projectsw.Model.GameView;
import org.projectsw.Model.InputController;
import org.projectsw.View.UIEvent;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements Server{

    private final Engine controller = new Engine();

    public ServerImpl() throws RemoteException {
        super();
    }
    public ServerImpl(int port) throws RemoteException {
        super(port);
    }
    public ServerImpl(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
    }

    @Override
    public void register(Client client) throws RemoteException {
        //TODO: gestire la possibile reconnect se un savegame è presente
        this.controller.getClients().add(client);
        this.controller.getGame().addObserver((o, arg) -> {
            if (arg == Game.Event.EXISTS_FIRST_PLAYER) {
                try {
                    client.update(new GameView(), arg);
                } catch (RemoteException e) {
                    throw new RuntimeException("cannot update the view " + e.getMessage());//TODO: gestire esplicitamente
                }
            } else {
                if (arg == Game.Event.ERROR){
                    try {
                        client.update(new GameView(this.controller.getGame().getError()), arg);
                    } catch (RemoteException e) {
                        throw new RuntimeException("cannot update the view " + e.getMessage());//TODO: gestire esplicitamente
                    }
                } else {
                    try {
                        client.update(new GameView(this.controller.getGame().getError()), arg);
                    } catch (RemoteException e) {
                        throw new RuntimeException("cannot update the view " + e.getMessage());//TODO: gestire esplicitamente
                    }
                }
            }
        });
    }

    @Override
    public void update(Client client, UIEvent arg, InputController input) throws RemoteException {
        this.controller.update(client, arg, input);
    }
}
