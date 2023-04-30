package org.projectsw;

import org.projectsw.Distributed.Client;
import org.projectsw.Distributed.ClientImpl;
import org.projectsw.Distributed.Server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AppClientRMI {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry();
        try {
            //TODO: da configurare rmiregistry
            Server server = (Server) registry.lookup("server");
            new ClientImpl(server);
        }catch(NotBoundException e){
            System.err.println("Server not found");
        }

    }
}
