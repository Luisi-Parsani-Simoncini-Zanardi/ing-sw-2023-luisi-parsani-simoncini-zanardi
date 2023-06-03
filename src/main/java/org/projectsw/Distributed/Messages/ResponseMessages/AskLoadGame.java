package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

public class AskLoadGame extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public AskLoadGame(SerializableGame model) {
        super(model);
    }
    @Override
    public void execute(TextualUI tui){
        tui.setNickFlag(false);
        tui.setFirstPlayerFlag(true);
        tui.setPreviousGameExist(true);
        tui.setConnectFlag(false);
    }
}
