package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a response message indicating a game found response.
 * Extends the ResponseMessage class and implements the Serializable interface.
 */
public class gameFound extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new gameFound object with the specified SerializableGame.
     * @param model the SerializableGame object representing the response message
     */
    public gameFound(SerializableGame model) {
        super(model);
    }

    /**
     * Executes the gameFound message on the specified TextualUI.
     * Sets the flags in the TextualUI to update its state accordingly.
     * @param tui the TextualUI on which to execute the action
     */
    @Override
    public void execute(TextualUI tui){
        tui.setPreviousGameExist(true);
    }
}
