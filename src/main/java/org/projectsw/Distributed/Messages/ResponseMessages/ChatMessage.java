package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.ConsoleColors;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

public class ChatMessage extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public ChatMessage(SerializableGame model) {
        super(model);
    }
    @Override
    public void execute(TextualUI tui){
        if(model.getMessage().getScope().equals("error"))
            System.out.println(model.getMessage().getPayload());
        else if(model.getMessage().getScope().equals("everyone"))
            System.out.println(tui.getNameColors().get(model.getMessage().getSender())+model.getMessage().getSender()+": "+ ConsoleColors.RESET+model.getMessage().getPayload());
        else if(model.getMessage().getScope().equals(tui.getNickname()))
            System.out.println(tui.getNameColors().get(model.getMessage().getSender())+model.getMessage().getSender()+": "+ConsoleColors.RESET+model.getMessage().getPayload());
    }
}
