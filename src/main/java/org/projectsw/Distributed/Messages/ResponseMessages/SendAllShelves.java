package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.GraphicalUI;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a response message indicating an all shelves response.
 * Extends the ResponseMessage class and implements the Serializable interface.
 */
public class SendAllShelves extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new SendAllShelves object with the specified SerializableGame.
     * @param model the SerializableGame object representing the response message
     */
    public SendAllShelves(SerializableGame model) {
        super(model);
    }

    /**
     * Executes the showAllShelves method on the provided TextualUI object.
     * @param tui the TextualUI on which to execute the message
     */
    public void execute(TextualUI tui){
        tui.showAllShelves(model);
    }
    public void execute(GraphicalUI gui){}
}

