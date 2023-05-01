package org.projectsw.Distributed;

import org.projectsw.Controller.Engine;
import org.projectsw.Exceptions.InvalidNumberOfPlayersException;
import org.projectsw.Model.GameStates;
import org.projectsw.Model.GameView;
import org.projectsw.Model.InputController;
import org.projectsw.View.UIEvent;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

import static org.projectsw.Util.Config.maxPlayers;

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
            if(this.controller.getClients().size()==0){
                try {
                    this.controller.firstPlayerJoin(client.getNickname(), client.getNumOfPLayer());
                } catch (InvalidNumberOfPlayersException e) {
                    throw new RuntimeException("cannot add first player " + e.getMessage());
                }
            }else if(this.controller.getClients().size()<maxPlayers){
                try {
                    this.controller.playerJoin(client.getNickname());
                } catch (Exception e) {
                    throw new RuntimeException("cannot add player " + e.getMessage());
                }
            }

            this.controller.getClients().add(client);

        this.controller.getGame().addObserver((o, arg) -> {
            try {
                client.update(new GameView(this.controller.getGame()), arg);
            } catch (RemoteException e) {
                throw new RuntimeException("cannot update the view " + e.getMessage());//da gestire esplicitamente
            }
        });

        if(this.controller.getGame().getGameState().equals(GameStates.RUNNING))
            this.controller.wakeUpClient();
    }

    @Override
    public void update(Client client, UIEvent arg, InputController input) throws RemoteException {
        this.controller.update(client, arg, input);
    }

    @Override
    public boolean askNum() throws RemoteException {
        return this.controller.getClients().size() == 0;
    }
}
