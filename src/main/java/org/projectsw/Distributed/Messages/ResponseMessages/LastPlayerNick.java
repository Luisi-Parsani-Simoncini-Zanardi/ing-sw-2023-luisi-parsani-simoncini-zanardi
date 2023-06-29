package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.GraphicalUI.GuiManager;
import org.projectsw.View.TextualUI;
import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a response message indicating a last player nickname response.
 * Extends the ResponseMessage class and implements the Serializable interface.
 */
public class LastPlayerNick extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new LastPlayerNick object with the specified SerializableGame.
     * @param model the SerializableGame object representing the response message
     */
    public LastPlayerNick(SerializableGame model) {
        super(model);
    }

    /**
     * Executes the LastPlayerNick message on the specified TextualUI.
     * Sets the name of the last player in the TextualUI to the client's nickname from the model.
     * @param tui the TextualUI on which to execute the action
     */
    @Override
    public void execute(TextualUI tui){
        tui.setLastPlayerName(model.getClientNickname());
    }

    /**
     * Executes the LastPlayerNick message on the specified TextualUI.
     * Sets the name of the last player in the TextualUI to the client's nickname from the model.
     * @param guiManager the GUI on which to execute the action
     */
    @Override
    public void execute(GuiManager guiManager){
        guiManager.setLastPlayerNick(model.getClientNickname());
    }
}
