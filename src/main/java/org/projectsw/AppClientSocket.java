package org.projectsw;

import org.projectsw.Distributed.ClientImplementation;
import org.projectsw.Distributed.SocketMiddleware.ServerStub;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.TimeUnit;

/**
 * The `AppClientSocket` class is the entry point for the Socket client application.
 */
public class AppClientSocket {

    /**
     * The main method of the `AppClientSocket` class.
     * @param args the command-line arguments
     * @throws RemoteException   if a remote exception occurs
     * @throws NotBoundException if the server is not bound
     */
    public static void main(String[] args) throws RemoteException, NotBoundException {
        ServerStub serverStub = new ServerStub("192.168.182.73", 4444);
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
        if ((args.length == 0)||(args[0]!="gui")) { //on a invalid parameter sets tui as default
            client.setTui(serverStub);
        } else {
            client.setGui(serverStub);
        }
        System.exit(0);
    }
}
