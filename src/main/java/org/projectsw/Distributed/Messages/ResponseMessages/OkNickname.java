package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.Enums.UIEndState;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

public class OkNickname extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public OkNickname(SerializableGame model) {
        super(model);
    }
    @Override
    public void execute(TextualUI tui){
        tui.setNickFlag(false);
        tui.setReturnedFlag(true);
        synchronized (tui) {
            tui.notifyAll();
        }
    }
}
