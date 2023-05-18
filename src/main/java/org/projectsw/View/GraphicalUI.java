package org.projectsw.View;

import org.projectsw.Distributed.Messages.InputMessages.InputMessage;
import org.projectsw.Distributed.Messages.ResponseMessages.ResponseMessage;
import org.projectsw.Model.Enums.GameEvent;
import org.projectsw.Model.GameView;
import org.projectsw.Util.Observable;
import org.projectsw.View.Enums.UIEvent;
import org.projectsw.View.Enums.UITurnState;

public class GraphicalUI extends Observable<InputMessage> implements Runnable{

    private UITurnState state;

    public void setState(UITurnState state){this.state = state;}
    public void update(ResponseMessage response){}

    @Override
    public void run() {

    }
}
