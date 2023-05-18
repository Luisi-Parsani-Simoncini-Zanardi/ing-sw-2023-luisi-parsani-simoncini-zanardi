package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.Enums.GameEvent;
import org.projectsw.Model.GameView;
import org.projectsw.View.GraphicalUI;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

public abstract class ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    protected final GameView model;

    public ResponseMessage(GameView model){
        this.model=model;
    }
    public GameView getModel(){
        return model;
    }
    public void execute(TextualUI tui){}
    public void execute(GraphicalUI gui){}
}
