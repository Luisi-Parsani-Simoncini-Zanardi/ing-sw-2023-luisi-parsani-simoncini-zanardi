package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.Enums.GameEvent;
import org.projectsw.Model.GameView;
import org.projectsw.View.GraphicalUI;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

public class UpdatedAllShelves extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public UpdatedAllShelves(GameView model) {
        super(model);
    }
    public void execute(TextualUI tui){
        tui.showAllShelves(model);
    }
    public void execute(GraphicalUI gui){}
}

