package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.ConsoleColors;
import org.projectsw.View.Enums.UITurnState;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

public class ErrorEmptyTemporaryTiles extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public ErrorEmptyTemporaryTiles(SerializableGame model) {
        super(model);
    }
    @Override
    public void execute(TextualUI tui){
        System.err.println("You don't have any tiles selected. Please select any tile...");
        tui.setTurnState(UITurnState.YOUR_TURN_SELECTION);
    }
}
