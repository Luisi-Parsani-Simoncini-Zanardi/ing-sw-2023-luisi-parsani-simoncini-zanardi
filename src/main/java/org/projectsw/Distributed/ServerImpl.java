package org.projectsw.Distributed;

import org.projectsw.Controller.Engine;
import org.projectsw.Distributed.Messages.InputMessages.InputMessage;
import org.projectsw.Distributed.Messages.ResponseMessages.ResponseMessage;
import org.projectsw.Model.Game;
import org.projectsw.Util.Observer;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ServerImpl extends UnicastRemoteObject implements Server{

    private final Engine controller = new Engine(this);
    private final Game model = new Game();

    private final Map<Client, Observer<Game, ResponseMessage>> clientObserverHashMap = new HashMap<>();

    public ServerImpl() throws RemoteException {
        super();
        controller.setGame(model);

    }
    public ServerImpl(int port) throws RemoteException {
        super(port);
        controller.setGame(model);
    }
    public ServerImpl(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
        controller.setGame(model);
    }

    @Override
    public void register(Client client) throws RemoteException {
        //TODO: gestire la possibile reconnect
        //TODO: gestire se il server crasha
        Observer<Game, ResponseMessage> observer = (o, response) -> {
            client.update(response);
        };
        clientObserverHashMap.put(client, observer);
        this.model.addObserver(observer);
    }

    @Override
    public void removeObserver(Client client){
        model.deleteObserver(clientObserverHashMap.get(client));
        clientObserverHashMap.remove(client);
    }

    @Override
    public void update(Client client, InputMessage input) throws RemoteException {
        this.controller.update(client, input);
    }
}
