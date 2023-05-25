package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.Util.Config;
import org.projectsw.View.ConsoleColors;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class ChatMessage extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public ChatMessage(SerializableGame model) {
        super(model);
    }
    @Override
    public void execute(TextualUI tui){
        if(model.getMessage().getScope().equals(Config.error))
            System.out.println(model.getMessage().getPayload());
        else if(model.getMessage().getScope().equals(Config.everyone)) {
            if (!Objects.equals(model.getMessage().getSender(), tui.getNickname()))
                tui.addBufferMessage(model.getMessage());
        } else if(model.getMessage().getScope().equals(tui.getNickname()))
            tui.addBufferMessage(model.getMessage());
    }
}
