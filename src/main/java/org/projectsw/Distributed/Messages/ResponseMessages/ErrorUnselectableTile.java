package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.GameView;
import org.projectsw.View.ConsoleColors;
import org.projectsw.View.Enums.UITurnState;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

public class ErrorUnselectableTile extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public ErrorUnselectableTile(GameView model) {
        super(model);
    }
    @Override
    public void execute(TextualUI tui) {
        System.out.println(ConsoleColors.RED + "Invalid Tile. Try again..." + ConsoleColors.RESET);
        tui.setTurnState(UITurnState.YOUR_TURN_SELECTION);
    }
}
