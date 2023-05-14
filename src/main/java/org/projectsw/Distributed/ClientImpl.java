package org.projectsw.Distributed;
import org.projectsw.Model.Game;
import org.projectsw.Model.GameView;
import org.projectsw.Model.InputController;
import org.projectsw.Util.Observer;
import org.projectsw.View.GraphicalUI;
import org.projectsw.View.TextualUI;
import org.projectsw.View.UIEvent;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;


public class ClientImpl extends UnicastRemoteObject implements Client{
    private TextualUI tui;
    private GraphicalUI gui;
    private Observer<TextualUI, UIEvent> tuiObserver;
    private Observer<GraphicalUI, UIEvent> guiObserver;

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
            throw new RemoteException("Cannot register client on server" + e.getCause());
        }
    }

    @Override
    public TextualUI getTui()throws RemoteException{
        return this.tui;
    }

    @Override
    public GraphicalUI getGui() throws RemoteException{
        return this.gui;
    }

    @Override
    public void update(GameView o, Game.Event arg) throws RemoteException {
        if(tui != null)
            tui.update(o, arg);
        else
            gui.update(o, arg);
    }


    public void setTui (Server server){
        gui = null;
        tui = new TextualUI(this);
        tuiObserver = (o, arg) -> {
            switch (arg){
                case CHOOSE_NUMBER_OF_PLAYERS -> {
                    try {
                        server.setNumberOfPlayers(new InputController(tui.getClientUID(), tui.getNumber()));
                    } catch (RemoteException e) {
                        throw new RemoteException("Cannot send the tile selection to the server" + e.getCause());
                    }
                }
                case CHOOSE_NICKNAME -> {
                    try {
                        server.initializePlayer(this, new InputController(tui.getNickname()));
                    } catch (RemoteException e) {
                        throw new RemoteException("Cannot send the tile selection to the server" + e.getCause());
                    }
                }
                case NEW_CHOOSE_NICKNAME -> {
                    try {
                        server.setCorrectNick(new InputController(tui.getNickname()));
                    } catch (RemoteException e) {
                        throw new RemoteException("Cannot send the tile selection to the server" + e.getCause());
                    }
                }
                case TILE_SELECTION -> {
                    try {
                        server.update(new InputController(tui.getClientUID(), tui.getPoint()), arg);
                    } catch (RemoteException e) {
                        throw new RemoteException("Cannot send the tile selection to the server" + e.getCause());
                    }
                }
                case SAY_IN_CHAT -> {
                    try {
                        server.update(new InputController(tui.getClientUID(), tui.getString()), arg);
                    } catch (RemoteException e) {
                        throw new RemoteException("Cannot send the message to the server: " + e.getCause());
                    }
                }
                case COLUMN_SELECTION, TILE_INSERTION-> {
                    try {
                        server.update(new InputController(tui.getClientUID(), tui.getNumber()), arg);
                    } catch (RemoteException e) {
                        throw new RemoteException("Cannot send the client input to the server while selecting the column: " + e.getCause());
                    }
                }
                default -> {
                    try {
                        server.update(new InputController(tui.getClientUID()), arg);
                    } catch (RemoteException e) {
                        throw new RemoteException("Cannot send the client input to the server: " + e.getCause());
                    }
                }
            }
        };
        tui.addObserver(tuiObserver);
        tui.run();
    }
    @Override
    public void setID(GameView serverResponse) throws RemoteException{
        tui.setID(serverResponse.getClientID());
    }

    @Override
    public void setNickname(GameView serverResponse) throws RemoteException{
        tui.setNickname(serverResponse.getCurrentPlayerName());
    }

    //usata solo lato server nel arraylist di client
    @Override
    public String getNickname() throws RemoteException{
        return tui.getNickname();
    }

    @Override
    public void askNumberOfPlayers() throws RemoteException{
        tui.askNumber();
    }

    @Override
    public void askNewNick(GameView nicks) throws RemoteException{
        tui.askNewNick(nicks.getPlayerNicks());
    }

    /**
     * close the client
     */
    @Override
    public void kill() throws RemoteException{
        tui.deleteObserver(tuiObserver);
        tui.kill();
        System.exit(0);
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
