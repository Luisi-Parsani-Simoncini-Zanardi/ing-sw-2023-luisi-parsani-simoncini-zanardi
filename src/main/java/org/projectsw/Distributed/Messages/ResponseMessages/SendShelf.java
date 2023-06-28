package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.GraphicalUI.GuiManager;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a response message indicating a shelf response.
 * Extends the ResponseMessage class and implements the Serializable interface.
 */
public class SendShelf extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new SendShelf object with the specified SerializableGame.
     * @param model the SerializableGame object representing the response message
     */
    public SendShelf(SerializableGame model) {
        super(model);
    }

    /**
     * Executes the showShelf method on the provided TextualUI object.
     * @param tui the TextualUI on which to execute the message
     */
    public void execute(TextualUI tui){
        tui.showShelf(model);
    }
    public void execute(GuiManager gui){gui.updateModel(model);}
}
