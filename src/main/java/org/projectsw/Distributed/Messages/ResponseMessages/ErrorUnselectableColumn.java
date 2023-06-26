package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.ConsoleColors;
import org.projectsw.View.Enums.UITurnState;
import org.projectsw.View.GraphicalUI;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a response message indicating a column selection error.
 * Extends the ResponseMessage class and implements the Serializable interface.
 */
public class ErrorUnselectableColumn extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ErrorUnselectableColumn object with the specified SerializableGame.
     * @param model the SerializableGame object representing the response message
     */
    public ErrorUnselectableColumn(SerializableGame model) {
        super(model);
    }

    /**
     * Executes the ErrorUnselectableColumn message on the specified TextualUI.
     * Sets the end state of the TextualUI to indicate that the UI is YOUR_TURN_COLUMN.
     * @param tui the TextualUI on which to execute the action
     */
    public void execute(TextualUI tui){
        System.err.println("Invalid Column. Try again...");
        tui.setTurnState(UITurnState.YOUR_TURN_COLUMN);
    }
    public void execute(GraphicalUI gui){}
}
