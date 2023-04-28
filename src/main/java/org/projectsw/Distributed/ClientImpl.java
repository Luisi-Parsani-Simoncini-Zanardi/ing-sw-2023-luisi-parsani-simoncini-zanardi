package org.projectsw.Distributed;

import org.projectsw.Model.Game;
import org.projectsw.Model.GameView;
import org.projectsw.View.GraphicalUI;
import org.projectsw.View.TextualUI;

import java.rmi.RemoteException;

public class ClientImpl implements Client{

    private TextualUI tui;
    private GraphicalUI gui;

    public ClientImpl(boolean useGui){
        if(useGui){

        }
    }
    @Override
    public void update(GameView o, Game.Event arg) throws RemoteException {

    }
}
