package org.projectsw.Distributed;

import org.projectsw.Controller.Engine;
import org.projectsw.Exceptions.*;
import org.projectsw.Model.GameView;
import org.projectsw.Model.InputController;
import org.projectsw.View.UIEvent;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements Server{

    private final Engine controller = new Engine();
    private final Object lock = new Object();

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
        //TODO: gestire la possibile reconnect
        //TODO: gestire se il server crasha
        this.controller.getClients().add(client);
        this.controller.getGame().addObserver((o, arg) -> {
            switch (arg){
                case SET_CLIENT_ID_RETURN -> {
                    try {
                        client.update(new GameView(this.controller.getGame().getClientID()), arg);
                    } catch (RemoteException e) {
                        throw new RuntimeException("cannot update the view " + e.getMessage());
                    }
                }
                case ERROR -> {
                    try {
                        client.update(new GameView(this.controller.getGame().getError(), this.controller.getGame().getClientID()), arg);
                    } catch (RemoteException e) {
                        throw new RuntimeException("cannot update the view " + e.getMessage());
                    }
                }
                default -> {
                    try {
                        client.update(new GameView(this.controller.getGame()), arg);
                    } catch (RemoteException e) {
                        throw new RuntimeException("cannot update the view " + e.getMessage());
                    }
                }
            }
        });
    }

    @Override
    public void update(InputController input, UIEvent arg) throws RemoteException {
        synchronized(lock){
            try {
                this.controller.update(input, arg);
            } catch (UnselectableTileException | NoMoreColumnSpaceException | MaxTemporaryTilesExceededException |
                     UpdatingOnWrongPlayerException | UnselectableColumnException e) {
                throw new RuntimeException("Something went wrong :(\nERROR: " + e.getMessage());
            }
        }
    }
}
