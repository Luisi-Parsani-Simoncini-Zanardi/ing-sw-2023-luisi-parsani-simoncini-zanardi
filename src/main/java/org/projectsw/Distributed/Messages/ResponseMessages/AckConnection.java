package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a response message indicating a connection response.
 * Extends the ResponseMessage class and implements the Serializable interface.
 */
public class AckConnection extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new AckConnection object with the specified SerializableGame.
     * @param model the SerializableGame object representing the response message
     */
    public AckConnection(SerializableGame model) {
        super(model);
    }

    /**
     * Executes the AckConnection message on the specified TextualUI.
     * Sets the connection flag to false and updates the flag indicating if the model is still being chosen.
     * @param tui the TextualUI on which to execute the message
     */
    @Override
    public void execute(TextualUI tui){
        tui.setConnectFlag(false);
        tui.setStillChoosing(!model.getBool());
    }
}
