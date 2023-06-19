package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.ConsoleColors;
import org.projectsw.View.Enums.UITurnState;
import org.projectsw.View.GraphicalUI.GuiManager;
import org.projectsw.View.GraphicalUI.MessagesGUI.UnselectableTileMessage;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

public class ErrorUnselectableTile extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public ErrorUnselectableTile(SerializableGame model) {
        super(model);
    }
    @Override
    public void execute(TextualUI tui) {
        System.err.println("Invalid Tile. Try again...");
        tui.setTurnState(UITurnState.YOUR_TURN_SELECTION);
    }

    @Override
    public void execute(GuiManager gui) {
        gui.setSelectionAccepted(false);
        gui.setTurnState(UITurnState.YOUR_TURN_SELECTION);
    }
}
