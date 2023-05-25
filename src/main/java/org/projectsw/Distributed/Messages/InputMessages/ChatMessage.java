package org.projectsw.Distributed.Messages.InputMessages;

import org.projectsw.Controller.Engine;
import org.projectsw.Util.Config;
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
            sender = input.getClientNickname();
            payload = input.getString();
            scope = Config.everyone;
        }else if(parts.length == 2){
            sender = input.getClientNickname();
            payload = parts[1];
            scope = parts[0];
        }else{
            sender = input.getClientNickname();
            payload = Config.error;
            scope = Config.error;
        }
        engine.sayInChat(sender,payload,scope);
    }
}
