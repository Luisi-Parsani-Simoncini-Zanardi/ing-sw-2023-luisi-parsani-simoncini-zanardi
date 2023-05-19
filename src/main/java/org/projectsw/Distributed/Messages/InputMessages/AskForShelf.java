package org.projectsw.Distributed.Messages.InputMessages;

import org.projectsw.Controller.Engine;
import org.projectsw.Model.InputController;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

public class AskForShelf extends InputMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public AskForShelf(InputController input) {
        super(input);
    }

    @Override
    public void execute(Engine engine) throws RemoteException {
        engine.shelfTransfer(input.getClientID());
    }
}
