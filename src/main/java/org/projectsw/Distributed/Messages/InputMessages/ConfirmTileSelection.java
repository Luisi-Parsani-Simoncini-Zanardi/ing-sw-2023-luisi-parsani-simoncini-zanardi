package org.projectsw.Distributed.Messages.InputMessages;

import org.projectsw.Controller.Engine;
import org.projectsw.View.SerializableInput;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;


/**
 * Represents an input message indicating a tile selection request.
 * Extends the InputMessage class and implements the Serializable interface.
 */
public class ConfirmTileSelection extends InputMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ConfirmTileSelection object with the specified SerializableInput.
     * @param input the SerializableInput object representing the input message
     */
    public ConfirmTileSelection(SerializableInput input) {
        super(input);
    }

    /**
     * Executes the selectTiles method on the provided Engine object.
     * @param engine the Engine object on which to perform the reconnection check
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public synchronized void execute(Engine engine) throws RemoteException {
        engine.selectTiles(input.getAlphanumericID(),input.getCoordinate());
    }
}
