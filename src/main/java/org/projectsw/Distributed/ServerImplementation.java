package org.projectsw.Distributed;

import org.projectsw.Controller.Engine;
import org.projectsw.Distributed.Messages.InputMessages.InputMessage;
import org.projectsw.Distributed.Messages.ResponseMessages.ResponseMessage;
import org.projectsw.Model.Game;
import org.projectsw.Util.Observer;
import org.projectsw.Util.OneToOneHashmap;

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
        for (Client client : controller.getClientObserverHashMap().getAllKey()) {
            try {
                client.ping(); // Verifica la connessione del client
            } catch (RemoteException e) {
                // Il client non è raggiungibile, consideralo disconnesso
                disconnectedClients.add(client);
            }
        }
        // Rimuovi i client disconnessi dalla registrazione dei client
        unregisterClients(disconnectedClients);
        disconnectedClients.clear();
    }

    private void unregisterClients(List<Client> clients) {
        for(Client client : clients) {
            try {
                controller.getPlayerFromNickname(client.getTui().getNickname()).setIsActive(false);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            controller.getClients_ID().removeByKey(client);
            model.deleteObserver(controller.getClientObserverHashMap().getValue(client));
            //TODO: rimuovere riferimenti dalle hashmap una volta rimosso l'observer
            try {
                if(client.getTui().getNickname().equals(controller.getGame().getCurrentPlayer().getNickname())) {
                controller.getClients_Nicks().removeByKey(client);
                controller.endTurn(controller.getGame().getCurrentPlayer().getNickname());
                controller.sendNexTurn();
            }
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
