package org.projectsw.View.GraphicalUI;

import org.projectsw.Distributed.Messages.InputMessages.InputMessage;
import org.projectsw.Distributed.Messages.ResponseMessages.ResponseMessage;
import org.projectsw.Util.Observable;
import org.projectsw.View.Enums.UITurnState;

public class GuiManager extends Observable<InputMessage> implements Runnable{

    private UITurnState state;

    public void setState(UITurnState state){this.state = state;}
    public void update(ResponseMessage response){}

    @Override
    public void run() {

    }
}
