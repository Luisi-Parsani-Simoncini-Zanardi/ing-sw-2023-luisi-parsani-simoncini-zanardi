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
        String sender = input.getClientNickname();
        String payload;
        String scope;
        String []parts = input.getString().split("/");
        if(parts.length == 1){
            payload = input.getString();
            scope = Config.everyone;
        }else if(parts.length == 2){
            payload = parts[1];
            scope = parts[0];
        }else{
            payload = Config.error;
            scope = Config.error;
        }
        engine.sayInChat(sender,payload,scope,input.getAlphanumericID());
    }
}
