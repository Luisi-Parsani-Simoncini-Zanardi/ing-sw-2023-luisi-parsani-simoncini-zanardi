package org.projectsw.Distributed;

import org.projectsw.Controller.Engine;
import org.projectsw.Distributed.Messages.InputMessages.InputMessage;
import org.projectsw.Distributed.Messages.InputMessages.SendNickname;
import org.projectsw.Distributed.Messages.ResponseMessages.ErrorMessage;
import org.projectsw.Distributed.Messages.ResponseMessages.Kill;
import org.projectsw.Distributed.Messages.ResponseMessages.ResponseMessage;
import org.projectsw.Model.Enums.GameState;
import org.projectsw.Model.Game;
import org.projectsw.Model.SerializableGame;
import org.projectsw.Util.Observer;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ServerImplementation extends UnicastRemoteObject implements Server{

    private final Engine controller = new Engine(this);
    private final Game model = new Game();
    private final MessageQueueHandler queueHandler = new MessageQueueHandler(controller);
    private final Thread queueThread;
    public ServerImplementation() throws RemoteException {
        super();
        controller.setGame(model);
        queueThread = new Thread(queueHandler);
        queueThread.start();
    }
    public ServerImplementation(int port) throws RemoteException {
        super(port);
        controller.setGame(model);
        queueThread = new Thread(queueHandler);
        queueThread.start();
    }
    public ServerImplementation(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
        controller.setGame(model);
        queueThread = new Thread(queueHandler);
        queueThread.start();
    }

    @Override
    public void register(Client client) throws RemoteException {
        Observer<Game, ResponseMessage> observer = (o, response) -> {
            client.update(response);
        };
        controller.getClientObserverHashMap().put(client, observer);
        this.model.addObserver(observer);
    }

    @Override
    public synchronized void update(Client client,InputMessage input) throws RemoteException {
        if(!controller.getClients_ID().getAllKey().contains(client))
            controller.getClients_ID().put(client,input.getInput().getAlphanumericID());
        queueHandler.add(input);
    }
    @Override
    public void startPingThread() {
        Thread pingThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000); // Verifica lo stato di connessione ogni 1 secondi
                    checkClientConnections();
                } catch (InterruptedException e) {
                    // Gestisci l'eccezione in base alle tue esigenze
                }
            }
        });
        pingThread.start();
    }
    public void checkClientConnections() {
        List<Client> disconnectedClients = new ArrayList<>();
        for (Client client : controller.getClients_ID().getAllKey()) {
            try {
                client.ping(); // Verifica la connessione del client
            } catch (RemoteException e) {
                // Il client non è raggiungibile, consideralo disconnesso
                disconnectedClients.add(client);
            }
        }
        // Rimuovi i client disconnessi dalla registrazione dei client
        unregisterClients(disconnectedClients);
        if(controller.getClients_ID().getAllKey().size() == 1 && controller.getGame().getGameState().equals(GameState.RUNNING) && controller.getOptionChoosed()){
            try {
                controller.getGame().setChangedAndNotifyObservers(new ErrorMessage(new SerializableGame(controller.getID_Nicks().getAllKey().get(0), "You are alone in this game. You will win in 10 seconds if no one reconnect")));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            controller.waitFor10Seconds();
            if(controller.getID_Nicks().getAllKey().size() == 1) {
                controller.getPlayerFromNickname(controller.getID_Nicks().getAllValue().get(0)).setPoints(10000);
                controller.sendResults();
                try {
                    controller.getGame().setChangedAndNotifyObservers(new Kill(new SerializableGame(controller.getID_Nicks().getAllKey().get(0),1)));
                } catch (RemoteException e) {
                    controller.getGame().deleteObserver(controller.getClientObserverHashMap().get(controller.getClients_ID().getKey(controller.getID_Nicks().getAllKey().get(0))));
                }
                System.exit(0);
            } else  {
                try {
                    controller.getGame().setChangedAndNotifyObservers(new ErrorMessage(new SerializableGame(controller.getID_Nicks().getAllKey().get(0), "another player reconnected")));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    private void unregisterClients(List<Client> clients) {
        for (Client client : clients) {
            String nick = controller.getNickFromClient(client);
            String ID = controller.getClients_ID().getValue(client);
            if(!controller.getGame().getGameState().equals(GameState.RUNNING) && ID.equals(controller.getFirstClient())) {
                controller.everlastingKill();
                System.exit(0);
            }
            controller.setIsActiveFromClient(client, false);
            controller.removeObserver(controller.getClients_ID().getValue(client));
            if (nick.equals(controller.getGame().getCurrentPlayer().getNickname())) {
                controller.endTurn(ID, controller.getGame().getCurrentPlayer().getNickname());
            }
        }
    }
}
