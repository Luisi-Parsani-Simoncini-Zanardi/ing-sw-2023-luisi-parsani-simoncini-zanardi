package org.projectsw;

import org.projectsw.Distributed.ClientImplementation;
import org.projectsw.Distributed.Server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * The `AppClientRMI` class is the entry point for the RMI client application.
 */
public class AppClientRMI {

    /**
     * The main method of the `AppClientRMI` class.
     * @param args the command-line arguments
     * @throws RemoteException   if a remote exception occurs
     * @throws NotBoundException if the server is not bound
     */
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("192.168.182.73");
        try {
            Server server = (Server) registry.lookup("server");
            ClientImplementation client = new ClientImplementation(server);
            if ((args.length == 0)||(args[0]!="gui")) { //on a invalid parameter sets tui as default
                client.setTui(server);
            } else {
                client.setGui(server);
            }
        }catch(NotBoundException e){
            throw new NotBoundException("Server not found");
        }
        System.exit(0);
    }
}
