package org.projectsw;

import org.projectsw.Distributed.ClientImpl;
import org.projectsw.Distributed.Server;
import org.projectsw.Distributed.ServerImpl;

import java.rmi.RemoteException;

public class App {
    public static void main(String[] args) throws RemoteException {
        Server server = new ServerImpl();
        new ClientImpl(server);
    }
}
