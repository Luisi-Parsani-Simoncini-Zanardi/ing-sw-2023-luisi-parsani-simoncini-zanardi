package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.Enums.GameEvent;
import org.projectsw.Model.GameView;
import org.projectsw.View.GraphicalUI;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

public class NameColors extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public NameColors(GameView model) {
        super(model);
    }
    public void execute(TextualUI tui){
        tui.setNameColors(model.getNameColors());
    }
    public void execute(GraphicalUI gui){}
}