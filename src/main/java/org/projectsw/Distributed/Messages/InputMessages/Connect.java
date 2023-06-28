package org.projectsw.Distributed.Messages.InputMessages;

import org.projectsw.Controller.Engine;
import org.projectsw.View.SerializableInput;
import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Represents an input message indicating a connection request.
 * Extends the InputMessage class and implements the Serializable interface.
 */
public class Connect extends InputMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new Connect object with the specified SerializableInput.
     * @param input the SerializableInput object representing the input message
     */
    public Connect(SerializableInput input) {
        super(input);
    }

    /**
     * Executes the Connect method on the provided Engine object.
     * @param engine the Engine object on which to perform the connection
     */
    @Override
    public synchronized void execute(Engine engine){
        try {
            engine.Connect(input.getAlphanumericID());
        } catch (RemoteException e) {
            throw new RuntimeException("Network error while initializing game: "+e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
