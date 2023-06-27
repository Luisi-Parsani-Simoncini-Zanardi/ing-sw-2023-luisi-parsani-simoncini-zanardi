package org.projectsw;

import org.projectsw.Distributed.ClientImplementation;
import org.projectsw.Distributed.Server;
import org.projectsw.Distributed.SocketMiddleware.ServerStub;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AppClient {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        if(args[1].equals("rmi")) {
            Registry registry = LocateRegistry.getRegistry(args[2]);
            try {
                Server server = (Server) registry.lookup("server");
                ClientImplementation client = new ClientImplementation(server);
                if (args[0].equals("tui")) {
                    client.setTui(server);
                } else if (args[0].equals("gui")) {
                    client.setGui(server);
                } else {
                    System.err.println("Error in the interface selection");
                }
            } catch (NotBoundException e) {
                throw new NotBoundException("Server not found");
            }
        }else if(args[1].equals("socket")){
            ServerStub serverStub = new ServerStub(args[2], 4444);
            ClientImplementation client = new ClientImplementation(serverStub);
            new Thread() {
                @Override
                public void run() {
                    while(true) {
                        try {
                            serverStub.receive(client);
                        } catch (RemoteException e) {
                            System.err.println("Cannot receive from server. Stopping...");
                            try {
                                serverStub.close();
                            } catch (RemoteException ex) {
                                System.err.println("Cannot close connection with server. Halting...");
                            }
                            System.exit(1);
                        }
                    }
                }
            }.start();
            if (args[0].equals("tui")) {
                client.setTui(serverStub);
            } else if (args[0].equals("gui")) {
                client.setGui(serverStub);
            } else {
                System.err.println("Error in the interface selection");
            }
        }
        System.exit(0);
    }
}
