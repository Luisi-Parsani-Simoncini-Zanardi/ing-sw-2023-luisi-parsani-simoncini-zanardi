package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a response message indicating an option chose response.
 * Extends the ResponseMessage class and implements the Serializable interface.
 */
public class optionChoosed extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new optionChoosed object with the specified SerializableGame.
     * @param model the SerializableGame object representing the response message
     */
    public optionChoosed(SerializableGame model) {
        super(model);
    }

    /**
     * Executes the optionChoosed message on the specified TextualUI.
     * Sets the flags in the TextualUI to update its state accordingly.
     * @param tui the TextualUI on which to execute the action
     */
    public void execute(TextualUI tui){
        tui.setStillChoosing(false);
        tui.setLoadFromFile(model.getBool());
        tui.setReturnedFlag(true);
        synchronized (tui) {
            tui.notifyAll();
        }
    }
}
