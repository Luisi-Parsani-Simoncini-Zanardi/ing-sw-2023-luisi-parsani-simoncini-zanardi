package org.projectsw.Distributed;

import org.projectsw.Controller.Engine;
import org.projectsw.Model.Game;
import org.projectsw.Model.GameView;

import java.rmi.RemoteException;

public class ServerImpl implements Server{

    private Engine controller;

    @Override
    public void register(Client client) throws RemoteException {
        this.controller.getClients().add(client);
        //adding observer
        this.controller.getGame().addObserver((o, arg) -> {
            try {
                client.update(new GameView(this.controller.getGame()), arg);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void update(Client client, Game arg) throws RemoteException {
        this.controller.update(client, arg);
    }
}
