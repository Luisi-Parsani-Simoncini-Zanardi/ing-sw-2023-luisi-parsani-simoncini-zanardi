package org.projectsw.Distributed.Messages.InputMessages;

import org.projectsw.Controller.Engine;
import org.projectsw.Distributed.Client;
import org.projectsw.View.SerializableInput;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

public class Connect extends InputMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public Connect(SerializableInput input) {
        super(input);
    }
    @Override
    public void execute(Client client, Engine engine){
        try {
            engine.initializeGame(client, input.getAlphanumericID());
        } catch (RemoteException e) {
            throw new RuntimeException("Network error while initializing game: "+e.getMessage());
        }
    }
}
