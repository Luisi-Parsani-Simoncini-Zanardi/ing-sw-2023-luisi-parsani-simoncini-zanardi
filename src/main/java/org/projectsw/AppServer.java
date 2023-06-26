package org.projectsw;

import org.projectsw.Distributed.Server;
import org.projectsw.Distributed.ServerImplementation;
import org.projectsw.Distributed.SocketMiddleware.ClientSkeleton;
import org.projectsw.View.ConsoleColors;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The `AppServer` class represents the server application.
 */
public class AppServer extends UnicastRemoteObject
{

    private static AppServer instance;

    private final Server server = new ServerImplementation();

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * Constructs an `AppServer` object.
     * @throws RemoteException if a remote exception occurs
     */
    protected AppServer() throws RemoteException {
    }

    /**
     * Returns the singleton instance of the `AppServer`.
     * @return the `AppServer` instance
     * @throws RemoteException if a remote exception occurs
     */
    public static AppServer getInstance() throws RemoteException {
        if (instance == null) {
            instance = new AppServer();
        }
        return instance;
    }

    /**
     * The main method of the `AppServer` class.
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Thread rmiThread = new Thread() {
            @Override
            public void run() {
                try {
                    startRMI(getInstance().getServer());
                } catch (RemoteException e) {
                    System.err.println("Cannot start RMI. This protocol will be disabled.");
                }
            }
        };

        rmiThread.start();

        Thread socketThread = new Thread() {
            @Override
            public void run() {
                try {
                    startSocket(getInstance().getServer());
                } catch (RemoteException e) {
                    System.err.println("Cannot start socket. This protocol will be disabled.");
                }
            }
        };

        socketThread.start();

        try {
            rmiThread.join();
            socketThread.join();
        } catch (InterruptedException e) {
            System.err.println("No connection protocol available. Exiting...");
        }
    }

    /**
     * Starts the RMI server.
     * @param server the server object
     * @throws RemoteException if a remote exception occurs
     */
    private static void startRMI(Server server) throws RemoteException {
        LocateRegistry.createRegistry(1099);
        Registry registry = LocateRegistry.getRegistry("192.168.182.73");//port 1099 standard
        registry.rebind("server", server);

        server.startPingThread();
    }

    /**
     * Starts the socket server.
     * @param server the server object
     * @throws RemoteException if a remote exception occurs
     */
    public static void startSocket(Server server) throws RemoteException {
        AppServer instance = getInstance();
        try (ServerSocket serverSocket = new ServerSocket(4444)) {
            while (true) {
                Socket socket = serverSocket.accept();
                instance.executorService.submit(() -> {
                    try {
                        ClientSkeleton clientSkeleton = new ClientSkeleton(socket);
                        server.register(clientSkeleton);
                        while (true) {
                            clientSkeleton.receive(server);
                        }
                    } catch (RemoteException e) {
                        System.err.println("An error while receive from client. Closing this connection...");
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            System.err.println("An error while closing socket: ");
                        }
                    }
                });
            }
        } catch (IOException e) {
            throw new RemoteException("An error while starting socket server: ", e);
        }
    }

    /**
     * Returns the server instance associated with this `AppServer`.
     * @return the server instance
     * @throws RemoteException if a remote exception occurs
     */
    public Server getServer() throws RemoteException {
        return this.server;
    }
}
