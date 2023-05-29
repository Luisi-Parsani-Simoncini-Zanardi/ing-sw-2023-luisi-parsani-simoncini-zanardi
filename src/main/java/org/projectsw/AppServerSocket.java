package org.projectsw;

import org.projectsw.Distributed.Server;
import org.projectsw.Distributed.ServerImpl;
import org.projectsw.Distributed.SocketMiddleware.ClientSkeleton;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;

//2:06/13
public class AppServerSocket {
    public static void main( String[] args ) throws RemoteException {
        try (ServerSocket serverSocket = new ServerSocket(4444)) {
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    ClientSkeleton clientSkeleton = new ClientSkeleton(socket);
                    Server server = new ServerImpl();
                    server.register(clientSkeleton);
                    while (true) {
                        clientSkeleton.receive(server);
                    }
                } catch (IOException e) {
                    System.err.println("Socket failed: " + e.getMessage() +". Closing connection and waiting for a new one...");
                }
            }
        } catch (IOException e) {
            throw new RemoteException("Cannot create server socket: ", e);
        }
    }
}