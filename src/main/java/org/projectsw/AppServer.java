package org.projectsw;

import org.projectsw.Distributed.Server;
import org.projectsw.Distributed.ServerImplementation;
import org.projectsw.Distributed.SocketMiddleware.ClientSkeleton;

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
 * Hello world!
 *
 */
public class AppServer extends UnicastRemoteObject
{

    private static AppServer instance;

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    protected AppServer() throws RemoteException {
    }

    public static AppServer getInstance() throws RemoteException {
        if (instance == null) {
            instance = new AppServer();
        }
        return instance;
    }

    public static void main(String[] args) {
        Thread rmiThread = new Thread() {
            @Override
            public void run() {
                try {
                    startRMI();
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
                    startSocket();
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

    private static void startRMI() throws RemoteException {
        Server server = new ServerImplementation();
        LocateRegistry.createRegistry(1099);
        Registry registry = LocateRegistry.getRegistry();//port 1099 standard
        registry.rebind("server", server);
    }

    public static void startSocket() throws RemoteException {
        AppServer instance = getInstance();
        try (ServerSocket serverSocket = new ServerSocket(4444)) {
            while (true) {
                Socket socket = serverSocket.accept();
                instance.executorService.submit(() -> {
                    try {
                        ClientSkeleton clientSkeleton = new ClientSkeleton(socket);
                        Server server = new ServerImplementation();
                        server.register(clientSkeleton);
                        while (true) {
                            clientSkeleton.receive(server);
                        }
                    } catch (RemoteException e) {
                        System.err.println("Cannot receive from client. Closing this connection...");
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            System.err.println("Cannot close socket");
                        }
                    }
                });
            }
        } catch (IOException e) {
            throw new RemoteException("Cannot start socket server", e);
        }
    }

    public Server connect() throws RemoteException {
        return new ServerImplementation();
    }
}
