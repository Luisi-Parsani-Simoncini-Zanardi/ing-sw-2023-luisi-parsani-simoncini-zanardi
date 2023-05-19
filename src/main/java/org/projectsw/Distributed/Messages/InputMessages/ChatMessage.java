package org.projectsw.Distributed.Messages.InputMessages;

import org.projectsw.Controller.Engine;
import org.projectsw.View.SerializableInput;

import java.io.Serial;
import java.io.Serializable;

public class ChatMessage extends InputMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public ChatMessage(SerializableInput input) {
        super(input);
    }
    @Override
    public void execute(Engine engine){
        String sender;
        String payload;
        String scope;
        String []parts = input.getString().split("/");
        if(parts.length == 1){
            sender = input.getNickname();
            payload = input.getString();
            scope = "everyone";
        }else{
            sender = input.getNickname();
            payload = parts[1];
            scope = parts[0];
        }
        engine.sayInChat(sender,payload,scope);
    }
}
