package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.GraphicalUI.GuiManager;
import org.projectsw.View.TextualUI;
import java.io.Serial;
import java.io.Serializable;

public class AckConnection extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public AckConnection(SerializableGame model) {
        super(model);
    }
    @Override
    public void execute(TextualUI tui){
        tui.setConnectFlag(false);
    }

    @Override
    public void execute(GuiManager guiManager){
        guiManager.notifyConnection();
    }
}
