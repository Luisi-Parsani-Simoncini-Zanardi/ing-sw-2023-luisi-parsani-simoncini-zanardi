package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.GameView;
import org.projectsw.View.Enums.UIEndState;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

public class CurrentPlayer extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public CurrentPlayer(GameView model) {
        super(model);
    }
    @Override
    public void execute(TextualUI tui){
        if (tui.getEndState() == UIEndState.LOBBY)
            System.out.println("Game started! \n");
        tui.showCurrentPlayer(model);
    }
}
