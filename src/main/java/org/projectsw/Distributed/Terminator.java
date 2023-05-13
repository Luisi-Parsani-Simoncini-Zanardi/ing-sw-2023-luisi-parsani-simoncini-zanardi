package org.projectsw.Distributed;

import java.rmi.RemoteException;
import java.util.TimerTask;

public class Terminator extends TimerTask {
    private final Client client;
    public Terminator(Client client){
        this.client=client;
    }
    @Override
    public void run() {
        try {
            client.kill();
        } catch (RemoteException ignore) {}
    }
}
