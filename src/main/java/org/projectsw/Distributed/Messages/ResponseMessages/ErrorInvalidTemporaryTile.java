package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Distributed.Messages.InputMessages.ColumnSelection;
import org.projectsw.Distributed.Messages.InputMessages.TemporaryTileSelection;
import org.projectsw.Model.GameView;
import org.projectsw.Model.InputController;
import org.projectsw.View.ConsoleColors;
import org.projectsw.View.Enums.UIEvent;
import org.projectsw.View.GraphicalUI;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

public class ErrorInvalidTemporaryTile extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public ErrorInvalidTemporaryTile(GameView model) {
        super(model);
    }
    public void execute(TextualUI tui){
        System.out.println(ConsoleColors.RED + "You don't have this tile. Try again..." + ConsoleColors.RESET);
        int number = tui.selectTemporaryTile();
        try {
            tui.setChangedAndNotifyObservers(new TemporaryTileSelection(new InputController(tui.getClientUID(), number)));
        } catch (RemoteException e) {
            throw new RuntimeException("Network error while notifying a tile insertion error: "+e.getCause());
        }
    }
    public void execute(GraphicalUI gui){}
}

