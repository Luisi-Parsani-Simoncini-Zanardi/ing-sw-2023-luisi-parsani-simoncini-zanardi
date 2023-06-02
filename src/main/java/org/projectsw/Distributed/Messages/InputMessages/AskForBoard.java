package org.projectsw.Distributed.Messages.InputMessages;

import org.projectsw.Controller.Engine;
import org.projectsw.View.SerializableInput;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

public class AskForBoard extends InputMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public AskForBoard(SerializableInput input) {
        super(input);
    }

    @Override
    public void execute(Engine engine) throws RemoteException {
        try {
            engine.boardTransfer(input.getAlphanumericID());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
