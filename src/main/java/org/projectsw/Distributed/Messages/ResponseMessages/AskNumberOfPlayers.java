package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.GameView;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

public class AskNumberOfPlayers extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public AskNumberOfPlayers(GameView model) {
        super(model);
    }

    @Override
    public void execute(TextualUI tui){
        tui.askNumber();
    }
}
