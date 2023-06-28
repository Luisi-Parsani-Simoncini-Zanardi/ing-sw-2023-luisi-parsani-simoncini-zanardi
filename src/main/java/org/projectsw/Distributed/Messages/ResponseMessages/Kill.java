package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.Enums.UIEndState;
import org.projectsw.View.Enums.UITurnState;
import org.projectsw.View.GraphicalUI.GuiManager;
import org.projectsw.View.TextualUI;
import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Represents a response message indicating a kill response.
 * Extends the ResponseMessage class and implements the Serializable interface.
 */
public class Kill extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new Kill object with the specified SerializableGame.
     * @param model the SerializableGame object representing the response message
     */
    public Kill(SerializableGame model) {
        super(model);
    }

    /**
     * Executes the kill method on the provided TextualUI object.
     * @param tui the TextualUI on which to execute the message
     */
    @Override
    public void execute(TextualUI tui) {
        tui.kill(model.getInteger());
        System.exit(0);
    }

    @Override
    public void execute(GuiManager guiManager) {
        guiManager.kill(0);
    }
}