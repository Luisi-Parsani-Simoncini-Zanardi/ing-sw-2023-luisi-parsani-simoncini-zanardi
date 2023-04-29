package org.projectsw;

import org.projectsw.Distributed.ClientImpl;
import org.projectsw.Distributed.Server;
import org.projectsw.Distributed.ServerImpl;

public class App {
    public static void main(String[] args){
        Server server = new ServerImpl();

        new ClientImpl(server);

    }
}
