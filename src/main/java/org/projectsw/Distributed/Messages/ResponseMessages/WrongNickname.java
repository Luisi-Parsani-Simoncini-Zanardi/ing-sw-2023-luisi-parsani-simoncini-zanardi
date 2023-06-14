package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.GraphicalUI.GuiManager;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

public class WrongNickname extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public WrongNickname(SerializableGame model) {
        super(model);
    }
    @Override
    public void execute(TextualUI tui){
        System.err.println("Invalid nickname!!!");
        tui.setReturnedFlag(true);
        synchronized (tui) {
            tui.notifyAll();
        }
    }

    @Override
    public void execute(GuiManager guiManager) {
        guiManager.notifyResponse2();
    }
}
