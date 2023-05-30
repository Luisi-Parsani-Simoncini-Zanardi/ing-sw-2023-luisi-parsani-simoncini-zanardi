package org.projectsw.Distributed.Messages.InputMessages;

import org.projectsw.Controller.Engine;
import org.projectsw.Distributed.Client;
import org.projectsw.View.SerializableInput;

import java.io.Serial;
import java.io.Serializable;

public class Connect extends InputMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public Connect(SerializableInput input) {
        super(input);
    }
    @Override
    public void execute(Client client, Engine engine){
        engine.initializeGame(client, input.getAlphanumericID());
    }
}
