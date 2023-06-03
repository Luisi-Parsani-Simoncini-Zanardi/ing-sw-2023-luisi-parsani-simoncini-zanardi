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

public class ServerImplementation extends UnicastRemoteObject implements Server{

    private final Engine controller = new Engine(this);
    private final Game model = new Game();
    private final Map<Client, Observer<Game, ResponseMessage>> clientObserverHashMap = new HashMap<>();
    private MessageQueueHandler queueHandler = new MessageQueueHandler(controller);
    private Thread queueThread;

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
        clientObserverHashMap.put(client, observer);
        this.model.addObserver(observer);
    }

    @Override
    public void removeObserver(Client client){
        model.deleteObserver(clientObserverHashMap.get(client));
        clientObserverHashMap.remove(client);
    }

    @Override
    public void update(InputMessage input) throws RemoteException {
        queueHandler.add(input);
        //this.controller.update(input);
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
        disconnectedClients.clear();
    }

    private void unregisterClients(List<Client> clients) {
        for(Client client : clients) {
            controller.getPlayerFromNickname(controller.getClients_Nicks().getValue(client)).setIsActive(false);
            controller.getClients_ID().removeByKey(client);
            removeObserver(client);
            if(controller.getGame().getCurrentPlayer().getNickname().equals(controller.getClients_Nicks().getValue(client))){
                controller.getClients_Nicks().removeByKey(client);
                controller.endTurn(controller.getGame().getCurrentPlayer().getNickname());
                controller.sendNexTurn();
            }
        }
    }
}
