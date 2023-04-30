package org.projectsw;

import org.projectsw.Distributed.Server;
import org.projectsw.Distributed.ServerImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AppServerRMI {
    public static void main(String[] args) throws RemoteException {
        Server server = new ServerImpl();
        LocateRegistry.createRegistry(1099);
        Registry registry = LocateRegistry.getRegistry();//port 1099 standard
        registry.rebind("server", server);
    }
}
