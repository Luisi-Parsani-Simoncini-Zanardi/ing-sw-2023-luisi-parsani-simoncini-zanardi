package org.projectsw;

import org.projectsw.Distributed.ClientImpl;
import org.projectsw.Distributed.Server;
import org.projectsw.Distributed.SocketMiddleware.ServerStub;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AppClientSocket {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        ServerStub serverStub = new ServerStub("serverStub", 1234);
        ClientImpl client = new ClientImpl(serverStub);
        if ((args.length == 0)||(args[0]!="gui")) { //on a invalid parameter sets tui as default
            client.setTui(serverStub);
        } else {
            client.setGui(serverStub);
        }
        System.exit(0);
    }
}
