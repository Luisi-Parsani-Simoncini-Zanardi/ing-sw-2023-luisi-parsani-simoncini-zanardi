package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.GraphicalUI.GuiManager;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

public class SendBoard extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public SendBoard(SerializableGame model) {
        super(model);
    }
    public void execute(TextualUI tui){
        tui.showBoard(model);
    }
    public void execute(GuiManager gui){
        gui.updateModel(model);
    }
}
