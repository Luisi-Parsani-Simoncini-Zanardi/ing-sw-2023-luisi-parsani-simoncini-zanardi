package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.Enums.UITurnState;
import org.projectsw.View.GraphicalUI.GuiManager;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

public class ErrorUnselectableColumn extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public ErrorUnselectableColumn(SerializableGame model) {
        super(model);
    }
    public void execute(TextualUI tui){
        System.err.println("Invalid Column. Try again...");
        tui.setTurnState(UITurnState.YOUR_TURN_COLUMN);
    }
    public void execute(GuiManager gui){}
}
