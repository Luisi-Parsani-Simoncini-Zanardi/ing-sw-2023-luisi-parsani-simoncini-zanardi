package org.projectsw.Distributed.Messages;

import org.projectsw.Model.Enums.GameEvent;
import org.projectsw.Model.GameView;
import org.projectsw.View.GraphicalUI;
import org.projectsw.View.TextualUI;

public abstract class ResponseMessage {
    private final GameView model;
    private GraphicalUI gui;
    private GameEvent event;

    public ResponseMessage(GameView model){
        this.model=model;
    }
    public void executeTui(TextualUI tui){}
    public void executeGui(GraphicalUI gui){}
}
