package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.GraphicalUI.GuiManager;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a response message indicating a name colors response.
 * Extends the ResponseMessage class and implements the Serializable interface.
 */
public class SendNameColors extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new SendNameColors object with the specified SerializableGame.
     * @param model the SerializableGame object representing the response message
     */
    public SendNameColors(SerializableGame model) {
        super(model);
    }

    /**
     * Executes the setNameColors method on the provided TextualUI object.
     * @param tui the TextualUI on which to execute the message
     */
    public void execute(TextualUI tui){
        tui.setNameColors(model.getNameColors());
    }
    public void execute(GuiManager gui){}
}