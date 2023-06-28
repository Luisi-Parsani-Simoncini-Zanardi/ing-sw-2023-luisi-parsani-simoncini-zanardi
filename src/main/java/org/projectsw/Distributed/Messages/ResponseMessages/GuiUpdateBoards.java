package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.GraphicalUI.GuiManager;
import java.io.Serial;
import java.io.Serializable;

public class GuiUpdateBoards extends ResponseMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    public GuiUpdateBoards(SerializableGame model) {
        super(model);
    }

    @Override
    public void execute(GuiManager gui) {
        gui.updateModel(model);
        //gui.updateBoard();
    }
}
