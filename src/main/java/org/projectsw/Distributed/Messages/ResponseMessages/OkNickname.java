package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.Enums.UIEndState;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a response message indicating a valid nickname response.
 * Extends the ResponseMessage class and implements the Serializable interface.
 */
public class OkNickname extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new OkNickname object with the specified SerializableGame.
     * @param model the SerializableGame object representing the response message
     */
    public OkNickname(SerializableGame model) {
        super(model);
    }

    /**
     * Executes the OkNickname message on the specified TextualUI.
     * Sets the flags in the TextualUI to update its state accordingly.
     * @param tui the TextualUI on which to execute the action
     */
    @Override
    public void execute(TextualUI tui){
        tui.setNickFlag(false);
        tui.setReturnedFlag(true);
        synchronized (tui) {
            tui.notifyAll();
        }
    }
}
