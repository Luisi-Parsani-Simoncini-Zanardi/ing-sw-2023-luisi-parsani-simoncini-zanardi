package org.projectsw.Distributed.Messages.InputMessages;
import org.projectsw.Controller.Engine;
import org.projectsw.Distributed.Client;
import org.projectsw.View.SerializableInput;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Represents an input message indicating a load game request.
 * Extends the InputMessage class and implements the Serializable interface.
 */
public class LoadGameSelection extends InputMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new LoadGameSelection object with the specified SerializableInput.
     * @param input the SerializableInput object representing the input message
     */
    public LoadGameSelection(SerializableInput input) {
        super(input);
    }

    /**
     * Executes the initializeFromSave method on the provided Engine object.
     * @param engine the Engine object on which to perform the load game request
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public void execute(Engine engine) throws RemoteException {engine.initializeFromSave(input.getAlphanumericID());}
}
