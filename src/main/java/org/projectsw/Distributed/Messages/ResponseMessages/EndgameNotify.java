package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.Enums.UIEndState;
import org.projectsw.View.GraphicalUI.GuiManager;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a response message indicating an end game response notify.
 * Extends the ResponseMessage class and implements the Serializable interface.
 */
public class EndgameNotify extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new AckConnection object with the specified SerializableGame.
     * @param model the EndgameNotify object representing the response message
     */
    public EndgameNotify(SerializableGame model) {
        super(model);
    }

    /**
     * Executes the EndgameNotify message on the specified TextualUI.
     * Sets the end state of the TextualUI to indicate that the UI is ENDING.
     * @param tui the TextualUI on which to execute the action
     */
    public void execute(TextualUI tui){
        tui.setEndState(UIEndState.ENDING);
    }
    public void execute(GuiManager gui){}
}