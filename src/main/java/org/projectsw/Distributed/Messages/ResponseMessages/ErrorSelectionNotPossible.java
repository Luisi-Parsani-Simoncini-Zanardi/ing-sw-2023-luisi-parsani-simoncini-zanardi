package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.GraphicalUI.GuiManager;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a response message indicating a tile selection error.
 * Extends the ResponseMessage class and implements the Serializable interface.
 */
public class ErrorSelectionNotPossible extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ErrorSelectionNotPossible object with the specified SerializableGame.
     * @param model the SerializableGame object representing the response message
     */
    public ErrorSelectionNotPossible(SerializableGame model) {
        super(model);
    }


    /**
     * Executes the ErrorSelectionNotPossible message on the specified TextualUI.
     * Sets the flags in the TextualUI to update its state accordingly.
     * @param tui the TextualUI on which to execute the action
     */
    @Override
    public void execute(TextualUI tui){
        tui.setTileSelectionPossible(false);
    }

    //TODO javadoc gui
    @Override
    public void execute(GuiManager gui) {
        gui.setTileSelectionPossible(false);
    }

}
