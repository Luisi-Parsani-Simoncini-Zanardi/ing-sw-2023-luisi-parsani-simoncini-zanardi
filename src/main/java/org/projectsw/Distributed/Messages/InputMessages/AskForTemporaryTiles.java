package org.projectsw.Distributed.Messages.InputMessages;

import org.projectsw.Controller.Engine;
import org.projectsw.View.SerializableInput;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

public class AskForTemporaryTiles extends InputMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public AskForTemporaryTiles(SerializableInput input) {
        super(input);
    }

    @Override
    public void execute(Engine engine) throws RemoteException {
        try {
            engine.temporaryTilesTransfer(input.getAlphanumericID());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}