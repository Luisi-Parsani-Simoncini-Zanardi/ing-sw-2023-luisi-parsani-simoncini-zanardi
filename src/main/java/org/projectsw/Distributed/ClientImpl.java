package org.projectsw.Distributed;

import org.projectsw.Model.Game;
import org.projectsw.Model.GameView;
import org.projectsw.View.GraphicalUI;
import org.projectsw.View.TextualUI;

import java.rmi.RemoteException;

public class ClientImpl implements Client{

    private TextualUI tui;
    private GraphicalUI gui;

    public ClientImpl(Server server){
        try {
            server.register(this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        tui.addObserver((o, arg) -> {
            try {
                server.update(this, arg);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }
    @Override
    public void update(GameView o, Game.Event arg) throws RemoteException {
        //Passa la GameView alla TextualUI
    }
}
