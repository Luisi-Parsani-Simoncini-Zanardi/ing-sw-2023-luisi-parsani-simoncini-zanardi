package org.projectsw.Distributed.Messages.InputMessages;

import org.projectsw.Controller.Engine;
import org.projectsw.View.SerializableInput;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents an input message indicating an observer deletion request.
 * Extends the InputMessage class and implements the Serializable interface.
 */
public class DeleteModelObserver extends InputMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new DeleteModelObserver object with the specified SerializableInput.
     * @param input the SerializableInput object representing the input message
     */
    public DeleteModelObserver(SerializableInput input) {
        super(input);
    }

    /**
     * Executes the removeObserver method on the provided Engine object.
     * @param engine the Engine object on which to perform the reconnection check
     */
    @Override
    public void execute(Engine engine){
        engine.removeObserver(input.getAlphanumericID(), 1);
    }
}
