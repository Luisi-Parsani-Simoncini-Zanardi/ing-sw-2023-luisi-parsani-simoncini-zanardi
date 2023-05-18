package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Distributed.Messages.InputMessages.ColumnSelection;
import org.projectsw.Model.GameView;
import org.projectsw.Model.InputController;
import org.projectsw.View.ConsoleColors;
import org.projectsw.View.Enums.UIEvent;
import org.projectsw.View.Enums.UITurnState;
import org.projectsw.View.GraphicalUI;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

public class ErrorUnselectableColumn extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public ErrorUnselectableColumn(GameView model) {
        super(model);
    }
    public void execute(TextualUI tui){
        System.out.println(ConsoleColors.RED + "Invalid Column. Try again..." + ConsoleColors.RESET);
        tui.setTurnState(UITurnState.YOUR_TURN_PHASE2);
    }
    public void execute(GraphicalUI gui){}
}
