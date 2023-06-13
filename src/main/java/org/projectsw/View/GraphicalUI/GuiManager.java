package org.projectsw.View.GraphicalUI;

import org.projectsw.Distributed.Client;
import org.projectsw.Distributed.Messages.InputMessages.InputMessage;
import org.projectsw.Distributed.Messages.ResponseMessages.ResponseMessage;
import org.projectsw.Util.Config;
import org.projectsw.Util.Observable;
import org.projectsw.Util.RandomAlphanumericGen;
import org.projectsw.View.Enums.UIEndState;
import org.projectsw.View.Enums.UITurnState;

public class GuiManager extends Observable<InputMessage> implements Runnable{

    private UITurnState turnState = UITurnState.OPPONENT_TURN;
    private UIEndState endState = UIEndState.LOBBY;
    private final Client client;
    private String alphanumericKey;

    public GuiManager(Client client){
        RandomAlphanumericGen gen = new RandomAlphanumericGen();
        alphanumericKey = gen.generateRandomString(100);
        this.client = client;
    }

    public void setState(UITurnState turnState){this.turnState = turnState;}

    public void setEndState(UIEndState endState) {this.endState = endState;}

    public void update(ResponseMessage response){
        if(response.getModel().getAlphanumericID().equals(this.alphanumericKey)||response.getModel().getAlphanumericID().equals(Config.broadcastID))
            response.execute(this);
    }


    @Override
    public void run() {

    }
}
