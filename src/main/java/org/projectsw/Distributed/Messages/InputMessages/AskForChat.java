package org.projectsw.Distributed.Messages.InputMessages;

import org.projectsw.Controller.Engine;
import org.projectsw.View.SerializableInput;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Represents an input message indicating a chat request.
 * Extends the InputMessage class and implements the Serializable interface.
 */
public class AskForChat extends InputMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new AskForChat object with the specified SerializableInput.
     * @param input the SerializableInput object representing the input message
     */
    public AskForChat(SerializableInput input) {
        super(input);
    }

    /**
     * Executes the sendChat method on the provided Engine object.
     * @param engine the Engine object on which to perform the chat request
     */
    @Override
    public void execute(Engine engine){
        engine.sendChat(input.getString(),input.getAlphanumericID());
    }
}
