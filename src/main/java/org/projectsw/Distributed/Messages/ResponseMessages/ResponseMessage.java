package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.GraphicalUI.GuiManager;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

public abstract class ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    protected final SerializableGame model;

    public ResponseMessage(SerializableGame model){
        this.model=model;
    }
    public SerializableGame getModel(){
        return model;
    }
    public void execute(TextualUI tui){}
    public void execute(GuiManager gui){}
}
