package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.GraphicalUI;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

public class ClientSendNumberOfPlayers extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public ClientSendNumberOfPlayers(SerializableGame model) {
        super(model);
    }
    public void execute(TextualUI tui){
        tui.setNumberOfPlayers(model.getInteger());
    }
    public void execute(GraphicalUI gui){}
}
