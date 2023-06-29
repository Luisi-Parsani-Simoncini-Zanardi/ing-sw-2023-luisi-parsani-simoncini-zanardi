package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.Util.Config;
import org.projectsw.View.GraphicalUI.GuiManager;
import org.projectsw.View.GraphicalUI.MessagesGUI.YouAreAloneMessage;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a response message indicating an error.
 * Extends the ResponseMessage class and implements the Serializable interface.
 */
public class ErrorMessage extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ErrorMessage object with the specified SerializableGame.
     * @param model the SerializableGame object representing the response message
     */
    public ErrorMessage(SerializableGame model) {
        super(model);
    }

    /**
     * send error message on the provided TextualUI object.
     * @param tui the TextualUI on which to execute the message
     */
    @Override
    public void execute(TextualUI tui) {
        System.err.println(model.getClientNickname());
    }

    @Override
    public void execute(GuiManager gui) {
        new YouAreAloneMessage();
    }
}
