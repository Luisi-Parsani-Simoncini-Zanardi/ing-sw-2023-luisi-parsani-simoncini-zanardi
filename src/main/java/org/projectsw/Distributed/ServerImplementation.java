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

/**
 * The ServerImplementation class implements the Server interface and represents the server component of the game application.
 * It extends UnicastRemoteObject to enable remote method invocation.
 * The class manages the game engine, message queue handling, client registration, client updates, and connection monitoring.
 */
public class ServerImplementation extends UnicastRemoteObject implements Server{

    private final Engine controller = new Engine(this);
    private final Game model = new Game();
    private final MessageQueueHandler queueHandler = new MessageQueueHandler(controller);
    private final Thread queueThread;

    /**
     * Constructs a new ServerImplementation with the default RMI port.
     * @throws RemoteException if a remote communication error occurs
     */
    public ServerImplementation() throws RemoteException {
        super();
        controller.setGame(model);
        queueThread = new Thread(queueHandler);
        queueThread.start();
    }

    /**
     * Constructs a new ServerImplementation with the specified RMI port.
     * @param port the RMI port to bind the server to
     * @throws RemoteException if a remote communication error occurs
     */
    public ServerImplementation(int port) throws RemoteException {
        super(port);
        controller.setGame(model);
        queueThread = new Thread(queueHandler);
        queueThread.start();
    }

    /**
     * Constructs a new ServerImplementation with the specified RMI port, client socket factory, and server socket factory.
     * @param port the RMI port to bind the server to
     * @param csf  the client socket factory
     * @param ssf  the server socket factory
     * @throws RemoteException if a remote communication error occurs
     */
    public ServerImplementation(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
        controller.setGame(model);
        queueThread = new Thread(queueHandler);
        queueThread.start();
    }

    /**
     * Registers a client to receive updates from the server.
     * @param client the client to register
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public void register(Client client) throws RemoteException {
        Observer<Game, ResponseMessage> observer = (o, response) -> {
            client.update(response);
        };
        controller.getClientObserverHashMap().put(client, observer);
        this.model.addObserver(observer);
    }

    /**
     * Updates the server with an input message from a client.
     * Adds the input message to the message queue for processing.
     * @param client the client sending the input message
     * @param input  the input message to be processed
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public synchronized void update(Client client,InputMessage input) throws RemoteException {
        if(!controller.getClients_ID().getAllKey().contains(client))
            controller.getClients_ID().put(client,input.getInput().getAlphanumericID());
        queueHandler.add(input);
    }

    /**
     * Starts a separate thread for monitoring client connections.
     */
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

    /**
     * Checks the connections of all registered clients.
     * Disconnects clients that are unreachable and performs necessary cleanup.
     */
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
        if(controller.getClients_ID().getAllKey().size() == 1 && controller.getGame().getGameState().equals(GameState.RUNNING) && controller.getOptionChoosed() && controller.getFreeNamesUsedInLastGame().isEmpty()){
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
                    controller.getGame().setChangedAndNotifyObservers(new Kill(new SerializableGame(controller.getID_Nicks().getAllKey().get(0),2)));
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

    /**
     * Unregisters disconnected clients.
     * @param clients the disconnected clients to unregister
     */
    private void unregisterClients(List<Client> clients) {
        for (Client client : clients) {
            if (controller.getID_Nicks().getAllKey().contains(controller.getClients_ID().getValue(client))) {
                String nick = controller.getNickFromClient(client);
                String ID = controller.getClients_ID().getValue(client);
                if (!controller.getGame().getGameState().equals(GameState.RUNNING) && ID.equals(controller.getFirstClient())) {
                    controller.everlastingKill();
                    System.exit(0);
                }
                if (controller.getGame().getPlayersNickname().contains(nick))
                    controller.setIsActiveFromClient(client, false);
                controller.removeObserver(controller.getClients_ID().getValue(client), 0);
                if (nick.equals(controller.getGame().getCurrentPlayer().getNickname())) {
                    controller.endTurn(ID, controller.getGame().getCurrentPlayer().getNickname());
                }
            }
        }
    }
}
