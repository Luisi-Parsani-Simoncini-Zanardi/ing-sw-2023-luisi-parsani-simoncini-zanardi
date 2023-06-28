package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.Enums.UITurnState;
import org.projectsw.View.GraphicalUI.GuiManager;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a response message indicating a temporary tiles error.
 * Extends the ResponseMessage class and implements the Serializable interface.
 */
public class ErrorInvalidTemporaryTile extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ErrorInvalidTemporaryTile object with the specified SerializableGame.
     * @param model the SerializableGame object representing the response message
     */
    public ErrorInvalidTemporaryTile(SerializableGame model) {
        super(model);
    }

    /**
     * Executes the ErrorInvalidTemporaryTile message on the specified TextualUI.
     * Sets the end state of the TextualUI to indicate that the UI is YOUR_TURN_INSERTION.
     * @param tui the TextualUI on which to execute the action
     */
    public void execute(TextualUI tui){
        System.err.println("You don't have this tile. Try again...");
        tui.setTurnState(UITurnState.YOUR_TURN_INSERTION);
    }
    public void execute(GuiManager gui){}
}

