package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a response message indicating a common goal response.
 * Extends the ResponseMessage class and implements the Serializable interface.
 */
public class SendCommonGoals extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new SendCommonGoals object with the specified SerializableGame.
     * @param model the SerializableGame object representing the response message
     */
    public SendCommonGoals(SerializableGame model) {
        super(model);
    }

    /**
     * Executes the showCommonGoals method on the provided TextualUI object.
     * @param tui the TextualUI on which to execute the message
     */
    @Override
    public void execute(TextualUI tui){
        tui.showCommonGoals(model);
    }
}