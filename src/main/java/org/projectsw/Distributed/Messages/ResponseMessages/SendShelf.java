package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.GraphicalUI.GuiManager;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

public class SendShelf extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public SendShelf(SerializableGame model) {
        super(model);
    }
    public void execute(TextualUI tui){
        tui.showShelf(model);
    }
    public void execute(GuiManager gui){gui.updateModel(model);}
}
