package org.projectsw.Distributed;

import org.projectsw.Model.Game;
import org.projectsw.Model.GameView;
import org.projectsw.Model.InputController;
import org.projectsw.View.GraphicalUI;
import org.projectsw.View.TextualUI;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;


public class ClientImpl extends UnicastRemoteObject implements Client{
    private TextualUI tui;
    private GraphicalUI gui;


    public ClientImpl(Server server) throws RemoteException{
        super();
        initialize(server);
    }

    public ClientImpl(int port, Server server) throws RemoteException {
        super(port);
        initialize(server);
    }

    public ClientImpl(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf, Server server) throws RemoteException {
        super(port, csf, ssf);
        initialize(server);
    }

    private void initialize(Server server) throws RemoteException{
        try {
            server.register(this);
        } catch (RemoteException e) {
            throw new RuntimeException("Cannot register client on server" + e.getMessage());
        }
    }

    public TextualUI getTui(){
        return this.tui;
    }
    public GraphicalUI getGui() {
        return this.gui;
    }

    @Override
    public void update(GameView o, Game.Event arg) throws RemoteException {
        if(tui != null)
            tui.update(o, arg);
        else
            gui.update(o, arg);
    }

    public void setTui (Server server) {
        gui = null;
        tui = new TextualUI();
        tui.addObserver((o, arg) -> {
            switch (arg){
                case TILE_SELECTION -> {
                    try {
                        server.update(new InputController(tui.getClientUID(), tui.getPoint()), arg);
                    } catch (RemoteException e) {
                        throw new RuntimeException("Cannot send the tile selection to the server" + e.getMessage());
                    }
                }
                case COLUMN_SELECTION, TILE_INSERTION, CHOOSE_PLAYER_NUMBER -> {
                    try {
                        server.update(new InputController(tui.getClientUID(), tui.getNumber()), arg);
                    } catch (RemoteException e) {
                        throw new RuntimeException("Cannot send the chat message to the server: " + e.getMessage());
                    }
                }
                case SAY_IN_CHAT -> {
                    try {
                        server.update(new InputController(tui.getClientUID(), tui.getString()), arg);
                    } catch (RemoteException e) {
                        throw new RuntimeException("Cannot send the message to the server: " + e.getMessage());
                    }
                }
                case CHOOSE_NICKNAME_AND_SET_CLIENT_ID -> {
                    try {
                        server.update(new InputController(tui.getClientUID(), tui.getNickname()), arg);
                    } catch (RemoteException e) {
                        throw new RuntimeException("Cannot send the client nickname to the server: " + e.getMessage());
                    }
                }
                default -> {
                    try {
                        server.update(new InputController(tui.getClientUID()), arg);
                    } catch (RemoteException e) {
                        throw new RuntimeException("Cannot send the client input to the server: " + e.getMessage());
                    }
                }
            }
        });
        tui.run();
    }

    public void setGui (Server server) {
        gui = new GraphicalUI();
        tui = null;
        gui.addObserver((o, arg) -> {
                /*try {
                    server.update(new InputController(tui.getClientUID(), tui.getPoint(),tui.getNumber(),tui.getNickname()), arg);
                    cambiare una volta finita GUI
                } catch (RemoteException e) {
                    throw new RuntimeException("Cannot send the client input" + e.getMessage());
                }*/
        });
        gui.run();
    }

}
