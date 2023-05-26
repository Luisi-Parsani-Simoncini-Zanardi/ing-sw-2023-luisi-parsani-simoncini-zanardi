package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.ConsoleColors;
import org.projectsw.View.Enums.UITurnState;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

public class LastPlayerNick extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public LastPlayerNick(SerializableGame model) {
        super(model);
    }
    @Override
    public void execute(TextualUI tui){
        tui.setLastPlayerName(model.getPlayerName());
    }
}
