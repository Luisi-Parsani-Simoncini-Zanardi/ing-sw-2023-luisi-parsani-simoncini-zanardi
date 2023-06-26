package org.projectsw.Distributed.Messages.InputMessages;

import org.projectsw.Controller.Engine;
import org.projectsw.Util.Config;
import org.projectsw.View.SerializableInput;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Represents an input message indicating a chat writing request.
 * Extends the InputMessage class and implements the Serializable interface.
 */
public class ChatMessage extends InputMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ChatMessage object with the specified SerializableInput.
     * @param input the SerializableInput object representing the input message
     */
    public ChatMessage(SerializableInput input) {
        super(input);
    }

    /**
     * Executes the sayInChat method on the provided Engine object.
     * @param engine the Engine object on which to perform the chat writing
     */
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
