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
 * Hello world!
 *
 */
public class AppServer extends UnicastRemoteObject
{

    private static AppServer instance;

    private static final Server server;

    static {
        try {
            server = new ServerImplementation();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

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
                    startRMI(getServer());
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
                    startSocket(getServer());
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

    private static void startRMI(Server server) throws RemoteException {
        LocateRegistry.createRegistry(1099);
        Registry registry = LocateRegistry.getRegistry();//port 1099 standard
        registry.rebind("server", server);

        //server.startPingThread();

    }

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

    public static Server getServer() throws RemoteException {
        return server;
    }
}
