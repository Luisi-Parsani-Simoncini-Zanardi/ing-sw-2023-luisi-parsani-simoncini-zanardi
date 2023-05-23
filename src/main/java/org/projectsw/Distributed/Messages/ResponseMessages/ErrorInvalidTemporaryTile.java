package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.ConsoleColors;
import org.projectsw.View.Enums.UITurnState;
import org.projectsw.View.GraphicalUI;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

public class ErrorInvalidTemporaryTile extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public ErrorInvalidTemporaryTile(SerializableGame model) {
        super(model);
    }
    public void execute(TextualUI tui){
        System.out.println(ConsoleColors.RED + "You don't have this tile. Try again..." + ConsoleColors.RESET);
        tui.setTurnState(UITurnState.YOUR_TURN_INSERTION);
    }
    public void execute(GraphicalUI gui){}
}

