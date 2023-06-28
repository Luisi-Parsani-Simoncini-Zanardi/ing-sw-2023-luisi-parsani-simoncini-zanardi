package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.Enums.UITurnState;
import org.projectsw.View.GraphicalUI.GuiManager;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a response message indicating a tile selection error.
 * Extends the ResponseMessage class and implements the Serializable interface.
 */
public class ErrorUnselectableTile extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ErrorUnselectableTile object with the specified SerializableGame.
     * @param model the SerializableGame object representing the response message
     */
    public ErrorUnselectableTile(SerializableGame model) {
        super(model);
    }

    /**
     * Executes the ErrorUnselectableTile message on the specified TextualUI.
     * Sets the end state of the TextualUI to indicate that the UI is YOUR_TURN_SELECTION.
     * @param tui the TextualUI on which to execute the action
     */
    @Override
    public void execute(TextualUI tui) {
        System.err.println("Invalid Tile. Try again...");
        tui.setTurnState(UITurnState.YOUR_TURN_SELECTION);
    }

    //TODO javadoc gui
    @Override
    public void execute(GuiManager gui) {
        gui.setTileSelectionAccepted(false);
    }
}
