package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

public class optionChoosed extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public optionChoosed(SerializableGame model) {
        super(model);
    }

    public void execute(TextualUI tui){
        tui.setStillChoosing(false);
        tui.setLoadFromFile(model.getBool());
        tui.setReturnedFlag(true);
        synchronized (tui) {
            tui.notifyAll();
        }
    }
}
