package org.projectsw.Distributed;

import org.projectsw.Controller.Engine;
import org.projectsw.Exceptions.*;
import org.projectsw.Model.Game;
import org.projectsw.Model.GameView;
import org.projectsw.Model.InputController;
import org.projectsw.Util.Observer;
import org.projectsw.View.UIEvent;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ServerImpl extends UnicastRemoteObject implements Server{

    private final Engine controller = new Engine();
    private final Game model = new Game();
    private final Object lock = new Object();
    private static int counter = 0;
    private String tempNick;
    private int numberOfPlayers;
    private final Map<Client, Observer<Game, Game.Event>> clientObserverHashMap = new HashMap<>();

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
    public void setNumberOfPlayers(InputController input)throws RemoteException{
      this.numberOfPlayers = input.getIndex();
    }

    @Override
    public void register(Client client) throws RemoteException {
        //TODO: gestire la possibile reconnect
        //TODO: gestire se il server crasha
        Observer<Game, Game.Event> observer = (o, arg) -> {
            if (Objects.requireNonNull(arg) == Game.Event.ERROR) {
                try {
                    client.update(new GameView(this.model.getError(), this.model.getClientID()), arg);
                } catch (RemoteException e) {
                    throw new RemoteException("cannot update the view " + e.getCause());
                }
            } else {
                try {
                    client.update(new GameView(this.model), arg);
                } catch (RemoteException e) {
                    throw new RemoteException("cannot update the view " + e.getCause());
                }
            }
        };
        clientObserverHashMap.put(client, observer);
        this.model.addObserver(observer);
    }

    @Override
    public void update(InputController input, UIEvent arg) throws RemoteException {
                this.controller.update(input, arg);
    }

    private ArrayList<String> getNicks() throws RemoteException {
        ArrayList<String> nicks = new ArrayList<>();
        for(Client client : this.controller.getClients())
            nicks.add(client.getNickname());
        return nicks;
    }

    @Override
    public void setCorrectNick(InputController input){
        this.tempNick = input.getString();
    }
    @Override
    public void initializePlayer(Client client, InputController input) throws RemoteException {
        synchronized(lock){
            if(this.controller.getClients().size()==0){
                counter++;
                client.setID(new GameView(counter));
                client.setNickname(new GameView(input.getString()));
                this.controller.getClients().add(client);
                client.askNumberOfPlayers();
                this.model.initializeGame(this.numberOfPlayers);
                this.controller.playerJoin(input.getString());
            }else if(this.controller.getClients().size()<this.numberOfPlayers){
                for(Client chekClient : this.controller.getClients()) {
                    if (chekClient.getNickname().equals(input.getString())) {
                        client.askNewNick(new GameView(getNicks()));
                    } else {
                        tempNick = input.getString();
                    }
                }
                counter++;
                client.setID(new GameView(counter));
                client.setNickname(new GameView(this.tempNick));
                this.controller.getClients().add(client);
                this.controller.playerJoin(this.tempNick);
            }else{
                controller.removeGameObserver(clientObserverHashMap.get(client));
                clientObserverHashMap.remove(client);
                client.kill();
            }
        }
    }
}
