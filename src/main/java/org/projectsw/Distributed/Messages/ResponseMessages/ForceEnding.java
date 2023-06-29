package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.GraphicalUI.GuiManager;
import org.projectsw.View.TextualUI;
import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a response message indicating a forced ending response.
 * Extends the ResponseMessage class and implements the Serializable interface.
 */
public class ForceEnding extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ForceEnding object with the specified SerializableGame.
     * @param model the SerializableGame object representing the response message
     */
    public ForceEnding(SerializableGame model) {
        super(model);
    }

    /**
     * Executes the ForceEnding message on the specified TextualUI.
     * Sets the flags in the TextualUI to update its state accordingly.
     * @param tui the TextualUI on which to execute the action
     */
    @Override
    public void execute(TextualUI tui){
        tui.setFlag(false);
        tui.setWaitResult(false);
        System.err.println("The game is ending, press a key to go to results...");
    }

    /**
     * Executes the ForceEnding message on the specified GraphicalUI.
     * Sets the flag stillPlaying in the GraphicalUI's GameMainFrame.
     * @param guiManager the GraphicalUI on which to execute the action
     */
    @Override
    public void execute(GuiManager guiManager) {
        guiManager.getGameMainFrame().setStillPlaying(false);
    }
}
