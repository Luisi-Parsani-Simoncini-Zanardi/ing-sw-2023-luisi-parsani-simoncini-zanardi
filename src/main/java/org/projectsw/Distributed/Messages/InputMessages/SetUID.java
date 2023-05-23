package org.projectsw.Distributed.Messages.InputMessages;

import org.projectsw.Controller.Engine;
import org.projectsw.Distributed.Client;
import org.projectsw.Model.InputController;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

public class SetUID extends InputMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public SetUID(InputController input) {
        super(input);
    }
    @Override
    public void execute(Client client, Engine engine) throws RemoteException {
        engine.setUID(client);
    }
}
