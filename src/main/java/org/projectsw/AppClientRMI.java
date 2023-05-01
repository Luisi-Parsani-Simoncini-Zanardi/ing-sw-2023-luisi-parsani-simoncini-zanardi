package org.projectsw;

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
            Server server = (Server) registry.lookup("server");
            new ClientImpl(server);
        }catch(NotBoundException e){
            throw new NotBoundException("Server not found");
        }

    }
}
