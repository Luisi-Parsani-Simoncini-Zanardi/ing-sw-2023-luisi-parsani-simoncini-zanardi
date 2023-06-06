package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

public class AskNumberOfPlayers extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public AskNumberOfPlayers(SerializableGame model) {
        super(model);
    }
    @Override
    public void execute(TextualUI tui){
        tui.setFirstPlayerFlag(true);
        tui.setPreviousGameExist(false);
        tui.setNickFlag(false);
    }
}
