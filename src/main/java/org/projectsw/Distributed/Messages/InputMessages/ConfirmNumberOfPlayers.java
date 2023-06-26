package org.projectsw.Distributed.Messages.InputMessages;

import org.projectsw.Controller.Engine;
import org.projectsw.View.SerializableInput;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents an input message indicating a number of player selection request.
 * Extends the InputMessage class and implements the Serializable interface.
 */
public class ConfirmNumberOfPlayers extends InputMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ConfirmNumberOfPlayers object with the specified SerializableInput.
     * @param input the SerializableInput object representing the input message
     */
    public ConfirmNumberOfPlayers(SerializableInput input) {
        super(input);
    }

    /**
     * Executes the setNumberOfPlayers method on the provided Engine object.
     * @param engine the Engine object on which to perform the player selection
     */
    @Override
    public void execute(Engine engine){

        engine.setNumberOfPlayers(input.getInteger(), input.getAlphanumericID());
    }
}
